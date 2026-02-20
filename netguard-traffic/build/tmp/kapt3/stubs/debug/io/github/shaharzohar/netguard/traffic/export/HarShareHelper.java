package io.github.shaharzohar.netguard.traffic.export;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lio/github/shaharzohar/netguard/traffic/export/HarShareHelper;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "exporter", "Lio/github/shaharzohar/netguard/traffic/export/HarExporter;", "shareAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "shareFile", "file", "Ljava/io/File;", "netguard-traffic_debug"})
public final class HarShareHelper {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.shaharzohar.netguard.traffic.export.HarExporter exporter = null;
    
    public HarShareHelper(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Export all traffic logs and open the Android share sheet.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object shareAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Share an existing HAR file via the Android share sheet.
     *
     * @param file The HAR file to share
     */
    public final void shareFile(@org.jetbrains.annotations.NotNull()
    java.io.File file) {
    }
}