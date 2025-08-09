package dev.aminyo.aminyomclib.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.*;

/**
 * JSON utilities for parsing and formatting
 */
public final class JsonUtils {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    private static final Gson COMPACT_GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .create();

    private JsonUtils() {}

    /**
     * Convert object to JSON string
     * @param object object to convert
     * @return JSON string
     */
    public static String toJson(Object object) {
        try {
            return GSON.toJson(object);
        } catch (Exception e) {
            return "{}";
        }
    }

    /**
     * Convert object to compact JSON string
     * @param object object to convert
     * @return compact JSON string
     */
    public static String toCompactJson(Object object) {
        try {
            return COMPACT_GSON.toJson(object);
        } catch (Exception e) {
            return "{}";
        }
    }

    /**
     * Parse JSON string to object
     * @param json JSON string
     * @param clazz target class
     * @return parsed object or null if failed
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        try {
            return GSON.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    /**
     * Parse JSON string to Map
     * @param json JSON string
     * @return Map representation or empty map if failed
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> fromJsonToMap(String json) {
        Map<String, Object> result = fromJson(json, Map.class);
        return result != null ? result : new HashMap<>();
    }

    /**
     * Parse JSON string to List
     * @param json JSON string
     * @return List representation or empty list if failed
     */
    @SuppressWarnings("unchecked")
    public static List<Object> fromJsonToList(String json) {
        List<Object> result = fromJson(json, List.class);
        return result != null ? result : new ArrayList<>();
    }

    /**
     * Check if string is valid JSON
     * @param json JSON string
     * @return true if valid
     */
    public static boolean isValidJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return false;
        }

        try {
            JsonParser.parseString(json);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }

    /**
     * Pretty print JSON string
     * @param json JSON string
     * @return formatted JSON string
     */
    public static String prettyPrint(String json) {
        try {
            JsonElement element = JsonParser.parseString(json);
            return GSON.toJson(element);
        } catch (Exception e) {
            return json;
        }
    }

    /**
     * Minify JSON string
     * @param json JSON string
     * @return minified JSON string
     */
    public static String minify(String json) {
        try {
            JsonElement element = JsonParser.parseString(json);
            return COMPACT_GSON.toJson(element);
        } catch (Exception e) {
            return json;
        }
    }

    /**
     * Deep merge two JSON objects
     * @param base base JSON object
     * @param overlay overlay JSON object
     * @return merged JSON object
     */
    public static Map<String, Object> merge(Map<String, Object> base, Map<String, Object> overlay) {
        Map<String, Object> result = new HashMap<>(base);

        for (Map.Entry<String, Object> entry : overlay.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (result.containsKey(key) && result.get(key) instanceof Map && value instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> baseMap = (Map<String, Object>) result.get(key);
                @SuppressWarnings("unchecked")
                Map<String, Object> overlayMap = (Map<String, Object>) value;
                result.put(key, merge(baseMap, overlayMap));
            } else {
                result.put(key, value);
            }
        }

        return result;
    }

    /**
     * Extract value from JSON path
     * @param json JSON object
     * @param path dot-separated path (e.g., "user.profile.name")
     * @return value or null if not found
     */
    @SuppressWarnings("unchecked")
    public static Object getValueFromPath(Map<String, Object> json, String path) {
        if (json == null || TextUtils.isEmpty(path)) {
            return null;
        }

        String[] parts = path.split("\\.");
        Object current = json;

        for (String part : parts) {
            if (current instanceof Map) {
                current = ((Map<String, Object>) current).get(part);
            } else {
                return null;
            }
        }

        return current;
    }

    /**
     * Set value at JSON path
     * @param json JSON object
     * @param path dot-separated path
     * @param value value to set
     */
    @SuppressWarnings("unchecked")
    public static void setValueAtPath(Map<String, Object> json, String path, Object value) {
        if (json == null || TextUtils.isEmpty(path)) {
            return;
        }

        String[] parts = path.split("\\.");
        Map<String, Object> current = json;

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
}