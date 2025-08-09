package dev.aminyo.aminyomclib.database;

/**
* Abstract database connection interface
*/
public interface DatabaseConnection {

    /**
     * Test the connection
     */
    boolean testConnection();

    /**
     * Close the connection
     */
    void close();

    /**
     * Check if connection is closed
     */
    boolean isClosed();

    /**
     * Get connection configuration
     */
    DatabaseConfig getConfig();
}
