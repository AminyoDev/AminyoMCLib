package it.aminyo.aminyomclib.file;

import dev.aminyo.aminyomclib.core.exceptions.AminyoRuntimeException;
import it.aminyo.aminyomclib.file.enums.FileFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * YAML file model implementation
 */
public class YamlFileModel extends FileModel {

    private final Yaml yaml;

    public YamlFileModel(String fileName, String dataFolder) throws AminyoRuntimeException {
        super(fileName.endsWith(".yml") || fileName.endsWith(".yaml") ? fileName : fileName + ".yml",
                FileFormat.YAML, dataFolder);
        this.yaml = new Yaml();
    }

    @Override
    protected Map<String, Object> loadFromFile() throws IOException {
        try (InputStream inputStream = Files.newInputStream(filePath)) {
            Map<String, Object> loadedData = yaml.load(inputStream);
            return loadedData != null ? loadedData : new HashMap<>();
        }
    }

    @Override
    protected void saveToFile() throws IOException {
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            yaml.dump(data, writer);
        }
    }

    @Override
    protected void createDefaultFile() throws IOException {
        Map<String, Object> defaultData = createDefaultData();
        synchronized (data) {
            data.putAll(defaultData);
        }
        saveToFile();
    }

    protected Map<String, Object> createDefaultData() {
        return new HashMap<>();
    }
}
