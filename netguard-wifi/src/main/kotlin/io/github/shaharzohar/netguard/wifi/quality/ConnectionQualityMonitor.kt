package io.github.shaharzohar.netguard.wifi.quality

import android.content.Context
import io.github.shaharzohar.netguard.core.NetGuard
import io.github.shaharzohar.netguard.core.models.QualityRating
import io.github.shaharzohar.netguard.wifi.models.BandwidthEstimate
import io.github.shaharzohar.netguard.wifi.models.ConnectionQualityEvent
import io.github.shaharzohar.netguard.wifi.models.ConnectionQualitySnapshot
import io.github.shaharzohar.netguard.wifi.monitor.WifiMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import java.net.Socket

/**
 * Monitors and measures connection quality including latency, jitter,
 * bandwidth, and signal strength.
 *
 * ## Usage
 *
 * ```kotlin
 * val monitor = ConnectionQualityMonitor(context)
 *
 * // One-shot measurement
 * val snapshot = monitor.measureQuality("8.8.8.8", 443, 10)
 * println("Latency: ${snapshot.latencyMs}ms, Quality: ${snapshot.overallQuality}")
 *
 * // Continuous monitoring
 * monitor.observeQuality(intervalMs = 10_000, host = "8.8.8.8").collect { snapshot ->
 *     println("Quality: ${snapshot.overallQuality}, Stability: ${snapshot.stabilityScore}")
 * }
 *
 * // Quality events
 * monitor.observeQualityEvents(intervalMs = 10_000, host = "8.8.8.8").collect { event ->
 *     when (event) {
 *         is ConnectionQualityEvent.QualityChanged -> println("Quality changed!")
 *         is ConnectionQualityEvent.StabilityAlert -> println("Unstable connection!")
 *         is ConnectionQualityEvent.LatencySpike -> println("Latency spike!")
 *     }
 * }
 * ```
 *
 * @since 1.1.0
 */
class ConnectionQualityMonitor(context: Context) {

    private val appContext = context.applicationContext
    private val jitterCalculator = JitterCalculator()
    private val bandwidthEstimator = BandwidthEstimator(appContext)
    private val wifiMonitor = WifiMonitor(appContext)

    /**
     * Performs a single connection quality measurement.
     *
     * @param host Target host for latency measurement
     * @param port Target port (default 443)
     * @param samples Number of latency samples to collect
     * @return [ConnectionQualitySnapshot] with all quality metrics
     */
    suspend fun measureQuality(
        host: String = DEFAULT_HOST,
        port: Int = DEFAULT_PORT,
        samples: Int = DEFAULT_SAMPLES
    ): ConnectionQualitySnapshot = withContext(Dispatchers.IO) {
        val latencies = measureLatencies(host, port, samples)
        val validLatencies = latencies.filterNotNull()

        val avgLatency = if (validLatencies.isNotEmpty()) {
            validLatencies.average().toLong()
        } else {
            -1L
        }

        val jitter = jitterCalculator.calculate(validLatencies)
        val bandwidth = bandwidthEstimator.estimatePassive()
        val signalStrength = getSignalStrength()
        val stabilityScore = calculateStability(latencies)
        val quality = calculateOverallQuality(avgLatency, jitter.jitterMs, stabilityScore)

        ConnectionQualitySnapshot(
            latencyMs = avgLatency,
            jitter = jitter,
            bandwidth = bandwidth,
            signalStrengthDbm = signalStrength,
            stabilityScore = stabilityScore,
            overallQuality = quality
        )
    }

