package io.github.shaharzohar.netguard.traffic.storage;

/**
 * Repository for storing and retrieving HTTP transaction logs.
 *
 * Provides a clean API over the Room database for transaction persistence.
 *
 * @since 1.0.0
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \'2\u00020\u0001:\u0001\'B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0086@\u00a2\u0006\u0002\u0010\u0007J\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012J\"\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u00142\u0006\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\nJ\u0012\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u0014J\u000e\u0010\u0018\u001a\u00020\u0019H\u0086@\u00a2\u0006\u0002\u0010\u0007J\u0012\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u0014J\u001c\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u00142\b\b\u0002\u0010\u001c\u001a\u00020\nJ\u0016\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u001fJ\u001a\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u00142\u0006\u0010!\u001a\u00020\"J*\u0010#\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0%H\u0086@\u00a2\u0006\u0002\u0010&R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006("}, d2 = {"Lio/github/shaharzohar/netguard/traffic/storage/TransactionRepository;", "", "dao", "Lio/github/shaharzohar/netguard/traffic/storage/TransactionDao;", "(Lio/github/shaharzohar/netguard/traffic/storage/TransactionDao;)V", "clearAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOlderThan", "days", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "", "Lio/github/shaharzohar/netguard/traffic/models/HttpTransaction;", "getById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getByStatusCodeRange", "Lkotlinx/coroutines/flow/Flow;", "minCode", "maxCode", "getErrors", "getSummary", "Lio/github/shaharzohar/netguard/traffic/models/TrafficSummary;", "observeAll", "observeRecent", "limit", "save", "transaction", "(Lio/github/shaharzohar/netguard/traffic/models/HttpTransaction;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "search", "query", "", "updateTransaction", "update", "Lkotlin/Function1;", "(JLkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "netguard-traffic_debug"})
public final class TransactionRepository {
    @org.jetbrains.annotations.NotNull()
    private final io.github.shaharzohar.netguard.traffic.storage.TransactionDao dao = null;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile io.github.shaharzohar.netguard.traffic.storage.TransactionRepository INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final io.github.shaharzohar.netguard.traffic.storage.TransactionRepository.Companion Companion = null;
    
    private TransactionRepository(io.github.shaharzohar.netguard.traffic.storage.TransactionDao dao) {
        super();
    }
    
    /**
     * Save a new transaction.
     *
     * @param transaction Transaction to save
     * @return The ID of the saved transaction
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object save(@org.jetbrains.annotations.NotNull()
    io.github.shaharzohar.netguard.traffic.models.HttpTransaction transaction, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    /**
     * Update an existing transaction.
     *
     * @param id Transaction ID
     * @param update Lambda to modify the transaction
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateTransaction(long id, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super io.github.shaharzohar.netguard.traffic.models.HttpTransaction, io.github.shaharzohar.netguard.traffic.models.HttpTransaction> update, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Get a transaction by ID.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super io.github.shaharzohar.netguard.traffic.models.HttpTransaction> $completion) {
        return null;
    }
    
    /**
     * Observe all transactions as a Flow.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<io.github.shaharzohar.netguard.traffic.models.HttpTransaction>> observeAll() {
        return null;
    }
    
    /**
     * Observe recent transactions.
     *
     * @param limit Maximum number of transactions to return
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<io.github.shaharzohar.netguard.traffic.models.HttpTransaction>> observeRecent(int limit) {
        return null;
    }
    
    /**
     * Search transactions by URL or method.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<io.github.shaharzohar.netguard.traffic.models.HttpTransaction>> search(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    /**
     * Get transactions with specific status code range.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<io.github.shaharzohar.netguard.traffic.models.HttpTransaction>> getByStatusCodeRange(int minCode, int maxCode) {
        return null;
    }
    
    /**
     * Get all error transactions.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<io.github.shaharzohar.netguard.traffic.models.HttpTransaction>> getErrors() {
        return null;
    }
    
    /**
     * Get all transactions synchronously.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<io.github.shaharzohar.netguard.traffic.models.HttpTransaction>> $completion) {
        return null;
    }
    
    /**
     * Clear all transactions.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Delete transactions older than specified duration.
     *
     * @param days Number of days to retain
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteOlderThan(int days, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Get traffic summary statistics.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getSummary(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super io.github.shaharzohar.netguard.traffic.models.TrafficSummary> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lio/github/shaharzohar/netguard/traffic/storage/TransactionRepository$Companion;", "", "()V", "INSTANCE", "Lio/github/shaharzohar/netguard/traffic/storage/TransactionRepository;", "getInstance", "context", "Landroid/content/Context;", "netguard-traffic_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final io.github.shaharzohar.netguard.traffic.storage.TransactionRepository getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}