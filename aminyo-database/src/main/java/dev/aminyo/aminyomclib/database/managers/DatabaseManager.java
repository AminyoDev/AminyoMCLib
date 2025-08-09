package dev.aminyo.aminyomclib.database.managers;

import dev.aminyo.aminyomclib.core.exceptions.AminyoException;
import dev.aminyo.aminyomclib.database.QueryResult;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Abstract database manager interface
 */
public interface DatabaseManager {

    /**
     * Execute query synchronously
     */
    QueryResult executeQuery(String query, Object... params) throws AminyoException;

    /**
     * Execute update synchronously
     */
    int executeUpdate(String query, Object... params) throws AminyoException;

    /**
     * Execute query asynchronously
     */
    CompletableFuture<QueryResult> executeQueryAsync(String query, Object... params);

    /**
     * Execute update asynchronously
     */
    CompletableFuture<Integer> executeUpdateAsync(String query, Object... params);

    /**
     * Execute batch updates
     */
    int[] executeBatch(String query, List<Object[]> paramsList) throws AminyoException;

    /**
     * Execute batch updates asynchronously
     */
    CompletableFuture<int[]> executeBatchAsync(String query, List<Object[]> paramsList);

    /**
     * Test database connection
     */
    boolean testConnection();

    /**
     * Close database connection
     */
    void close();
}
