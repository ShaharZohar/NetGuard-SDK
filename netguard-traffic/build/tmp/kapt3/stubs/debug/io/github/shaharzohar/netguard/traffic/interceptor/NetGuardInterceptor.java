package io.github.shaharzohar.netguard.traffic.interceptor;

/**
 * OkHttp interceptor that logs HTTP traffic for debugging and analysis.
 *
 * This interceptor captures complete request/response cycles including:
 * - URLs, methods, and headers
 * - Request and response bodies (with configurable size limits)
 * - Timing information
 * - TLS/SSL details
 *
 * ## Usage
 *
 * ```kotlin
 * val client = OkHttpClient.Builder()
 *    .addInterceptor(NetGuardInterceptor(context))
 *    .build()
 * ```
 *
 * ## Configuration
 *
 * Configure through [NetGuard.initialize]:
 *
 * ```kotlin
 * NetGuard.initialize(context) {
 *    traffic {
 *        maxContentLength = 500_000L
 *        redactHeaders("X-Custom-Auth")
 *    }
 * }
 * ```
 *
 * @param repository Repository for storing transactions
 * @param config Traffic logging configuration
 * @since 1.0.0
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 )2\u00020\u0001:\u0001)B\u0007\b\u0016\u00a2\u0006\u0002\u0010\u0002B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u001e\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0012\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u000bH\u0002J\u0010\u0010 \u001a\u00020\u00162\u0006\u0010!\u001a\u00020\u0014H\u0002J\u0018\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00162\u0006\u0010%\u001a\u00020&H\u0002J0\u0010\'\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010(\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001cH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lio/github/shaharzohar/netguard/traffic/interceptor/NetGuardInterceptor;", "Lokhttp3/Interceptor;", "()V", "repository", "Lio/github/shaharzohar/netguard/traffic/storage/TransactionRepository;", "config", "Lio/github/shaharzohar/netguard/core/config/TrafficConfig;", "(Lio/github/shaharzohar/netguard/traffic/storage/TransactionRepository;Lio/github/shaharzohar/netguard/core/config/TrafficConfig;)V", "scope", "Lkotlinx/coroutines/CoroutineScope;", "captureRequestBody", "", "request", "Lokhttp3/Request;", "captureResponseBody", "Lkotlin/Pair;", "", "response", "Lokhttp3/Response;", "createTransaction", "Lio/github/shaharzohar/netguard/traffic/models/HttpTransaction;", "requestTime", "", "headersToJson", "headers", "Lokhttp3/Headers;", "intercept", "chain", "Lokhttp3/Interceptor$Chain;", "isPlaintext", "", "contentType", "saveTransaction", "transaction", "updateTransactionWithError", "", "transactionId", "error", "Ljava/io/IOException;", "updateTransactionWithResponse", "responseTime", "Companion", "netguard-traffic_debug"})
public final class NetGuardInterceptor implements okhttp3.Interceptor {
    @org.jetbrains.annotations.NotNull()
    private final io.github.shaharzohar.netguard.traffic.storage.TransactionRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.shaharzohar.netguard.core.config.TrafficConfig config = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "NetGuardInterceptor";
    @org.jetbrains.annotations.NotNull()
    public static final io.github.shaharzohar.netguard.traffic.interceptor.NetGuardInterceptor.Companion Companion = null;
    
    public NetGuardInterceptor(@org.jetbrains.annotations.NotNull()
    io.github.shaharzohar.netguard.traffic.storage.TransactionRepository repository, @org.jetbrains.annotations.NotNull()
    io.github.shaharzohar.netguard.core.config.TrafficConfig config) {
        super();
    }
    
    /**
     * Creates an interceptor using the default repository.
     */
    public NetGuardInterceptor() {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public okhttp3.Response intercept(@org.jetbrains.annotations.NotNull()
    okhttp3.Interceptor.Chain chain) {
        return null;
    }
    
    private final io.github.shaharzohar.netguard.traffic.models.HttpTransaction createTransaction(okhttp3.Request request, long requestTime) {
        return null;
    }
    
    private final java.lang.String captureRequestBody(okhttp3.Request request) {
        return null;
    }
    
    private final okhttp3.Response updateTransactionWithResponse(long transactionId, okhttp3.Response response, long responseTime, long requestTime, okhttp3.Interceptor.Chain chain) {
        return null;
    }
    
    private final kotlin.Pair<java.lang.String, byte[]> captureResponseBody(okhttp3.Response response) {
        return null;
    }
    
    private final void updateTransactionWithError(long transactionId, java.io.IOException error) {
    }
    
    private final long saveTransaction(io.github.shaharzohar.netguard.traffic.models.HttpTransaction transaction) {
        return 0L;
    }
    
    private final java.lang.String headersToJson(okhttp3.Headers headers) {
        return null;
    }
    
    private final boolean isPlaintext(java.lang.String contentType) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lio/github/shaharzohar/netguard/traffic/interceptor/NetGuardInterceptor$Companion;", "", "()V", "TAG", "", "netguard-traffic_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}