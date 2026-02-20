package io.github.shaharzohar.netguard.core.vpn

import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import io.github.shaharzohar.netguard.core.NetGuard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.Inet4Address
import java.net.NetworkInterface

/**
 * Detects potential DNS leaks when a VPN is active.
 *
 * A DNS leak occurs when DNS queries are sent outside the VPN tunnel,
 * potentially exposing browsing activity to the local ISP.
 *
 * @since 1.1.0
 */
internal class DnsLeakDetector(context: Context) {

    private val connectivityManager = context.getSystemService<ConnectivityManager>()
        ?: throw IllegalStateException("ConnectivityManager not available")

    /**
     * Check for DNS leaks by comparing DNS servers against VPN interface subnets.
     */
    suspend fun check(): DnsLeakResult = withContext(Dispatchers.IO) {
        try {
            val activeNetwork = connectivityManager.activeNetwork
                ?: return@withContext DnsLeakResult.Error("No active network")

            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
                ?: return@withContext DnsLeakResult.Error("Cannot read network capabilities")

            if (!capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                return@withContext DnsLeakResult.NoVpn
            }

            val vpnLinkProperties = connectivityManager.getLinkProperties(activeNetwork)
                ?: return@withContext DnsLeakResult.Error("Cannot read VPN link properties")

            val vpnDnsServers = vpnLinkProperties.dnsServers
                .map { it.hostAddress ?: "" }
                .filter { it.isNotEmpty() }

            val vpnSubnets = getVpnSubnets(vpnLinkProperties)

            // Check all network interfaces for DNS servers outside VPN
            val leakedServers = mutableListOf<String>()
            @Suppress("DEPRECATION")
            val allNetworks = connectivityManager.allNetworks

            for (network in allNetworks) {
                val netCaps = connectivityManager.getNetworkCapabilities(network) ?: continue
                if (netCaps.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) continue

                val linkProps = connectivityManager.getLinkProperties(network) ?: continue
                for (dns in linkProps.dnsServers) {
                    val addr = dns.hostAddress ?: continue
                    if (!isAddressInSubnets(addr, vpnSubnets)) {
                        leakedServers.add(addr)
                    }
                }
            }

            if (leakedServers.isEmpty()) {
                DnsLeakResult.NoLeak(vpnDnsServers = vpnDnsServers)
            } else {
                DnsLeakResult.LeakDetected(
                    leakedServers = leakedServers.distinct(),
                    vpnDnsServers = vpnDnsServers
                )
            }
        } catch (e: Exception) {
            NetGuard.logger.e(TAG, "DNS leak check failed", e)
            DnsLeakResult.Error("DNS leak check failed: ${e.message}", e)
        }
    }

    private fun getVpnSubnets(linkProperties: LinkProperties): List<String> {
        return linkProperties.linkAddresses.mapNotNull { linkAddr ->
            val address = linkAddr.address
            if (address is Inet4Address) {
                "${address.hostAddress}/${linkAddr.prefixLength}"
            } else {
                null
            }
        }
    }

    private fun isAddressInSubnets(address: String, subnets: List<String>): Boolean {
        val addrBytes = try {
            Inet4Address.getByName(address).address
        } catch (e: Exception) {
            return false
        }

        return subnets.any { subnet ->
            try {
                val parts = subnet.split("/")
                val subnetAddr = Inet4Address.getByName(parts[0]).address
                val prefixLen = parts[1].toInt()
                val mask = (-1 shl (32 - prefixLen))

                val addrInt = addrBytes.toInt()
                val subnetInt = subnetAddr.toInt()

                (addrInt and mask) == (subnetInt and mask)
            } catch (e: Exception) {
                false
            }
        }
    }

    private fun ByteArray.toInt(): Int {
        return ((this[0].toInt() and 0xFF) shl 24) or
                ((this[1].toInt() and 0xFF) shl 16) or
                ((this[2].toInt() and 0xFF) shl 8) or
                (this[3].toInt() and 0xFF)
    }

    companion object {
        private const val TAG = "DnsLeakDetector"
    }
}
