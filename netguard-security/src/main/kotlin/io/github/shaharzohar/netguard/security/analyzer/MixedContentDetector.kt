package io.github.shaharzohar.netguard.security.analyzer

import io.github.shaharzohar.netguard.security.models.MixedContentWarning
import io.github.shaharzohar.netguard.security.models.SecurityWarning
import io.github.shaharzohar.netguard.security.models.SecurityWarning.Severity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.Interceptor
import okhttp3.Response
import java.util.regex.Pattern

/**
 * OkHttp interceptor that detects mixed content in HTTPS responses.
 *
 * Mixed content occurs when an HTTPS page loads resources (images, scripts, stylesheets)
 * over insecure HTTP, which can allow attackers to tamper with those resources.
 *
 * @since 1.1.0
 */
internal class MixedContentDetector {

    private val _warnings = MutableSharedFlow<SecurityWarning>(extraBufferCapacity = 64)
    private val _mixedContentWarnings = MutableSharedFlow<MixedContentWarning>(extraBufferCapacity = 64)

    /** Flow of security warnings for mixed content detections. */
    val warnings: Flow<SecurityWarning> = _warnings.asSharedFlow()

    /** Flow of detailed mixed content warnings. */
    val mixedContentWarnings: Flow<MixedContentWarning> = _mixedContentWarnings.asSharedFlow()

    /**
     * Creates an OkHttp [Interceptor] that scans HTTPS HTML responses for HTTP resource references.
     */
    fun createInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)

        // Only check HTTPS responses with HTML content
        if (request.url.isHttps && isHtmlResponse(response)) {
            checkForMixedContent(request.url.toString(), response)
        }

        response
    }

    private fun isHtmlResponse(response: Response): Boolean {
        val contentType = response.header("Content-Type") ?: return false
        return contentType.contains("text/html", ignoreCase = true)
    }

    private fun checkForMixedContent(parentUrl: String, response: Response) {
        val body = response.peekBody(MAX_SCAN_SIZE)
        val html = body.string()

        // Scan for http:// references in resource-loading attributes
        RESOURCE_PATTERNS.forEach { (resourceType, pattern) ->
            val matcher = pattern.matcher(html)
            while (matcher.find()) {
                val insecureUrl = matcher.group(1) ?: continue

                val warning = MixedContentWarning(
                    parentUrl = parentUrl,
                    insecureResourceUrl = insecureUrl,
                    resourceType = resourceType
                )
                _mixedContentWarnings.tryEmit(warning)

                _warnings.tryEmit(
                    SecurityWarning(
                        severity = if (isActiveContent(resourceType)) Severity.HIGH else Severity.MEDIUM,
                        code = "MIXED_CONTENT",
                        message = "Mixed content: $resourceType loaded over HTTP from $parentUrl",
                        recommendation = "Change $insecureUrl to use HTTPS"
                    )
                )
            }
        }
    }

    private fun isActiveContent(resourceType: String): Boolean {
        return resourceType in setOf("script", "iframe", "object", "embed")
    }

    companion object {
        private const val MAX_SCAN_SIZE = 512_000L // 512KB

        private val RESOURCE_PATTERNS = mapOf(
            "script" to Pattern.compile("""src\s*=\s*["'](http://[^"']+)["']""", Pattern.CASE_INSENSITIVE),
            "stylesheet" to Pattern.compile("""href\s*=\s*["'](http://[^"']+\.css[^"']*)["']""", Pattern.CASE_INSENSITIVE),
            "image" to Pattern.compile("""src\s*=\s*["'](http://[^"']+\.(?:png|jpg|jpeg|gif|svg|webp)[^"']*)["']""", Pattern.CASE_INSENSITIVE),
            "iframe" to Pattern.compile("""<iframe[^>]+src\s*=\s*["'](http://[^"']+)["']""", Pattern.CASE_INSENSITIVE),
            "form_action" to Pattern.compile("""<form[^>]+action\s*=\s*["'](http://[^"']+)["']""", Pattern.CASE_INSENSITIVE)
        )
    }
}
