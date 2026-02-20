package io.github.shaharzohar.netguard.wifi.quality

import io.github.shaharzohar.netguard.wifi.models.JitterMeasurement
import kotlin.math.abs

/**
 * Calculates network jitter using RFC 3550 interarrival jitter estimation.
 *
 * Jitter is the variation in packet transit time. High jitter indicates
 * an unstable connection, particularly problematic for real-time applications.
 *
 * @since 1.1.0
 */
internal class JitterCalculator {

    /**
     * Calculate jitter from a series of latency measurements.
     *
     * Uses the RFC 3550 exponential moving average approach:
     * J(i) = J(i-1) + (|D(i-1,i)| - J(i-1)) / 16
     *
     * where D(i-1,i) is the difference in transit times between
     * consecutive packets.
     *
     * @param latencies List of latency measurements in milliseconds
     * @return [JitterMeasurement] with calculated jitter metrics
     */
    fun calculate(latencies: List<Long>): JitterMeasurement {
        if (latencies.size < 2) {
            return JitterMeasurement(
                jitterMs = 0,
                maxDeviationMs = 0,
                samples = latencies.size
            )
        }

        val mean = latencies.average()
        var jitter = 0.0
        var maxDeviation = 0L

        for (i in 1 until latencies.size) {
            val diff = abs(latencies[i] - latencies[i - 1])
            // RFC 3550 exponential smoothing
            jitter += (diff - jitter) / 16.0

            val deviation = abs(latencies[i] - mean.toLong())
            if (deviation > maxDeviation) {
                maxDeviation = deviation
            }
        }

        return JitterMeasurement(
            jitterMs = jitter.toLong(),
            maxDeviationMs = maxDeviation,
            samples = latencies.size
        )
    }
}
