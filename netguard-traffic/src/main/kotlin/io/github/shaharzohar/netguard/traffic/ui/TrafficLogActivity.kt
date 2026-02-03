package io.github.shaharzohar.netguard.traffic.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import io.github.shaharzohar.netguard.traffic.R
import io.github.shaharzohar.netguard.traffic.models.HttpTransaction
import io.github.shaharzohar.netguard.traffic.storage.TransactionRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
class TrafficLogActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var adapter: TransactionAdapter
    private lateinit var repository: TransactionRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traffic_log)

        repository = TransactionRepository.getInstance(applicationContext)

        setupToolbar()
        setupRecyclerView()
        observeTransactions()
    }

    private fun setupToolbar() {
        findViewById<MaterialToolbar>(R.id.toolbar).apply {
            title = "Network Traffic"
            setNavigationOnClickListener { finish() }
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_clear -> {
                        lifecycleScope.launch {
                            repository.clearAll()
                        }
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        emptyView = findViewById(R.id.emptyView)

        adapter = TransactionAdapter { transaction ->
            TransactionDetailActivity.start(this, transaction.id)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun observeTransactions() {
        lifecycleScope.launch {
            repository.observeRecent(500).collectLatest { transactions ->
                adapter.submitList(transactions)
                emptyView.visibility = if (transactions.isEmpty()) View.VISIBLE else View.GONE
                recyclerView.visibility = if (transactions.isEmpty()) View.GONE else View.VISIBLE
            }
        }
    }

    companion object {
        /**
         * Start the traffic log activity.
         */
        fun start(context: Context) {
            context.startActivity(Intent(context, TrafficLogActivity::class.java))
        }
    }
}

/**
 * RecyclerView adapter for HTTP transactions.
 */
internal class TransactionAdapter(
    private val onClick: (HttpTransaction) -> Unit
) : ListAdapter<HttpTransaction, TransactionAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val methodView: TextView = itemView.findViewById(R.id.methodText)
        private val statusView: TextView = itemView.findViewById(R.id.statusText)
        private val pathView: TextView = itemView.findViewById(R.id.pathText)
        private val hostView: TextView = itemView.findViewById(R.id.hostText)
        private val durationView: TextView = itemView.findViewById(R.id.durationText)
        private val timeView: TextView = itemView.findViewById(R.id.timeText)

        private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        fun bind(transaction: HttpTransaction, onClick: (HttpTransaction) -> Unit) {
            methodView.text = transaction.method
            pathView.text = transaction.path
            hostView.text = transaction.host
            timeView.text = dateFormat.format(Date(transaction.requestTime))

            // Status code with color
            val code = transaction.responseCode
            statusView.text = code?.toString() ?: "..."
            statusView.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    when {
                        code == null -> R.color.status_pending
                        code < 300 -> R.color.status_success
                        code < 400 -> R.color.status_redirect
                        code < 500 -> R.color.status_client_error
                        else -> R.color.status_server_error
                    }
                )
            )

            // Duration
            durationView.text = transaction.durationMs?.let { "${it}ms" } ?: "-"

            // Error indicator
            if (transaction.error != null) {
                statusView.text = "ERR"
                statusView.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.status_server_error)
                )
            }

            itemView.setOnClickListener { onClick(transaction) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HttpTransaction>() {
            override fun areItemsTheSame(old: HttpTransaction, new: HttpTransaction) =
                old.id == new.id

            override fun areContentsTheSame(old: HttpTransaction, new: HttpTransaction) =
                old == new
        }
    }
}
