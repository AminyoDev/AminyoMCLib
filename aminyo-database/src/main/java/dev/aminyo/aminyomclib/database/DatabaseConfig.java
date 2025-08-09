package dev.aminyo.aminyomclib.database;

import dev.aminyo.aminyomclib.database.enums.DatabaseType;

import java.util.HashMap;
import java.util.Map;

/**
 * Database connection configuration
 */
public class DatabaseConfig {
    private DatabaseType type;
    private String host = "localhost";
    private int port;
    private String database;
    private String username;
    private String password;
    private String filePath;
    private Map<String, String> properties;

    // Connection pool settings
    private int maxPoolSize = 10;
    private int minIdleSize = 5;
    private long connectionTimeout = 30000;
    private long idleTimeout = 600000;
    private long maxLifetime = 1800000;

    public DatabaseConfig(DatabaseType type) {
        this.type = type;
        this.properties = new HashMap<>();

        // Set default ports
        switch (type) {
            case MYSQL:
            case MARIADB:
                this.port = 3306;
                break;
            case POSTGRESQL:
                this.port = 5432;
                break;
            case MONGODB:
                this.port = 27017;
                break;
            default:
                this.port = 0;
        }
    }

    // Getters and setters
    public DatabaseType getType() { return type; }
    public void setType(DatabaseType type) { this.type = type; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }

    public String getDatabase() { return database; }
    public void setDatabase(String database) { this.database = database; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public Map<String, String> getProperties() { return properties; }
    public void setProperties(Map<String, String> properties) { this.properties = properties; }

    public int getMaxPoolSize() { return maxPoolSize; }
    public void setMaxPoolSize(int maxPoolSize) { this.maxPoolSize = maxPoolSize; }

    public int getMinIdleSize() { return minIdleSize; }
    public void setMinIdleSize(int minIdleSize) { this.minIdleSize = minIdleSize; }

    public long getConnectionTimeout() { return connectionTimeout; }
    public void setConnectionTimeout(long connectionTimeout) { this.connectionTimeout = connectionTimeout; }

    public long getIdleTimeout() { return idleTimeout; }
    public void setIdleTimeout(long idleTimeout) { this.idleTimeout = idleTimeout; }

    public long getMaxLifetime() { return maxLifetime; }
    public void setMaxLifetime(long maxLifetime) { this.maxLifetime = maxLifetime; }

    /**
     * Build JDBC URL from configuration
     */
    public String buildJdbcUrl() {
        String url = type.getUrlPattern();

        if (type.isFile()) {
            url = url.replace("{file}", filePath != null ? filePath : database);
        } else {
            url = url.replace("{host}", host)
                    .replace("{port}", String.valueOf(port))
                    .replace("{database}", database != null ? database : "");
        }

        if (!properties.isEmpty()) {
            StringBuilder params = new StringBuilder();
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                if (params.length() > 0) params.append("&");
                params.append(entry.getKey()).append("=").append(entry.getValue());
            }

            url += (url.contains("?") ? "&" : "?") + params.toString();
        }

        return url;
    }
}
