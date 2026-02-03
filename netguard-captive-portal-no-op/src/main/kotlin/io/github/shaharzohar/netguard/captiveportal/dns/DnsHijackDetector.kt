package io.github.shaharzohar.netguard.captiveportal.dns

import io.github.shaharzohar.netguard.captiveportal.models.DnsHijackResult

/**
 * No-op implementation of DnsHijackDetector.
 */
class DnsHijackDetector {
    suspend fun detect(): DnsHijackResult = DnsHijackResult.Clear
    suspend fun detectForDomain(domain: String, expectedIps: List<String>): DnsHijackResult = DnsHijackResult.Clear
    suspend fun resolveAll(domain: String): List<String> = emptyList()
}
