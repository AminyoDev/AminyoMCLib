package it.aminyo.aminyomclib.file.extensions;

import dev.aminyo.aminyomclib.core.exceptions.AminyoRuntimeException;
import it.aminyo.aminyomclib.file.FileModel;
import it.aminyo.aminyomclib.file.enums.FileFormat;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * JSON file model implementation
 */
public class JsonFileModel extends FileModel {

    private final Gson gson;

    public JsonFileModel(String fileName, String dataFolder) throws AminyoRuntimeException {
        super(fileName.endsWith(".json") ? fileName : fileName + ".json",
                FileFormat.JSON, dataFolder);
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Map<String, Object> loadFromFile() throws IOException {
        String content = new String(Files.readAllBytes(filePath));
        if (content.trim().isEmpty()) {
            return new HashMap<>();
        }

        JsonElement element = JsonParser.parseString(content);
        if (element.isJsonObject()) {
            return gson.fromJson(element, Map.class);
        }

        return new HashMap<>();
    }

    @Override
    protected void saveToFile() throws IOException {
        String json = gson.toJson(data);
        Files.write(filePath, json.getBytes());
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
