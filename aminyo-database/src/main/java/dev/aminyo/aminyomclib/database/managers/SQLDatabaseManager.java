package dev.aminyo.aminyomclib.database.managers;

import dev.aminyo.aminyomclib.core.exceptions.AminyoException;
import dev.aminyo.aminyomclib.core.exceptions.AminyoRuntimeException;
import dev.aminyo.aminyomclib.database.QueryResult;
import dev.aminyo.aminyomclib.database.connections.SQLDatabaseConnection;

import java.sql.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SQL database manager implementation
 */
public class SQLDatabaseManager implements DatabaseManager {

    private final SQLDatabaseConnection connection;
    private final ExecutorService executorService;

    public SQLDatabaseManager(SQLDatabaseConnection connection) {
        this.connection = connection;
        this.executorService = Executors.newCachedThreadPool(r -> {
            Thread thread = new Thread(r, "SQLDatabaseManager-Thread");
            thread.setDaemon(true);
            return thread;
        });
    }

    @Override
    public QueryResult executeQuery(String query, Object... params) throws AminyoException {
        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            setParameters(stmt, params);

            try (ResultSet rs = stmt.executeQuery()) {
                return QueryResult.fromResultSet(rs);
            }

        } catch (SQLException e) {
            throw new AminyoException("Failed to execute query: " + query, e);
        }
    }

    @Override
    public int executeUpdate(String query, Object... params) throws AminyoException {
        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            setParameters(stmt, params);
            return stmt.executeUpdate();

        } catch (SQLException e) {
            throw new AminyoException("Failed to execute update: " + query, e);
        }
    }

    @Override
    public CompletableFuture<QueryResult> executeQueryAsync(String query, Object... params) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return executeQuery(query, params);
            } catch (AminyoException e) {
                try {
                    throw new AminyoRuntimeException(e);
                } catch (AminyoRuntimeException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }, executorService);
    }

    @Override
    public CompletableFuture<Integer> executeUpdateAsync(String query, Object... params) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return executeUpdate(query, params);
            } catch (AminyoException e) {
                try {
                    throw new AminyoRuntimeException(e);
                } catch (AminyoRuntimeException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }, executorService);
    }

    @Override
    public int[] executeBatch(String query, List<Object[]> paramsList) throws AminyoException {
        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            conn.setAutoCommit(false);

            try {
                for (Object[] params : paramsList) {
                    setParameters(stmt, params);
                    stmt.addBatch();
                }

                int[] results = stmt.executeBatch();
                conn.commit();
                return results;

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new AminyoException("Failed to execute batch: " + query, e);
        }
    }

    @Override
    public CompletableFuture<int[]> executeBatchAsync(String query, List<Object[]> paramsList) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return executeBatch(query, paramsList);
            } catch (AminyoException e) {
                try {
                    throw new AminyoRuntimeException(e);
                } catch (AminyoRuntimeException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }, executorService);
    }

    @Override
    public boolean testConnection() {
        return connection.testConnection();
    }

    @Override
    public void close() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
        connection.close();
    }

    private void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }

    public SQLDatabaseConnection getConnection() {
        return connection;
    }
}
