package io.github.shaharzohar.netguard.wifi.models

data class WifiConnectionInfo(
    val ssid: String? = null, val bssid: String? = null, val rssi: Int = 0,
    val linkSpeedMbps: Int = 0, val frequencyMhz: Int = 0, val securityType: Int? = null,
    val ipAddress: String? = null, val macAddress: String? = null
) {
    val signalStrengthPercent: Int = 0
    val signalQuality: SignalQuality = SignalQuality.POOR
    val band: WifiBand = WifiBand.BAND_2_4_GHZ
}

enum class SignalQuality { EXCELLENT, GOOD, FAIR, WEAK, POOR }
enum class WifiBand { BAND_2_4_GHZ, BAND_5_GHZ }

data class WifiScanResult(
    val ssid: String = "", val bssid: String = "", val rssi: Int = 0,
    val frequencyMhz: Int = 0, val capabilities: String = "", val timestamp: Long = 0
) {
    val isSecure: Boolean = false
    val isOpen: Boolean = true
}

data class LatencyResult(
    val host: String = "", val minMs: Long = 0, val maxMs: Long = 0,
    val avgMs: Long = 0, val packetLoss: Float = 0f, val samples: Int = 0
)
