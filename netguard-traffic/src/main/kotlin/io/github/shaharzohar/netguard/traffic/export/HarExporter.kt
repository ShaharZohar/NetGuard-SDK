package io.github.shaharzohar.netguard.traffic.export

import android.content.Context
import io.github.shaharzohar.netguard.core.NetGuard
import io.github.shaharzohar.netguard.traffic.models.HttpTransaction
import io.github.shaharzohar.netguard.traffic.storage.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.net.URL
import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Exports captured HTTP transactions to HAR (HTTP Archive) 1.2 format.
 *
 * HAR files can be imported into Chrome DevTools, Charles Proxy, Fiddler,
 * and other HTTP debugging tools.
 *
 * ## Usage
 *
 * ```kotlin
 * val exporter = HarExporter(context)
 *
 * // Export all transactions as JSON string
 * val harJson = exporter.exportAll()
 *
 * // Export to a file
 * val file = exporter.exportToFile(File(cacheDir, "traffic.har"))
 *
 * // Export to cache for sharing
 * val cacheFile = exporter.exportToCacheFile(context)
 * ```
 *
 * @since 1.1.0
 */
class HarExporter(context: Context) {

    private val repository = TransactionRepository.getInstance(context.applicationContext)

    /**
     * Export all captured transactions as a HAR JSON string.
     */
    suspend fun exportAll(): String = withContext(Dispatchers.IO) {
        val transactions = repository.getAll()
        toHarJson(transactions)
    }

    /**
     * Export transactions within a time range as a HAR JSON string.
     *
     * @param fromTimestamp Start of the range (epoch millis, inclusive)
     * @param toTimestamp End of the range (epoch millis, inclusive)
     */
    suspend fun exportRange(fromTimestamp: Long, toTimestamp: Long): String =
        withContext(Dispatchers.IO) {
            val transactions = repository.getAll().filter {
                it.requestTime in fromTimestamp..toTimestamp
            }
            toHarJson(transactions)
        }

    /**
     * Export specific transactions by their IDs.
     *
     * @param transactionIds List of transaction IDs to export
     */
    suspend fun exportTransactions(transactionIds: List<Long>): String =
        withContext(Dispatchers.IO) {
            val idSet = transactionIds.toSet()
            val transactions = repository.getAll().filter { it.id in idSet }
            toHarJson(transactions)
        }

    /**
     * Export all transactions to a file.
     *
     * @param file Destination file
     * @return The written file
     */
    suspend fun exportToFile(file: File): File = withContext(Dispatchers.IO) {
        val json = exportAll()
        file.writeText(json)
        file
    }

    /**
     * Export all transactions to a cache file suitable for sharing.
     *
     * The file is placed in the app's cache directory under `netguard_har/`.
     *
     * @param context Android context
     * @return The cache file containing HAR data
     */
    suspend fun exportToCacheFile(context: Context): File = withContext(Dispatchers.IO) {
        val harDir = File(context.cacheDir, "netguard_har")
        harDir.mkdirs()
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val file = File(harDir, "netguard_traffic_$timestamp.har")
        exportToFile(file)
    }

    private fun toHarJson(transactions: List<HttpTransaction>): String {
        val harLog = HarLog(
            creator = HarCreator(version = NetGuard.VERSION),
            entries = transactions.map { it.toHarEntry() }
        )
        return serializeHarLog(harLog)
    }

    private fun HttpTransaction.toHarEntry(): HarEntry {
        val requestHeaders = parseHeaders(requestHeaders)
        val responseHeaders = parseHeaders(responseHeaders)

        return HarEntry(
            startedDateTime = formatIso8601(requestTime),
            time = durationMs ?: 0,
            request = HarRequest(
                method = method,
                url = url,
                httpVersion = protocol ?: "HTTP/1.1",
                headers = requestHeaders,
                queryString = parseQueryString(url),
                headersSize = calculateHeadersSize(requestHeaders),
                bodySize = requestContentLength ?: 0,
                postData = requestBody?.let { body ->
                    HarPostData(
                        mimeType = requestContentType ?: "application/octet-stream",
                        text = body
                    )
                }
            ),
            response = HarResponse(
                status = responseCode ?: 0,
                statusText = responseMessage ?: "",
                httpVersion = protocol ?: "HTTP/1.1",
                headers = responseHeaders,
                content = HarContent(
                    size = responseContentLength ?: 0,
                    mimeType = responseContentType ?: "application/octet-stream",
                    text = responseBody
                ),
                headersSize = calculateHeadersSize(responseHeaders),
                bodySize = responseContentLength ?: 0
            ),
            timings = HarTimings(
                send = -1,
                wait = durationMs ?: -1,
                receive = -1
            ),
            connection = buildConnectionString()
        )
    }

