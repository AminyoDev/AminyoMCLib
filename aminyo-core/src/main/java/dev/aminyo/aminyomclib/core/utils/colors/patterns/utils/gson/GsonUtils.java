package dev.aminyo.aminyomclib.core.utils.colors.patterns.utils.gson;

import com.google.gson.*;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GsonUtils {

    private static final Gson gson = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .create();

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    public static JsonElement parseJson(String json) throws JsonSyntaxException {
        return JsonParser.parseString(json);
    }

    public static boolean isValidJson(String json) {
        try {
            JsonParser.parseString(json);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }

    public static JsonObject toJsonObject(Object obj) {
        return gson.toJsonTree(obj).getAsJsonObject();
    }

    public static JsonArray toJsonArray(Object obj) {
        return gson.toJsonTree(obj).getAsJsonArray();
    }

    public static <T> T fromJsonFile(String filePath, Class<T> clazz) throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            return gson.fromJson(reader, clazz);
        }
    }

    public static void toJsonFile(String filePath, Object obj) throws IOException {
        try (Writer writer = Files.newBufferedWriter(Paths.get(filePath))) {
            gson.toJson(obj, writer);
        }
    }

    public static <T> T fromJsonFile(String filePath, Type typeOfT) throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            return gson.fromJson(reader, typeOfT);
        }
    }

    public static Gson getGson() {
        return gson;
    }
}


