package io.github.shaharzohar.netguard.traffic.export

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

/**
 * Helper for sharing HAR files via the Android share sheet.
 *
 * ## Usage
 *
 * ```kotlin
 * val shareHelper = HarShareHelper(context)
 *
 * // Export and share all traffic in one step
 * shareHelper.shareAll()
 *
 * // Share an existing HAR file
 * shareHelper.shareFile(harFile)
 * ```
 *
 * @since 1.1.0
 */
class HarShareHelper(private val context: Context) {

    private val exporter = HarExporter(context)

    /**
     * Export all traffic logs and open the Android share sheet.
     */
    suspend fun shareAll() {
        val file = exporter.exportToCacheFile(context)
        shareFile(file)
    }

    /**
     * Share an existing HAR file via the Android share sheet.
     *
     * @param file The HAR file to share
     */
    fun shareFile(file: File) {
        val authority = "${context.packageName}.netguard.fileprovider"
        val uri = FileProvider.getUriForFile(context, authority, file)

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/json"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "NetGuard Traffic Export")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        val chooser = Intent.createChooser(intent, "Share HAR file").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(chooser)
    }
}
