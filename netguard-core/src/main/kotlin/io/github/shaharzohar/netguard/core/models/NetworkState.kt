package io.github.shaharzohar.netguard.core.models

/**
 * Represents the current state of network connectivity.
 *
 * @since 1.0.0
 */
sealed class NetworkState {

    /**
     * Device is disconnected from all networks.
     */
    data object Disconnected : NetworkState()

    /**
     * Device is connected to a network.
     *
     * @property type The type of network connection
     * @property hasCaptivePortal True if a captive portal is detected
     * @property isValidated True if the network has been validated by the system
     * @property hasInternet True if the network has internet capability
     * @property linkDownstreamBandwidthKbps Downstream bandwidth estimate in Kbps
     * @property linkUpstreamBandwidthKbps Upstream bandwidth estimate in Kbps
     */
    data class Connected(
        val type: NetworkType,
        val hasCaptivePortal: Boolean = false,
        val isValidated: Boolean = false,
        val hasInternet: Boolean = false,
        val linkDownstreamBandwidthKbps: Int = -1,
        val linkUpstreamBandwidthKbps: Int = -1
    ) : NetworkState() {

        /**
         * Returns true if the connection appears to be working properly.
         */
        val isFullyConnected: Boolean
            get() = isValidated && hasInternet && !hasCaptivePortal
    }

    /**
     * Returns true if connected to any network.
     */
    val isConnected: Boolean
        get() = this is Connected
}

/**
 * Type of network connection.
 *
 * @since 1.0.0
 */
enum class NetworkType {
    /** WiFi network */
    WIFI,
    /** Cellular/Mobile data network */
    CELLULAR,
    /** Ethernet connection */
    ETHERNET,
    /** VPN connection */
    VPN,
    /** Unknown network type */
    UNKNOWN
}

/**
 * Result of a network quality measurement.
 *
 * @property latencyMs Round-trip latency in milliseconds
 * @property packetLoss Packet loss ratio (0.0 to 1.0)
 * @property jitterMs Latency variation in milliseconds
 * @property downloadSpeedKbps Estimated download speed in Kbps
 * @property uploadSpeedKbps Estimated upload speed in Kbps
 * @since 1.0.0
 */
data class NetworkQuality(
    val latencyMs: Long,
    val packetLoss: Float,
    val jitterMs: Long,
    val downloadSpeedKbps: Int,
    val uploadSpeedKbps: Int
) {
    /**
     * Quality rating based on measured metrics.
     */
    val rating: QualityRating
        get() = when {
            latencyMs < 50 && packetLoss < 0.01f -> QualityRating.EXCELLENT
            latencyMs < 100 && packetLoss < 0.02f -> QualityRating.GOOD
            latencyMs < 200 && packetLoss < 0.05f -> QualityRating.FAIR
            else -> QualityRating.POOR
        }
}

/**
 * Network quality rating.
 */
enum class QualityRating {
    EXCELLENT,
    GOOD,
    FAIR,
    POOR
}
