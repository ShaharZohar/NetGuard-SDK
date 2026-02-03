package io.github.shaharzohar.netguard.traffic.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import io.github.shaharzohar.netguard.traffic.R
import io.github.shaharzohar.netguard.traffic.models.HttpTransaction
import io.github.shaharzohar.netguard.traffic.storage.TransactionRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Activity showing detailed information about a single HTTP transaction.
 *
 * @since 1.0.0
 */
class TransactionDetailActivity : AppCompatActivity() {

    private lateinit var contentView: TextView
    private var transaction: HttpTransaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_detail)

        val transactionId = intent.getLongExtra(EXTRA_TRANSACTION_ID, -1)
        if (transactionId == -1L) {
            finish()
            return
        }

        setupToolbar()
        setupTabs()
        loadTransaction(transactionId)
    }

    private fun setupToolbar() {
        findViewById<MaterialToolbar>(R.id.toolbar).apply {
            setNavigationOnClickListener { finish() }
        }
    }

    private fun setupTabs() {
        contentView = findViewById(R.id.contentView)

        findViewById<TabLayout>(R.id.tabLayout).addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    transaction?.let { showContent(it, tab.position) }
                }
                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            }
        )
    }

    private fun loadTransaction(id: Long) {
        val repository = TransactionRepository.getInstance(applicationContext)

        lifecycleScope.launch {
            transaction = repository.getById(id)
            transaction?.let { tx ->
                findViewById<MaterialToolbar>(R.id.toolbar).subtitle = tx.summary
                showContent(tx, 0)
            }
        }
    }

    private fun showContent(tx: HttpTransaction, tabIndex: Int) {
        val content = when (tabIndex) {
            0 -> buildOverview(tx)
            1 -> buildRequestDetails(tx)
            2 -> buildResponseDetails(tx)
            else -> ""
        }
        contentView.text = content
    }

    private fun buildOverview(tx: HttpTransaction): String = buildString {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())

        appendLine("═══ OVERVIEW ═══")
        appendLine()
        appendLine("URL: ${tx.url}")
        appendLine("Method: ${tx.method}")
        appendLine("Status: ${tx.responseCode ?: "Pending"} ${tx.responseMessage ?: ""}")
        appendLine()
        appendLine("═══ TIMING ═══")
        appendLine()
        appendLine("Request Time: ${dateFormat.format(Date(tx.requestTime))}")
        tx.responseTime?.let {
            appendLine("Response Time: ${dateFormat.format(Date(it))}")
        }
        tx.durationMs?.let {
            appendLine("Duration: ${it}ms")
        }
        appendLine()
        appendLine("═══ CONNECTION ═══")
        appendLine()
        tx.protocol?.let { appendLine("Protocol: $it") }
        tx.tlsVersion?.let { appendLine("TLS Version: $it") }
        tx.cipherSuite?.let { appendLine("Cipher Suite: $it") }

        tx.error?.let {
            appendLine()
            appendLine("═══ ERROR ═══")
            appendLine()
            appendLine(it)
        }
    }

    private fun buildRequestDetails(tx: HttpTransaction): String = buildString {
        appendLine("═══ REQUEST HEADERS ═══")
        appendLine()
        appendLine(formatHeaders(tx.requestHeaders))
        appendLine()
        appendLine("═══ REQUEST BODY ═══")
        appendLine()
        appendLine(tx.requestBody ?: "[No body]")
    }

    private fun buildResponseDetails(tx: HttpTransaction): String = buildString {
        appendLine("═══ RESPONSE HEADERS ═══")
        appendLine()
        appendLine(formatHeaders(tx.responseHeaders))
        appendLine()
        appendLine("═══ RESPONSE BODY ═══")
        appendLine()
        appendLine(formatBody(tx.responseBody, tx.responseContentType))
    }

    private fun formatHeaders(headersJson: String?): String {
        if (headersJson.isNullOrBlank()) return "[No headers]"
        return try {
            val json = JSONObject(headersJson)
            buildString {
                json.keys().forEach { key ->
                    appendLine("$key: ${json.getString(key)}")
                }
            }
        } catch (e: Exception) {
            headersJson
        }
    }

    private fun formatBody(body: String?, contentType: String?): String {
        if (body.isNullOrBlank()) return "[No body]"

        // Try to pretty-print JSON
        if (contentType?.contains("json", ignoreCase = true) == true) {
            return try {
                JSONObject(body).toString(2)
            } catch (e: Exception) {
                body
            }
        }

        return body
    }

    companion object {
        private const val EXTRA_TRANSACTION_ID = "transaction_id"

        fun start(context: Context, transactionId: Long) {
            context.startActivity(
                Intent(context, TransactionDetailActivity::class.java)
                    .putExtra(EXTRA_TRANSACTION_ID, transactionId)
            )
        }
    }
}
