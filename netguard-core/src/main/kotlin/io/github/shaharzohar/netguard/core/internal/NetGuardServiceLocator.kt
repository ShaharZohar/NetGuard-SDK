package io.github.shaharzohar.netguard.core.internal

import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.VisibleForTesting
import androidx.core.content.getSystemService
import io.github.shaharzohar.netguard.core.config.NetGuardConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Internal service locator for NetGuard SDK.
 *
 * Provides lazy-initialized dependencies to SDK components without requiring
 * consumers to adopt a specific DI framework.
 *
 * This class is internal and should not be used by SDK consumers.
 */
internal class NetGuardServiceLocator(
    private val context: Context,
    private val config: NetGuardConfig
) {

    // Dispatchers
    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    // System Services
    val connectivityManager: ConnectivityManager by lazy {
        context.getSystemService<ConnectivityManager>()
            ?: throw IllegalStateException("ConnectivityManager not available")
    }

    // Network Monitor
    val networkMonitor: NetworkMonitor by lazy {
        NetworkMonitorImpl(connectivityManager, ioDispatcher)
    }

    // Configuration accessors
    fun getCaptivePortalConfig() = config.captivePortalConfig
    fun getTrafficConfig() = config.trafficConfig
    fun getWifiConfig() = config.wifiConfig

    // Test support
    @VisibleForTesting
    internal var testNetworkMonitor: NetworkMonitor? = null

    @VisibleForTesting
    internal fun setTestDependencies(
        networkMonitor: NetworkMonitor? = null
    ) {
        testNetworkMonitor = networkMonitor
    }

    @VisibleForTesting
    internal fun clearTestDependencies() {
        testNetworkMonitor = null
    }
}
