package io.github.shaharzohar.netguard.captiveportal.models

import android.net.Network

/**
 * Represents the current state of captive portal detection.
 *
 * @since 1.0.0
 */
sealed class CaptivePortalState {

    /**
     * No captive portal detected, network is clear.
     */
    data object Clear : CaptivePortalState()

    /**
     * Currently checking for captive portal.
     */
    data object Checking : CaptivePortalState()

    /**
     * Captive portal detected on the network.
     *
     * @property network The Android Network object
     * @property portalUrl The URL of the captive portal login page (if detected)
     * @property wisprData WISPr protocol data (if available)
     * @property redirectUrl The URL the portal redirected to
     */
    data class Detected(
        val network: Network? = null,
        val portalUrl: String? = null,
        val wisprData: WISPrData? = null,
        val redirectUrl: String? = null
    ) : CaptivePortalState()

    /**
     * Error occurred during detection.
     *
     * @property message Error description
     * @property cause The underlying exception
     */
    data class Error(
        val message: String,
        val cause: Throwable? = null
    ) : CaptivePortalState()
}

/**
 * Result of an HTTP probe for captive portal detection.
 *
 * @since 1.0.0
 */
data class ProbeResult(
    val probeUrl: String,
    val responseCode: Int,
    val redirectUrl: String?,
    val responseBody: String?,
    val wisprData: WISPrData?,
    val durationMs: Long,
    val isCaptivePortal: Boolean
) {
    companion object {
        /**
         * Creates a successful probe result (no captive portal).
         */
        fun success(probeUrl: String, durationMs: Long) = ProbeResult(
            probeUrl = probeUrl,
            responseCode = 204,
            redirectUrl = null,
            responseBody = null,
            wisprData = null,
            durationMs = durationMs,
            isCaptivePortal = false
        )

        /**
         * Creates a probe result indicating captive portal detection.
         */
        fun captivePortal(
            probeUrl: String,
            responseCode: Int,
            redirectUrl: String?,
            responseBody: String?,
            wisprData: WISPrData?,
            durationMs: Long
        ) = ProbeResult(
            probeUrl = probeUrl,
            responseCode = responseCode,
            redirectUrl = redirectUrl,
            responseBody = responseBody,
            wisprData = wisprData,
            durationMs = durationMs,
            isCaptivePortal = true
        )
    }
}

/**
 * WISPr (Wireless Internet Service Provider roaming) protocol data.
 *
 * WISPr is an XML-based protocol embedded in captive portal pages that allows
 * smart clients to authenticate automatically.
 *
 * @property messageType WISPr message type (100 = redirect required, 140 = auth pending)
 * @property responseCode WISPr response code (0 = no error)
 * @property loginUrl URL for form submission
 * @property abortLoginUrl URL to abort the login process
 * @property logoffUrl URL to log off from the network
 * @property accessProcedure Access procedure type
 * @property locationName Human-readable location name
 * @property accessLocation Access location identifier
 * @property replyMessage Message from the portal
 * @property version WISPr version (1.0 or 2.0)
 *
 * @since 1.0.0
 */
data class WISPrData(
    val messageType: Int,
    val responseCode: Int,
    val loginUrl: String?,
    val abortLoginUrl: String?,
    val logoffUrl: String?,
    val accessProcedure: String?,
    val locationName: String?,
    val accessLocation: String?,
    val replyMessage: String?,
    val version: String?
) {
    /**
     * Returns true if authentication is required.
     */
    val requiresAuthentication: Boolean
        get() = messageType == MESSAGE_TYPE_REDIRECT

    /**
     * Returns true if there was an error.
     */
    val hasError: Boolean
        get() = responseCode != RESPONSE_CODE_NO_ERROR

    companion object {
        const val MESSAGE_TYPE_REDIRECT = 100
        const val MESSAGE_TYPE_AUTH_NOTIFICATION = 120
        const val MESSAGE_TYPE_AUTH_PENDING = 140
        const val MESSAGE_TYPE_AUTH_SUCCESS = 150

        const val RESPONSE_CODE_NO_ERROR = 0
        const val RESPONSE_CODE_LOGIN_SUCCEEDED = 50
        const val RESPONSE_CODE_LOGIN_FAILED = 100
        const val RESPONSE_CODE_RADIUS_ERROR = 102
        const val RESPONSE_CODE_NETWORK_ERROR = 105
    }
}

/**
 * Result of a WISPr login attempt.
 *
 * @since 1.0.0
 */
sealed class WISPrLoginResult {
    /**
     * Login was successful.
     */
    data class Success(
        val logoffUrl: String?,
        val replyMessage: String?
    ) : WISPrLoginResult()

    /**
     * Login failed.
     */
    data class Failed(
        val responseCode: Int,
        val replyMessage: String?
    ) : WISPrLoginResult()

    /**
     * Error during login.
     */
    data class Error(
        val message: String,
        val cause: Throwable? = null
    ) : WISPrLoginResult()
}

/**
 * Result of DNS hijacking detection.
 *
 * @since 1.0.0
 */
sealed class DnsHijackResult {
    /**
     * No DNS hijacking detected.
     */
    data object Clear : DnsHijackResult()

    /**
     * DNS hijacking detected.
     *
     * @property domain The domain that was hijacked
     * @property expectedIp The expected IP address
     * @property actualIp The actual IP address returned
     */
    data class Detected(
        val domain: String,
        val expectedIp: String,
        val actualIp: String
    ) : DnsHijackResult()

    /**
     * Error during detection.
     */
    data class Error(
        val message: String,
        val cause: Throwable? = null
    ) : DnsHijackResult()
}
