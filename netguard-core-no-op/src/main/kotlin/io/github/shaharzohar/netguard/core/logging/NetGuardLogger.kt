package io.github.shaharzohar.netguard.core.logging

/**
 * Logger interface - no-op version.
 */
interface NetGuardLogger {
    fun v(tag: String, message: String, throwable: Throwable? = null)
    fun d(tag: String, message: String, throwable: Throwable? = null)
    fun i(tag: String, message: String, throwable: Throwable? = null)
    fun w(tag: String, message: String, throwable: Throwable? = null)
    fun e(tag: String, message: String, throwable: Throwable? = null)
    fun wtf(tag: String, message: String, throwable: Throwable? = null)
}

/**
 * No-op logger implementation.
 */
class NoOpLogger : NetGuardLogger {
    override fun v(tag: String, message: String, throwable: Throwable?) = Unit
    override fun d(tag: String, message: String, throwable: Throwable?) = Unit
    override fun i(tag: String, message: String, throwable: Throwable?) = Unit
    override fun w(tag: String, message: String, throwable: Throwable?) = Unit
    override fun e(tag: String, message: String, throwable: Throwable?) = Unit
    override fun wtf(tag: String, message: String, throwable: Throwable?) = Unit
}

class DefaultLogger : NetGuardLogger {
    override fun v(tag: String, message: String, throwable: Throwable?) = Unit
    override fun d(tag: String, message: String, throwable: Throwable?) = Unit
    override fun i(tag: String, message: String, throwable: Throwable?) = Unit
    override fun w(tag: String, message: String, throwable: Throwable?) = Unit
    override fun e(tag: String, message: String, throwable: Throwable?) = Unit
    override fun wtf(tag: String, message: String, throwable: Throwable?) = Unit
}
