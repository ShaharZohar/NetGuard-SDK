package io.github.shaharzohar.netguard.traffic.interceptor

import io.github.shaharzohar.netguard.core.NetGuard
import io.github.shaharzohar.netguard.core.config.TrafficConfig
import io.github.shaharzohar.netguard.traffic.models.HttpTransaction
import io.github.shaharzohar.netguard.traffic.storage.TransactionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import okio.GzipSource
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

/**
 * OkHttp interceptor that logs HTTP traffic for debugging and analysis.
 *
 * This interceptor captures complete request/response cycles including:
 * - URLs, methods, and headers
 * - Request and response bodies (with configurable size limits)
 * - Timing information
 * - TLS/SSL details
 *
 * ## Usage
 *
 * ```kotlin
 * val client = OkHttpClient.Builder()
 *     .addInterceptor(NetGuardInterceptor(context))
 *     .build()
 * ```
 *
 * ## Configuration
 *
 * Configure through [NetGuard.initialize]:
 *
 * ```kotlin
 * NetGuard.initialize(context) {
 *     traffic {
 *         maxContentLength = 500_000L
 *         redactHeaders("X-Custom-Auth")
 *     }
 * }
 * ```
 *
 * @param repository Repository for storing transactions
 * @param config Traffic logging configuration
 * @since 1.0.0
 */
class NetGuardInterceptor(
    private val repository: TransactionRepository,
    private val config: TrafficConfig = NetGuard.configuration.trafficConfig
) : Interceptor {

    private val scope = CoroutineScope(Dispatchers.IO)

    /**
     * Creates an interceptor using the default repository.
     */
    constructor() : this(
        repository = TransactionRepository.getInstance(NetGuard.getContext()),
        config = NetGuard.configuration.trafficConfig
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestTime = System.currentTimeMillis()

        // Build transaction from request
        val transaction = createTransaction(request, requestTime)

        // Save initial transaction
        val savedId = saveTransaction(transaction)

        return try {
            val response = chain.proceed(request)
            val responseTime = System.currentTimeMillis()

            // Update transaction with response data
            updateTransactionWithResponse(
                savedId,
                response,
                responseTime,
                requestTime,
                chain
            )

            response
        } catch (e: IOException) {
            // Update transaction with error
            updateTransactionWithError(savedId, e)
            throw e
        }
    }

    private fun createTransaction(request: Request, requestTime: Long): HttpTransaction {
        val requestBody = if (config.enableRequestLogging) {
            captureRequestBody(request)
        } else null

        return HttpTransaction(
            url = request.url.toString(),
            method = request.method,
            requestHeaders = headersToJson(request.headers),
            requestBody = requestBody,
            requestContentType = request.body?.contentType()?.toString(),
            requestContentLength = request.body?.contentLength(),
            requestTime = requestTime
        )
    }

    private fun captureRequestBody(request: Request): String? {
        val body = request.body ?: return null

        if (body.contentLength() > config.maxContentLength) {
            return "[Body too large: ${body.contentLength()} bytes]"
        }

        return try {
            val buffer = Buffer()
            body.writeTo(buffer)
            buffer.readString(Charset.forName("UTF-8"))
        } catch (e: Exception) {
            NetGuard.logger.w(TAG, "Failed to capture request body", e)
            null
        }
    }

    private fun updateTransactionWithResponse(
        transactionId: Long,
        response: Response,
        responseTime: Long,
        requestTime: Long,
        chain: Interceptor.Chain
    ): Response {
        val responseBody = if (config.enableResponseLogging) {
            captureResponseBody(response)
        } else null

        scope.launch {
            repository.updateTransaction(transactionId) { tx ->
                tx.copy(
                    responseCode = response.code,
                    responseMessage = response.message,
                    responseHeaders = headersToJson(response.headers),
                    responseBody = responseBody?.first,
                    responseContentType = response.body?.contentType()?.toString(),
                    responseContentLength = response.body?.contentLength(),
                    responseTime = responseTime,
                    durationMs = responseTime - requestTime,
                    protocol = response.protocol.toString(),
                    tlsVersion = if (config.enableTlsInfo) chain.connection()?.handshake()?.tlsVersion?.javaName else null,
                    cipherSuite = if (config.enableTlsInfo) chain.connection()?.handshake()?.cipherSuite?.javaName else null,
                    isComplete = true
                )
            }
        }

        // Return response with body that can still be consumed
        return if (responseBody != null) {
            response.newBuilder()
                .body(responseBody.second.toResponseBody(response.body?.contentType()))
                .build()
        } else {
            response
        }
    }

    private fun captureResponseBody(response: Response): Pair<String, ByteArray>? {
        val body = response.body ?: return null

        val contentLength = body.contentLength()
        if (contentLength > config.maxContentLength) {
            return "[Body too large: $contentLength bytes]" to ByteArray(0)
        }

        return try {
            val source = body.source()
            source.request(Long.MAX_VALUE)
            var buffer = source.buffer

            // Handle gzip encoding
            if ("gzip".equals(response.header("Content-Encoding"), ignoreCase = true)) {
                GzipSource(buffer.clone()).use { gzippedSource ->
                    buffer = Buffer()
                    buffer.writeAll(gzippedSource)
                }
            }

            val bytes = buffer.clone().readByteArray()
            val content = if (isPlaintext(body.contentType()?.toString())) {
                String(bytes, Charset.forName("UTF-8"))
            } else {
                "[Binary data: ${bytes.size} bytes]"
            }

            content to bytes
        } catch (e: Exception) {
            NetGuard.logger.w(TAG, "Failed to capture response body", e)
            null
        }
    }

    private fun updateTransactionWithError(transactionId: Long, error: IOException) {
        scope.launch {
            repository.updateTransaction(transactionId) { tx ->
                tx.copy(
                    error = error.message ?: "Unknown error",
                    isComplete = true
                )
            }
        }
    }

    private fun saveTransaction(transaction: HttpTransaction): Long {
        var id = 0L
        // Use blocking call for initial save to get ID
        kotlinx.coroutines.runBlocking {
            id = repository.save(transaction)
        }
        return id
    }

    private fun headersToJson(headers: Headers): String {
        val json = JSONObject()
        for (name in headers.names()) {
            val value = if (name.lowercase() in config.redactedHeaders.map { it.lowercase() }) {
                "██████"
            } else {
                headers[name]
            }
            json.put(name, value)
        }
        return json.toString()
    }

    private fun isPlaintext(contentType: String?): Boolean {
        if (contentType == null) return false
        return contentType.contains("text", ignoreCase = true) ||
               contentType.contains("json", ignoreCase = true) ||
               contentType.contains("xml", ignoreCase = true) ||
               contentType.contains("html", ignoreCase = true) ||
               contentType.contains("form-urlencoded", ignoreCase = true)
    }

    companion object {
        private const val TAG = "NetGuardInterceptor"
    }
}
