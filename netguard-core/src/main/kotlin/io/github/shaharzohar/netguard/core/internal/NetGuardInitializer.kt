package io.github.shaharzohar.netguard.core.internal

import android.content.Context
import androidx.startup.Initializer
import io.github.shaharzohar.netguard.core.NetGuard

/**
 * AndroidX App Startup initializer for NetGuard SDK.
 *
 * This enables zero-configuration initialization of the SDK. The SDK will be
 * initialized automatically when the application starts.
 *
 * To disable auto-initialization, add this to your AndroidManifest.xml:
 * ```xml
 * <provider
 *     android:name="androidx.startup.InitializationProvider"
 *     android:authorities="${applicationId}.androidx-startup"
 *     tools:node="merge">
 *     <meta-data
 *         android:name="io.github.shaharzohar.netguard.core.internal.NetGuardInitializer"
 *         tools:node="remove" />
 * </provider>
 * ```
 */
internal class NetGuardInitializer : Initializer<NetGuard> {

    override fun create(context: Context): NetGuard {
        NetGuard.initializeFromStartup(context)
        return NetGuard
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
