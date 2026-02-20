package io.github.shaharzohar.netguard.wifi.models

import io.github.shaharzohar.netguard.core.models.NetworkQuality
import io.github.shaharzohar.netguard.core.models.QualityRating

/**
 * Complete snapshot of connection quality metrics at a point in time.
 *
 * @since 1.1.0
 */
data class ConnectionQualitySnapshot(
    val latencyMs: Long,
    val jitter: JitterMeasurement,
    val bandwidth: BandwidthEstimate,
    val signalStrengthDbm: Int?,
    val stabilityScore: Float,
    val overallQuality: QualityRating,
    val timestamp: Long = System.currentTimeMillis()
) {
    /**
     * Converts this snapshot to the core [NetworkQuality] model.
     */
    fun toNetworkQuality(): NetworkQuality = NetworkQuality(
        latencyMs = latencyMs,
        packetLoss = 1f - stabilityScore,
        jitterMs = jitter.jitterMs,
        downloadSpeedKbps = bandwidth.downloadKbps,
        uploadSpeedKbps = bandwidth.uploadKbps
    )
}

/**
 * Events emitted during quality monitoring.
 *
 * @since 1.1.0
 */
sealed class ConnectionQualityEvent {

    /**
     * Quality rating has changed.
     */
    data class QualityChanged(
        val previous: QualityRating,
        val current: QualityRating,
        val snapshot: ConnectionQualitySnapshot
    ) : ConnectionQualityEvent()

    /**
     * Connection stability has dropped below threshold.
     */
    data class StabilityAlert(
        val stabilityScore: Float,
        val message: String
    ) : ConnectionQualityEvent()

    /**
     * A significant latency spike was detected.
     */
    data class LatencySpike(
        val latencyMs: Long,
        val averageMs: Long,
        val deviationFactor: Float
    ) : ConnectionQualityEvent()
}

/**
 * Jitter measurement based on RFC 3550 interarrival jitter calculation.
 *
 * @property jitterMs Average jitter in milliseconds
 * @property maxDeviationMs Maximum deviation from mean latency
 * @property samples Number of samples used in the calculation
 * @since 1.1.0
 */
data class JitterMeasurement(
    val jitterMs: Long,
    val maxDeviationMs: Long,
    val samples: Int
)

/**
 * Network bandwidth estimate.
 *
 * @property downloadKbps Estimated download bandwidth in Kbps
 * @property uploadKbps Estimated upload bandwidth in Kbps
 * @property method The method used to estimate bandwidth
 * @since 1.1.0
 */
data class BandwidthEstimate(
    val downloadKbps: Int,
    val uploadKbps: Int,
    val method: BandwidthMethod
)

/**
 * Method used to estimate bandwidth.
 *
 * @since 1.1.0
 */
enum class BandwidthMethod {
    /** Derived from WiFi link speed */
    LINK_SPEED,
    /** From ConnectivityManager bandwidth estimates */
    CONNECTIVITY_MANAGER,
    /** Active measurement by downloading test data */
    ACTIVE_PROBE
}
