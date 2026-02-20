package io.github.shaharzohar.netguard.traffic.ui;

/**
 * Activity for viewing HTTP traffic logs.
 *
 * Launch this activity to see a list of all captured HTTP transactions
 * with details about each request/response.
 *
 * ## Usage
 *
 * ```kotlin
 * TrafficLogActivity.start(context)
 * ```
 *
 * @since 1.0.0
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\u0012\u0010\u000f\u001a\u00020\u000e2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014J\b\u0010\u0012\u001a\u00020\u000eH\u0002J\b\u0010\u0013\u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lio/github/shaharzohar/netguard/traffic/ui/TrafficLogActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lio/github/shaharzohar/netguard/traffic/ui/TransactionAdapter;", "emptyView", "Landroid/widget/TextView;", "harShareHelper", "Lio/github/shaharzohar/netguard/traffic/export/HarShareHelper;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "repository", "Lio/github/shaharzohar/netguard/traffic/storage/TransactionRepository;", "observeTransactions", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setupRecyclerView", "setupToolbar", "Companion", "netguard-traffic_debug"})
public final class TrafficLogActivity extends androidx.appcompat.app.AppCompatActivity {
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private android.widget.TextView emptyView;
    private io.github.shaharzohar.netguard.traffic.ui.TransactionAdapter adapter;
    private io.github.shaharzohar.netguard.traffic.storage.TransactionRepository repository;
    private io.github.shaharzohar.netguard.traffic.export.HarShareHelper harShareHelper;
    @org.jetbrains.annotations.NotNull()
    public static final io.github.shaharzohar.netguard.traffic.ui.TrafficLogActivity.Companion Companion = null;
    
    public TrafficLogActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupToolbar() {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void observeTransactions() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lio/github/shaharzohar/netguard/traffic/ui/TrafficLogActivity$Companion;", "", "()V", "start", "", "context", "Landroid/content/Context;", "netguard-traffic_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Start the traffic log activity.
         */
        public final void start(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
    }
}