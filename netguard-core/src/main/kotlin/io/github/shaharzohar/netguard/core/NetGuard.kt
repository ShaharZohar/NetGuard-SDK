package io.github.shaharzohar.netguard.core

import android.content.Context
import io.github.shaharzohar.netguard.core.config.NetGuardConfig
import io.github.shaharzohar.netguard.core.internal.NetGuardServiceLocator
import io.github.shaharzohar.netguard.core.logging.NetGuardLogger
import io.github.shaharzohar.netguard.core.logging.DefaultLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Main entry point for the NetGuard Developer SDK.
 *
 * NetGuard provides comprehensive network diagnostics capabilities for Android applications,
 * including captive portal detection, traffic logging, and WiFi monitoring.
 *
 * ## Quick Start
 *
 * The SDK initializes automatically via AndroidX App Startup. For custom configuration:
 *
 * ```kotlin
 * NetGuard.initialize(context) {
 *     logger = TimberLogger()
 *     isDebugMode = true
 * }
 * ```
 *
 * ## Manual Initialization
 *
 * To disable auto-initialization and configure manually:
 *
 * 1. Add to your AndroidManifest.xml:
 * ```xml
 * <provider
 *     android:name="androidx.startup.InitializationProvider"
 *     android:authorities="${applicationId}.androidx-startup"
 *     tools:node="remove" />
 * ```
 *
 * 2. Initialize in your Application class:
 * ```kotlin
 * NetGuard.initialize(this) {
 *     // your configuration
 * }
 * ```
 *
 * @see NetGuardConfig
 * @since 1.0.0
 */
object NetGuard {

    private val initialized = AtomicBoolean(false)
    private lateinit var applicationContext: Context
    private lateinit var config: NetGuardConfig
    private lateinit var serviceLocator: NetGuardServiceLocator

    internal val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    /**
     * Current SDK version.
     */
    const val VERSION = "1.0.0"

    /**
     * Returns true if the SDK has been initialized.
     */
    val isInitialized: Boolean
        get() = initialized.get()

    /**
     * Returns the current configuration.
     *
     * @throws IllegalStateException if SDK is not initialized
     */
    val configuration: NetGuardConfig
        get() {
            checkInitialized()
            return config
        }

    /**
     * Returns the logger instance.
     */
    val logger: NetGuardLogger
        get() = if (initialized.get()) config.logger else DefaultLogger()

    /**
     * Initializes the NetGuard SDK with default configuration.
     *
     * This is called automatically by AndroidX App Startup. Call this method only if
     * you need custom configuration or have disabled auto-initialization.
     *
     * @param context Application context
     * @param block Optional configuration block
     * @throws IllegalStateException if already initialized
     */
    @JvmStatic
    @JvmOverloads
    fun initialize(
        context: Context,
        block: NetGuardConfig.Builder.() -> Unit = {}
    ) {
        if (!initialized.compareAndSet(false, true)) {
            logger.w(TAG, "NetGuard already initialized, ignoring duplicate call")
            return
        }

        applicationContext = context.applicationContext
        config = NetGuardConfig.Builder().apply(block).build()
        serviceLocator = NetGuardServiceLocator(applicationContext, config)

        logger.i(TAG, "NetGuard SDK v$VERSION initialized")
        logger.d(TAG, "Configuration: $config")
    }

    /**
     * Internal initialization for App Startup.
     */
    internal fun initializeFromStartup(context: Context) {
        if (initialized.get()) {
            return
        }
        initialize(context)
    }

    /**
     * Returns the service locator for internal use by other modules.
     */
    internal fun getServiceLocator(): NetGuardServiceLocator {
        checkInitialized()
        return serviceLocator
    }

    /**
     * Returns the application context.
     */
    internal fun getContext(): Context {
        checkInitialized()
        return applicationContext
    }

    private fun checkInitialized() {
        check(initialized.get()) {
            "NetGuard SDK not initialized. Call NetGuard.initialize(context) first."
        }
    }

    /**
     * Resets the SDK state. For testing purposes only.
     */
    @androidx.annotation.VisibleForTesting
    internal fun reset() {
        initialized.set(false)
    }

    private const val TAG = "NetGuard"
}
