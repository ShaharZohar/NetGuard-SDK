package io.github.shaharzohar.netguard.wifi.quality

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import androidx.core.content.getSystemService
import io.github.shaharzohar.netguard.core.NetGuard
import io.github.shaharzohar.netguard.wifi.models.BandwidthEstimate
import io.github.shaharzohar.netguard.wifi.models.BandwidthMethod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

/**
 * Estimates network bandwidth using passive and active methods.
 *
 * Passive methods (link speed, ConnectivityManager) are zero-cost and provide rough estimates.
 * Active probing downloads real data for more accurate measurement but uses network bandwidth.
 *
 * @since 1.1.0
 */
internal class BandwidthEstimator(context: Context) {

    private val connectivityManager = context.getSystemService<ConnectivityManager>()
    private val wifiManager = context.applicationContext.getSystemService<WifiManager>()

    /**
     * Estimate bandwidth passively using system APIs (no data usage).
     *
     * Tries ConnectivityManager first, falls back to WiFi link speed.
     */
    suspend fun estimatePassive(): BandwidthEstimate = withContext(Dispatchers.IO) {
        // Try ConnectivityManager bandwidth estimates
        val cmEstimate = getConnectivityManagerEstimate()
        if (cmEstimate != null) return@withContext cmEstimate

        // Fall back to WiFi link speed
        getLinkSpeedEstimate() ?: BandwidthEstimate(
            downloadKbps = -1,
            uploadKbps = -1,
            method = BandwidthMethod.LINK_SPEED
        )
    }

    /**
     * Actively measure bandwidth by downloading data from a test URL.
     *
     * @param testUrl URL to download from for bandwidth measurement
     * @param durationMs Maximum duration for the test in milliseconds
     */
    suspend fun measureActive(
        testUrl: String,
        durationMs: Long = 5000
    ): BandwidthEstimate = withContext(Dispatchers.IO) {
        try {
            val url = URL(testUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 5000
            connection.readTimeout = durationMs.toInt()
            connection.requestMethod = "GET"

            val startTime = System.nanoTime()
            var totalBytes = 0L
            val buffer = ByteArray(8192)

            connection.inputStream.use { input ->
                while (true) {
                    val elapsed = (System.nanoTime() - startTime) / 1_000_000
                    if (elapsed >= durationMs) break

                    val bytesRead = input.read(buffer)
                    if (bytesRead == -1) break
                    totalBytes += bytesRead
                }
            }

            val elapsedMs = (System.nanoTime() - startTime) / 1_000_000
            val downloadKbps = if (elapsedMs > 0) {
                ((totalBytes * 8 * 1000) / (elapsedMs * 1024)).toInt()
            } else {
                -1
            }

            connection.disconnect()

            BandwidthEstimate(
                downloadKbps = downloadKbps,
                uploadKbps = -1, // Upload not measured via download test
                method = BandwidthMethod.ACTIVE_PROBE
            )
        } catch (e: Exception) {
            NetGuard.logger.w(TAG, "Active bandwidth measurement failed", e)
            // Fall back to passive estimate
            estimatePassive()
        }
    }

    private fun getConnectivityManagerEstimate(): BandwidthEstimate? {
        val cm = connectivityManager ?: return null
        val network = cm.activeNetwork ?: return null
        val capabilities = cm.getNetworkCapabilities(network) ?: return null

        val downKbps = capabilities.linkDownstreamBandwidthKbps
        val upKbps = capabilities.linkUpstreamBandwidthKbps

        if (downKbps <= 0 && upKbps <= 0) return null

        return BandwidthEstimate(
            downloadKbps = downKbps,
            uploadKbps = upKbps,
            method = BandwidthMethod.CONNECTIVITY_MANAGER
        )
    }

    @Suppress("DEPRECATION")
    private fun getLinkSpeedEstimate(): BandwidthEstimate? {
        val wm = wifiManager ?: return null
        val linkSpeedMbps = wm.connectionInfo?.linkSpeed ?: return null
        if (linkSpeedMbps <= 0) return null

        val linkSpeedKbps = linkSpeedMbps * 1024

        return BandwidthEstimate(
            downloadKbps = linkSpeedKbps,
            uploadKbps = linkSpeedKbps,
            method = BandwidthMethod.LINK_SPEED
        )
    }

    companion object {
        private const val TAG = "BandwidthEstimator"
    }
}
