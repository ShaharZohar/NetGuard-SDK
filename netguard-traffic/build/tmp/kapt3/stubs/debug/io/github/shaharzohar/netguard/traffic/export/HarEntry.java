package io.github.shaharzohar.netguard.traffic.export;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u000bH\u00c6\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003JS\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010&\u001a\u00020\'H\u00d6\u0001J\t\u0010(\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006)"}, d2 = {"Lio/github/shaharzohar/netguard/traffic/export/HarEntry;", "", "startedDateTime", "", "time", "", "request", "Lio/github/shaharzohar/netguard/traffic/export/HarRequest;", "response", "Lio/github/shaharzohar/netguard/traffic/export/HarResponse;", "timings", "Lio/github/shaharzohar/netguard/traffic/export/HarTimings;", "connection", "serverIPAddress", "(Ljava/lang/String;JLio/github/shaharzohar/netguard/traffic/export/HarRequest;Lio/github/shaharzohar/netguard/traffic/export/HarResponse;Lio/github/shaharzohar/netguard/traffic/export/HarTimings;Ljava/lang/String;Ljava/lang/String;)V", "getConnection", "()Ljava/lang/String;", "getRequest", "()Lio/github/shaharzohar/netguard/traffic/export/HarRequest;", "getResponse", "()Lio/github/shaharzohar/netguard/traffic/export/HarResponse;", "getServerIPAddress", "getStartedDateTime", "getTime", "()J", "getTimings", "()Lio/github/shaharzohar/netguard/traffic/export/HarTimings;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "netguard-traffic_debug"})
public final class HarEntry {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String startedDateTime = null;
    private final long time = 0L;
    @org.jetbrains.annotations.NotNull()
    private final io.github.shaharzohar.netguard.traffic.export.HarRequest request = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.shaharzohar.netguard.traffic.export.HarResponse response = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.shaharzohar.netguard.traffic.export.HarTimings timings = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String connection = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String serverIPAddress = null;
    
    public HarEntry(@org.jetbrains.annotations.NotNull()
    java.lang.String startedDateTime, long time, @org.jetbrains.annotations.NotNull()
    io.github.shaharzohar.netguard.traffic.export.HarRequest request, @org.jetbrains.annotations.NotNull()
    io.github.shaharzohar.netguard.traffic.export.HarResponse response, @org.jetbrains.annotations.NotNull()
    io.github.shaharzohar.netguard.traffic.export.HarTimings timings, @org.jetbrains.annotations.Nullable()
    java.lang.String connection, @org.jetbrains.annotations.Nullable()
    java.lang.String serverIPAddress) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStartedDateTime() {
        return null;
    }
    
    public final long getTime() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.shaharzohar.netguard.traffic.export.HarRequest getRequest() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.shaharzohar.netguard.traffic.export.HarResponse getResponse() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.shaharzohar.netguard.traffic.export.HarTimings getTimings() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getConnection() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getServerIPAddress() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.shaharzohar.netguard.traffic.export.HarRequest component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.shaharzohar.netguard.traffic.export.HarResponse component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.shaharzohar.netguard.traffic.export.HarTimings component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.shaharzohar.netguard.traffic.export.HarEntry copy(@org.jetbrains.annotations.NotNull()
    java.lang.String startedDateTime, long time, @org.jetbrains.annotations.NotNull()
    io.github.shaharzohar.netguard.traffic.export.HarRequest request, @org.jetbrains.annotations.NotNull()
    io.github.shaharzohar.netguard.traffic.export.HarResponse response, @org.jetbrains.annotations.NotNull()
    io.github.shaharzohar.netguard.traffic.export.HarTimings timings, @org.jetbrains.annotations.Nullable()
    java.lang.String connection, @org.jetbrains.annotations.Nullable()
    java.lang.String serverIPAddress) {
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