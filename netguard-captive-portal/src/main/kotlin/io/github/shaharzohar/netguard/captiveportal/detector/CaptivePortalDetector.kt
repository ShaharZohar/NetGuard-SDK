package io.github.shaharzohar.netguard.captiveportal.detector

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import io.github.shaharzohar.netguard.captiveportal.models.CaptivePortalState
import io.github.shaharzohar.netguard.captiveportal.models.ProbeResult
import io.github.shaharzohar.netguard.captiveportal.wispr.WISPrParser
import io.github.shaharzohar.netguard.core.NetGuard
import io.github.shaharzohar.netguard.core.config.CaptivePortalConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

/**
 * Detects captive portals using multiple techniques.
 *
 * This class combines:
 * - Android's native [NetworkCapabilities.NET_CAPABILITY_CAPTIVE_PORTAL] detection
 * - HTTP probe-based detection
 * - WISPr protocol parsing for auto-authentication support
 *
 * ## Usage
 *
 * ```kotlin
 * val detector = CaptivePortalDetector(context)
 *
 * // Real-time monitoring
 * detector.observeState().collect { state ->
 *     when (state) {
 *         is CaptivePortalState.Detected -> handlePortal(state)
 *         is CaptivePortalState.Clear -> hidePortalUI()
 *     }
 * }
 *
 * // One-shot detection
 * val state = detector.checkNow()
 * ```
 *
 * @param context Application or Activity context
 * @param config Optional custom configuration (defaults to SDK config)
 * @since 1.0.0
 */
class CaptivePortalDetector(
    private val context: Context,
    private val config: CaptivePortalConfig = NetGuard.configuration.captivePortalConfig,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val connectivityManager = context.getSystemService<ConnectivityManager>()
        ?: throw IllegalStateException("ConnectivityManager not available")

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(config.connectionTimeoutMs, TimeUnit.MILLISECONDS)
        .readTimeout(config.connectionTimeoutMs, TimeUnit.MILLISECONDS)
        .followRedirects(false)
        .followSslRedirects(false)
        .build()

    private val wisprParser = WISPrParser()

    /**
     * Observe captive portal state changes in real-time.
     *
     * This method combines Android's native captive portal detection with
     * periodic HTTP probing for comprehensive coverage.
     *
     * @return Flow emitting [CaptivePortalState] changes
     */
    fun observeState(): Flow<CaptivePortalState> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(
                network: Network,
                capabilities: NetworkCapabilities
            ) {
                val hasPortal = capabilities.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_CAPTIVE_PORTAL
                )
                val isValidated = capabilities.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_VALIDATED
                )

                val state = when {
                    hasPortal -> CaptivePortalState.Detected(network = network)
                    isValidated -> CaptivePortalState.Clear
                    else -> CaptivePortalState.Checking
                }
                trySend(state)
            }

            override fun onLost(network: Network) {
                trySend(CaptivePortalState.Clear)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, callback)

        // Emit initial state
        trySend(CaptivePortalState.Checking)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.flowOn(dispatcher)

    /**
     * Periodically check for captive portals using HTTP probing.
     *
     * @param intervalMs Check interval in milliseconds (defaults to config value)
     * @return Flow emitting [CaptivePortalState] at regular intervals
     */
    fun observeWithProbing(
        intervalMs: Long = config.checkIntervalMs
    ): Flow<CaptivePortalState> = flow {
        while (true) {
            emit(CaptivePortalState.Checking)
            val result = performHttpProbe()
            emit(result)
            delay(intervalMs)
        }
    }.flowOn(dispatcher)

    /**
     * Perform a one-time captive portal check.
     *
     * This method performs HTTP probing against configured URLs and
     * parses any WISPr data found.
     *
     * @return Current [CaptivePortalState]
     */
    suspend fun checkNow(): CaptivePortalState = withContext(dispatcher) {
        performHttpProbe()
    }

    /**
     * Perform HTTP probe against all configured URLs.
     *
     * @return [CaptivePortalState] based on probe results
     */
    suspend fun performHttpProbe(): CaptivePortalState = withContext(dispatcher) {
        var lastError: Exception? = null

        for (url in config.probeUrls) {
            try {
                val result = probeUrl(url)
                if (result.isCaptivePortal) {
                    return@withContext CaptivePortalState.Detected(
                        portalUrl = result.redirectUrl ?: result.probeUrl,
                        wisprData = result.wisprData,
                        redirectUrl = result.redirectUrl
                    )
                }
                // Probe succeeded with no portal — network is clear
                return@withContext CaptivePortalState.Clear
            } catch (e: Exception) {
                lastError = e
                NetGuard.logger.w(TAG, "Probe failed for $url", e)
                // Continue to next URL
            }
        }

        // All probes failed — likely blocked by a captive portal or no internet
        CaptivePortalState.Error(
            message = "All probe URLs failed — possible captive portal blocking connections",
            cause = lastError
        )
    }

    /**
     * Probe a single URL for captive portal detection.
     *
     * @param url URL to probe
     * @return [ProbeResult] with detection details
     */
    suspend fun probeUrl(url: String): ProbeResult = withContext(dispatcher) {
        val startTime = System.currentTimeMillis()

        try {
            val request = Request.Builder()
                .url(url)
                .header("User-Agent", USER_AGENT)
                .build()

            httpClient.newCall(request).execute().use { response ->
                val durationMs = System.currentTimeMillis() - startTime
                val responseCode = response.code
                val body = response.body?.string()

                // Check for redirect (captive portal indicator)
                if (responseCode in 300..399) {
                    val redirectUrl = response.header("Location")
                    val wisprData = if (config.enableWisprParsing && body != null) {
                        wisprParser.parse(body)
                    } else null

                    return@withContext ProbeResult.captivePortal(
                        probeUrl = url,
                        responseCode = responseCode,
                        redirectUrl = redirectUrl,
                        responseBody = body,
                        wisprData = wisprData,
                        durationMs = durationMs
                    )
                }

                // 204 No Content = expected success response (no captive portal)
                if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                    return@withContext ProbeResult.success(url, durationMs)
                }

                // HTTP 200 from a generate_204 endpoint is unexpected — indicates
                // a captive portal intercepting the request (even with empty body)
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val wisprData = if (config.enableWisprParsing && !body.isNullOrEmpty()) {
                        wisprParser.parse(body)
                    } else null

                    return@withContext ProbeResult.captivePortal(
                        probeUrl = url,
                        responseCode = responseCode,
                        redirectUrl = null,
                        responseBody = body,
                        wisprData = wisprData,
                        durationMs = durationMs
                    )
                }

                // Any other unexpected response code indicates network interference
                ProbeResult.captivePortal(
                    probeUrl = url,
                    responseCode = responseCode,
                    redirectUrl = null,
                    responseBody = body,
                    wisprData = null,
                    durationMs = durationMs
                )
            }
        } catch (e: Exception) {
            NetGuard.logger.e(TAG, "Error probing $url", e)
            throw e
        }
    }

    /**
     * Get detailed probe results for all configured URLs.
     *
     * Useful for debugging and research purposes.
     *
     * @return List of [ProbeResult] for each configured URL
     */
    suspend fun probeAllUrls(): List<ProbeResult> = withContext(dispatcher) {
        config.probeUrls.mapNotNull { url ->
            try {
                probeUrl(url)
            } catch (e: Exception) {
                NetGuard.logger.w(TAG, "Failed to probe $url", e)
                null
            }
        }
    }

    companion object {
        private const val TAG = "CaptivePortalDetector"
        private const val USER_AGENT = "Mozilla/5.0 (Linux; Android) NetGuard/1.0"
    }
}
