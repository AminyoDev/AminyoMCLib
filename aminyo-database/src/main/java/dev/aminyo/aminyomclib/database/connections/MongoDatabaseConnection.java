package dev.aminyo.aminyomclib.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import dev.aminyo.aminyomclib.core.exceptions.AminyoException;
import org.bson.Document;

/**
 * MongoDB database connection implementation
 */
public class MongoDatabaseConnection implements DatabaseConnection {

    private final DatabaseConfig config;
    private MongoClient mongoClient;
    private MongoDatabase database;

    public MongoDatabaseConnection(DatabaseConfig config) throws AminyoException {
        this.config = config;
        initialize();
    }

    private void initialize() throws AminyoException {
        try {
            String connectionString = config.buildJdbcUrl();
            if (config.getUsername() != null && config.getPassword() != null) {
                connectionString = connectionString.replace("mongodb://",
                        "mongodb://" + config.getUsername() + ":" + config.getPassword() + "@");
            }

            this.mongoClient = MongoClients.create(connectionString);
            this.database = mongoClient.getDatabase(config.getDatabase());

        } catch (Exception e) {
            throw new AminyoException("Failed to initialize MongoDB connection", e);
        }
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    @Override
    public boolean testConnection() {
        try {
            database.runCommand(new Document("ping", 1));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    @Override
    public boolean isClosed() {
        return mongoClient == null;
    }

    @Override
    public DatabaseConfig getConfig() {
        return config;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