    private fun HttpTransaction.buildConnectionString(): String? {
        val parts = mutableListOf<String>()
        tlsVersion?.let { parts.add(it) }
        cipherSuite?.let { parts.add(it) }
        return if (parts.isNotEmpty()) parts.joinToString(" / ") else null
    }

    private fun parseHeaders(headersJson: String?): List<HarHeader> {
        if (headersJson.isNullOrBlank()) return emptyList()
        return try {
            val jsonObj = JSONObject(headersJson)
            jsonObj.keys().asSequence().map { key ->
                HarHeader(name = key, value = jsonObj.optString(key, ""))
            }.toList()
        } catch (e: Exception) {
            // Try as JSONArray of objects with name/value
            try {
                val jsonArray = JSONArray(headersJson)
                (0 until jsonArray.length()).map { i ->
                    val obj = jsonArray.getJSONObject(i)
                    HarHeader(
                        name = obj.optString("name", ""),
                        value = obj.optString("value", "")
                    )
                }
            } catch (e2: Exception) {
                emptyList()
            }
        }
    }

    private fun parseQueryString(urlString: String): List<HarQueryParam> {
        return try {
            val url = URL(urlString)
            val query = url.query ?: return emptyList()
            query.split("&").mapNotNull { param ->
                val parts = param.split("=", limit = 2)
                if (parts.isNotEmpty()) {
                    HarQueryParam(
                        name = URLDecoder.decode(parts[0], "UTF-8"),
                        value = if (parts.size > 1) URLDecoder.decode(parts[1], "UTF-8") else ""
                    )
                } else null
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun calculateHeadersSize(headers: List<HarHeader>): Int {
        if (headers.isEmpty()) return -1
        return headers.sumOf { "${it.name}: ${it.value}\r\n".length } + 2 // +2 for trailing \r\n
    }

    private fun formatIso8601(timestampMs: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(Date(timestampMs))
    }

    private fun serializeHarLog(harLog: HarLog): String {
        val root = JSONObject()
        val log = JSONObject().apply {
            put("version", harLog.version)
            put("creator", JSONObject().apply {
                put("name", harLog.creator.name)
                put("version", harLog.creator.version)
            })
            put("entries", JSONArray().apply {
                harLog.entries.forEach { entry ->
                    put(serializeEntry(entry))
                }
            })
        }
        root.put("log", log)
        return root.toString(2)
    }

    private fun serializeEntry(entry: HarEntry): JSONObject = JSONObject().apply {
        put("startedDateTime", entry.startedDateTime)
        put("time", entry.time)
        put("request", serializeRequest(entry.request))
        put("response", serializeResponse(entry.response))
        put("timings", JSONObject().apply {
            put("send", entry.timings.send)
            put("wait", entry.timings.wait)
            put("receive", entry.timings.receive)
        })
        entry.connection?.let { put("connection", it) }
        entry.serverIPAddress?.let { put("serverIPAddress", it) }
    }

    private fun serializeRequest(req: HarRequest): JSONObject = JSONObject().apply {
        put("method", req.method)
        put("url", req.url)
        put("httpVersion", req.httpVersion)
        put("headers", serializeHeaders(req.headers))
        put("queryString", JSONArray().apply {
            req.queryString.forEach { q ->
                put(JSONObject().apply {
                    put("name", q.name)
                    put("value", q.value)
                })
            }
        })
        put("headersSize", req.headersSize)
        put("bodySize", req.bodySize)
        req.postData?.let { pd ->
            put("postData", JSONObject().apply {
                put("mimeType", pd.mimeType)
                put("text", pd.text)
            })
        }
    }

    private fun serializeResponse(resp: HarResponse): JSONObject = JSONObject().apply {
        put("status", resp.status)
        put("statusText", resp.statusText)
        put("httpVersion", resp.httpVersion)
        put("headers", serializeHeaders(resp.headers))
        put("content", JSONObject().apply {
            put("size", resp.content.size)
            put("mimeType", resp.content.mimeType)
            resp.content.text?.let { put("text", it) }
        })
        put("headersSize", resp.headersSize)
        put("bodySize", resp.bodySize)
        put("redirectURL", resp.redirectURL)
    }

    private fun serializeHeaders(headers: List<HarHeader>): JSONArray = JSONArray().apply {
        headers.forEach { h ->
            put(JSONObject().apply {
                put("name", h.name)
                put("value", h.value)
            })
        }
    }
}
