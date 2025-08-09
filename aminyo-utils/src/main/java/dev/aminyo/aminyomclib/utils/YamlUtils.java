package dev.aminyo.aminyomclib.utils;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.util.*;

/**
 * YAML utilities for parsing and formatting
 */
public final class YamlUtils {

    private static final Yaml YAML;
    private static final Yaml PRETTY_YAML;

    static {
        // Standard YAML configuration
        YAML = new Yaml();

        // Pretty YAML configuration
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        options.setIndent(2);

        Representer representer = new Representer(options);
        representer.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        PRETTY_YAML = new Yaml(representer, options);
    }

    private YamlUtils() {}

    /**
     * Convert object to YAML string
     * @param object object to convert
     * @return YAML string
     */
    public static String toYaml(Object object) {
        try {
            return PRETTY_YAML.dump(object);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Parse YAML string to object
     * @param yaml YAML string
     * @param clazz target class
     * @return parsed object or null if failed
     */
    public static <T> T fromYaml(String yaml, Class<T> clazz) {
        if (TextUtils.isEmpty(yaml)) {
            return null;
        }

        try {
            Object obj = YAML.load(yaml);
            return clazz.cast(obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Parse YAML string to Map
     * @param yaml YAML string
     * @return Map representation or empty map if failed
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> fromYamlToMap(String yaml) {
        if (TextUtils.isEmpty(yaml)) {
            return new HashMap<>();
        }

        try {
            Object obj = YAML.load(yaml);
            return obj instanceof Map ? (Map<String, Object>) obj : new HashMap<>();
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    /**
     * Parse YAML string to List
     * @param yaml YAML string
     * @return List representation or empty list if failed
     */
    @SuppressWarnings("unchecked")
    public static List<Object> fromYamlToList(String yaml) {
        if (TextUtils.isEmpty(yaml)) {
            return new ArrayList<>();
        }

        try {
            Object obj = YAML.load(yaml);
            return obj instanceof List ? (List<Object>) obj : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Load YAML from file
     * @param file file to load
     * @return Map representation or empty map if failed
     */
    public static Map<String, Object> loadFromFile(File file) {
        if (file == null || !file.exists()) {
            return new HashMap<>();
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            Object obj = YAML.load(fis);
            return obj instanceof Map ? (Map<String, Object>) obj : new HashMap<>();
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    /**
     * Save YAML to file
     * @param object object to save
     * @param file target file
     * @return true if successful
     */
    public static boolean saveToFile(Object object, File file) {
        if (file == null) {
            return false;
        }

        try {
            // Create parent directories if they don't exist
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            try (FileWriter writer = new FileWriter(file)) {
                PRETTY_YAML.dump(object, writer);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if string is valid YAML
     * @param yaml YAML string
     * @return true if valid
     */
    public static boolean isValidYaml(String yaml) {
        if (TextUtils.isEmpty(yaml)) {
            return false;
        }

        try {
            YAML.load(yaml);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Deep merge two YAML maps
     * @param base base YAML map
     * @param overlay overlay YAML map
     * @return merged YAML map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> merge(Map<String, Object> base, Map<String, Object> overlay) {
        Map<String, Object> result = new LinkedHashMap<>(base);

        for (Map.Entry<String, Object> entry : overlay.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (result.containsKey(key) && result.get(key) instanceof Map && value instanceof Map) {
                Map<String, Object> baseMap = (Map<String, Object>) result.get(key);
                Map<String, Object> overlayMap = (Map<String, Object>) value;
                result.put(key, merge(baseMap, overlayMap));
            } else {
                result.put(key, value);
            }
        }

        return result;
    }

    /**
     * Extract value from YAML path
     * @param yaml YAML map
     * @param path dot-separated path
     * @return value or null if not found
     */
    @SuppressWarnings("unchecked")
    public static Object getValueFromPath(Map<String, Object> yaml, String path) {
        if (yaml == null || TextUtils.isEmpty(path)) {
            return null;
        }

        String[] parts = path.split("\\.");
        Object current = yaml;

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
     * Set value at YAML path
     * @param yaml YAML map
     * @param path dot-separated path
     * @param value value to set
     */
    @SuppressWarnings("unchecked")
    public static void setValueAtPath(Map<String, Object> yaml, String path, Object value) {
        if (yaml == null || TextUtils.isEmpty(path)) {
            return;
        }

        String[] parts = path.split("\\.");
        Map<String, Object> current = yaml;

        for (int i = 0; i < parts.length - 1; i++) {
            String part = parts[i];
            Object next = current.get(part);

            if (!(next instanceof Map)) {
                next = new LinkedHashMap<String, Object>();
                current.put(part, next);
            }

            current = (Map<String, Object>) next;
        }

        current.put(parts[parts.length - 1], value);
    }

    /**
     * Convert YAML to JSON
     * @param yaml YAML string
     * @return JSON string
     */
    public static String yamlToJson(String yaml) {
        Map<String, Object> map = fromYamlToMap(yaml);
        return JsonUtils.toJson(map);
    }

    /**
     * Convert JSON to YAML
     * @param json JSON string
     * @return YAML string
     */
    public static String jsonToYaml(String json) {
        Map<String, Object> map = JsonUtils.fromJsonToMap(json);
        return toYaml(map);
    }
}
