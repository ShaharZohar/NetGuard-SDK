package io.github.shaharzohar.netguard.traffic.storage;

/**
 * Room DAO for HTTP transactions.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\rH\'J\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0010\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0011\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ$\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\r2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\'J\u000e\u0010\u0016\u001a\u00020\u0014H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\rH\'J\u001c\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\r2\u0006\u0010\u0019\u001a\u00020\u0014H\'J\u0016\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u001cJ\u001c\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\r2\u0006\u0010\u001e\u001a\u00020\u001fH\'J\u0016\u0010 \u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u001c\u00a8\u0006!"}, d2 = {"Lio/github/shaharzohar/netguard/traffic/storage/TransactionDao;", "", "deleteAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOlderThan", "timestamp", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "", "Lio/github/shaharzohar/netguard/traffic/models/HttpTransaction;", "getAllFlow", "Lkotlinx/coroutines/flow/Flow;", "getAverageDuration", "", "getById", "id", "getByStatusCodeRange", "minCode", "", "maxCode", "getCount", "getErrors", "getRecentFlow", "limit", "insert", "transaction", "(Lio/github/shaharzohar/netguard/traffic/models/HttpTransaction;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "search", "query", "", "update", "netguard-traffic_debug"})
@androidx.room.Dao()
public abstract interface TransactionDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    io.github.shaharzohar.netguard.traffic.models.HttpTransaction transaction, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    io.github.shaharzohar.netguard.traffic.models.HttpTransaction transaction, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM http_transactions WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super io.github.shaharzohar.netguard.traffic.models.HttpTransaction> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM http_transactions ORDER BY requestTime DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<io.github.shaharzohar.netguard.traffic.models.HttpTransaction>> getAllFlow();
    
    @androidx.room.Query(value = "SELECT * FROM http_transactions ORDER BY requestTime DESC LIMIT :limit")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<io.github.shaharzohar.netguard.traffic.models.HttpTransaction>> getRecentFlow(int limit);
    
    @androidx.room.Query(value = "SELECT * FROM http_transactions ORDER BY requestTime DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<io.github.shaharzohar.netguard.traffic.models.HttpTransaction>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM http_transactions WHERE url LIKE \'%\' || :query || \'%\' OR method LIKE \'%\' || :query || \'%\' ORDER BY requestTime DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<io.github.shaharzohar.netguard.traffic.models.HttpTransaction>> search(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Query(value = "SELECT * FROM http_transactions WHERE responseCode >= :minCode AND responseCode < :maxCode ORDER BY requestTime DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<io.github.shaharzohar.netguard.traffic.models.HttpTransaction>> getByStatusCodeRange(int minCode, int maxCode);
    
    @androidx.room.Query(value = "SELECT * FROM http_transactions WHERE error IS NOT NULL ORDER BY requestTime DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<io.github.shaharzohar.netguard.traffic.models.HttpTransaction>> getErrors();
    
    @androidx.room.Query(value = "DELETE FROM http_transactions")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM http_transactions WHERE requestTime < :timestamp")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteOlderThan(long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM http_transactions")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT AVG(durationMs) FROM http_transactions WHERE durationMs IS NOT NULL")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAverageDuration(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Double> $completion);
}