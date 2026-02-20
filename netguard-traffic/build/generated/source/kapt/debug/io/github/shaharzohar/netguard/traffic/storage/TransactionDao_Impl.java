package io.github.shaharzohar.netguard.traffic.storage;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import io.github.shaharzohar.netguard.traffic.models.HttpTransaction;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TransactionDao_Impl implements TransactionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<HttpTransaction> __insertionAdapterOfHttpTransaction;

  private final EntityDeletionOrUpdateAdapter<HttpTransaction> __updateAdapterOfHttpTransaction;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOlderThan;

  public TransactionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHttpTransaction = new EntityInsertionAdapter<HttpTransaction>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `http_transactions` (`id`,`url`,`method`,`requestHeaders`,`requestBody`,`requestContentType`,`requestContentLength`,`requestTime`,`responseCode`,`responseMessage`,`responseHeaders`,`responseBody`,`responseContentType`,`responseContentLength`,`responseTime`,`durationMs`,`protocol`,`tlsVersion`,`cipherSuite`,`error`,`isComplete`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HttpTransaction entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getUrl() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getUrl());
        }
        if (entity.getMethod() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getMethod());
        }
        if (entity.getRequestHeaders() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getRequestHeaders());
        }
        if (entity.getRequestBody() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getRequestBody());
        }
        if (entity.getRequestContentType() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getRequestContentType());
        }
        if (entity.getRequestContentLength() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getRequestContentLength());
        }
        statement.bindLong(8, entity.getRequestTime());
        if (entity.getResponseCode() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getResponseCode());
        }
        if (entity.getResponseMessage() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getResponseMessage());
        }
        if (entity.getResponseHeaders() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getResponseHeaders());
        }
        if (entity.getResponseBody() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getResponseBody());
        }
        if (entity.getResponseContentType() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getResponseContentType());
        }
        if (entity.getResponseContentLength() == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, entity.getResponseContentLength());
        }
        if (entity.getResponseTime() == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, entity.getResponseTime());
        }
        if (entity.getDurationMs() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getDurationMs());
        }
        if (entity.getProtocol() == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.getProtocol());
        }
        if (entity.getTlsVersion() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getTlsVersion());
        }
        if (entity.getCipherSuite() == null) {
          statement.bindNull(19);
        } else {
          statement.bindString(19, entity.getCipherSuite());
        }
        if (entity.getError() == null) {
          statement.bindNull(20);
        } else {
          statement.bindString(20, entity.getError());
        }
        final int _tmp = entity.isComplete() ? 1 : 0;
        statement.bindLong(21, _tmp);
      }
    };
    this.__updateAdapterOfHttpTransaction = new EntityDeletionOrUpdateAdapter<HttpTransaction>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `http_transactions` SET `id` = ?,`url` = ?,`method` = ?,`requestHeaders` = ?,`requestBody` = ?,`requestContentType` = ?,`requestContentLength` = ?,`requestTime` = ?,`responseCode` = ?,`responseMessage` = ?,`responseHeaders` = ?,`responseBody` = ?,`responseContentType` = ?,`responseContentLength` = ?,`responseTime` = ?,`durationMs` = ?,`protocol` = ?,`tlsVersion` = ?,`cipherSuite` = ?,`error` = ?,`isComplete` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HttpTransaction entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getUrl() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getUrl());
        }
        if (entity.getMethod() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getMethod());
        }
        if (entity.getRequestHeaders() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getRequestHeaders());
        }
        if (entity.getRequestBody() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getRequestBody());
        }
        if (entity.getRequestContentType() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getRequestContentType());
        }
        if (entity.getRequestContentLength() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getRequestContentLength());
        }
        statement.bindLong(8, entity.getRequestTime());
        if (entity.getResponseCode() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getResponseCode());
        }
        if (entity.getResponseMessage() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getResponseMessage());
        }
        if (entity.getResponseHeaders() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getResponseHeaders());
        }
        if (entity.getResponseBody() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getResponseBody());
        }
        if (entity.getResponseContentType() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getResponseContentType());
        }
        if (entity.getResponseContentLength() == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, entity.getResponseContentLength());
        }
        if (entity.getResponseTime() == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, entity.getResponseTime());
        }
        if (entity.getDurationMs() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getDurationMs());
        }
        if (entity.getProtocol() == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.getProtocol());
        }
        if (entity.getTlsVersion() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getTlsVersion());
        }
        if (entity.getCipherSuite() == null) {
          statement.bindNull(19);
        } else {
          statement.bindString(19, entity.getCipherSuite());
        }
        if (entity.getError() == null) {
          statement.bindNull(20);
        } else {
          statement.bindString(20, entity.getError());
        }
        final int _tmp = entity.isComplete() ? 1 : 0;
        statement.bindLong(21, _tmp);
        statement.bindLong(22, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM http_transactions";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOlderThan = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM http_transactions WHERE requestTime < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final HttpTransaction transaction,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfHttpTransaction.insertAndReturnId(transaction);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final HttpTransaction transaction,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfHttpTransaction.handle(transaction);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOlderThan(final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOlderThan.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteOlderThan.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getById(final long id, final Continuation<? super HttpTransaction> $completion) {
    final String _sql = "SELECT * FROM http_transactions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<HttpTransaction>() {
      @Override
      @Nullable
      public HttpTransaction call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "method");
          final int _cursorIndexOfRequestHeaders = CursorUtil.getColumnIndexOrThrow(_cursor, "requestHeaders");
          final int _cursorIndexOfRequestBody = CursorUtil.getColumnIndexOrThrow(_cursor, "requestBody");
          final int _cursorIndexOfRequestContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "requestContentType");
          final int _cursorIndexOfRequestContentLength = CursorUtil.getColumnIndexOrThrow(_cursor, "requestContentLength");
          final int _cursorIndexOfRequestTime = CursorUtil.getColumnIndexOrThrow(_cursor, "requestTime");
          final int _cursorIndexOfResponseCode = CursorUtil.getColumnIndexOrThrow(_cursor, "responseCode");
          final int _cursorIndexOfResponseMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "responseMessage");
          final int _cursorIndexOfResponseHeaders = CursorUtil.getColumnIndexOrThrow(_cursor, "responseHeaders");
          final int _cursorIndexOfResponseBody = CursorUtil.getColumnIndexOrThrow(_cursor, "responseBody");
          final int _cursorIndexOfResponseContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "responseContentType");
          final int _cursorIndexOfResponseContentLength = CursorUtil.getColumnIndexOrThrow(_cursor, "responseContentLength");
          final int _cursorIndexOfResponseTime = CursorUtil.getColumnIndexOrThrow(_cursor, "responseTime");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfTlsVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "tlsVersion");
          final int _cursorIndexOfCipherSuite = CursorUtil.getColumnIndexOrThrow(_cursor, "cipherSuite");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfIsComplete = CursorUtil.getColumnIndexOrThrow(_cursor, "isComplete");
          final HttpTransaction _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final String _tmpMethod;
            if (_cursor.isNull(_cursorIndexOfMethod)) {
              _tmpMethod = null;
            } else {
              _tmpMethod = _cursor.getString(_cursorIndexOfMethod);
            }
            final String _tmpRequestHeaders;
            if (_cursor.isNull(_cursorIndexOfRequestHeaders)) {
              _tmpRequestHeaders = null;
            } else {
              _tmpRequestHeaders = _cursor.getString(_cursorIndexOfRequestHeaders);
            }
            final String _tmpRequestBody;
            if (_cursor.isNull(_cursorIndexOfRequestBody)) {
              _tmpRequestBody = null;
            } else {
              _tmpRequestBody = _cursor.getString(_cursorIndexOfRequestBody);
            }
            final String _tmpRequestContentType;
            if (_cursor.isNull(_cursorIndexOfRequestContentType)) {
              _tmpRequestContentType = null;
            } else {
              _tmpRequestContentType = _cursor.getString(_cursorIndexOfRequestContentType);
            }
            final Long _tmpRequestContentLength;
            if (_cursor.isNull(_cursorIndexOfRequestContentLength)) {
              _tmpRequestContentLength = null;
            } else {
              _tmpRequestContentLength = _cursor.getLong(_cursorIndexOfRequestContentLength);
            }
            final long _tmpRequestTime;
            _tmpRequestTime = _cursor.getLong(_cursorIndexOfRequestTime);
            final Integer _tmpResponseCode;
            if (_cursor.isNull(_cursorIndexOfResponseCode)) {
              _tmpResponseCode = null;
            } else {
              _tmpResponseCode = _cursor.getInt(_cursorIndexOfResponseCode);
            }
            final String _tmpResponseMessage;
            if (_cursor.isNull(_cursorIndexOfResponseMessage)) {
              _tmpResponseMessage = null;
            } else {
              _tmpResponseMessage = _cursor.getString(_cursorIndexOfResponseMessage);
            }
            final String _tmpResponseHeaders;
            if (_cursor.isNull(_cursorIndexOfResponseHeaders)) {
              _tmpResponseHeaders = null;
            } else {
              _tmpResponseHeaders = _cursor.getString(_cursorIndexOfResponseHeaders);
            }
            final String _tmpResponseBody;
            if (_cursor.isNull(_cursorIndexOfResponseBody)) {
              _tmpResponseBody = null;
            } else {
              _tmpResponseBody = _cursor.getString(_cursorIndexOfResponseBody);
            }
            final String _tmpResponseContentType;
            if (_cursor.isNull(_cursorIndexOfResponseContentType)) {
              _tmpResponseContentType = null;
            } else {
              _tmpResponseContentType = _cursor.getString(_cursorIndexOfResponseContentType);
            }
            final Long _tmpResponseContentLength;
            if (_cursor.isNull(_cursorIndexOfResponseContentLength)) {
              _tmpResponseContentLength = null;
            } else {
              _tmpResponseContentLength = _cursor.getLong(_cursorIndexOfResponseContentLength);
            }
            final Long _tmpResponseTime;
            if (_cursor.isNull(_cursorIndexOfResponseTime)) {
              _tmpResponseTime = null;
            } else {
              _tmpResponseTime = _cursor.getLong(_cursorIndexOfResponseTime);
            }
            final Long _tmpDurationMs;
            if (_cursor.isNull(_cursorIndexOfDurationMs)) {
              _tmpDurationMs = null;
            } else {
              _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            }
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final String _tmpTlsVersion;
            if (_cursor.isNull(_cursorIndexOfTlsVersion)) {
              _tmpTlsVersion = null;
            } else {
              _tmpTlsVersion = _cursor.getString(_cursorIndexOfTlsVersion);
            }
            final String _tmpCipherSuite;
            if (_cursor.isNull(_cursorIndexOfCipherSuite)) {
              _tmpCipherSuite = null;
            } else {
              _tmpCipherSuite = _cursor.getString(_cursorIndexOfCipherSuite);
            }
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final boolean _tmpIsComplete;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsComplete);
            _tmpIsComplete = _tmp != 0;
            _result = new HttpTransaction(_tmpId,_tmpUrl,_tmpMethod,_tmpRequestHeaders,_tmpRequestBody,_tmpRequestContentType,_tmpRequestContentLength,_tmpRequestTime,_tmpResponseCode,_tmpResponseMessage,_tmpResponseHeaders,_tmpResponseBody,_tmpResponseContentType,_tmpResponseContentLength,_tmpResponseTime,_tmpDurationMs,_tmpProtocol,_tmpTlsVersion,_tmpCipherSuite,_tmpError,_tmpIsComplete);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<HttpTransaction>> getAllFlow() {
    final String _sql = "SELECT * FROM http_transactions ORDER BY requestTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"http_transactions"}, new Callable<List<HttpTransaction>>() {
      @Override
      @NonNull
      public List<HttpTransaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "method");
          final int _cursorIndexOfRequestHeaders = CursorUtil.getColumnIndexOrThrow(_cursor, "requestHeaders");
          final int _cursorIndexOfRequestBody = CursorUtil.getColumnIndexOrThrow(_cursor, "requestBody");
          final int _cursorIndexOfRequestContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "requestContentType");
          final int _cursorIndexOfRequestContentLength = CursorUtil.getColumnIndexOrThrow(_cursor, "requestContentLength");
          final int _cursorIndexOfRequestTime = CursorUtil.getColumnIndexOrThrow(_cursor, "requestTime");
          final int _cursorIndexOfResponseCode = CursorUtil.getColumnIndexOrThrow(_cursor, "responseCode");
          final int _cursorIndexOfResponseMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "responseMessage");
          final int _cursorIndexOfResponseHeaders = CursorUtil.getColumnIndexOrThrow(_cursor, "responseHeaders");
          final int _cursorIndexOfResponseBody = CursorUtil.getColumnIndexOrThrow(_cursor, "responseBody");
          final int _cursorIndexOfResponseContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "responseContentType");
          final int _cursorIndexOfResponseContentLength = CursorUtil.getColumnIndexOrThrow(_cursor, "responseContentLength");
          final int _cursorIndexOfResponseTime = CursorUtil.getColumnIndexOrThrow(_cursor, "responseTime");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfTlsVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "tlsVersion");
          final int _cursorIndexOfCipherSuite = CursorUtil.getColumnIndexOrThrow(_cursor, "cipherSuite");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfIsComplete = CursorUtil.getColumnIndexOrThrow(_cursor, "isComplete");
          final List<HttpTransaction> _result = new ArrayList<HttpTransaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HttpTransaction _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final String _tmpMethod;
            if (_cursor.isNull(_cursorIndexOfMethod)) {
              _tmpMethod = null;
            } else {
              _tmpMethod = _cursor.getString(_cursorIndexOfMethod);
            }
            final String _tmpRequestHeaders;
            if (_cursor.isNull(_cursorIndexOfRequestHeaders)) {
              _tmpRequestHeaders = null;
            } else {
              _tmpRequestHeaders = _cursor.getString(_cursorIndexOfRequestHeaders);
            }
            final String _tmpRequestBody;
            if (_cursor.isNull(_cursorIndexOfRequestBody)) {
              _tmpRequestBody = null;
            } else {
              _tmpRequestBody = _cursor.getString(_cursorIndexOfRequestBody);
            }
            final String _tmpRequestContentType;
            if (_cursor.isNull(_cursorIndexOfRequestContentType)) {
              _tmpRequestContentType = null;
            } else {
              _tmpRequestContentType = _cursor.getString(_cursorIndexOfRequestContentType);
            }
            final Long _tmpRequestContentLength;
            if (_cursor.isNull(_cursorIndexOfRequestContentLength)) {
              _tmpRequestContentLength = null;
            } else {
              _tmpRequestContentLength = _cursor.getLong(_cursorIndexOfRequestContentLength);
            }
            final long _tmpRequestTime;
            _tmpRequestTime = _cursor.getLong(_cursorIndexOfRequestTime);
            final Integer _tmpResponseCode;
            if (_cursor.isNull(_cursorIndexOfResponseCode)) {
              _tmpResponseCode = null;
            } else {
              _tmpResponseCode = _cursor.getInt(_cursorIndexOfResponseCode);
            }
            final String _tmpResponseMessage;
            if (_cursor.isNull(_cursorIndexOfResponseMessage)) {
              _tmpResponseMessage = null;
            } else {
              _tmpResponseMessage = _cursor.getString(_cursorIndexOfResponseMessage);
            }
            final String _tmpResponseHeaders;
            if (_cursor.isNull(_cursorIndexOfResponseHeaders)) {
              _tmpResponseHeaders = null;
            } else {
              _tmpResponseHeaders = _cursor.getString(_cursorIndexOfResponseHeaders);
            }
            final String _tmpResponseBody;
            if (_cursor.isNull(_cursorIndexOfResponseBody)) {
              _tmpResponseBody = null;
            } else {
              _tmpResponseBody = _cursor.getString(_cursorIndexOfResponseBody);
            }
            final String _tmpResponseContentType;
            if (_cursor.isNull(_cursorIndexOfResponseContentType)) {
              _tmpResponseContentType = null;
            } else {
              _tmpResponseContentType = _cursor.getString(_cursorIndexOfResponseContentType);
            }
            final Long _tmpResponseContentLength;
            if (_cursor.isNull(_cursorIndexOfResponseContentLength)) {
              _tmpResponseContentLength = null;
            } else {
              _tmpResponseContentLength = _cursor.getLong(_cursorIndexOfResponseContentLength);
            }
            final Long _tmpResponseTime;
            if (_cursor.isNull(_cursorIndexOfResponseTime)) {
              _tmpResponseTime = null;
            } else {
              _tmpResponseTime = _cursor.getLong(_cursorIndexOfResponseTime);
            }
            final Long _tmpDurationMs;
            if (_cursor.isNull(_cursorIndexOfDurationMs)) {
              _tmpDurationMs = null;
            } else {
              _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            }
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final String _tmpTlsVersion;
            if (_cursor.isNull(_cursorIndexOfTlsVersion)) {
              _tmpTlsVersion = null;
            } else {
              _tmpTlsVersion = _cursor.getString(_cursorIndexOfTlsVersion);
            }
            final String _tmpCipherSuite;
            if (_cursor.isNull(_cursorIndexOfCipherSuite)) {
              _tmpCipherSuite = null;
            } else {
              _tmpCipherSuite = _cursor.getString(_cursorIndexOfCipherSuite);
            }
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final boolean _tmpIsComplete;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsComplete);
            _tmpIsComplete = _tmp != 0;
            _item = new HttpTransaction(_tmpId,_tmpUrl,_tmpMethod,_tmpRequestHeaders,_tmpRequestBody,_tmpRequestContentType,_tmpRequestContentLength,_tmpRequestTime,_tmpResponseCode,_tmpResponseMessage,_tmpResponseHeaders,_tmpResponseBody,_tmpResponseContentType,_tmpResponseContentLength,_tmpResponseTime,_tmpDurationMs,_tmpProtocol,_tmpTlsVersion,_tmpCipherSuite,_tmpError,_tmpIsComplete);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<HttpTransaction>> getRecentFlow(final int limit) {
    final String _sql = "SELECT * FROM http_transactions ORDER BY requestTime DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"http_transactions"}, new Callable<List<HttpTransaction>>() {
      @Override
      @NonNull
      public List<HttpTransaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "method");
          final int _cursorIndexOfRequestHeaders = CursorUtil.getColumnIndexOrThrow(_cursor, "requestHeaders");
          final int _cursorIndexOfRequestBody = CursorUtil.getColumnIndexOrThrow(_cursor, "requestBody");
          final int _cursorIndexOfRequestContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "requestContentType");
          final int _cursorIndexOfRequestContentLength = CursorUtil.getColumnIndexOrThrow(_cursor, "requestContentLength");
          final int _cursorIndexOfRequestTime = CursorUtil.getColumnIndexOrThrow(_cursor, "requestTime");
          final int _cursorIndexOfResponseCode = CursorUtil.getColumnIndexOrThrow(_cursor, "responseCode");
          final int _cursorIndexOfResponseMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "responseMessage");
          final int _cursorIndexOfResponseHeaders = CursorUtil.getColumnIndexOrThrow(_cursor, "responseHeaders");
          final int _cursorIndexOfResponseBody = CursorUtil.getColumnIndexOrThrow(_cursor, "responseBody");
          final int _cursorIndexOfResponseContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "responseContentType");
          final int _cursorIndexOfResponseContentLength = CursorUtil.getColumnIndexOrThrow(_cursor, "responseContentLength");
          final int _cursorIndexOfResponseTime = CursorUtil.getColumnIndexOrThrow(_cursor, "responseTime");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfTlsVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "tlsVersion");
          final int _cursorIndexOfCipherSuite = CursorUtil.getColumnIndexOrThrow(_cursor, "cipherSuite");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfIsComplete = CursorUtil.getColumnIndexOrThrow(_cursor, "isComplete");
          final List<HttpTransaction> _result = new ArrayList<HttpTransaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HttpTransaction _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final String _tmpMethod;
            if (_cursor.isNull(_cursorIndexOfMethod)) {
              _tmpMethod = null;
            } else {
              _tmpMethod = _cursor.getString(_cursorIndexOfMethod);
            }
            final String _tmpRequestHeaders;
            if (_cursor.isNull(_cursorIndexOfRequestHeaders)) {
              _tmpRequestHeaders = null;
            } else {
              _tmpRequestHeaders = _cursor.getString(_cursorIndexOfRequestHeaders);
            }
            final String _tmpRequestBody;
            if (_cursor.isNull(_cursorIndexOfRequestBody)) {
              _tmpRequestBody = null;
            } else {
              _tmpRequestBody = _cursor.getString(_cursorIndexOfRequestBody);
            }
            final String _tmpRequestContentType;
            if (_cursor.isNull(_cursorIndexOfRequestContentType)) {
              _tmpRequestContentType = null;
            } else {
              _tmpRequestContentType = _cursor.getString(_cursorIndexOfRequestContentType);
            }
            final Long _tmpRequestContentLength;
            if (_cursor.isNull(_cursorIndexOfRequestContentLength)) {
              _tmpRequestContentLength = null;
            } else {
              _tmpRequestContentLength = _cursor.getLong(_cursorIndexOfRequestContentLength);
            }
            final long _tmpRequestTime;
            _tmpRequestTime = _cursor.getLong(_cursorIndexOfRequestTime);
            final Integer _tmpResponseCode;
            if (_cursor.isNull(_cursorIndexOfResponseCode)) {
              _tmpResponseCode = null;
            } else {
              _tmpResponseCode = _cursor.getInt(_cursorIndexOfResponseCode);
            }
            final String _tmpResponseMessage;
            if (_cursor.isNull(_cursorIndexOfResponseMessage)) {
              _tmpResponseMessage = null;
            } else {
              _tmpResponseMessage = _cursor.getString(_cursorIndexOfResponseMessage);
            }
            final String _tmpResponseHeaders;
            if (_cursor.isNull(_cursorIndexOfResponseHeaders)) {
              _tmpResponseHeaders = null;
            } else {
              _tmpResponseHeaders = _cursor.getString(_cursorIndexOfResponseHeaders);
            }
            final String _tmpResponseBody;
            if (_cursor.isNull(_cursorIndexOfResponseBody)) {
              _tmpResponseBody = null;
            } else {
              _tmpResponseBody = _cursor.getString(_cursorIndexOfResponseBody);
            }
            final String _tmpResponseContentType;
            if (_cursor.isNull(_cursorIndexOfResponseContentType)) {
              _tmpResponseContentType = null;
            } else {
              _tmpResponseContentType = _cursor.getString(_cursorIndexOfResponseContentType);
            }
            final Long _tmpResponseContentLength;
            if (_cursor.isNull(_cursorIndexOfResponseContentLength)) {
              _tmpResponseContentLength = null;
            } else {
              _tmpResponseContentLength = _cursor.getLong(_cursorIndexOfResponseContentLength);
            }
            final Long _tmpResponseTime;
            if (_cursor.isNull(_cursorIndexOfResponseTime)) {
              _tmpResponseTime = null;
            } else {
              _tmpResponseTime = _cursor.getLong(_cursorIndexOfResponseTime);
            }
            final Long _tmpDurationMs;
            if (_cursor.isNull(_cursorIndexOfDurationMs)) {
              _tmpDurationMs = null;
            } else {
              _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            }
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final String _tmpTlsVersion;
            if (_cursor.isNull(_cursorIndexOfTlsVersion)) {
              _tmpTlsVersion = null;
            } else {
              _tmpTlsVersion = _cursor.getString(_cursorIndexOfTlsVersion);
            }
            final String _tmpCipherSuite;
            if (_cursor.isNull(_cursorIndexOfCipherSuite)) {
              _tmpCipherSuite = null;
            } else {
              _tmpCipherSuite = _cursor.getString(_cursorIndexOfCipherSuite);
            }
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final boolean _tmpIsComplete;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsComplete);
            _tmpIsComplete = _tmp != 0;
            _item = new HttpTransaction(_tmpId,_tmpUrl,_tmpMethod,_tmpRequestHeaders,_tmpRequestBody,_tmpRequestContentType,_tmpRequestContentLength,_tmpRequestTime,_tmpResponseCode,_tmpResponseMessage,_tmpResponseHeaders,_tmpResponseBody,_tmpResponseContentType,_tmpResponseContentLength,_tmpResponseTime,_tmpDurationMs,_tmpProtocol,_tmpTlsVersion,_tmpCipherSuite,_tmpError,_tmpIsComplete);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAll(final Continuation<? super List<HttpTransaction>> $completion) {
    final String _sql = "SELECT * FROM http_transactions ORDER BY requestTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<HttpTransaction>>() {
      @Override
      @NonNull
      public List<HttpTransaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "method");
          final int _cursorIndexOfRequestHeaders = CursorUtil.getColumnIndexOrThrow(_cursor, "requestHeaders");
          final int _cursorIndexOfRequestBody = CursorUtil.getColumnIndexOrThrow(_cursor, "requestBody");
          final int _cursorIndexOfRequestContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "requestContentType");
          final int _cursorIndexOfRequestContentLength = CursorUtil.getColumnIndexOrThrow(_cursor, "requestContentLength");
          final int _cursorIndexOfRequestTime = CursorUtil.getColumnIndexOrThrow(_cursor, "requestTime");
          final int _cursorIndexOfResponseCode = CursorUtil.getColumnIndexOrThrow(_cursor, "responseCode");
          final int _cursorIndexOfResponseMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "responseMessage");
          final int _cursorIndexOfResponseHeaders = CursorUtil.getColumnIndexOrThrow(_cursor, "responseHeaders");
          final int _cursorIndexOfResponseBody = CursorUtil.getColumnIndexOrThrow(_cursor, "responseBody");
          final int _cursorIndexOfResponseContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "responseContentType");
          final int _cursorIndexOfResponseContentLength = CursorUtil.getColumnIndexOrThrow(_cursor, "responseContentLength");
          final int _cursorIndexOfResponseTime = CursorUtil.getColumnIndexOrThrow(_cursor, "responseTime");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfTlsVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "tlsVersion");
          final int _cursorIndexOfCipherSuite = CursorUtil.getColumnIndexOrThrow(_cursor, "cipherSuite");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfIsComplete = CursorUtil.getColumnIndexOrThrow(_cursor, "isComplete");
          final List<HttpTransaction> _result = new ArrayList<HttpTransaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HttpTransaction _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final String _tmpMethod;
            if (_cursor.isNull(_cursorIndexOfMethod)) {
              _tmpMethod = null;
            } else {
              _tmpMethod = _cursor.getString(_cursorIndexOfMethod);
            }
            final String _tmpRequestHeaders;
            if (_cursor.isNull(_cursorIndexOfRequestHeaders)) {
              _tmpRequestHeaders = null;
            } else {
              _tmpRequestHeaders = _cursor.getString(_cursorIndexOfRequestHeaders);
            }
            final String _tmpRequestBody;
            if (_cursor.isNull(_cursorIndexOfRequestBody)) {
              _tmpRequestBody = null;
            } else {
              _tmpRequestBody = _cursor.getString(_cursorIndexOfRequestBody);
            }
            final String _tmpRequestContentType;
            if (_cursor.isNull(_cursorIndexOfRequestContentType)) {
              _tmpRequestContentType = null;
            } else {
              _tmpRequestContentType = _cursor.getString(_cursorIndexOfRequestContentType);
            }
            final Long _tmpRequestContentLength;
            if (_cursor.isNull(_cursorIndexOfRequestContentLength)) {
              _tmpRequestContentLength = null;
            } else {
              _tmpRequestContentLength = _cursor.getLong(_cursorIndexOfRequestContentLength);
            }
            final long _tmpRequestTime;
            _tmpRequestTime = _cursor.getLong(_cursorIndexOfRequestTime);
            final Integer _tmpResponseCode;
            if (_cursor.isNull(_cursorIndexOfResponseCode)) {
              _tmpResponseCode = null;
            } else {
              _tmpResponseCode = _cursor.getInt(_cursorIndexOfResponseCode);
            }
            final String _tmpResponseMessage;
            if (_cursor.isNull(_cursorIndexOfResponseMessage)) {
              _tmpResponseMessage = null;
            } else {
              _tmpResponseMessage = _cursor.getString(_cursorIndexOfResponseMessage);
            }
            final String _tmpResponseHeaders;
            if (_cursor.isNull(_cursorIndexOfResponseHeaders)) {
              _tmpResponseHeaders = null;
            } else {
              _tmpResponseHeaders = _cursor.getString(_cursorIndexOfResponseHeaders);
            }
            final String _tmpResponseBody;
            if (_cursor.isNull(_cursorIndexOfResponseBody)) {
              _tmpResponseBody = null;
            } else {
              _tmpResponseBody = _cursor.getString(_cursorIndexOfResponseBody);
            }
            final String _tmpResponseContentType;
            if (_cursor.isNull(_cursorIndexOfResponseContentType)) {
              _tmpResponseContentType = null;
            } else {
              _tmpResponseContentType = _cursor.getString(_cursorIndexOfResponseContentType);
            }
            final Long _tmpResponseContentLength;
            if (_cursor.isNull(_cursorIndexOfResponseContentLength)) {
              _tmpResponseContentLength = null;
            } else {
              _tmpResponseContentLength = _cursor.getLong(_cursorIndexOfResponseContentLength);
            }
            final Long _tmpResponseTime;
            if (_cursor.isNull(_cursorIndexOfResponseTime)) {
              _tmpResponseTime = null;
            } else {
              _tmpResponseTime = _cursor.getLong(_cursorIndexOfResponseTime);
            }
            final Long _tmpDurationMs;
            if (_cursor.isNull(_cursorIndexOfDurationMs)) {
              _tmpDurationMs = null;
            } else {
              _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            }
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final String _tmpTlsVersion;
            if (_cursor.isNull(_cursorIndexOfTlsVersion)) {
              _tmpTlsVersion = null;
            } else {
              _tmpTlsVersion = _cursor.getString(_cursorIndexOfTlsVersion);
            }
            final String _tmpCipherSuite;
            if (_cursor.isNull(_cursorIndexOfCipherSuite)) {
              _tmpCipherSuite = null;
            } else {
              _tmpCipherSuite = _cursor.getString(_cursorIndexOfCipherSuite);
            }
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final boolean _tmpIsComplete;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsComplete);
            _tmpIsComplete = _tmp != 0;
            _item = new HttpTransaction(_tmpId,_tmpUrl,_tmpMethod,_tmpRequestHeaders,_tmpRequestBody,_tmpRequestContentType,_tmpRequestContentLength,_tmpRequestTime,_tmpResponseCode,_tmpResponseMessage,_tmpResponseHeaders,_tmpResponseBody,_tmpResponseContentType,_tmpResponseContentLength,_tmpResponseTime,_tmpDurationMs,_tmpProtocol,_tmpTlsVersion,_tmpCipherSuite,_tmpError,_tmpIsComplete);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<HttpTransaction>> search(final String query) {
    final String _sql = "SELECT * FROM http_transactions WHERE url LIKE '%' || ? || '%' OR method LIKE '%' || ? || '%' ORDER BY requestTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 2;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"http_transactions"}, new Callable<List<HttpTransaction>>() {
      @Override
      @NonNull
      public List<HttpTransaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "method");
          final int _cursorIndexOfRequestHeaders = CursorUtil.getColumnIndexOrThrow(_cursor, "requestHeaders");
          final int _cursorIndexOfRequestBody = CursorUtil.getColumnIndexOrThrow(_cursor, "requestBody");
          final int _cursorIndexOfRequestContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "requestContentType");
          final int _cursorIndexOfRequestContentLength = CursorUtil.getColumnIndexOrThrow(_cursor, "requestContentLength");
          final int _cursorIndexOfRequestTime = CursorUtil.getColumnIndexOrThrow(_cursor, "requestTime");
          final int _cursorIndexOfResponseCode = CursorUtil.getColumnIndexOrThrow(_cursor, "responseCode");
          final int _cursorIndexOfResponseMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "responseMessage");
          final int _cursorIndexOfResponseHeaders = CursorUtil.getColumnIndexOrThrow(_cursor, "responseHeaders");
          final int _cursorIndexOfResponseBody = CursorUtil.getColumnIndexOrThrow(_cursor, "responseBody");
          final int _cursorIndexOfResponseContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "responseContentType");
          final int _cursorIndexOfResponseContentLength = CursorUtil.getColumnIndexOrThrow(_cursor, "responseContentLength");
          final int _cursorIndexOfResponseTime = CursorUtil.getColumnIndexOrThrow(_cursor, "responseTime");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfTlsVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "tlsVersion");
          final int _cursorIndexOfCipherSuite = CursorUtil.getColumnIndexOrThrow(_cursor, "cipherSuite");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfIsComplete = CursorUtil.getColumnIndexOrThrow(_cursor, "isComplete");
          final List<HttpTransaction> _result = new ArrayList<HttpTransaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HttpTransaction _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final String _tmpMethod;
            if (_cursor.isNull(_cursorIndexOfMethod)) {
              _tmpMethod = null;
            } else {
              _tmpMethod = _cursor.getString(_cursorIndexOfMethod);
            }
            final String _tmpRequestHeaders;
            if (_cursor.isNull(_cursorIndexOfRequestHeaders)) {
              _tmpRequestHeaders = null;
            } else {
              _tmpRequestHeaders = _cursor.getString(_cursorIndexOfRequestHeaders);
            }
            final String _tmpRequestBody;
            if (_cursor.isNull(_cursorIndexOfRequestBody)) {
              _tmpRequestBody = null;
            } else {
              _tmpRequestBody = _cursor.getString(_cursorIndexOfRequestBody);
            }
            final String _tmpRequestContentType;
            if (_cursor.isNull(_cursorIndexOfRequestContentType)) {
              _tmpRequestContentType = null;
            } else {
              _tmpRequestContentType = _cursor.getString(_cursorIndexOfRequestContentType);
            }
            final Long _tmpRequestContentLength;
            if (_cursor.isNull(_cursorIndexOfRequestContentLength)) {
              _tmpRequestContentLength = null;
            } else {
              _tmpRequestContentLength = _cursor.getLong(_cursorIndexOfRequestContentLength);
            }
            final long _tmpRequestTime;
            _tmpRequestTime = _cursor.getLong(_cursorIndexOfRequestTime);
            final Integer _tmpResponseCode;
            if (_cursor.isNull(_cursorIndexOfResponseCode)) {
              _tmpResponseCode = null;
            } else {
              _tmpResponseCode = _cursor.getInt(_cursorIndexOfResponseCode);
            }
            final String _tmpResponseMessage;
            if (_cursor.isNull(_cursorIndexOfResponseMessage)) {
              _tmpResponseMessage = null;
            } else {
              _tmpResponseMessage = _cursor.getString(_cursorIndexOfResponseMessage);
            }
            final String _tmpResponseHeaders;
            if (_cursor.isNull(_cursorIndexOfResponseHeaders)) {
              _tmpResponseHeaders = null;
            } else {
              _tmpResponseHeaders = _cursor.getString(_cursorIndexOfResponseHeaders);
            }
            final String _tmpResponseBody;
            if (_cursor.isNull(_cursorIndexOfResponseBody)) {
              _tmpResponseBody = null;
            } else {
              _tmpResponseBody = _cursor.getString(_cursorIndexOfResponseBody);
            }
            final String _tmpResponseContentType;
            if (_cursor.isNull(_cursorIndexOfResponseContentType)) {
              _tmpResponseContentType = null;
            } else {
              _tmpResponseContentType = _cursor.getString(_cursorIndexOfResponseContentType);
            }
            final Long _tmpResponseContentLength;
            if (_cursor.isNull(_cursorIndexOfResponseContentLength)) {
              _tmpResponseContentLength = null;
            } else {
              _tmpResponseContentLength = _cursor.getLong(_cursorIndexOfResponseContentLength);
            }
            final Long _tmpResponseTime;
            if (_cursor.isNull(_cursorIndexOfResponseTime)) {
              _tmpResponseTime = null;
            } else {
              _tmpResponseTime = _cursor.getLong(_cursorIndexOfResponseTime);
            }
            final Long _tmpDurationMs;
            if (_cursor.isNull(_cursorIndexOfDurationMs)) {
              _tmpDurationMs = null;
            } else {
              _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            }
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final String _tmpTlsVersion;
            if (_cursor.isNull(_cursorIndexOfTlsVersion)) {
              _tmpTlsVersion = null;
            } else {
              _tmpTlsVersion = _cursor.getString(_cursorIndexOfTlsVersion);
            }
            final String _tmpCipherSuite;
            if (_cursor.isNull(_cursorIndexOfCipherSuite)) {
              _tmpCipherSuite = null;
            } else {
              _tmpCipherSuite = _cursor.getString(_cursorIndexOfCipherSuite);
            }
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final boolean _tmpIsComplete;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsComplete);
            _tmpIsComplete = _tmp != 0;
            _item = new HttpTransaction(_tmpId,_tmpUrl,_tmpMethod,_tmpRequestHeaders,_tmpRequestBody,_tmpRequestContentType,_tmpRequestContentLength,_tmpRequestTime,_tmpResponseCode,_tmpResponseMessage,_tmpResponseHeaders,_tmpResponseBody,_tmpResponseContentType,_tmpResponseContentLength,_tmpResponseTime,_tmpDurationMs,_tmpProtocol,_tmpTlsVersion,_tmpCipherSuite,_tmpError,_tmpIsComplete);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<HttpTransaction>> getByStatusCodeRange(final int minCode, final int maxCode) {
    final String _sql = "SELECT * FROM http_transactions WHERE responseCode >= ? AND responseCode < ? ORDER BY requestTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, minCode);
    _argIndex = 2;
    _statement.bindLong(_argIndex, maxCode);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"http_transactions"}, new Callable<List<HttpTransaction>>() {
      @Override
      @NonNull
      public List<HttpTransaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "method");
          final int _cursorIndexOfRequestHeaders = CursorUtil.getColumnIndexOrThrow(_cursor, "requestHeaders");
          final int _cursorIndexOfRequestBody = CursorUtil.getColumnIndexOrThrow(_cursor, "requestBody");
          final int _cursorIndexOfRequestContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "requestContentType");
          final int _cursorIndexOfRequestContentLength = CursorUtil.getColumnIndexOrThrow(_cursor, "requestContentLength");
          final int _cursorIndexOfRequestTime = CursorUtil.getColumnIndexOrThrow(_cursor, "requestTime");
          final int _cursorIndexOfResponseCode = CursorUtil.getColumnIndexOrThrow(_cursor, "responseCode");
          final int _cursorIndexOfResponseMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "responseMessage");
          final int _cursorIndexOfResponseHeaders = CursorUtil.getColumnIndexOrThrow(_cursor, "responseHeaders");
          final int _cursorIndexOfResponseBody = CursorUtil.getColumnIndexOrThrow(_cursor, "responseBody");
          final int _cursorIndexOfResponseContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "responseContentType");
          final int _cursorIndexOfResponseContentLength = CursorUtil.getColumnIndexOrThrow(_cursor, "responseContentLength");
          final int _cursorIndexOfResponseTime = CursorUtil.getColumnIndexOrThrow(_cursor, "responseTime");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfTlsVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "tlsVersion");
          final int _cursorIndexOfCipherSuite = CursorUtil.getColumnIndexOrThrow(_cursor, "cipherSuite");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfIsComplete = CursorUtil.getColumnIndexOrThrow(_cursor, "isComplete");
          final List<HttpTransaction> _result = new ArrayList<HttpTransaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HttpTransaction _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final String _tmpMethod;
            if (_cursor.isNull(_cursorIndexOfMethod)) {
              _tmpMethod = null;
            } else {
              _tmpMethod = _cursor.getString(_cursorIndexOfMethod);
            }
            final String _tmpRequestHeaders;
            if (_cursor.isNull(_cursorIndexOfRequestHeaders)) {
              _tmpRequestHeaders = null;
            } else {
              _tmpRequestHeaders = _cursor.getString(_cursorIndexOfRequestHeaders);
            }
            final String _tmpRequestBody;
            if (_cursor.isNull(_cursorIndexOfRequestBody)) {
              _tmpRequestBody = null;
            } else {
              _tmpRequestBody = _cursor.getString(_cursorIndexOfRequestBody);
            }
            final String _tmpRequestContentType;
            if (_cursor.isNull(_cursorIndexOfRequestContentType)) {
              _tmpRequestContentType = null;
            } else {
              _tmpRequestContentType = _cursor.getString(_cursorIndexOfRequestContentType);
            }
            final Long _tmpRequestContentLength;
            if (_cursor.isNull(_cursorIndexOfRequestContentLength)) {
              _tmpRequestContentLength = null;
            } else {
              _tmpRequestContentLength = _cursor.getLong(_cursorIndexOfRequestContentLength);
            }
            final long _tmpRequestTime;
            _tmpRequestTime = _cursor.getLong(_cursorIndexOfRequestTime);
            final Integer _tmpResponseCode;
            if (_cursor.isNull(_cursorIndexOfResponseCode)) {
              _tmpResponseCode = null;
            } else {
              _tmpResponseCode = _cursor.getInt(_cursorIndexOfResponseCode);
            }
            final String _tmpResponseMessage;
            if (_cursor.isNull(_cursorIndexOfResponseMessage)) {
              _tmpResponseMessage = null;
            } else {
              _tmpResponseMessage = _cursor.getString(_cursorIndexOfResponseMessage);
            }
            final String _tmpResponseHeaders;
            if (_cursor.isNull(_cursorIndexOfResponseHeaders)) {
              _tmpResponseHeaders = null;
            } else {
              _tmpResponseHeaders = _cursor.getString(_cursorIndexOfResponseHeaders);
            }
            final String _tmpResponseBody;
            if (_cursor.isNull(_cursorIndexOfResponseBody)) {
              _tmpResponseBody = null;
            } else {
              _tmpResponseBody = _cursor.getString(_cursorIndexOfResponseBody);
            }
            final String _tmpResponseContentType;
            if (_cursor.isNull(_cursorIndexOfResponseContentType)) {
              _tmpResponseContentType = null;
            } else {
              _tmpResponseContentType = _cursor.getString(_cursorIndexOfResponseContentType);
            }
            final Long _tmpResponseContentLength;
            if (_cursor.isNull(_cursorIndexOfResponseContentLength)) {
              _tmpResponseContentLength = null;
            } else {
              _tmpResponseContentLength = _cursor.getLong(_cursorIndexOfResponseContentLength);
            }
            final Long _tmpResponseTime;
            if (_cursor.isNull(_cursorIndexOfResponseTime)) {
              _tmpResponseTime = null;
            } else {
              _tmpResponseTime = _cursor.getLong(_cursorIndexOfResponseTime);
            }
            final Long _tmpDurationMs;
            if (_cursor.isNull(_cursorIndexOfDurationMs)) {
              _tmpDurationMs = null;
            } else {
              _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            }
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final String _tmpTlsVersion;
            if (_cursor.isNull(_cursorIndexOfTlsVersion)) {
              _tmpTlsVersion = null;
            } else {
              _tmpTlsVersion = _cursor.getString(_cursorIndexOfTlsVersion);
            }
            final String _tmpCipherSuite;
            if (_cursor.isNull(_cursorIndexOfCipherSuite)) {
              _tmpCipherSuite = null;
            } else {
              _tmpCipherSuite = _cursor.getString(_cursorIndexOfCipherSuite);
            }
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final boolean _tmpIsComplete;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsComplete);
            _tmpIsComplete = _tmp != 0;
            _item = new HttpTransaction(_tmpId,_tmpUrl,_tmpMethod,_tmpRequestHeaders,_tmpRequestBody,_tmpRequestContentType,_tmpRequestContentLength,_tmpRequestTime,_tmpResponseCode,_tmpResponseMessage,_tmpResponseHeaders,_tmpResponseBody,_tmpResponseContentType,_tmpResponseContentLength,_tmpResponseTime,_tmpDurationMs,_tmpProtocol,_tmpTlsVersion,_tmpCipherSuite,_tmpError,_tmpIsComplete);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<HttpTransaction>> getErrors() {
    final String _sql = "SELECT * FROM http_transactions WHERE error IS NOT NULL ORDER BY requestTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"http_transactions"}, new Callable<List<HttpTransaction>>() {
      @Override
      @NonNull
      public List<HttpTransaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "method");
          final int _cursorIndexOfRequestHeaders = CursorUtil.getColumnIndexOrThrow(_cursor, "requestHeaders");
          final int _cursorIndexOfRequestBody = CursorUtil.getColumnIndexOrThrow(_cursor, "requestBody");
          final int _cursorIndexOfRequestContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "requestContentType");
          final int _cursorIndexOfRequestContentLength = CursorUtil.getColumnIndexOrThrow(_cursor, "requestContentLength");
          final int _cursorIndexOfRequestTime = CursorUtil.getColumnIndexOrThrow(_cursor, "requestTime");
          final int _cursorIndexOfResponseCode = CursorUtil.getColumnIndexOrThrow(_cursor, "responseCode");
          final int _cursorIndexOfResponseMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "responseMessage");
          final int _cursorIndexOfResponseHeaders = CursorUtil.getColumnIndexOrThrow(_cursor, "responseHeaders");
          final int _cursorIndexOfResponseBody = CursorUtil.getColumnIndexOrThrow(_cursor, "responseBody");
          final int _cursorIndexOfResponseContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "responseContentType");
          final int _cursorIndexOfResponseContentLength = CursorUtil.getColumnIndexOrThrow(_cursor, "responseContentLength");
          final int _cursorIndexOfResponseTime = CursorUtil.getColumnIndexOrThrow(_cursor, "responseTime");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfTlsVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "tlsVersion");
          final int _cursorIndexOfCipherSuite = CursorUtil.getColumnIndexOrThrow(_cursor, "cipherSuite");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final int _cursorIndexOfIsComplete = CursorUtil.getColumnIndexOrThrow(_cursor, "isComplete");
          final List<HttpTransaction> _result = new ArrayList<HttpTransaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HttpTransaction _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final String _tmpMethod;
            if (_cursor.isNull(_cursorIndexOfMethod)) {
              _tmpMethod = null;
            } else {
              _tmpMethod = _cursor.getString(_cursorIndexOfMethod);
            }
            final String _tmpRequestHeaders;
            if (_cursor.isNull(_cursorIndexOfRequestHeaders)) {
              _tmpRequestHeaders = null;
            } else {
              _tmpRequestHeaders = _cursor.getString(_cursorIndexOfRequestHeaders);
            }
            final String _tmpRequestBody;
            if (_cursor.isNull(_cursorIndexOfRequestBody)) {
              _tmpRequestBody = null;
            } else {
              _tmpRequestBody = _cursor.getString(_cursorIndexOfRequestBody);
            }
            final String _tmpRequestContentType;
            if (_cursor.isNull(_cursorIndexOfRequestContentType)) {
              _tmpRequestContentType = null;
            } else {
              _tmpRequestContentType = _cursor.getString(_cursorIndexOfRequestContentType);
            }
            final Long _tmpRequestContentLength;
            if (_cursor.isNull(_cursorIndexOfRequestContentLength)) {
              _tmpRequestContentLength = null;
            } else {
              _tmpRequestContentLength = _cursor.getLong(_cursorIndexOfRequestContentLength);
            }
            final long _tmpRequestTime;
            _tmpRequestTime = _cursor.getLong(_cursorIndexOfRequestTime);
            final Integer _tmpResponseCode;
            if (_cursor.isNull(_cursorIndexOfResponseCode)) {
              _tmpResponseCode = null;
            } else {
              _tmpResponseCode = _cursor.getInt(_cursorIndexOfResponseCode);
            }
            final String _tmpResponseMessage;
            if (_cursor.isNull(_cursorIndexOfResponseMessage)) {
              _tmpResponseMessage = null;
            } else {
              _tmpResponseMessage = _cursor.getString(_cursorIndexOfResponseMessage);
            }
            final String _tmpResponseHeaders;
            if (_cursor.isNull(_cursorIndexOfResponseHeaders)) {
              _tmpResponseHeaders = null;
            } else {
              _tmpResponseHeaders = _cursor.getString(_cursorIndexOfResponseHeaders);
            }
            final String _tmpResponseBody;
            if (_cursor.isNull(_cursorIndexOfResponseBody)) {
              _tmpResponseBody = null;
            } else {
              _tmpResponseBody = _cursor.getString(_cursorIndexOfResponseBody);
            }
            final String _tmpResponseContentType;
            if (_cursor.isNull(_cursorIndexOfResponseContentType)) {
              _tmpResponseContentType = null;
            } else {
              _tmpResponseContentType = _cursor.getString(_cursorIndexOfResponseContentType);
            }
            final Long _tmpResponseContentLength;
            if (_cursor.isNull(_cursorIndexOfResponseContentLength)) {
              _tmpResponseContentLength = null;
            } else {
              _tmpResponseContentLength = _cursor.getLong(_cursorIndexOfResponseContentLength);
            }
            final Long _tmpResponseTime;
            if (_cursor.isNull(_cursorIndexOfResponseTime)) {
              _tmpResponseTime = null;
            } else {
              _tmpResponseTime = _cursor.getLong(_cursorIndexOfResponseTime);
            }
            final Long _tmpDurationMs;
            if (_cursor.isNull(_cursorIndexOfDurationMs)) {
              _tmpDurationMs = null;
            } else {
              _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            }
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final String _tmpTlsVersion;
            if (_cursor.isNull(_cursorIndexOfTlsVersion)) {
              _tmpTlsVersion = null;
            } else {
              _tmpTlsVersion = _cursor.getString(_cursorIndexOfTlsVersion);
            }
            final String _tmpCipherSuite;
            if (_cursor.isNull(_cursorIndexOfCipherSuite)) {
              _tmpCipherSuite = null;
            } else {
              _tmpCipherSuite = _cursor.getString(_cursorIndexOfCipherSuite);
            }
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            final boolean _tmpIsComplete;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsComplete);
            _tmpIsComplete = _tmp != 0;
            _item = new HttpTransaction(_tmpId,_tmpUrl,_tmpMethod,_tmpRequestHeaders,_tmpRequestBody,_tmpRequestContentType,_tmpRequestContentLength,_tmpRequestTime,_tmpResponseCode,_tmpResponseMessage,_tmpResponseHeaders,_tmpResponseBody,_tmpResponseContentType,_tmpResponseContentLength,_tmpResponseTime,_tmpDurationMs,_tmpProtocol,_tmpTlsVersion,_tmpCipherSuite,_tmpError,_tmpIsComplete);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM http_transactions";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAverageDuration(final Continuation<? super Double> $completion) {
    final String _sql = "SELECT AVG(durationMs) FROM http_transactions WHERE durationMs IS NOT NULL";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
