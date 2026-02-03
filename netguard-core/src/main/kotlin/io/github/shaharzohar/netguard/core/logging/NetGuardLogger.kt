package io.github.shaharzohar.netguard.core.logging

import android.util.Log

/**
 * Logger interface for NetGuard SDK.
 *
 * Implement this interface to integrate with your preferred logging framework
 * (Timber, custom logger, etc.).
 *
 * ## Example: Timber Integration
 *
 * ```kotlin
 * class TimberLogger : NetGuardLogger {
 *     override fun v(tag: String, message: String, throwable: Throwable?) {
 *         throwable?.let { Timber.tag(tag).v(it, message) } ?: Timber.tag(tag).v(message)
 *     }
 *     // ... implement other methods
 * }
 * ```
 *
 * @since 1.0.0
 */
interface NetGuardLogger {
    /**
     * Log a verbose message.
     */
    fun v(tag: String, message: String, throwable: Throwable? = null)

    /**
     * Log a debug message.
     */
    fun d(tag: String, message: String, throwable: Throwable? = null)

    /**
     * Log an info message.
     */
    fun i(tag: String, message: String, throwable: Throwable? = null)

    /**
     * Log a warning message.
     */
    fun w(tag: String, message: String, throwable: Throwable? = null)

    /**
     * Log an error message.
     */
    fun e(tag: String, message: String, throwable: Throwable? = null)

    /**
     * Log a "what a terrible failure" message.
     */
    fun wtf(tag: String, message: String, throwable: Throwable? = null)
}

/**
 * Default logger implementation using Android's [Log] class.
 */
class DefaultLogger : NetGuardLogger {
    override fun v(tag: String, message: String, throwable: Throwable?) {
        if (throwable != null) {
            Log.v(tag, message, throwable)
        } else {
            Log.v(tag, message)
        }
    }

    override fun d(tag: String, message: String, throwable: Throwable?) {
        if (throwable != null) {
            Log.d(tag, message, throwable)
        } else {
            Log.d(tag, message)
        }
    }

    override fun i(tag: String, message: String, throwable: Throwable?) {
        if (throwable != null) {
            Log.i(tag, message, throwable)
        } else {
            Log.i(tag, message)
        }
    }

    override fun w(tag: String, message: String, throwable: Throwable?) {
        if (throwable != null) {
            Log.w(tag, message, throwable)
        } else {
            Log.w(tag, message)
        }
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        if (throwable != null) {
            Log.e(tag, message, throwable)
        } else {
            Log.e(tag, message)
        }
    }

    override fun wtf(tag: String, message: String, throwable: Throwable?) {
        if (throwable != null) {
            Log.wtf(tag, message, throwable)
        } else {
            Log.wtf(tag, message)
        }
    }
}

/**
 * No-operation logger that discards all log messages.
 * Useful for production builds where logging should be disabled.
 */
class NoOpLogger : NetGuardLogger {
    override fun v(tag: String, message: String, throwable: Throwable?) = Unit
    override fun d(tag: String, message: String, throwable: Throwable?) = Unit
    override fun i(tag: String, message: String, throwable: Throwable?) = Unit
    override fun w(tag: String, message: String, throwable: Throwable?) = Unit
    override fun e(tag: String, message: String, throwable: Throwable?) = Unit
    override fun wtf(tag: String, message: String, throwable: Throwable?) = Unit
}
