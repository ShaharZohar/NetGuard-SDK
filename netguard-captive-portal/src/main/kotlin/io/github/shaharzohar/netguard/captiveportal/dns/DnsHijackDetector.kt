package io.github.shaharzohar.netguard.captiveportal.dns

import io.github.shaharzohar.netguard.captiveportal.models.DnsHijackResult
import io.github.shaharzohar.netguard.core.NetGuard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.InetAddress

/**
 * Detects DNS hijacking by comparing resolved IP addresses against known values.
 *
 * Some captive portals intercept DNS queries rather than HTTP requests.
 * This detector identifies such hijacking by resolving known domains
 * and comparing results against expected IP addresses.
 *
 * ## Usage
 *
 * ```kotlin
 * val detector = DnsHijackDetector()
 * when (val result = detector.detect()) {
 *     is DnsHijackResult.Detected -> {
 *         println("DNS hijacked: ${result.domain} -> ${result.actualIp}")
 *     }
 *     is DnsHijackResult.Clear -> println("No DNS hijacking detected")
 * }
 * ```
 *
 * @since 1.0.0
 */
class DnsHijackDetector {

    /**
     * Known DNS mappings used for hijack detection.
     *
     * These are well-known domains with stable IP addresses that are
     * unlikely to change frequently.
     */
    private val knownMappings = mapOf(
        "dns.google" to listOf("8.8.8.8", "8.8.4.4"),
        "one.one.one.one" to listOf("1.1.1.1", "1.0.0.1"),
        "dns.quad9.net" to listOf("9.9.9.9", "149.112.112.112")
    )

    /**
     * Detect DNS hijacking by resolving known domains.
     *
     * @return [DnsHijackResult] indicating whether hijacking was detected
     */
    suspend fun detect(): DnsHijackResult = withContext(Dispatchers.IO) {
        for ((domain, expectedIps) in knownMappings) {
            try {
                val addresses = InetAddress.getAllByName(domain)
                val actualIps = addresses.mapNotNull { it.hostAddress }

                // Check if any resolved IP matches expected values
                val hasMatch = actualIps.any { it in expectedIps }

                if (!hasMatch && actualIps.isNotEmpty()) {
                    return@withContext DnsHijackResult.Detected(
                        domain = domain,
                        expectedIp = expectedIps.first(),
                        actualIp = actualIps.first()
                    )
                }
            } catch (e: Exception) {
                NetGuard.logger.w(TAG, "Failed to resolve $domain", e)
                // Continue to next domain
            }
        }
        DnsHijackResult.Clear
    }

    /**
     * Detect DNS hijacking for a specific domain.
     *
     * @param domain Domain to check
     * @param expectedIps List of expected IP addresses
     * @return [DnsHijackResult] for this domain
     */
    suspend fun detectForDomain(
        domain: String,
        expectedIps: List<String>
    ): DnsHijackResult = withContext(Dispatchers.IO) {
        try {
            val addresses = InetAddress.getAllByName(domain)
            val actualIps = addresses.mapNotNull { it.hostAddress }

            val hasMatch = actualIps.any { it in expectedIps }

            if (!hasMatch && actualIps.isNotEmpty()) {
                DnsHijackResult.Detected(
                    domain = domain,
                    expectedIp = expectedIps.first(),
                    actualIp = actualIps.first()
                )
            } else {
                DnsHijackResult.Clear
            }
        } catch (e: Exception) {
            NetGuard.logger.e(TAG, "DNS resolution failed for $domain", e)
            DnsHijackResult.Error(e.message ?: "DNS resolution failed", e)
        }
    }

    /**
     * Resolve a domain and return all IP addresses.
     *
     * Useful for debugging DNS behavior.
     *
     * @param domain Domain to resolve
     * @return List of resolved IP addresses
     */
    suspend fun resolveAll(domain: String): List<String> = withContext(Dispatchers.IO) {
        try {
            InetAddress.getAllByName(domain).mapNotNull { it.hostAddress }
        } catch (e: Exception) {
            NetGuard.logger.w(TAG, "Failed to resolve $domain", e)
            emptyList()
        }
    }

    companion object {
        private const val TAG = "DnsHijackDetector"
    }
}
