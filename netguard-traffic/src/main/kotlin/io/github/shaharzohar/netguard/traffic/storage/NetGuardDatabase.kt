package io.github.shaharzohar.netguard.traffic.storage

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import io.github.shaharzohar.netguard.traffic.models.HttpTransaction
import kotlinx.coroutines.flow.Flow

/**
 * Room DAO for HTTP transactions.
 */
@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(transaction: HttpTransaction): Long

    @Update
    suspend fun update(transaction: HttpTransaction)

    @Query("SELECT * FROM http_transactions WHERE id = :id")
    suspend fun getById(id: Long): HttpTransaction?

    @Query("SELECT * FROM http_transactions ORDER BY requestTime DESC")
    fun getAllFlow(): Flow<List<HttpTransaction>>

    @Query("SELECT * FROM http_transactions ORDER BY requestTime DESC LIMIT :limit")
    fun getRecentFlow(limit: Int): Flow<List<HttpTransaction>>

    @Query("SELECT * FROM http_transactions ORDER BY requestTime DESC")
    suspend fun getAll(): List<HttpTransaction>

    @Query("SELECT * FROM http_transactions WHERE url LIKE '%' || :query || '%' OR method LIKE '%' || :query || '%' ORDER BY requestTime DESC")
    fun search(query: String): Flow<List<HttpTransaction>>

    @Query("SELECT * FROM http_transactions WHERE responseCode >= :minCode AND responseCode < :maxCode ORDER BY requestTime DESC")
    fun getByStatusCodeRange(minCode: Int, maxCode: Int): Flow<List<HttpTransaction>>

    @Query("SELECT * FROM http_transactions WHERE error IS NOT NULL ORDER BY requestTime DESC")
    fun getErrors(): Flow<List<HttpTransaction>>

    @Query("DELETE FROM http_transactions")
    suspend fun deleteAll()

    @Query("DELETE FROM http_transactions WHERE requestTime < :timestamp")
    suspend fun deleteOlderThan(timestamp: Long)

    @Query("SELECT COUNT(*) FROM http_transactions")
    suspend fun getCount(): Int

    @Query("SELECT AVG(durationMs) FROM http_transactions WHERE durationMs IS NOT NULL")
    suspend fun getAverageDuration(): Double?
}

/**
 * Room database for storing HTTP transactions.
 */
@Database(
    entities = [HttpTransaction::class],
    version = 1,
    exportSchema = false
)
abstract class NetGuardDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {
        private const val DATABASE_NAME = "netguard_traffic.db"

        @Volatile
        private var INSTANCE: NetGuardDatabase? = null

        fun getInstance(context: Context): NetGuardDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    NetGuardDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
