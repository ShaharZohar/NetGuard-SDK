package io.github.shaharzohar.netguard.captiveportal.models

import android.net.Network

sealed class CaptivePortalState {
    data object Clear : CaptivePortalState()
    data object Checking : CaptivePortalState()
    data class Detected(
        val network: Network? = null,
        val portalUrl: String? = null,
        val wisprData: WISPrData? = null,
        val redirectUrl: String? = null
    ) : CaptivePortalState()
    data class Error(val message: String, val cause: Throwable? = null) : CaptivePortalState()
}

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
        fun success(probeUrl: String, durationMs: Long) = ProbeResult(probeUrl, 204, null, null, null, durationMs, false)
        fun captivePortal(probeUrl: String, responseCode: Int, redirectUrl: String?, responseBody: String?, wisprData: WISPrData?, durationMs: Long) =
            ProbeResult(probeUrl, responseCode, redirectUrl, responseBody, wisprData, durationMs, true)
    }
}

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
    val requiresAuthentication: Boolean = false
    val hasError: Boolean = false
    companion object {
        const val MESSAGE_TYPE_REDIRECT = 100
        const val MESSAGE_TYPE_AUTH_SUCCESS = 150
        const val RESPONSE_CODE_NO_ERROR = 0
        const val RESPONSE_CODE_LOGIN_SUCCEEDED = 50
        const val RESPONSE_CODE_LOGIN_FAILED = 100
        const val RESPONSE_CODE_RADIUS_ERROR = 102
        const val RESPONSE_CODE_NETWORK_ERROR = 105
    }
}

sealed class WISPrLoginResult {
    data class Success(val logoffUrl: String?, val replyMessage: String?) : WISPrLoginResult()
    data class Failed(val responseCode: Int, val replyMessage: String?) : WISPrLoginResult()
    data class Error(val message: String, val cause: Throwable? = null) : WISPrLoginResult()
}

sealed class DnsHijackResult {
    data object Clear : DnsHijackResult()
    data class Detected(val domain: String, val expectedIp: String, val actualIp: String) : DnsHijackResult()
    data class Error(val message: String, val cause: Throwable? = null) : DnsHijackResult()
}
