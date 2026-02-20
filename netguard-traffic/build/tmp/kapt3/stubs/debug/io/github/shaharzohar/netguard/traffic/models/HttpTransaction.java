package io.github.shaharzohar.netguard.traffic.models;

/**
 * Represents a complete HTTP request/response transaction.
 *
 * @since 1.0.0
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\bR\b\u0087\b\u0018\u00002\u00020\u0001B\u00e7\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u001a\u00a2\u0006\u0002\u0010\u001bJ\t\u0010P\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010Q\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010R\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010S\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010T\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010U\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010!J\u0010\u0010V\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010!J\u0010\u0010W\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010!J\u000b\u0010X\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010Y\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010Z\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010[\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\\\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010]\u001a\u00020\u001aH\u00c6\u0003J\t\u0010^\u001a\u00020\u0005H\u00c6\u0003J\t\u0010_\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010`\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010a\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010!J\t\u0010c\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010d\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u00fe\u0001\u0010e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0019\u001a\u00020\u001aH\u00c6\u0001\u00a2\u0006\u0002\u0010fJ\u0013\u0010g\u001a\u00020\u001a2\b\u0010h\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0010\u0010i\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\t\u0010j\u001a\u00020\rH\u00d6\u0001J\t\u0010k\u001a\u00020\u0005H\u00d6\u0001R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001e\u0010\u0014\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010$\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u001d\"\u0004\b&\u0010\u001fR\u0011\u0010\'\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b(\u0010\u001dR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u001a\u0010\u0019\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010+\"\u0004\b,\u0010-R\u0011\u0010.\u001a\u00020\u001a8F\u00a2\u0006\u0006\u001a\u0004\b.\u0010+R\u0011\u0010/\u001a\u00020\u001a8F\u00a2\u0006\u0006\u001a\u0004\b/\u0010+R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010\u001dR\u0011\u00101\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b2\u0010\u001dR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u001d\"\u0004\b4\u0010\u001fR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u0010\u001dR\u0015\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010$\u001a\u0004\b6\u0010!R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u0010\u001dR\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u0010\u001dR\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010*R\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b:\u0010\u001d\"\u0004\b;\u0010\u001fR\u001e\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010@\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u001e\u0010\u0012\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010$\u001a\u0004\bA\u0010!\"\u0004\bB\u0010#R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bC\u0010\u001d\"\u0004\bD\u0010\u001fR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bE\u0010\u001d\"\u0004\bF\u0010\u001fR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bG\u0010\u001d\"\u0004\bH\u0010\u001fR\u001e\u0010\u0013\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010$\u001a\u0004\bI\u0010!\"\u0004\bJ\u0010#R\u0011\u0010K\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\bL\u0010\u001dR\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bM\u0010\u001d\"\u0004\bN\u0010\u001fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bO\u0010\u001d\u00a8\u0006l"}, d2 = {"Lio/github/shaharzohar/netguard/traffic/models/HttpTransaction;", "", "id", "", "url", "", "method", "requestHeaders", "requestBody", "requestContentType", "requestContentLength", "requestTime", "responseCode", "", "responseMessage", "responseHeaders", "responseBody", "responseContentType", "responseContentLength", "responseTime", "durationMs", "protocol", "tlsVersion", "cipherSuite", "error", "isComplete", "", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;JLjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V", "getCipherSuite", "()Ljava/lang/String;", "setCipherSuite", "(Ljava/lang/String;)V", "getDurationMs", "()Ljava/lang/Long;", "setDurationMs", "(Ljava/lang/Long;)V", "Ljava/lang/Long;", "getError", "setError", "host", "getHost", "getId", "()J", "()Z", "setComplete", "(Z)V", "isError", "isSuccess", "getMethod", "path", "getPath", "getProtocol", "setProtocol", "getRequestBody", "getRequestContentLength", "getRequestContentType", "getRequestHeaders", "getRequestTime", "getResponseBody", "setResponseBody", "getResponseCode", "()Ljava/lang/Integer;", "setResponseCode", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getResponseContentLength", "setResponseContentLength", "getResponseContentType", "setResponseContentType", "getResponseHeaders", "setResponseHeaders", "getResponseMessage", "setResponseMessage", "getResponseTime", "setResponseTime", "summary", "getSummary", "getTlsVersion", "setTlsVersion", "getUrl", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;JLjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)Lio/github/shaharzohar/netguard/traffic/models/HttpTransaction;", "equals", "other", "extractPath", "hashCode", "toString", "netguard-traffic_debug"})
@androidx.room.Entity(tableName = "http_transactions")
public final class HttpTransaction {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String url = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String method = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String requestHeaders = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String requestBody = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String requestContentType = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long requestContentLength = null;
    private final long requestTime = 0L;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Integer responseCode;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String responseMessage;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String responseHeaders;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String responseBody;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String responseContentType;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Long responseContentLength;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Long responseTime;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Long durationMs;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String protocol;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String tlsVersion;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String cipherSuite;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String error;
    private boolean isComplete;
    
    public HttpTransaction(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.NotNull()
    java.lang.String method, @org.jetbrains.annotations.NotNull()
    java.lang.String requestHeaders, @org.jetbrains.annotations.Nullable()
    java.lang.String requestBody, @org.jetbrains.annotations.Nullable()
    java.lang.String requestContentType, @org.jetbrains.annotations.Nullable()
    java.lang.Long requestContentLength, long requestTime, @org.jetbrains.annotations.Nullable()
    java.lang.Integer responseCode, @org.jetbrains.annotations.Nullable()
    java.lang.String responseMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String responseHeaders, @org.jetbrains.annotations.Nullable()
    java.lang.String responseBody, @org.jetbrains.annotations.Nullable()
    java.lang.String responseContentType, @org.jetbrains.annotations.Nullable()
    java.lang.Long responseContentLength, @org.jetbrains.annotations.Nullable()
    java.lang.Long responseTime, @org.jetbrains.annotations.Nullable()
    java.lang.Long durationMs, @org.jetbrains.annotations.Nullable()
    java.lang.String protocol, @org.jetbrains.annotations.Nullable()
    java.lang.String tlsVersion, @org.jetbrains.annotations.Nullable()
    java.lang.String cipherSuite, @org.jetbrains.annotations.Nullable()
    java.lang.String error, boolean isComplete) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMethod() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRequestHeaders() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getRequestBody() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getRequestContentType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getRequestContentLength() {
        return null;
    }
    
    public final long getRequestTime() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getResponseCode() {
        return null;
    }
    
    public final void setResponseCode(@org.jetbrains.annotations.Nullable()
    java.lang.Integer p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getResponseMessage() {
        return null;
    }
    
    public final void setResponseMessage(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getResponseHeaders() {
        return null;
    }
    
    public final void setResponseHeaders(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getResponseBody() {
        return null;
    }
    
    public final void setResponseBody(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getResponseContentType() {
        return null;
    }
    
    public final void setResponseContentType(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getResponseContentLength() {
        return null;
    }
    
    public final void setResponseContentLength(@org.jetbrains.annotations.Nullable()
    java.lang.Long p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getResponseTime() {
        return null;
    }
    
    public final void setResponseTime(@org.jetbrains.annotations.Nullable()
    java.lang.Long p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getDurationMs() {
        return null;
    }
    
    public final void setDurationMs(@org.jetbrains.annotations.Nullable()
    java.lang.Long p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getProtocol() {
        return null;
    }
    
    public final void setProtocol(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTlsVersion() {
        return null;
    }
    
    public final void setTlsVersion(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCipherSuite() {
        return null;
    }
    
    public final void setCipherSuite(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    public final void setError(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    public final boolean isComplete() {
        return false;
    }
    
    public final void setComplete(boolean p0) {
    }
    
    public final boolean isSuccess() {
        return false;
    }
    
    public final boolean isError() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSummary() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHost() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPath() {
        return null;
    }
    
    private final java.lang.String extractPath(java.lang.String url) {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component14() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component15() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component16() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component17() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component18() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component19() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component20() {
        return null;
    }
    
    public final boolean component21() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component7() {
        return null;
    }
    
    public final long component8() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.shaharzohar.netguard.traffic.models.HttpTransaction copy(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.NotNull()
    java.lang.String method, @org.jetbrains.annotations.NotNull()
    java.lang.String requestHeaders, @org.jetbrains.annotations.Nullable()
    java.lang.String requestBody, @org.jetbrains.annotations.Nullable()
    java.lang.String requestContentType, @org.jetbrains.annotations.Nullable()
    java.lang.Long requestContentLength, long requestTime, @org.jetbrains.annotations.Nullable()
    java.lang.Integer responseCode, @org.jetbrains.annotations.Nullable()
    java.lang.String responseMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String responseHeaders, @org.jetbrains.annotations.Nullable()
    java.lang.String responseBody, @org.jetbrains.annotations.Nullable()
    java.lang.String responseContentType, @org.jetbrains.annotations.Nullable()
    java.lang.Long responseContentLength, @org.jetbrains.annotations.Nullable()
    java.lang.Long responseTime, @org.jetbrains.annotations.Nullable()
    java.lang.Long durationMs, @org.jetbrains.annotations.Nullable()
    java.lang.String protocol, @org.jetbrains.annotations.Nullable()
    java.lang.String tlsVersion, @org.jetbrains.annotations.Nullable()
    java.lang.String cipherSuite, @org.jetbrains.annotations.Nullable()
    java.lang.String error, boolean isComplete) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}