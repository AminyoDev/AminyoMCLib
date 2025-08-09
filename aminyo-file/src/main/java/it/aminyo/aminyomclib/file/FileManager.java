package it.aminyo.aminyomclib.file;

import dev.aminyo.aminyomclib.core.AminyoModule;
import dev.aminyo.aminyomclib.core.exceptions.AminyoException;
import dev.aminyo.aminyomclib.core.exceptions.AminyoRuntimeException;
import it.aminyo.aminyomclib.file.enums.FileFormat;
import it.aminyo.aminyomclib.file.extensions.JsonFileModel;
import it.aminyo.aminyomclib.file.extensions.TomlFileModel;
import it.aminyo.aminyomclib.file.extensions.YamlFileModel;
import it.aminyo.aminyomclib.file.properties.PropertiesFileModel;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * File manager for handling multiple configuration files
 */
public class FileManager extends AminyoModule {

    private final Map<String, FileModel> loadedFiles;
    private final String dataFolder;

    public FileManager(String dataFolder) {
        super("FileManager");
        this.dataFolder = dataFolder;
        this.loadedFiles = new ConcurrentHashMap<>();
    }

    @Override
    protected void onEnable() throws AminyoException {
        logger.info("File Manager enabled with data folder: {}", dataFolder);
    }

    @Override
    protected void onDisable() throws AminyoException {
        // Save all loaded files before shutdown
        for (FileModel file : loadedFiles.values()) {
            try {
                file.save();
            } catch (Exception e) {
                logger.warn("Failed to save file during shutdown: {}", file.getFilePath(), e);
            }
        }
        loadedFiles.clear();
        logger.info("File Manager disabled");
    }

    @Override
    public void reload() throws AminyoException {
        for (FileModel file : loadedFiles.values()) {
            file.reload();
        }
    }

    @Override
    public void save() throws AminyoException {
        for (FileModel file : loadedFiles.values()) {
            file.save();
        }
    }

    /**
     * Load or get existing YAML file
     */
    public YamlFileModel getYamlFile(String fileName) {
        return (YamlFileModel) loadedFiles.computeIfAbsent(fileName, k -> {
            YamlFileModel file = null;
            try {
                file = new YamlFileModel(fileName, dataFolder);
            } catch (AminyoRuntimeException e) {
                throw new RuntimeException(e);
            }
            try {
                file.reload();
            } catch (AminyoException e) {
                logger.error("Failed to load YAML file: {}", fileName, e);
                try {
                    throw new AminyoRuntimeException("Failed to load YAML file: " + fileName, e);
                } catch (AminyoRuntimeException ex) {
                    throw new RuntimeException(ex);
                }
            }
            return file;
        });
    }

    /**
     * Load or get existing JSON file
     */
    public JsonFileModel getJsonFile(String fileName) {
        return (JsonFileModel) loadedFiles.computeIfAbsent(fileName, k -> {
            JsonFileModel file = null;
            try {
                file = new JsonFileModel(fileName, dataFolder);
            } catch (AminyoRuntimeException e) {
                throw new RuntimeException(e);
            }
            try {
                file.reload();
            } catch (AminyoException e) {
                logger.error("Failed to load JSON file: {}", fileName, e);
                try {
                    throw new AminyoRuntimeException("Failed to load JSON file: " + fileName, e);
                } catch (AminyoRuntimeException ex) {
                    throw new RuntimeException(ex);
                }
            }
            return file;
        });
    }

    /**
     * Load or get existing TOML file
     */
    public TomlFileModel getTomlFile(String fileName) {
        return (TomlFileModel) loadedFiles.computeIfAbsent(fileName, k -> {
            TomlFileModel file = null;
            try {
                file = new TomlFileModel(fileName, dataFolder);
            } catch (AminyoRuntimeException e) {
                throw new RuntimeException(e);
            }
            try {
                file.reload();
            } catch (AminyoException e) {
                logger.error("Failed to load TOML file: {}", fileName, e);
                try {
                    throw new AminyoRuntimeException("Failed to load TOML file: " + fileName, e);
                } catch (AminyoRuntimeException ex) {
                    throw new RuntimeException(ex);
                }
            }
            return file;
        });
    }

    /**
     * Load or get existing Properties file
     */
    public PropertiesFileModel getPropertiesFile(String fileName) {
        return (PropertiesFileModel) loadedFiles.computeIfAbsent(fileName, k -> {
            PropertiesFileModel file = null;
            try {
                file = new PropertiesFileModel(fileName, dataFolder);
            } catch (AminyoRuntimeException e) {
                throw new RuntimeException(e);
            }
            try {
                file.reload();
            } catch (AminyoException e) {
                logger.error("Failed to load Properties file: {}", fileName, e);
                try {
                    throw new AminyoRuntimeException("Failed to load Properties file: " + fileName, e);
                } catch (AminyoRuntimeException ex) {
                    throw new RuntimeException(ex);
                }
            }
            return file;
        });
    }

    /**
     * Get file by format
     */
    public FileModel getFile(String fileName, FileFormat format) {
        switch (format) {
            case YAML:
                return getYamlFile(fileName);
            case JSON:
                return getJsonFile(fileName);
            case TOML:
                return getTomlFile(fileName);
            case PROPERTIES:
                return getPropertiesFile(fileName);
            default:
                throw new IllegalArgumentException("Unsupported file format: " + format);
        }
    }

    /**
     * Auto-detect format from filename and load
     */
    public FileModel getFileAuto(String fileName) {
        FileFormat format = FileFormat.fromExtension(getFileExtension(fileName));
        if (format == null) {
            format = FileFormat.YAML; // Default to YAML
        }
        return getFile(fileName, format);
    }

    /**
     * Unload a file from memory
     */
    public void unloadFile(String fileName) {
        FileModel file = loadedFiles.remove(fileName);
        if (file != null) {
            try {
                file.save();
            } catch (AminyoException e) {
                logger.warn("Failed to save file before unloading: {}", fileName, e);
            }
        }
    }

    /**
     * Check if file is loaded
     */
    public boolean isFileLoaded(String fileName) {
        return loadedFiles.containsKey(fileName);
    }

    /**
     * Get all loaded file names
     */
    public Set<String> getLoadedFileNames() {
        return new HashSet<>(loadedFiles.keySet());
    }

    /**
     * Get data folder path
     */
    public String getDataFolder() {
        return dataFolder;
    }

    private String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        return lastDot > 0 ? fileName.substring(lastDot + 1) : "";
    }
}
