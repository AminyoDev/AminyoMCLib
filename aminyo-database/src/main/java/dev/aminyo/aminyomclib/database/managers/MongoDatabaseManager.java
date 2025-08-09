package dev.aminyo.aminyomclib.database.managers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.aminyo.aminyomclib.core.exceptions.AminyoException;
import dev.aminyo.aminyomclib.database.QueryResult;
import dev.aminyo.aminyomclib.database.connections.MongoDatabaseConnection;
import org.bson.Document;

import java.sql.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * MongoDB database manager implementation
 */
public class MongoDatabaseManager implements DatabaseManager {

    private final MongoDatabaseConnection connection;
    private final ExecutorService executorService;

    public MongoDatabaseManager(MongoDatabaseConnection connection) {
        this.connection = connection;
        this.executorService = Executors.newCachedThreadPool(r -> {
            Thread thread = new Thread(r, "MongoDatabaseManager-Thread");
            thread.setDaemon(true);
            return thread;
        });
    }

    @Override
    public QueryResult executeQuery(String query, Object... params) throws AminyoException {
        // MongoDB doesn't use SQL queries - this would need to be adapted
        // for MongoDB operations like find, aggregate, etc.
        throw new UnsupportedOperationException("Use MongoDB-specific methods instead");
    }

    @Override
    public int executeUpdate(String query, Object... params) throws AminyoException {
        // MongoDB doesn't use SQL updates - this would need to be adapted
        // for MongoDB operations like updateOne, updateMany, etc.
        throw new UnsupportedOperationException("Use MongoDB-specific methods instead");
    }

    @Override
    public CompletableFuture<QueryResult> executeQueryAsync(String query, Object... params) {
        return CompletableFuture.failedFuture(
                new UnsupportedOperationException("Use MongoDB-specific methods instead"));
    }

    @Override
    public CompletableFuture<Integer> executeUpdateAsync(String query, Object... params) {
        return CompletableFuture.failedFuture(
                new UnsupportedOperationException("Use MongoDB-specific methods instead"));
    }

    @Override
    public int[] executeBatch(String query, List<Object[]> paramsList) throws AminyoException {
        throw new UnsupportedOperationException("Use MongoDB-specific methods instead");
    }

    @Override
    public CompletableFuture<int[]> executeBatchAsync(String query, List<Object[]> paramsList) {
        return CompletableFuture.failedFuture(
                new UnsupportedOperationException("Use MongoDB-specific methods instead"));
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
     * Insert document into collection
     */
    public CompletableFuture<Void> insertDocument(String collectionName, Document document) {
        return CompletableFuture.runAsync(() -> {
            MongoCollection<Document> collection = connection.getCollection(collectionName);
            collection.insertOne(document);
        }, executorService);
    }

    /**
     * Find documents in collection
     */
    public CompletableFuture<List<Document>> findDocuments(String collectionName, Document filter) {
        return CompletableFuture.supplyAsync(() -> {
            MongoCollection<Document> collection = connection.getCollection(collectionName);
            return collection.find(filter).into(new ArrayList<>());
        }, executorService);
    }

    /**
     * Update documents in collection
     */
    public CompletableFuture<Long> updateDocuments(String collectionName, Document filter, Document update) {
        return CompletableFuture.supplyAsync(() -> {
            MongoCollection<Document> collection = connection.getCollection(collectionName);
            return collection.updateMany(filter, update).getModifiedCount();
        }, executorService);
    }

    /**
     * Delete documents from collection
     */
    public CompletableFuture<Long> deleteDocuments(String collectionName, Document filter) {
        return CompletableFuture.supplyAsync(() -> {
            MongoCollection<Document> collection = connection.getCollection(collectionName);
            return collection.deleteMany(filter).getDeletedCount();
        }, executorService);
    }

    public MongoDatabaseConnection getConnection() {
        return connection;
    }
}
