package io.github.shaharzohar.netguard.traffic.export;

/**
 * Exports captured HTTP transactions to HAR (HTTP Archive) 1.2 format.
 *
 * HAR files can be imported into Chrome DevTools, Charles Proxy, Fiddler,
 * and other HTTP debugging tools.
 *
 * ## Usage
 *
 * ```kotlin
 * val exporter = HarExporter(context)
 *
 * // Export all transactions as JSON string
 * val harJson = exporter.exportAll()
 *
 * // Export to a file
 * val file = exporter.exportToFile(File(cacheDir, "traffic.har"))
 *
 * // Export to cache for sharing
 * val cacheFile = exporter.exportToCacheFile(context)
 * ```
 *
 * @since 1.1.0
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u000e\u0010\f\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u001e\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u0003H\u0086@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0015H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u001c\u0010\u001a\u001a\u00020\r2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00110\nH\u0086@\u00a2\u0006\u0002\u0010\u001cJ\u0010\u0010\u001d\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\u0011H\u0002J\u0018\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\b\u0010 \u001a\u0004\u0018\u00010\rH\u0002J\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\n2\u0006\u0010#\u001a\u00020\rH\u0002J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\'H\u0002J\u0010\u0010(\u001a\u00020\r2\u0006\u0010)\u001a\u00020*H\u0002J\u0016\u0010+\u001a\u00020,2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0010\u0010-\u001a\u00020%2\u0006\u0010.\u001a\u00020/H\u0002J\u0010\u00100\u001a\u00020%2\u0006\u00101\u001a\u000202H\u0002J\u0016\u00103\u001a\u00020\r2\f\u00104\u001a\b\u0012\u0004\u0012\u0002050\nH\u0002J\u000e\u00106\u001a\u0004\u0018\u00010\r*\u000205H\u0002J\f\u00107\u001a\u00020\'*\u000205H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00068"}, d2 = {"Lio/github/shaharzohar/netguard/traffic/export/HarExporter;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "repository", "Lio/github/shaharzohar/netguard/traffic/storage/TransactionRepository;", "calculateHeadersSize", "", "headers", "", "Lio/github/shaharzohar/netguard/traffic/export/HarHeader;", "exportAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exportRange", "fromTimestamp", "", "toTimestamp", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exportToCacheFile", "Ljava/io/File;", "(Landroid/content/Context;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exportToFile", "file", "(Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exportTransactions", "transactionIds", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "formatIso8601", "timestampMs", "parseHeaders", "headersJson", "parseQueryString", "Lio/github/shaharzohar/netguard/traffic/export/HarQueryParam;", "urlString", "serializeEntry", "Lorg/json/JSONObject;", "entry", "Lio/github/shaharzohar/netguard/traffic/export/HarEntry;", "serializeHarLog", "harLog", "Lio/github/shaharzohar/netguard/traffic/export/HarLog;", "serializeHeaders", "Lorg/json/JSONArray;", "serializeRequest", "req", "Lio/github/shaharzohar/netguard/traffic/export/HarRequest;", "serializeResponse", "resp", "Lio/github/shaharzohar/netguard/traffic/export/HarResponse;", "toHarJson", "transactions", "Lio/github/shaharzohar/netguard/traffic/models/HttpTransaction;", "buildConnectionString", "toHarEntry", "netguard-traffic_debug"})
public final class HarExporter {
    @org.jetbrains.annotations.NotNull()
    private final io.github.shaharzohar.netguard.traffic.storage.TransactionRepository repository = null;
    
    public HarExporter(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Export all captured transactions as a HAR JSON string.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object exportAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Export transactions within a time range as a HAR JSON string.
     *
     * @param fromTimestamp Start of the range (epoch millis, inclusive)
     * @param toTimestamp End of the range (epoch millis, inclusive)
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object exportRange(long fromTimestamp, long toTimestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Export specific transactions by their IDs.
     *
     * @param transactionIds List of transaction IDs to export
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object exportTransactions(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Long> transactionIds, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Export all transactions to a file.
     *
     * @param file Destination file
     * @return The written file
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object exportToFile(@org.jetbrains.annotations.NotNull()
    java.io.File file, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.io.File> $completion) {
        return null;
    }
    
    /**
     * Export all transactions to a cache file suitable for sharing.
     *
     * The file is placed in the app's cache directory under `netguard_har/`.
     *
     * @param context Android context
     * @return The cache file containing HAR data
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object exportToCacheFile(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.io.File> $completion) {
        return null;
    }
    
    private final java.lang.String toHarJson(java.util.List<io.github.shaharzohar.netguard.traffic.models.HttpTransaction> transactions) {
        return null;
    }
    
    private final io.github.shaharzohar.netguard.traffic.export.HarEntry toHarEntry(io.github.shaharzohar.netguard.traffic.models.HttpTransaction $this$toHarEntry) {
        return null;
    }
    
    private final java.lang.String buildConnectionString(io.github.shaharzohar.netguard.traffic.models.HttpTransaction $this$buildConnectionString) {
        return null;
    }
    
    private final java.util.List<io.github.shaharzohar.netguard.traffic.export.HarHeader> parseHeaders(java.lang.String headersJson) {
        return null;
    }
    
    private final java.util.List<io.github.shaharzohar.netguard.traffic.export.HarQueryParam> parseQueryString(java.lang.String urlString) {
        return null;
    }
    
    private final int calculateHeadersSize(java.util.List<io.github.shaharzohar.netguard.traffic.export.HarHeader> headers) {
        return 0;
    }
    
    private final java.lang.String formatIso8601(long timestampMs) {
        return null;
    }
    
    private final java.lang.String serializeHarLog(io.github.shaharzohar.netguard.traffic.export.HarLog harLog) {
        return null;
    }
    
    private final org.json.JSONObject serializeEntry(io.github.shaharzohar.netguard.traffic.export.HarEntry entry) {
        return null;
    }
    
    private final org.json.JSONObject serializeRequest(io.github.shaharzohar.netguard.traffic.export.HarRequest req) {
        return null;
    }
    
    private final org.json.JSONObject serializeResponse(io.github.shaharzohar.netguard.traffic.export.HarResponse resp) {
        return null;
    }
    
    private final org.json.JSONArray serializeHeaders(java.util.List<io.github.shaharzohar.netguard.traffic.export.HarHeader> headers) {
        return null;
    }
}