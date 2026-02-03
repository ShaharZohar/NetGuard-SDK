package io.github.shaharzohar.netguard.wifi.monitor

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.content.getSystemService
import io.github.shaharzohar.netguard.core.NetGuard
import io.github.shaharzohar.netguard.wifi.models.LatencyResult
import io.github.shaharzohar.netguard.wifi.models.WifiConnectionInfo
import io.github.shaharzohar.netguard.wifi.models.WifiScanResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import java.net.Socket

/**
 * Monitors WiFi connection state and signal quality.
 *
 * ## Usage
 *
 * ```kotlin
 * val monitor = WifiMonitor(context)
 *
 * // Observe WiFi info changes
 * monitor.observeWifiInfo().collect { info ->
 *     println("Connected to: ${info.ssid}, Signal: ${info.signalQuality}")
 * }
 *
 * // Measure network latency
 * val latency = monitor.measureLatency("8.8.8.8")
 * println("Latency: ${latency.avgMs}ms")
 * ```
 *
 * @since 1.0.0
 */
class WifiMonitor(private val context: Context) {

    private val connectivityManager = context.getSystemService<ConnectivityManager>()
        ?: throw IllegalStateException("ConnectivityManager not available")

    private val wifiManager = context.applicationContext.getSystemService<WifiManager>()

    /**
     * Observe WiFi connection info changes.
     *
     * On Android 12+, requires ACCESS_FINE_LOCATION permission for SSID/BSSID.
     */
    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun observeWifiInfo(): Flow<WifiConnectionInfo> = callbackFlow {
        val callback = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            createAndroid12Callback { info -> trySend(info) }
        } else {
            createLegacyCallback { info -> trySend(info) }
        }

        val request = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        connectivityManager.registerNetworkCallback(request, callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.flowOn(Dispatchers.IO)

    @RequiresApi(Build.VERSION_CODES.S)
    private fun createAndroid12Callback(
        onInfo: (WifiConnectionInfo) -> Unit
    ): ConnectivityManager.NetworkCallback {
        return object : ConnectivityManager.NetworkCallback(FLAG_INCLUDE_LOCATION_INFO) {
            override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
                val wifiInfo = capabilities.transportInfo as? WifiInfo ?: return
                onInfo(wifiInfo.toConnectionInfo())
            }
        }
    }

    private fun createLegacyCallback(
        onInfo: (WifiConnectionInfo) -> Unit
    ): ConnectivityManager.NetworkCallback {
        return object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
                @Suppress("DEPRECATION")
                val wifiInfo = wifiManager?.connectionInfo ?: return
                onInfo(wifiInfo.toConnectionInfo())
            }
        }
    }

    /**
     * Get current WiFi connection info.
     */
    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    suspend fun getCurrentWifiInfo(): WifiConnectionInfo? = withContext(Dispatchers.IO) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            getCurrentWifiInfoAndroid12()
        } else {
            @Suppress("DEPRECATION")
            wifiManager?.connectionInfo?.toConnectionInfo()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun getCurrentWifiInfoAndroid12(): WifiConnectionInfo? {
        val network = connectivityManager.activeNetwork ?: return null
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return null
        val wifiInfo = capabilities.transportInfo as? WifiInfo ?: return null
        return wifiInfo.toConnectionInfo()
    }

    /**
     * Observe signal strength at regular intervals.
     */
    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun observeSignalStrength(intervalMs: Long = 5000): Flow<Int> = flow {
        while (true) {
            getCurrentWifiInfo()?.rssi?.let { emit(it) }
            delay(intervalMs)
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Get available WiFi networks from scan results.
     */
    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE])
    fun getScanResults(): List<WifiScanResult> {
        @Suppress("DEPRECATION")
        return wifiManager?.scanResults?.map { result ->
            WifiScanResult(
                ssid = result.SSID,
                bssid = result.BSSID,
                rssi = result.level,
                frequencyMhz = result.frequency,
                capabilities = result.capabilities,
                timestamp = result.timestamp
            )
        } ?: emptyList()
    }

    /**
     * Measure network latency to a host.
     */
    suspend fun measureLatency(
        host: String,
        port: Int = 443,
        samples: Int = 5,
        timeoutMs: Int = 5000
    ): LatencyResult = withContext(Dispatchers.IO) {
        val measurements = mutableListOf<Long?>()

        repeat(samples) {
            measurements.add(measureSingleLatency(host, port, timeoutMs))
            delay(100)
        }

        val validMeasurements = measurements.filterNotNull()
        val packetLoss = (measurements.size - validMeasurements.size) / measurements.size.toFloat()

        LatencyResult(
            host = host,
            minMs = validMeasurements.minOrNull() ?: -1,
            maxMs = validMeasurements.maxOrNull() ?: -1,
            avgMs = if (validMeasurements.isNotEmpty()) validMeasurements.average().toLong() else -1,
            packetLoss = packetLoss,
            samples = samples
        )
    }

    private fun measureSingleLatency(host: String, port: Int, timeoutMs: Int): Long? {
        return try {
            val start = System.nanoTime()
            Socket().use { socket ->
                socket.connect(InetSocketAddress(host, port), timeoutMs)
            }
            (System.nanoTime() - start) / 1_000_000
        } catch (e: Exception) {
            NetGuard.logger.w(TAG, "Latency measurement failed for $host", e)
            null
        }
    }

    private fun WifiInfo.toConnectionInfo() = WifiConnectionInfo(
        ssid = ssid?.removeSurrounding("\""),
        bssid = bssid,
        rssi = rssi,
        linkSpeedMbps = linkSpeed,
        frequencyMhz = frequency,
        securityType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) currentSecurityType else null,
        ipAddress = null,
        macAddress = null
    )

    companion object {
        private const val TAG = "WifiMonitor"
    }
}
