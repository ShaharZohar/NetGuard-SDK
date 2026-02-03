package io.github.shaharzohar.netguard.captiveportal.detector

import android.content.Context
import io.github.shaharzohar.netguard.captiveportal.models.CaptivePortalState
import io.github.shaharzohar.netguard.captiveportal.models.ProbeResult
import io.github.shaharzohar.netguard.core.config.CaptivePortalConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * No-op implementation of CaptivePortalDetector.
 */
class CaptivePortalDetector(
    context: Context,
    config: CaptivePortalConfig = CaptivePortalConfig.DEFAULT,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun observeState(): Flow<CaptivePortalState> = emptyFlow()
    fun observeWithProbing(intervalMs: Long = 0): Flow<CaptivePortalState> = emptyFlow()
    suspend fun checkNow(): CaptivePortalState = CaptivePortalState.Clear
    suspend fun performHttpProbe(): CaptivePortalState = CaptivePortalState.Clear
    suspend fun probeUrl(url: String): ProbeResult = ProbeResult.success(url, 0)
    suspend fun probeAllUrls(): List<ProbeResult> = emptyList()
}
