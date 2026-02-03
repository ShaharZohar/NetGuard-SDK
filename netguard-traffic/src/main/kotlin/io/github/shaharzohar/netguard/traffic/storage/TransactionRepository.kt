package io.github.shaharzohar.netguard.traffic.storage

import android.content.Context
import io.github.shaharzohar.netguard.core.NetGuard
import io.github.shaharzohar.netguard.traffic.models.HttpTransaction
import io.github.shaharzohar.netguard.traffic.models.TrafficSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

/**
 * Repository for storing and retrieving HTTP transaction logs.
 *
 * Provides a clean API over the Room database for transaction persistence.
 *
 * @since 1.0.0
 */
class TransactionRepository private constructor(
    private val dao: TransactionDao
) {

    /**
     * Save a new transaction.
     *
     * @param transaction Transaction to save
     * @return The ID of the saved transaction
     */
    suspend fun save(transaction: HttpTransaction): Long = withContext(Dispatchers.IO) {
        dao.insert(transaction)
    }

    /**
     * Update an existing transaction.
     *
     * @param id Transaction ID
     * @param update Lambda to modify the transaction
     */
    suspend fun updateTransaction(id: Long, update: (HttpTransaction) -> HttpTransaction) {
        withContext(Dispatchers.IO) {
            dao.getById(id)?.let { existing ->
                dao.update(update(existing))
            }
        }
    }

    /**
     * Get a transaction by ID.
     */
    suspend fun getById(id: Long): HttpTransaction? = withContext(Dispatchers.IO) {
        dao.getById(id)
    }

    /**
     * Observe all transactions as a Flow.
     */
    fun observeAll(): Flow<List<HttpTransaction>> = dao.getAllFlow()

    /**
     * Observe recent transactions.
     *
     * @param limit Maximum number of transactions to return
     */
    fun observeRecent(limit: Int = 100): Flow<List<HttpTransaction>> = dao.getRecentFlow(limit)

    /**
     * Search transactions by URL or method.
     */
    fun search(query: String): Flow<List<HttpTransaction>> = dao.search(query)

    /**
     * Get transactions with specific status code range.
     */
    fun getByStatusCodeRange(minCode: Int, maxCode: Int): Flow<List<HttpTransaction>> =
        dao.getByStatusCodeRange(minCode, maxCode)

    /**
     * Get all error transactions.
     */
    fun getErrors(): Flow<List<HttpTransaction>> = dao.getErrors()

    /**
     * Get all transactions synchronously.
     */
    suspend fun getAll(): List<HttpTransaction> = withContext(Dispatchers.IO) {
        dao.getAll()
    }

    /**
     * Clear all transactions.
     */
    suspend fun clearAll() = withContext(Dispatchers.IO) {
        dao.deleteAll()
    }

    /**
     * Delete transactions older than specified duration.
     *
     * @param days Number of days to retain
     */
    suspend fun deleteOlderThan(days: Int) = withContext(Dispatchers.IO) {
        val cutoffTime = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(days.toLong())
        dao.deleteOlderThan(cutoffTime)
    }

    /**
     * Get traffic summary statistics.
     */
    suspend fun getSummary(): TrafficSummary = withContext(Dispatchers.IO) {
        val transactions = dao.getAll()
        val successful = transactions.count { it.isSuccess }
        val failed = transactions.count { it.isError }
        val avgDuration = dao.getAverageDuration()?.toLong() ?: 0L

        TrafficSummary(
            totalRequests = transactions.size,
            successfulRequests = successful,
            failedRequests = failed,
            averageDurationMs = avgDuration,
            totalBytesReceived = transactions.sumOf { it.responseContentLength ?: 0 },
            totalBytesSent = transactions.sumOf { it.requestContentLength ?: 0 }
        )
    }

    companion object {
        @Volatile
        private var INSTANCE: TransactionRepository? = null

        fun getInstance(context: Context): TransactionRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: TransactionRepository(
                    NetGuardDatabase.getInstance(context).transactionDao()
                ).also { INSTANCE = it }
            }
        }
    }
}
