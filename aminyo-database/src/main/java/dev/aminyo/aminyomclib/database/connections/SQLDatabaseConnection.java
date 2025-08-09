package dev.aminyo.aminyomclib.database.connections;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.aminyo.aminyomclib.core.exceptions.AminyoException;
import dev.aminyo.aminyomclib.database.DatabaseConfig;

import java.sql.*;
import java.util.*;

/**
 * SQL database connection implementation using HikariCP
 */
public class SQLDatabaseConnection implements DatabaseConnection {

    private final DatabaseConfig config;
    private HikariDataSource dataSource;

    public SQLDatabaseConnection(DatabaseConfig config) throws AminyoException {
        this.config = config;
        initialize();
    }

    private void initialize() throws AminyoException {
        try {
            // Load driver class
            if (!config.getType().getDriverClass().isEmpty()) {
                Class.forName(config.getType().getDriverClass());
            }

            // Configure HikariCP
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(config.buildJdbcUrl());

            if (config.getUsername() != null) {
                hikariConfig.setUsername(config.getUsername());
            }
            if (config.getPassword() != null) {
                hikariConfig.setPassword(config.getPassword());
            }

            hikariConfig.setMaximumPoolSize(config.getMaxPoolSize());
            hikariConfig.setMinimumIdle(config.getMinIdleSize());
            hikariConfig.setConnectionTimeout(config.getConnectionTimeout());
            hikariConfig.setIdleTimeout(config.getIdleTimeout());
            hikariConfig.setMaxLifetime(config.getMaxLifetime());

            // Additional properties
            for (Map.Entry<String, String> entry : config.getProperties().entrySet()) {
                hikariConfig.addDataSourceProperty(entry.getKey(), entry.getValue());
            }

            this.dataSource = new HikariDataSource(hikariConfig);

        } catch (Exception e) {
            throw new AminyoException("Failed to initialize SQL database connection", e);
        }
    }

    /**
     * Get a connection from the pool
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public boolean testConnection() {
        try (Connection connection = getConnection()) {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    @Override
    public boolean isClosed() {
        return dataSource == null || dataSource.isClosed();
    }

    @Override
    public DatabaseConfig getConfig() {
        return config;
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }
}
