package io.github.shaharzohar.netguard.wifi.models

/**
 * Information about the current WiFi connection.
 *
 * @since 1.0.0
 */
data class WifiConnectionInfo(
    val ssid: String?,
    val bssid: String?,
    val rssi: Int,
    val linkSpeedMbps: Int,
    val frequencyMhz: Int,
    val securityType: Int?,
    val ipAddress: String?,
    val macAddress: String?
) {
    /** Signal strength as a percentage (0-100). */
    val signalStrengthPercent: Int
        get() = when {
            rssi >= -50 -> 100
            rssi >= -60 -> 80
            rssi >= -70 -> 60
            rssi >= -80 -> 40
            rssi >= -90 -> 20
            else -> 0
        }

    /** Human-readable signal quality. */
    val signalQuality: SignalQuality
        get() = when (signalStrengthPercent) {
            in 80..100 -> SignalQuality.EXCELLENT
            in 60..79 -> SignalQuality.GOOD
            in 40..59 -> SignalQuality.FAIR
            in 20..39 -> SignalQuality.WEAK
            else -> SignalQuality.POOR
        }

    /** WiFi band (2.4GHz or 5GHz). */
    val band: WifiBand
        get() = if (frequencyMhz < 3000) WifiBand.BAND_2_4_GHZ else WifiBand.BAND_5_GHZ
}

enum class SignalQuality { EXCELLENT, GOOD, FAIR, WEAK, POOR }

enum class WifiBand { BAND_2_4_GHZ, BAND_5_GHZ }

/**
 * Information about a scanned WiFi network.
 */
data class WifiScanResult(
    val ssid: String,
    val bssid: String,
    val rssi: Int,
    val frequencyMhz: Int,
    val capabilities: String,
    val timestamp: Long
) {
    val isSecure: Boolean
        get() = capabilities.contains("WPA") || capabilities.contains("WEP")

    val isOpen: Boolean
        get() = !isSecure
}

/**
 * Network latency measurement result.
 */
data class LatencyResult(
    val host: String,
    val minMs: Long,
    val maxMs: Long,
    val avgMs: Long,
    val packetLoss: Float,
    val samples: Int
)
