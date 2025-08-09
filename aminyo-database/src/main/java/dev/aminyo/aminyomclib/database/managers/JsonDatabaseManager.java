package dev.aminyo.aminyomclib.database.managers;

import dev.aminyo.aminyomclib.core.exceptions.AminyoException;
import dev.aminyo.aminyomclib.core.exceptions.AminyoRuntimeException;
import dev.aminyo.aminyomclib.database.QueryResult;
import dev.aminyo.aminyomclib.database.connections.JsonDatabaseConnection;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * JSON database manager implementation
 */
public class JsonDatabaseManager implements DatabaseManager {

    private final JsonDatabaseConnection connection;
    private final ExecutorService executorService;

    public JsonDatabaseManager(JsonDatabaseConnection connection) {
        this.connection = connection;
        this.executorService = Executors.newCachedThreadPool(r -> {
            Thread thread = new Thread(r, "JsonDatabaseManager-Thread");
            thread.setDaemon(true);
            return thread;
        });
    }

    @Override
    public QueryResult executeQuery(String query, Object... params) throws AminyoException {
        // JSON doesn't support SQL queries - implement simple key-value operations
        throw new UnsupportedOperationException("Use JSON-specific methods instead");
    }

    @Override
    public int executeUpdate(String query, Object... params) throws AminyoException {
        throw new UnsupportedOperationException("Use JSON-specific methods instead");
    }

    @Override
    public CompletableFuture<QueryResult> executeQueryAsync(String query, Object... params) {
        return CompletableFuture.failedFuture(
                new UnsupportedOperationException("Use JSON-specific methods instead"));
    }

    @Override
    public CompletableFuture<Integer> executeUpdateAsync(String query, Object... params) {
        return CompletableFuture.failedFuture(
                new UnsupportedOperationException("Use JSON-specific methods instead"));
    }

    @Override
    public int[] executeBatch(String query, List<Object[]> paramsList) throws AminyoException {
        throw new UnsupportedOperationException("Use JSON-specific methods instead");
    }

    @Override
    public CompletableFuture<int[]> executeBatchAsync(String query, List<Object[]> paramsList) {
        return CompletableFuture.failedFuture(
                new UnsupportedOperationException("Use JSON-specific methods instead"));
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

    /**
     * Set value in JSON database
     */
    public CompletableFuture<Void> setValue(String key, Object value) {
        return CompletableFuture.runAsync(() -> {
            try {
                connection.getJsonFile().set(key, value);
            } catch (AminyoRuntimeException e) {
                throw new RuntimeException(e);
            }
        }, executorService);
    }

    /**
     * Get value from JSON database
     */
    public CompletableFuture<Object> getValue(String key) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return connection.getJsonFile().get(key);
            } catch (AminyoRuntimeException e) {
                throw new RuntimeException(e);
            }
        }, executorService);
    }

    /**
     * Remove value from JSON database
     */
    public CompletableFuture<Void> removeValue(String key) {
        return CompletableFuture.runAsync(() -> {
            try {
                connection.getJsonFile().remove(key);
            } catch (AminyoRuntimeException e) {
                throw new RuntimeException(e);
            }
        }, executorService);
    }

    /**
     * Check if key exists
     */
    public CompletableFuture<Boolean> containsKey(String key) {
        return CompletableFuture.supplyAsync(() -> {
            return connection.getJsonFile().contains(key);
        }, executorService);
    }

    /**
     * Get all keys
     */
    public CompletableFuture<Set<String>> getAllKeys() {
        return CompletableFuture.supplyAsync(() -> {
            return connection.getJsonFile().getKeys();
        }, executorService);
    }

    public JsonDatabaseConnection getConnection() {
        return connection;
    }
}
