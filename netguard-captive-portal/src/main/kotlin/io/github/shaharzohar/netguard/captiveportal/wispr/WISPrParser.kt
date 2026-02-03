package io.github.shaharzohar.netguard.captiveportal.wispr

import io.github.shaharzohar.netguard.captiveportal.models.WISPrData
import io.github.shaharzohar.netguard.captiveportal.models.WISPrLoginResult
import io.github.shaharzohar.netguard.core.NetGuard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

/**
 * Parser for WISPr (Wireless Internet Service Provider roaming) protocol.
 *
 * WISPr is an XML-based protocol embedded in HTML comments of captive portal pages.
 * It enables smart clients to authenticate automatically without user interaction.
 *
 * ## Supported Versions
 * - WISPr 1.0: Basic authentication
 * - WISPr 2.0: EAP-based authentication (partial support)
 *
 * @since 1.0.0
 */
class WISPrParser {

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    /**
     * Parse WISPr data from HTML content.
     *
     * @param html HTML content from captive portal page
     * @return [WISPrData] if found, null otherwise
     */
    fun parse(html: String): WISPrData? {
        val wisprXml = extractWISPrXml(html) ?: return null
        return parseWISPrXml(wisprXml)
    }

    private fun extractWISPrXml(html: String): String? {
        val patterns = listOf(
            Pattern.compile(
                "<!--\\s*(<\\?xml[^>]*\\?>)?\\s*(<WISPAccessGatewayParam[^>]*>.*?</WISPAccessGatewayParam>)\\s*-->",
                Pattern.DOTALL or Pattern.CASE_INSENSITIVE
            ),
            Pattern.compile(
                "(<WISPAccessGatewayParam[^>]*>.*?</WISPAccessGatewayParam>)",
                Pattern.DOTALL or Pattern.CASE_INSENSITIVE
            )
        )

        for (pattern in patterns) {
            val matcher = pattern.matcher(html)
            if (matcher.find()) {
                return matcher.group(matcher.groupCount())
            }
        }
        return null
    }

    private fun parseWISPrXml(xml: String): WISPrData? {
        return try {
            WISPrData(
                messageType = extractInt(xml, "MessageType") ?: return null,
                responseCode = extractInt(xml, "ResponseCode") ?: 0,
                loginUrl = extractString(xml, "LoginURL"),
                abortLoginUrl = extractString(xml, "AbortLoginURL"),
                logoffUrl = extractString(xml, "LogoffURL"),
                accessProcedure = extractString(xml, "AccessProcedure"),
                locationName = extractString(xml, "LocationName"),
                accessLocation = extractString(xml, "AccessLocation"),
                replyMessage = extractString(xml, "ReplyMessage"),
                version = detectVersion(xml)
            )
        } catch (e: Exception) {
            NetGuard.logger.w(TAG, "Failed to parse WISPr XML", e)
            null
        }
    }

    private fun extractString(xml: String, tag: String): String? {
        val pattern = Pattern.compile(
            "<$tag>\\s*(.*?)\\s*</$tag>",
            Pattern.DOTALL or Pattern.CASE_INSENSITIVE
        )
        val matcher = pattern.matcher(xml)
        return if (matcher.find()) {
            matcher.group(1)?.trim()?.let { unescapeHtml(it) }
        } else null
    }

    private fun extractInt(xml: String, tag: String): Int? {
        return extractString(xml, tag)?.toIntOrNull()
    }

    private fun detectVersion(xml: String): String {
        return when {
            xml.contains("EAPMethod", ignoreCase = true) -> "2.0"
            xml.contains("WISPr 2.0", ignoreCase = true) -> "2.0"
            else -> "1.0"
        }
    }

    private fun unescapeHtml(text: String): String {
        return text
            .replace("&amp;", "&")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&quot;", "\"")
            .replace("&#39;", "'")
            .replace("&apos;", "'")
    }

    /**
     * Perform WISPr authentication.
     *
     * @param loginUrl The WISPr login URL
     * @param username Username for authentication
     * @param password Password for authentication
     * @return [WISPrLoginResult] indicating success or failure
     */
    suspend fun performLogin(
        loginUrl: String,
        username: String,
        password: String
    ): WISPrLoginResult = withContext(Dispatchers.IO) {
        try {
            val formBody = FormBody.Builder()
                .add("UserName", username)
                .add("Password", password)
                .build()

            val request = Request.Builder()
                .url(loginUrl)
                .post(formBody)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build()

            httpClient.newCall(request).execute().use { response ->
                val body = response.body?.string() ?: ""
                val wisprData = parse(body)

                when {
                    wisprData == null -> {
                        if (response.isSuccessful) {
                            WISPrLoginResult.Success(null, null)
                        } else {
                            WISPrLoginResult.Failed(response.code, "HTTP error: ${response.code}")
                        }
                    }
                    wisprData.responseCode == WISPrData.RESPONSE_CODE_LOGIN_SUCCEEDED ||
                    wisprData.messageType == WISPrData.MESSAGE_TYPE_AUTH_SUCCESS -> {
                        WISPrLoginResult.Success(
                            logoffUrl = wisprData.logoffUrl,
                            replyMessage = wisprData.replyMessage
                        )
                    }
                    else -> {
                        WISPrLoginResult.Failed(
                            responseCode = wisprData.responseCode,
                            replyMessage = wisprData.replyMessage
                        )
                    }
                }
            }
        } catch (e: Exception) {
            NetGuard.logger.e(TAG, "WISPr login failed", e)
            WISPrLoginResult.Error(e.message ?: "Unknown error", e)
        }
    }

    /**
     * Perform WISPr logoff.
     *
     * @param logoffUrl The WISPr logoff URL
     * @return True if logoff was successful
     */
    suspend fun performLogoff(logoffUrl: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder()
                .url(logoffUrl)
                .get()
                .build()

            httpClient.newCall(request).execute().use { response ->
                response.isSuccessful
            }
        } catch (e: Exception) {
            NetGuard.logger.e(TAG, "WISPr logoff failed", e)
            false
        }
    }

    companion object {
        private const val TAG = "WISPrParser"
    }
}
