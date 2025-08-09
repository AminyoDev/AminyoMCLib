package it.aminyo.aminyomclib.file;

import dev.aminyo.aminyomclib.core.exceptions.AminyoException;
import dev.aminyo.aminyomclib.core.exceptions.AminyoRuntimeException;
import it.aminyo.aminyomclib.file.enums.FileFormat;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class FileModel {

    protected final Path filePath;
    protected final FileFormat format;
    protected final Map<String, Object> data;
    protected boolean loaded;
    protected boolean autoSave;

    protected FileModel(String fileName, FileFormat format, String dataFolder) throws AminyoRuntimeException {
        this.filePath = Paths.get(dataFolder, fileName);
        this.format = format;
        this.data = new ConcurrentHashMap<>();
        this.loaded = false;
        this.autoSave = false;

        // Create directories if they don't exist
        try {
            Files.createDirectories(filePath.getParent());
        } catch (IOException e) {
            throw new AminyoRuntimeException("Failed to create directories for file: " + filePath, e);
        }
    }

    /**
     * Reload the configuration from disk
     */
    public void reload() throws AminyoException {
        try {
            if (!Files.exists(filePath)) {
                createDefaultFile();
                loaded = true;
                return;
            }

            Map<String, Object> loadedData = loadFromFile();

            synchronized (data) {
                data.clear();
                data.putAll(loadedData);
            }

            loaded = true;

        } catch (Exception e) {
            throw new AminyoException("Failed to reload file: " + filePath, e);
        }
    }

    /**
     * Save the configuration to disk
     */
    public void save() throws AminyoException {
        try {
            saveToFile();
        } catch (Exception e) {
            throw new AminyoException("Failed to save file: " + filePath, e);
        }
    }

    /**
     * Update configuration with new values
     */
    public void update(Map<String, Object> updates) throws AminyoException {
        synchronized (data) {
            data.putAll(updates);
        }

        if (autoSave) {
            save();
        }
    }

    /**
     * Get configuration value as Object
     */
    public Object get(String path) throws AminyoRuntimeException {
        return get(path, null);
    }

    /**
     * Get configuration value with default
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String path, T defaultValue) throws AminyoRuntimeException {
        if (!loaded) {
            try {
                reload();
            } catch (AminyoException e) {
                throw new AminyoRuntimeException("Failed to load configuration", e);
            }
        }

        synchronized (data) {
            Object value = getNestedValue(data, path);
            return value != null ? (T) value : defaultValue;
        }
    }

    /**
     * Get string value
     */
    public String getString(String path) throws AminyoRuntimeException {
        return getString(path, null);
    }

    public String getString(String path, String defaultValue) throws AminyoRuntimeException {
        Object value = get(path);
        return value != null ? String.valueOf(value) : defaultValue;
    }

    /**
     * Get integer value
     */
    public int getInt(String path) throws AminyoRuntimeException {
        return getInt(path, 0);
    }

    public int getInt(String path, int defaultValue) throws AminyoRuntimeException {
        Object value = get(path);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Get double value
     */
    public double getDouble(String path) throws AminyoRuntimeException {
        return getDouble(path, 0.0);
    }

    public double getDouble(String path, double defaultValue) throws AminyoRuntimeException {
        Object value = get(path);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Get boolean value
     */
    public boolean getBoolean(String path) throws AminyoRuntimeException {
        return getBoolean(path, false);
    }

    public boolean getBoolean(String path, boolean defaultValue) throws AminyoRuntimeException {
        Object value = get(path);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        }
        return defaultValue;
    }

    /**
     * Get list value
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getList(String path) throws AminyoRuntimeException {
        Object value = get(path);
        if (value instanceof List) {
            return (List<T>) value;
        }
        return new ArrayList<>();
    }

    /**
     * Get string list
     */
    public List<String> getStringList(String path) throws AminyoRuntimeException {
        List<?> list = getList(path);
        List<String> stringList = new ArrayList<>();
        for (Object item : list) {
            stringList.add(String.valueOf(item));
        }
        return stringList;
    }

    /**
     * Set configuration value
     */
    public void set(String path, Object value) throws AminyoRuntimeException {
        synchronized (data) {
            setNestedValue(data, path, value);
        }

        if (autoSave) {
            try {
                save();
            } catch (AminyoException e) {
                throw new AminyoRuntimeException("Failed to auto-save configuration", e);
            }
        }
    }

    /**
     * Check if path exists
     */
    public boolean contains(String path) {
        synchronized (data) {
            return getNestedValue(data, path) != null;
        }
    }

    /**
     * Remove value at path
     */
    public void remove(String path) throws AminyoRuntimeException {
        synchronized (data) {
            removeNestedValue(data, path);
        }

        if (autoSave) {
            try {
                save();
            } catch (AminyoException e) {
                throw new AminyoRuntimeException("Failed to auto-save configuration", e);
            }
        }
    }

    /**
     * Get all keys at root level
     */
    public Set<String> getKeys() {
        return getKeys("");
    }

    /**
     * Get all keys at specific path
     */
    @SuppressWarnings("unchecked")
    public Set<String> getKeys(String path) {
        synchronized (data) {
            Object value = path.isEmpty() ? data : getNestedValue(data, path);
            if (value instanceof Map) {
                return new HashSet<>(((Map<String, Object>) value).keySet());
            }
            return new HashSet<>();
        }
    }

    /**
     * Get the file path
     */
    public Path getFilePath() {
        return filePath;
    }

    /**
     * Get the file format
     */
    public FileFormat getFormat() {
        return format;
    }

    /**
     * Check if configuration is loaded
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Enable or disable auto-save
     */
    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    /**
     * Check if auto-save is enabled
     */
    public boolean isAutoSave() {
        return autoSave;
    }

    /**
     * Get a copy of all data
     */
    public Map<String, Object> getAllData() {
        synchronized (data) {
            return new HashMap<>(data);
        }
    }

    // Abstract methods for format-specific implementations
    protected abstract Map<String, Object> loadFromFile() throws IOException;
    protected abstract void saveToFile() throws IOException;
    protected abstract void createDefaultFile() throws IOException;

    // Helper methods for nested path access
    @SuppressWarnings("unchecked")
    private Object getNestedValue(Map<String, Object> map, String path) {
        if (path == null || path.isEmpty()) {
            return map;
        }

        String[] parts = path.split("\\.");
        Object current = map;

        for (String part : parts) {
            if (current instanceof Map) {
                current = ((Map<String, Object>) current).get(part);
            } else {
                return null;
            }
        }

        return current;
    }

    @SuppressWarnings("unchecked")
    private void setNestedValue(Map<String, Object> map, String path, Object value) {
        if (path == null || path.isEmpty()) {
            return;
        }

        String[] parts = path.split("\\.");
        Map<String, Object> current = map;

        for (int i = 0; i < parts.length - 1; i++) {
            String part = parts[i];
            Object next = current.get(part);

            if (!(next instanceof Map)) {
                next = new HashMap<String, Object>();
                current.put(part, next);
            }

            current = (Map<String, Object>) next;
        }

        current.put(parts[parts.length - 1], value);
    }

    @SuppressWarnings("unchecked")
    private void removeNestedValue(Map<String, Object> map, String path) {
        if (path == null || path.isEmpty()) {
            return;
        }

        String[] parts = path.split("\\.");
        Map<String, Object> current = map;

        for (int i = 0; i < parts.length - 1; i++) {
            String part = parts[i];
            Object next = current.get(part);

            if (!(next instanceof Map)) {
                return; // Path doesn't exist
            }

            current = (Map<String, Object>) next;
        }

        current.remove(parts[parts.length - 1]);
    }
}
