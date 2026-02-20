package io.github.shaharzohar.netguard.traffic.export

/**
 * HAR 1.2 specification data classes for HTTP Archive export.
 *
 * These models follow the HAR 1.2 spec (http://www.softwareishard.com/blog/har-12-spec/)
 * and can be serialized to JSON for import into Chrome DevTools, Charles Proxy, etc.
 *
 * @since 1.1.0
 */

data class HarLog(
    val version: String = "1.2",
    val creator: HarCreator,
    val entries: List<HarEntry>
)

data class HarCreator(
    val name: String = "NetGuard SDK",
    val version: String
)

data class HarEntry(
    val startedDateTime: String,
    val time: Long,
    val request: HarRequest,
    val response: HarResponse,
    val timings: HarTimings,
    val connection: String? = null,
    val serverIPAddress: String? = null
)

data class HarRequest(
    val method: String,
    val url: String,
    val httpVersion: String,
    val headers: List<HarHeader>,
    val queryString: List<HarQueryParam>,
    val headersSize: Int,
    val bodySize: Long,
    val postData: HarPostData? = null
)

data class HarResponse(
    val status: Int,
    val statusText: String,
    val httpVersion: String,
    val headers: List<HarHeader>,
    val content: HarContent,
    val headersSize: Int,
    val bodySize: Long,
    val redirectURL: String = ""
)

data class HarHeader(
    val name: String,
    val value: String
)

data class HarQueryParam(
    val name: String,
    val value: String
)

data class HarPostData(
    val mimeType: String,
    val text: String
)

data class HarContent(
    val size: Long,
    val mimeType: String,
    val text: String? = null
)

data class HarTimings(
    val send: Long = -1,
    val wait: Long = -1,
    val receive: Long = -1
)
