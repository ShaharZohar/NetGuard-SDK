package io.github.shaharzohar.netguard.sample

import android.app.Application
import io.github.shaharzohar.netguard.core.NetGuard
import io.github.shaharzohar.netguard.core.logging.DefaultLogger

/**
 * Sample application demonstrating NetGuard SDK initialization.
 */
class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize NetGuard with custom configuration
        NetGuard.initialize(this) {
            isDebugMode = true
            logger = DefaultLogger()
            maxLogRetentionDays = 7

            captivePortal {
                checkIntervalMs = 30_000
                enableWisprParsing = true
                enableDnsHijackDetection = true
            }

            traffic {
                maxContentLength = 500_000
                redactHeaders("X-Custom-Auth", "X-Session-Token")
            }

            wifi {
                enableSignalStrengthMonitoring = true
                signalStrengthIntervalMs = 5_000
            }
        }
    }
}
