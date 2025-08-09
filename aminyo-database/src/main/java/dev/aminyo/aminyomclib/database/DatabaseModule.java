package dev.aminyo.aminyomclib.database;

import dev.aminyo.aminyomclib.core.AminyoModule;
import dev.aminyo.aminyomclib.core.exceptions.AminyoException;
import dev.aminyo.aminyomclib.core.exceptions.AminyoRuntimeException;
import dev.aminyo.aminyomclib.database.connections.JsonDatabaseConnection;
import dev.aminyo.aminyomclib.database.connections.MongoDatabaseConnection;
import dev.aminyo.aminyomclib.database.connections.SQLDatabaseConnection;
import dev.aminyo.aminyomclib.database.managers.DatabaseManager;
import dev.aminyo.aminyomclib.database.managers.JsonDatabaseManager;
import dev.aminyo.aminyomclib.database.managers.MongoDatabaseManager;
import dev.aminyo.aminyomclib.database.managers.SQLDatabaseManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Main database module for managing multiple database connections
 */
public class DatabaseModule extends AminyoModule {

    private final Map<String, DatabaseManager> managers;
    private final String dataFolder;

    public DatabaseModule(String dataFolder) {
        super("DatabaseModule");
        this.dataFolder = dataFolder;
        this.managers = new ConcurrentHashMap<>();
    }

    @Override
    protected void onEnable() throws AminyoException {
        logger.info("Database Module enabled");
    }

    @Override
    protected void onDisable() throws AminyoException {
        // Close all database connections
        for (DatabaseManager manager : managers.values()) {
            try {
                manager.close();
            } catch (Exception e) {
                logger.warn("Error closing database manager", e);
            }
        }
        managers.clear();
        logger.info("Database Module disabled");
    }

    @Override
    public void reload() throws AminyoException {
        // Reload would typically re-read database configurations
        logger.info("Database Module reloaded");
    }

    @Override
    public void save() throws AminyoException {
        // Save would typically flush any pending operations
        for (DatabaseManager manager : managers.values()) {
            if (manager instanceof JsonDatabaseManager) {
                try {
                    ((JsonDatabaseManager) manager).getConnection().getJsonFile().save();
                } catch (Exception e) {
                    logger.warn("Error saving JSON database", e);
                }
            }
        }
    }

    /**
     * Create and register a database manager
     */
    public DatabaseManager createDatabaseManager(String name, DatabaseConfig config) throws AminyoException, AminyoRuntimeException {
        DatabaseManager manager = managers.get(name);
        if (manager != null) {
            return manager;
        }

        switch (config.getType()) {
            case MYSQL:
            case MARIADB:
            case POSTGRESQL:
            case H2:
            case SQLITE:
                SQLDatabaseConnection sqlConnection = new SQLDatabaseConnection(config);
                manager = new SQLDatabaseManager(sqlConnection);
                break;

            case MONGODB:
                MongoDatabaseConnection mongoConnection = new MongoDatabaseConnection(config);
                manager = new MongoDatabaseManager(mongoConnection);
                break;

            case JSON:
                JsonDatabaseConnection jsonConnection = new JsonDatabaseConnection(config, dataFolder);
                manager = new JsonDatabaseManager(jsonConnection);
                break;

            default:
                throw new AminyoException("Unsupported database type: " + config.getType());
        }

        managers.put(name, manager);
        logger.info("Created database manager '{}' for type: {}", name, config.getType());

        return manager;
    }

    /**
     * Get existing database manager
     */
    public DatabaseManager getDatabaseManager(String name) {
        return managers.get(name);
    }

    /**
     * Remove and close database manager
     */
    public void removeDatabaseManager(String name) {
        DatabaseManager manager = managers.remove(name);
        if (manager != null) {
            manager.close();
            logger.info("Removed database manager: {}", name);
        }
    }

    /**
     * Get all registered database manager names
     */
    public Set<String> getDatabaseManagerNames() {
        return new HashSet<>(managers.keySet());
    }

    /**
     * Test all database connections
     */
    public Map<String, Boolean> testAllConnections() {
        Map<String, Boolean> results = new HashMap<>();
        for (Map.Entry<String, DatabaseManager> entry : managers.entrySet()) {
            results.put(entry.getKey(), entry.getValue().testConnection());
        }
        return results;
    }
}
