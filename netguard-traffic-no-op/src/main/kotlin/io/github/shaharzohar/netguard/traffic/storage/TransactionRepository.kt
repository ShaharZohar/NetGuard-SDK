package io.github.shaharzohar.netguard.traffic.storage

import android.content.Context
import io.github.shaharzohar.netguard.traffic.models.HttpTransaction
import io.github.shaharzohar.netguard.traffic.models.TrafficSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class TransactionRepository private constructor() {
    suspend fun save(transaction: HttpTransaction): Long = 0
    suspend fun updateTransaction(id: Long, update: (HttpTransaction) -> HttpTransaction) {}
    suspend fun getById(id: Long): HttpTransaction? = null
    fun observeAll(): Flow<List<HttpTransaction>> = emptyFlow()
    fun observeRecent(limit: Int = 100): Flow<List<HttpTransaction>> = emptyFlow()
    fun search(query: String): Flow<List<HttpTransaction>> = emptyFlow()
    fun getByStatusCodeRange(minCode: Int, maxCode: Int): Flow<List<HttpTransaction>> = emptyFlow()
    fun getErrors(): Flow<List<HttpTransaction>> = emptyFlow()
    suspend fun getAll(): List<HttpTransaction> = emptyList()
    suspend fun clearAll() {}
    suspend fun deleteOlderThan(days: Int) {}
    suspend fun getSummary(): TrafficSummary = TrafficSummary()
    companion object {
        private val INSTANCE = TransactionRepository()
        fun getInstance(context: Context): TransactionRepository = INSTANCE
    }
}
