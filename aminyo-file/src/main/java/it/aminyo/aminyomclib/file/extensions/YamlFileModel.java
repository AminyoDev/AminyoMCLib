package it.aminyo.aminyomclib.file.extensions;

import dev.aminyo.aminyomclib.core.exceptions.AminyoRuntimeException;
import it.aminyo.aminyomclib.file.FileModel;
import it.aminyo.aminyomclib.file.enums.FileFormat;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

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
