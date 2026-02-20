package io.github.shaharzohar.netguard.traffic.storage;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class NetGuardDatabase_Impl extends NetGuardDatabase {
  private volatile TransactionDao _transactionDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `http_transactions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `url` TEXT NOT NULL, `method` TEXT NOT NULL, `requestHeaders` TEXT NOT NULL, `requestBody` TEXT, `requestContentType` TEXT, `requestContentLength` INTEGER, `requestTime` INTEGER NOT NULL, `responseCode` INTEGER, `responseMessage` TEXT, `responseHeaders` TEXT, `responseBody` TEXT, `responseContentType` TEXT, `responseContentLength` INTEGER, `responseTime` INTEGER, `durationMs` INTEGER, `protocol` TEXT, `tlsVersion` TEXT, `cipherSuite` TEXT, `error` TEXT, `isComplete` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '5353b4a796d6d2be218721c1385232b4')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `http_transactions`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsHttpTransactions = new HashMap<String, TableInfo.Column>(21);
        _columnsHttpTransactions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("url", new TableInfo.Column("url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("method", new TableInfo.Column("method", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("requestHeaders", new TableInfo.Column("requestHeaders", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("requestBody", new TableInfo.Column("requestBody", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("requestContentType", new TableInfo.Column("requestContentType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("requestContentLength", new TableInfo.Column("requestContentLength", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("requestTime", new TableInfo.Column("requestTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("responseCode", new TableInfo.Column("responseCode", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("responseMessage", new TableInfo.Column("responseMessage", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("responseHeaders", new TableInfo.Column("responseHeaders", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("responseBody", new TableInfo.Column("responseBody", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("responseContentType", new TableInfo.Column("responseContentType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("responseContentLength", new TableInfo.Column("responseContentLength", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("responseTime", new TableInfo.Column("responseTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("durationMs", new TableInfo.Column("durationMs", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("protocol", new TableInfo.Column("protocol", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("tlsVersion", new TableInfo.Column("tlsVersion", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("cipherSuite", new TableInfo.Column("cipherSuite", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("error", new TableInfo.Column("error", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHttpTransactions.put("isComplete", new TableInfo.Column("isComplete", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHttpTransactions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHttpTransactions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHttpTransactions = new TableInfo("http_transactions", _columnsHttpTransactions, _foreignKeysHttpTransactions, _indicesHttpTransactions);
        final TableInfo _existingHttpTransactions = TableInfo.read(db, "http_transactions");
        if (!_infoHttpTransactions.equals(_existingHttpTransactions)) {
          return new RoomOpenHelper.ValidationResult(false, "http_transactions(io.github.shaharzohar.netguard.traffic.models.HttpTransaction).\n"
                  + " Expected:\n" + _infoHttpTransactions + "\n"
                  + " Found:\n" + _existingHttpTransactions);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "5353b4a796d6d2be218721c1385232b4", "087d81ba92b46604e85eaed300673a06");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "http_transactions");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `http_transactions`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(TransactionDao.class, TransactionDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public TransactionDao transactionDao() {
    if (_transactionDao != null) {
      return _transactionDao;
    } else {
      synchronized(this) {
        if(_transactionDao == null) {
          _transactionDao = new TransactionDao_Impl(this);
        }
        return _transactionDao;
      }
    }
  }
}
