package io.github.shaharzohar.netguard.traffic.ui;

/**
 * Activity showing detailed information about a single HTTP transaction.
 *
 * @since 1.0.0
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u0002J\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u0002J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u0002J\u001c\u0010\f\u001a\u00020\b2\b\u0010\r\u001a\u0004\u0018\u00010\b2\b\u0010\u000e\u001a\u0004\u0018\u00010\bH\u0002J\u0012\u0010\u000f\u001a\u00020\b2\b\u0010\u0010\u001a\u0004\u0018\u00010\bH\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0012\u0010\u0015\u001a\u00020\u00122\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J\b\u0010\u0018\u001a\u00020\u0012H\u0002J\b\u0010\u0019\u001a\u00020\u0012H\u0002J\u0018\u0010\u001a\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u001cH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lio/github/shaharzohar/netguard/traffic/ui/TransactionDetailActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "contentView", "Landroid/widget/TextView;", "transaction", "Lio/github/shaharzohar/netguard/traffic/models/HttpTransaction;", "buildOverview", "", "tx", "buildRequestDetails", "buildResponseDetails", "formatBody", "body", "contentType", "formatHeaders", "headersJson", "loadTransaction", "", "id", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setupTabs", "setupToolbar", "showContent", "tabIndex", "", "Companion", "netguard-traffic_debug"})
public final class TransactionDetailActivity extends androidx.appcompat.app.AppCompatActivity {
    private android.widget.TextView contentView;
    @org.jetbrains.annotations.Nullable()
    private io.github.shaharzohar.netguard.traffic.models.HttpTransaction transaction;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String EXTRA_TRANSACTION_ID = "transaction_id";
    @org.jetbrains.annotations.NotNull()
    public static final io.github.shaharzohar.netguard.traffic.ui.TransactionDetailActivity.Companion Companion = null;
    
    public TransactionDetailActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupToolbar() {
    }
    
    private final void setupTabs() {
    }
    
    private final void loadTransaction(long id) {
    }
    
    private final void showContent(io.github.shaharzohar.netguard.traffic.models.HttpTransaction tx, int tabIndex) {
    }
    
    private final java.lang.String buildOverview(io.github.shaharzohar.netguard.traffic.models.HttpTransaction tx) {
        return null;
    }
    
    private final java.lang.String buildRequestDetails(io.github.shaharzohar.netguard.traffic.models.HttpTransaction tx) {
        return null;
    }
    
    private final java.lang.String buildResponseDetails(io.github.shaharzohar.netguard.traffic.models.HttpTransaction tx) {
        return null;
    }
    
    private final java.lang.String formatHeaders(java.lang.String headersJson) {
        return null;
    }
    
    private final java.lang.String formatBody(java.lang.String body, java.lang.String contentType) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lio/github/shaharzohar/netguard/traffic/ui/TransactionDetailActivity$Companion;", "", "()V", "EXTRA_TRANSACTION_ID", "", "start", "", "context", "Landroid/content/Context;", "transactionId", "", "netguard-traffic_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final void start(@org.jetbrains.annotations.NotNull()
        android.content.Context context, long transactionId) {
        }
    }
}