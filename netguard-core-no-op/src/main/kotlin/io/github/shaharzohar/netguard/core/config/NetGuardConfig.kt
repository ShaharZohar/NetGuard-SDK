package io.github.shaharzohar.netguard.core.config

import io.github.shaharzohar.netguard.core.logging.NetGuardLogger
import io.github.shaharzohar.netguard.core.logging.NoOpLogger

/**
 * No-op configuration for release builds.
 */
data class NetGuardConfig internal constructor(
    val isDebugMode: Boolean = false,
    val logger: NetGuardLogger = NoOpLogger(),
    val maxLogRetentionDays: Int = 7,
    val enableAutoNetworkMonitoring: Boolean = false,
    val captivePortalConfig: CaptivePortalConfig = CaptivePortalConfig.DEFAULT,
    val trafficConfig: TrafficConfig = TrafficConfig.DEFAULT,
    val wifiConfig: WifiConfig = WifiConfig.DEFAULT
) {
    class Builder {
        var isDebugMode: Boolean = false
        var logger: NetGuardLogger = NoOpLogger()
        var maxLogRetentionDays: Int = 7
        var enableAutoNetworkMonitoring: Boolean = false

        fun captivePortal(block: CaptivePortalConfig.Builder.() -> Unit) {}
        fun traffic(block: TrafficConfig.Builder.() -> Unit) {}
        fun wifi(block: WifiConfig.Builder.() -> Unit) {}

        internal fun build() = DEFAULT
    }

    companion object {
        val DEFAULT = NetGuardConfig()
    }
}

data class CaptivePortalConfig internal constructor(
    val probeUrls: List<String> = emptyList(),
    val checkIntervalMs: Long = 0L,
    val connectionTimeoutMs: Long = 0L,
    val enableWisprParsing: Boolean = false,
    val enableDnsHijackDetection: Boolean = false
) {
    class Builder {
        var probeUrls: List<String> = emptyList()
        var checkIntervalMs: Long = 0L
        var connectionTimeoutMs: Long = 0L
        var enableWisprParsing: Boolean = false
        var enableDnsHijackDetection: Boolean = false

        internal fun build() = DEFAULT
    }

    companion object {
        val DEFAULT = CaptivePortalConfig()
    }
}

data class TrafficConfig internal constructor(
    val maxContentLength: Long = 0L,
    val redactedHeaders: Set<String> = emptySet(),
    val enableRequestLogging: Boolean = false,
    val enableResponseLogging: Boolean = false,
    val enableTlsInfo: Boolean = false
) {
    class Builder {
        var maxContentLength: Long = 0L
        var redactedHeaders: Set<String> = emptySet()
        var enableRequestLogging: Boolean = false
        var enableResponseLogging: Boolean = false
        var enableTlsInfo: Boolean = false

        fun redactHeaders(vararg headers: String) {}
        internal fun build() = DEFAULT
    }

    companion object {
        val DEFAULT = TrafficConfig()
    }
}

data class WifiConfig internal constructor(
    val enableScanResults: Boolean = false,
    val enableSignalStrengthMonitoring: Boolean = false,
    val signalStrengthIntervalMs: Long = 0L
) {
    class Builder {
        var enableScanResults: Boolean = false
        var enableSignalStrengthMonitoring: Boolean = false
        var signalStrengthIntervalMs: Long = 0L

        internal fun build() = DEFAULT
    }

    companion object {
        val DEFAULT = WifiConfig()
    }
}
