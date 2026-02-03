package io.github.shaharzohar.netguard.traffic.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a complete HTTP request/response transaction.
 *
 * @since 1.0.0
 */
@Entity(tableName = "http_transactions")
data class HttpTransaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    // Request info
    val url: String,
    val method: String,
    val requestHeaders: String, // JSON
    val requestBody: String?,
    val requestContentType: String?,
    val requestContentLength: Long?,
    val requestTime: Long,

    // Response info
    var responseCode: Int? = null,
    var responseMessage: String? = null,
    var responseHeaders: String? = null, // JSON
    var responseBody: String? = null,
    var responseContentType: String? = null,
    var responseContentLength: Long? = null,
    var responseTime: Long? = null,

    // Timing
    var durationMs: Long? = null,

    // Connection info
    var protocol: String? = null,
    var tlsVersion: String? = null,
    var cipherSuite: String? = null,

    // Metadata
    var error: String? = null,
    var isComplete: Boolean = false
) {
    /**
     * Returns true if the request was successful (2xx status code).
     */
    val isSuccess: Boolean
        get() = responseCode in 200..299

    /**
     * Returns true if there was an error during the request.
     */
    val isError: Boolean
        get() = error != null || (responseCode != null && responseCode!! >= 400)

    /**
     * Returns a short summary of the transaction.
     */
    val summary: String
        get() = "$method ${extractPath(url)} → ${responseCode ?: "..."}"

    /**
     * Returns the host from the URL.
     */
    val host: String
        get() = try {
            java.net.URL(url).host
        } catch (e: Exception) {
            url
        }

    /**
     * Returns the path from the URL.
     */
    val path: String
        get() = extractPath(url)

    private fun extractPath(url: String): String {
        return try {
            val parsed = java.net.URL(url)
            val path = parsed.path.ifEmpty { "/" }
            if (parsed.query != null) "$path?${parsed.query}" else path
        } catch (e: Exception) {
            url
        }
    }
}

/**
 * Summary statistics for traffic logs.
 */
data class TrafficSummary(
    val totalRequests: Int,
    val successfulRequests: Int,
    val failedRequests: Int,
    val averageDurationMs: Long,
    val totalBytesReceived: Long,
    val totalBytesSent: Long
)
