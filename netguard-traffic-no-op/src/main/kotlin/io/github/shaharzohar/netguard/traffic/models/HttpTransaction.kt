package io.github.shaharzohar.netguard.traffic.models

data class HttpTransaction(
    val id: Long = 0, val url: String = "", val method: String = "", val requestHeaders: String = "",
    val requestBody: String? = null, val requestContentType: String? = null, val requestContentLength: Long? = null,
    val requestTime: Long = 0, var responseCode: Int? = null, var responseMessage: String? = null,
    var responseHeaders: String? = null, var responseBody: String? = null, var responseContentType: String? = null,
    var responseContentLength: Long? = null, var responseTime: Long? = null, var durationMs: Long? = null,
    var protocol: String? = null, var tlsVersion: String? = null, var cipherSuite: String? = null,
    var error: String? = null, var isComplete: Boolean = false
) {
    val isSuccess: Boolean = false
    val isError: Boolean = false
    val summary: String = ""
    val host: String = ""
    val path: String = ""
}

data class TrafficSummary(
    val totalRequests: Int = 0, val successfulRequests: Int = 0, val failedRequests: Int = 0,
    val averageDurationMs: Long = 0, val totalBytesReceived: Long = 0, val totalBytesSent: Long = 0
)
