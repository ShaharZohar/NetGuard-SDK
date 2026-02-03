package io.github.shaharzohar.netguard.wifi.monitor

import android.content.Context
import io.github.shaharzohar.netguard.wifi.models.LatencyResult
import io.github.shaharzohar.netguard.wifi.models.WifiConnectionInfo
import io.github.shaharzohar.netguard.wifi.models.WifiScanResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class WifiMonitor(context: Context) {
    fun observeWifiInfo(): Flow<WifiConnectionInfo> = emptyFlow()
    suspend fun getCurrentWifiInfo(): WifiConnectionInfo? = null
    fun observeSignalStrength(intervalMs: Long = 5000): Flow<Int> = emptyFlow()
    fun getScanResults(): List<WifiScanResult> = emptyList()
    suspend fun measureLatency(host: String, port: Int = 443, samples: Int = 5, timeoutMs: Int = 5000): LatencyResult = LatencyResult()
}
