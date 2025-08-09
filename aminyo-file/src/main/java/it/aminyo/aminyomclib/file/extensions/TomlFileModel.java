package it.aminyo.aminyomclib.file.extensions;

import dev.aminyo.aminyomclib.core.exceptions.AminyoRuntimeException;
import it.aminyo.aminyomclib.file.FileModel;
import it.aminyo.aminyomclib.file.enums.FileFormat;

import java.io.*;
import java.util.*;


import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;

/**
 * TOML file model implementation
 */
public class TomlFileModel extends FileModel {

    private final TomlWriter tomlWriter;

    public TomlFileModel(String fileName, String dataFolder) throws AminyoRuntimeException {
        super(fileName.endsWith(".toml") ? fileName : fileName + ".toml",
                FileFormat.TOML, dataFolder);
        this.tomlWriter = new TomlWriter();
    }

    @Override
    protected Map<String, Object> loadFromFile() throws IOException {
        Toml toml = new Toml().read(filePath.toFile());
        return toml.toMap();
    }

    @Override
    protected void saveToFile() throws IOException {
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            tomlWriter.write(data, writer);
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
