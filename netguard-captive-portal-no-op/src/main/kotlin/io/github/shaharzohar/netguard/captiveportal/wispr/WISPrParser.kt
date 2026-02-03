package io.github.shaharzohar.netguard.captiveportal.wispr

import io.github.shaharzohar.netguard.captiveportal.models.WISPrData
import io.github.shaharzohar.netguard.captiveportal.models.WISPrLoginResult

/**
 * No-op implementation of WISPrParser.
 */
class WISPrParser {
    fun parse(html: String): WISPrData? = null
    suspend fun performLogin(loginUrl: String, username: String, password: String): WISPrLoginResult =
        WISPrLoginResult.Error("No-op implementation")
    suspend fun performLogoff(logoffUrl: String): Boolean = false
}
