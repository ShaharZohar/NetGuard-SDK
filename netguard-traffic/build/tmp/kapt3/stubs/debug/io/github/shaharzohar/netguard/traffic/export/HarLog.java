package io.github.shaharzohar.netguard.traffic.export;

/**
 * HAR 1.2 specification data classes for HTTP Archive export.
 *
 * These models follow the HAR 1.2 spec (http://www.softwareishard.com/blog/har-12-spec/)
 * and can be serialized to JSON for import into Chrome DevTools, Charles Proxy, etc.
 *
 * @since 1.1.0
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J-\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001a"}, d2 = {"Lio/github/shaharzohar/netguard/traffic/export/HarLog;", "", "version", "", "creator", "Lio/github/shaharzohar/netguard/traffic/export/HarCreator;", "entries", "", "Lio/github/shaharzohar/netguard/traffic/export/HarEntry;", "(Ljava/lang/String;Lio/github/shaharzohar/netguard/traffic/export/HarCreator;Ljava/util/List;)V", "getCreator", "()Lio/github/shaharzohar/netguard/traffic/export/HarCreator;", "getEntries", "()Ljava/util/List;", "getVersion", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "netguard-traffic_debug"})
public final class HarLog {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String version = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.shaharzohar.netguard.traffic.export.HarCreator creator = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<io.github.shaharzohar.netguard.traffic.export.HarEntry> entries = null;
    
    public HarLog(@org.jetbrains.annotations.NotNull()
    java.lang.String version, @org.jetbrains.annotations.NotNull()
    io.github.shaharzohar.netguard.traffic.export.HarCreator creator, @org.jetbrains.annotations.NotNull()
    java.util.List<io.github.shaharzohar.netguard.traffic.export.HarEntry> entries) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getVersion() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.shaharzohar.netguard.traffic.export.HarCreator getCreator() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<io.github.shaharzohar.netguard.traffic.export.HarEntry> getEntries() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.shaharzohar.netguard.traffic.export.HarCreator component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<io.github.shaharzohar.netguard.traffic.export.HarEntry> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.shaharzohar.netguard.traffic.export.HarLog copy(@org.jetbrains.annotations.NotNull()
    java.lang.String version, @org.jetbrains.annotations.NotNull()
    io.github.shaharzohar.netguard.traffic.export.HarCreator creator, @org.jetbrains.annotations.NotNull()
    java.util.List<io.github.shaharzohar.netguard.traffic.export.HarEntry> entries) {
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