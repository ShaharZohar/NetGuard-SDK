package io.github.shaharzohar.netguard.core.config

import io.github.shaharzohar.netguard.core.logging.DefaultLogger
import io.github.shaharzohar.netguard.core.logging.NetGuardLogger

/**
 * Configuration options for the NetGuard SDK.
 *
 * Use [Builder] to create a configuration instance:
 *
 * ```kotlin
 * NetGuard.initialize(context) {
 *     isDebugMode = true
 *     logger = TimberLogger()
 *     maxLogRetentionDays = 7
 * }
 * ```
 *
 * @property isDebugMode When true, enables verbose logging and additional diagnostics
 * @property logger Custom logger implementation for SDK logs
 * @property maxLogRetentionDays Number of days to retain logged data
 * @property enableAutoNetworkMonitoring When true, automatically monitors network changes
 * @since 1.0.0
 */
data class NetGuardConfig internal constructor(
    val isDebugMode: Boolean,
    val logger: NetGuardLogger,
    val maxLogRetentionDays: Int,
    val enableAutoNetworkMonitoring: Boolean,
    val captivePortalConfig: CaptivePortalConfig,
    val trafficConfig: TrafficConfig,
    val wifiConfig: WifiConfig
) {

    /**
     * Builder for creating [NetGuardConfig] instances.
     */
    class Builder {
        /**
         * Enable debug mode for verbose logging.
         */
        var isDebugMode: Boolean = false

        /**
         * Custom logger implementation. Defaults to Android Log.
         */
        var logger: NetGuardLogger = DefaultLogger()

        /**
         * Maximum days to retain logged network data.
         */
        var maxLogRetentionDays: Int = 7

        /**
         * Automatically monitor network state changes.
         */
        var enableAutoNetworkMonitoring: Boolean = true

        private var captivePortalConfig: CaptivePortalConfig = CaptivePortalConfig.DEFAULT
        private var trafficConfig: TrafficConfig = TrafficConfig.DEFAULT
        private var wifiConfig: WifiConfig = WifiConfig.DEFAULT

        /**
         * Configure captive portal detection settings.
         */
        fun captivePortal(block: CaptivePortalConfig.Builder.() -> Unit) {
            captivePortalConfig = CaptivePortalConfig.Builder().apply(block).build()
        }

        /**
         * Configure traffic logging settings.
         */
        fun traffic(block: TrafficConfig.Builder.() -> Unit) {
            trafficConfig = TrafficConfig.Builder().apply(block).build()
        }

        /**
         * Configure WiFi monitoring settings.
         */
        fun wifi(block: WifiConfig.Builder.() -> Unit) {
            wifiConfig = WifiConfig.Builder().apply(block).build()
        }

        internal fun build(): NetGuardConfig = NetGuardConfig(
            isDebugMode = isDebugMode,
            logger = logger,
            maxLogRetentionDays = maxLogRetentionDays,
            enableAutoNetworkMonitoring = enableAutoNetworkMonitoring,
            captivePortalConfig = captivePortalConfig,
            trafficConfig = trafficConfig,
            wifiConfig = wifiConfig
        )
    }

    companion object {
        /**
         * Default configuration with sensible defaults.
         */
        val DEFAULT = Builder().build()
    }
}

/**
 * Configuration for captive portal detection.
 */
data class CaptivePortalConfig internal constructor(
    val probeUrls: List<String>,
    val checkIntervalMs: Long,
    val connectionTimeoutMs: Long,
    val enableWisprParsing: Boolean,
    val enableDnsHijackDetection: Boolean
) {
    class Builder {
        var probeUrls: List<String> = listOf(
            "http://connectivitycheck.gstatic.com/generate_204",
            "http://clients3.google.com/generate_204",
            "http://www.google.com/gen_204"
        )
        var checkIntervalMs: Long = 30_000L
        var connectionTimeoutMs: Long = 10_000L
        var enableWisprParsing: Boolean = true
        var enableDnsHijackDetection: Boolean = true

        internal fun build() = CaptivePortalConfig(
            probeUrls = probeUrls,
            checkIntervalMs = checkIntervalMs,
            connectionTimeoutMs = connectionTimeoutMs,
            enableWisprParsing = enableWisprParsing,
            enableDnsHijackDetection = enableDnsHijackDetection
        )
    }

    companion object {
        val DEFAULT = Builder().build()
    }
}

/**
 * Configuration for traffic logging.
 */
data class TrafficConfig internal constructor(
    val maxContentLength: Long,
    val redactedHeaders: Set<String>,
    val enableRequestLogging: Boolean,
    val enableResponseLogging: Boolean,
    val enableTlsInfo: Boolean
) {
    class Builder {
        var maxContentLength: Long = 250_000L
        var redactedHeaders: Set<String> = setOf(
            "Authorization",
            "Cookie",
            "Set-Cookie",
            "X-Auth-Token",
            "X-API-Key"
        )
        var enableRequestLogging: Boolean = true
        var enableResponseLogging: Boolean = true
        var enableTlsInfo: Boolean = true

        /**
         * Add headers to be redacted from logs.
         */
        fun redactHeaders(vararg headers: String) {
            redactedHeaders = redactedHeaders + headers.toSet()
        }

        internal fun build() = TrafficConfig(
            maxContentLength = maxContentLength,
            redactedHeaders = redactedHeaders,
            enableRequestLogging = enableRequestLogging,
            enableResponseLogging = enableResponseLogging,
            enableTlsInfo = enableTlsInfo
        )
    }

    companion object {
        val DEFAULT = Builder().build()
    }
}

/**
 * Configuration for WiFi monitoring.
 */
data class WifiConfig internal constructor(
    val enableScanResults: Boolean,
    val enableSignalStrengthMonitoring: Boolean,
    val signalStrengthIntervalMs: Long
) {
    class Builder {
        var enableScanResults: Boolean = true
        var enableSignalStrengthMonitoring: Boolean = true
        var signalStrengthIntervalMs: Long = 5_000L

        internal fun build() = WifiConfig(
            enableScanResults = enableScanResults,
            enableSignalStrengthMonitoring = enableSignalStrengthMonitoring,
            signalStrengthIntervalMs = signalStrengthIntervalMs
        )
    }

    companion object {
        val DEFAULT = Builder().build()
    }
}
