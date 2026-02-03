package io.github.shaharzohar.netguard.core

import android.content.Context
import io.github.shaharzohar.netguard.core.config.NetGuardConfig
import io.github.shaharzohar.netguard.core.logging.NetGuardLogger
import io.github.shaharzohar.netguard.core.logging.NoOpLogger

/**
 * No-op implementation of NetGuard for release builds.
 *
 * All methods are stubs that do nothing, ensuring zero overhead in production.
 */
object NetGuard {

    const val VERSION = "1.0.0"

    val isInitialized: Boolean = false

    val configuration: NetGuardConfig = NetGuardConfig.DEFAULT

    val logger: NetGuardLogger = NoOpLogger()

    @JvmStatic
    @JvmOverloads
    fun initialize(
        context: Context,
        block: NetGuardConfig.Builder.() -> Unit = {}
    ) {
        // No-op
    }

    internal fun initializeFromStartup(context: Context) {
        // No-op
    }
}
