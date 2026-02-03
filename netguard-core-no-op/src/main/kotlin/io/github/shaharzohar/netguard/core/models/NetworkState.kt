package io.github.shaharzohar.netguard.core.models

sealed class NetworkState {
    data object Disconnected : NetworkState()

    data class Connected(
        val type: NetworkType = NetworkType.UNKNOWN,
        val hasCaptivePortal: Boolean = false,
        val isValidated: Boolean = false,
        val hasInternet: Boolean = false,
        val linkDownstreamBandwidthKbps: Int = -1,
        val linkUpstreamBandwidthKbps: Int = -1
    ) : NetworkState() {
        val isFullyConnected: Boolean = false
    }

    val isConnected: Boolean
        get() = this is Connected
}

enum class NetworkType {
    WIFI,
    CELLULAR,
    ETHERNET,
    VPN,
    UNKNOWN
}

data class NetworkQuality(
    val latencyMs: Long = 0L,
    val packetLoss: Float = 0f,
    val jitterMs: Long = 0L,
    val downloadSpeedKbps: Int = 0,
    val uploadSpeedKbps: Int = 0
) {
    val rating: QualityRating = QualityRating.POOR
}

enum class QualityRating {
    EXCELLENT,
    GOOD,
    FAIR,
    POOR
}
