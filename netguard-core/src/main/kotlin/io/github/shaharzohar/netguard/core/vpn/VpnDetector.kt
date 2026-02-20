package io.github.shaharzohar.netguard.core.vpn

import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import io.github.shaharzohar.netguard.core.NetGuard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.net.NetworkInterface

/**
 * Detects and analyzes VPN connections on the device.
 *
 * Provides synchronous checks, full analysis with protocol hinting,
 * real-time monitoring, DNS leak detection, and split tunneling detection.
 *
 * ## Usage
 *
 * ```kotlin
 * val vpnDetector = VpnDetector(context)
 *
 * // Quick check
 * if (vpnDetector.isVpnActive()) {
 *     // VPN is running
 * }
 *
 * // Full analysis
 * val state = vpnDetector.detect()
 * if (state is VpnState.Connected) {
 *     println("Protocol: ${state.protocolHint}")
 * }
 *
 * // Real-time monitoring
 * vpnDetector.observeVpnState().collect { state ->
 *     println("VPN state changed: $state")
 * }
 * ```
 *
 * @since 1.1.0
 */
class VpnDetector(context: Context) {

    private val appContext = context.applicationContext
    private val connectivityManager = appContext.getSystemService<ConnectivityManager>()
        ?: throw IllegalStateException("ConnectivityManager not available")
    private val dnsLeakDetector = DnsLeakDetector(appContext)

    /**
     * Lightweight synchronous check for an active VPN connection.
     *
     * Uses [NetworkCapabilities.TRANSPORT_VPN] to determine if a VPN transport is active.
     *
     * @return true if a VPN transport is currently active
     */
    fun isVpnActive(): Boolean {
        return try {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
                ?: return false
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
        } catch (e: Exception) {
            NetGuard.logger.w(TAG, "VPN active check failed", e)
            false
        }
    }

    /**
     * Performs a full VPN analysis including protocol detection and interface inspection.
     *
     * @return [VpnState] with connection details or disconnected/error state
     */
    suspend fun detect(): VpnState = withContext(Dispatchers.IO) {
        try {
            val activeNetwork = connectivityManager.activeNetwork
                ?: return@withContext VpnState.Disconnected

            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
                ?: return@withContext VpnState.Disconnected

            if (!capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                return@withContext VpnState.Disconnected
            }

            val linkProperties = connectivityManager.getLinkProperties(activeNetwork)
            val interfaceName = linkProperties?.interfaceName ?: "unknown"
            val protocolHint = detectProtocol(interfaceName)
            val addresses = getInterfaceAddresses(interfaceName)

            VpnState.Connected(
                interfaceName = interfaceName,
                protocolHint = protocolHint,
                addresses = addresses
            )
        } catch (e: Exception) {
            NetGuard.logger.e(TAG, "VPN detection failed", e)
            VpnState.Error("VPN detection failed: ${e.message}", e)
        }
    }

    /**
     * Observes VPN state changes in real-time.
     *
     * Emits a new [VpnState] whenever a VPN connection is established or lost.
     *
     * @return Flow of VPN state changes
     */
    fun observeVpnState(): Flow<VpnState> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val caps = connectivityManager.getNetworkCapabilities(network)
                if (caps?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) == true) {
                    val linkProps = connectivityManager.getLinkProperties(network)
                    val ifName = linkProps?.interfaceName ?: "unknown"
                    trySend(
                        VpnState.Connected(
                            interfaceName = ifName,
                            protocolHint = detectProtocol(ifName),
                            addresses = getInterfaceAddresses(ifName)
                        )
                    )
                }
            }

            override fun onLost(network: Network) {
                trySend(VpnState.Disconnected)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                    val linkProps = connectivityManager.getLinkProperties(network)
                    val ifName = linkProps?.interfaceName ?: "unknown"
                    trySend(
                        VpnState.Connected(
                            interfaceName = ifName,
                            protocolHint = detectProtocol(ifName),
                            addresses = getInterfaceAddresses(ifName)
                        )
                    )
                }
            }
        }

        val request = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_VPN)
            .build()

        connectivityManager.registerNetworkCallback(request, callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Checks for DNS leaks when a VPN is active.
     *
     * Compares system DNS servers against VPN interface subnets to detect
     * DNS queries that may bypass the VPN tunnel.
     *
     * @return [DnsLeakResult] indicating leak status
     */
    suspend fun checkDnsLeak(): DnsLeakResult = dnsLeakDetector.check()

    /**
     * Detects split tunneling by inspecting VPN routing configuration.
     *
     * A split tunnel routes only certain traffic through the VPN,
     * while a full tunnel routes all traffic through it.
     *
     * @return [SplitTunnelingResult] with routing details
     */
    suspend fun detectSplitTunneling(): SplitTunnelingResult = withContext(Dispatchers.IO) {
        try {
            val activeNetwork = connectivityManager.activeNetwork
                ?: return@withContext SplitTunnelingResult.NoVpn

            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
                ?: return@withContext SplitTunnelingResult.NoVpn

            if (!capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                return@withContext SplitTunnelingResult.NoVpn
            }

            val linkProperties = connectivityManager.getLinkProperties(activeNetwork)
                ?: return@withContext SplitTunnelingResult.Error("Cannot read link properties")

            val routes = linkProperties.routes
            val hasDefaultRoute = routes.any { it.isDefaultRoute }

            if (hasDefaultRoute) {
                SplitTunnelingResult.FullTunnel
            } else {
                val vpnRoutes = routes.map { route ->
                    route.destination.toString()
                }
                // Check non-VPN networks for their routes
                val directRoutes = mutableListOf<String>()
                @Suppress("DEPRECATION")
                for (network in connectivityManager.allNetworks) {
                    val netCaps = connectivityManager.getNetworkCapabilities(network) ?: continue
                    if (netCaps.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) continue
                    val lp = connectivityManager.getLinkProperties(network) ?: continue
                    lp.routes.forEach { route ->
                        directRoutes.add(route.destination.toString())
                    }
                }

                SplitTunnelingResult.SplitTunnel(
                    vpnRoutes = vpnRoutes,
                    directRoutes = directRoutes.distinct()
                )
            }
        } catch (e: Exception) {
            NetGuard.logger.e(TAG, "Split tunneling detection failed", e)
            SplitTunnelingResult.Error("Split tunneling detection failed: ${e.message}", e)
        }
    }

    /**
     * Detect VPN protocol based on network interface name.
     */
    private fun detectProtocol(interfaceName: String): VpnProtocolHint {
        val name = interfaceName.lowercase()
        return when {
            name.startsWith("tun") -> VpnProtocolHint.OPENVPN
            name.startsWith("wg") -> VpnProtocolHint.WIREGUARD
            name.startsWith("ipsec") || name.contains("ipsec") -> VpnProtocolHint.IPSEC
            name.startsWith("ppp") -> VpnProtocolHint.PPTP
            else -> VpnProtocolHint.UNKNOWN
        }
    }

    /**
     * Get IP addresses assigned to a network interface.
     */
    private fun getInterfaceAddresses(interfaceName: String): List<String> {
        return try {
            val iface = NetworkInterface.getByName(interfaceName) ?: return emptyList()
            iface.inetAddresses.toList().mapNotNull { it.hostAddress }
        } catch (e: Exception) {
            NetGuard.logger.w(TAG, "Failed to get interface addresses for $interfaceName", e)
            emptyList()
        }
    }

    companion object {
        private const val TAG = "VpnDetector"
    }
}
