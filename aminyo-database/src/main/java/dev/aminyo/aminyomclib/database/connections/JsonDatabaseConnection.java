package dev.aminyo.aminyomclib.database;

import dev.aminyo.aminyomclib.core.exceptions.AminyoException;
import dev.aminyo.aminyomclib.core.exceptions.AminyoRuntimeException;
import it.aminyo.aminyomclib.file.extensions.JsonFileModel;

/**
 * JSON file-based database connection
 */
public class JsonDatabaseConnection implements DatabaseConnection {

    private final DatabaseConfig config;
    private final JsonFileModel jsonFile;
    private final String dataFolder;

    public JsonDatabaseConnection(DatabaseConfig config, String dataFolder) throws AminyoException, AminyoRuntimeException {
        this.config = config;
        this.dataFolder = dataFolder;
        this.jsonFile = new JsonFileModel(config.getDatabase() + ".json", dataFolder);

        try {
            jsonFile.reload();
        } catch (Exception e) {
            throw new AminyoException("Failed to initialize JSON database", e);
        }
    }

    public JsonFileModel getJsonFile() {
        return jsonFile;
    }

    @Override
    public boolean testConnection() {
        return jsonFile.isLoaded();
    }

    @Override
    public void close() {
        try {
            jsonFile.save();
        } catch (Exception e) {
            // Log error but don't throw
        }
    }

    @Override
    public boolean isClosed() {
        return !jsonFile.isLoaded();
    }

    @Override
    public DatabaseConfig getConfig() {
        return config;
    }
}