    /**
     * Observes connection quality at regular intervals.
     *
     * @param intervalMs Time between measurements in milliseconds
     * @param host Target host for latency probes
     * @return Flow emitting periodic quality snapshots
     */
    fun observeQuality(
        intervalMs: Long = 10_000,
        host: String = DEFAULT_HOST
    ): Flow<ConnectionQualitySnapshot> = flow {
        while (true) {
            emit(measureQuality(host))
            delay(intervalMs)
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Observes quality change events.
     *
     * Only emits when significant changes occur: quality rating changes,
     * stability drops below threshold, or latency spikes are detected.
     *
     * @param intervalMs Time between measurements in milliseconds
     * @param host Target host for latency probes
     * @return Flow of quality events
     */
    fun observeQualityEvents(
        intervalMs: Long = 10_000,
        host: String = DEFAULT_HOST
    ): Flow<ConnectionQualityEvent> = flow {
        var previousQuality: QualityRating? = null
        val recentLatencies = mutableListOf<Long>()

        while (true) {
            val snapshot = measureQuality(host)

            // Quality changed event
            val prevQuality = previousQuality
            if (prevQuality != null && prevQuality != snapshot.overallQuality) {
                emit(
                    ConnectionQualityEvent.QualityChanged(
                        previous = prevQuality,
                        current = snapshot.overallQuality,
                        snapshot = snapshot
                    )
                )
            }

            // Stability alert
            if (snapshot.stabilityScore < STABILITY_ALERT_THRESHOLD) {
                emit(
                    ConnectionQualityEvent.StabilityAlert(
                        stabilityScore = snapshot.stabilityScore,
                        message = "Connection stability is low (${(snapshot.stabilityScore * 100).toInt()}%)"
                    )
                )
            }

            // Latency spike detection
            if (recentLatencies.isNotEmpty() && snapshot.latencyMs > 0) {
                val avg = recentLatencies.average()
                if (avg > 0) {
                    val factor = snapshot.latencyMs / avg.toFloat()
                    if (factor >= LATENCY_SPIKE_FACTOR) {
                        emit(
                            ConnectionQualityEvent.LatencySpike(
                                latencyMs = snapshot.latencyMs,
                                averageMs = avg.toLong(),
                                deviationFactor = factor
                            )
                        )
                    }
                }
            }

            if (snapshot.latencyMs > 0) {
                recentLatencies.add(snapshot.latencyMs)
                if (recentLatencies.size > MAX_RECENT_LATENCIES) {
                    recentLatencies.removeAt(0)
                }
            }

            previousQuality = snapshot.overallQuality
            delay(intervalMs)
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Estimate bandwidth passively without using any data.
     *
     * @return [BandwidthEstimate] from system APIs
     */
    suspend fun estimateBandwidth(): BandwidthEstimate = bandwidthEstimator.estimatePassive()

    /**
     * Actively measure download bandwidth by downloading data from a test URL.
     *
     * @param testUrl URL to download from
     * @param durationMs Maximum test duration in milliseconds
     * @return [BandwidthEstimate] from active measurement
     */
    suspend fun measureBandwidth(
        testUrl: String,
        durationMs: Long = 5000
    ): BandwidthEstimate = bandwidthEstimator.measureActive(testUrl, durationMs)

    private suspend fun measureLatencies(
        host: String,
        port: Int,
        samples: Int
    ): List<Long?> = withContext(Dispatchers.IO) {
        val results = mutableListOf<Long?>()
        repeat(samples) {
            results.add(measureSingleLatency(host, port))
            delay(100)
        }
        results
    }

    private fun measureSingleLatency(host: String, port: Int): Long? {
        return try {
            val start = System.nanoTime()
            Socket().use { socket ->
                socket.connect(InetSocketAddress(host, port), TIMEOUT_MS)
            }
            (System.nanoTime() - start) / 1_000_000
        } catch (e: Exception) {
            null
        }
    }

    @Suppress("MissingPermission")
    private fun getSignalStrength(): Int? {
        return try {
            @Suppress("DEPRECATION")
            val wifiManager = appContext.getSystemService(Context.WIFI_SERVICE) as? android.net.wifi.WifiManager
            wifiManager?.connectionInfo?.rssi
        } catch (e: Exception) {
            null
        }
    }

    private fun calculateStability(latencies: List<Long?>): Float {
        if (latencies.isEmpty()) return 0f
        val successCount = latencies.count { it != null }
        return successCount.toFloat() / latencies.size
    }

    private fun calculateOverallQuality(
        latencyMs: Long,
        jitterMs: Long,
        stability: Float
    ): QualityRating {
        if (latencyMs < 0) return QualityRating.POOR

        return when {
            latencyMs < 50 && jitterMs < 10 && stability > 0.95f -> QualityRating.EXCELLENT
            latencyMs < 100 && jitterMs < 30 && stability > 0.85f -> QualityRating.GOOD
            latencyMs < 200 && jitterMs < 50 && stability > 0.70f -> QualityRating.FAIR
            else -> QualityRating.POOR
        }
    }

    companion object {
        private const val TAG = "ConnectionQualityMonitor"
        private const val DEFAULT_HOST = "8.8.8.8"
        private const val DEFAULT_PORT = 443
        private const val DEFAULT_SAMPLES = 5
        private const val TIMEOUT_MS = 5000
        private const val STABILITY_ALERT_THRESHOLD = 0.7f
        private const val LATENCY_SPIKE_FACTOR = 3.0f
        private const val MAX_RECENT_LATENCIES = 20
    }
}
