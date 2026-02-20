package io.github.shaharzohar.netguard.traffic.storage;

/**
 * Room database for storing HTTP transactions.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&\u00a8\u0006\u0006"}, d2 = {"Lio/github/shaharzohar/netguard/traffic/storage/NetGuardDatabase;", "Landroidx/room/RoomDatabase;", "()V", "transactionDao", "Lio/github/shaharzohar/netguard/traffic/storage/TransactionDao;", "Companion", "netguard-traffic_debug"})
@androidx.room.Database(entities = {io.github.shaharzohar.netguard.traffic.models.HttpTransaction.class}, version = 1, exportSchema = false)
public abstract class NetGuardDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String DATABASE_NAME = "netguard_traffic.db";
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile io.github.shaharzohar.netguard.traffic.storage.NetGuardDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final io.github.shaharzohar.netguard.traffic.storage.NetGuardDatabase.Companion Companion = null;
    
    public NetGuardDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract io.github.shaharzohar.netguard.traffic.storage.TransactionDao transactionDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lio/github/shaharzohar/netguard/traffic/storage/NetGuardDatabase$Companion;", "", "()V", "DATABASE_NAME", "", "INSTANCE", "Lio/github/shaharzohar/netguard/traffic/storage/NetGuardDatabase;", "getInstance", "context", "Landroid/content/Context;", "netguard-traffic_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final io.github.shaharzohar.netguard.traffic.storage.NetGuardDatabase getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}