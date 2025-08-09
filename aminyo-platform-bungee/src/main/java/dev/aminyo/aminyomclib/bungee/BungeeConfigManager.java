package dev.aminyo.aminyomclib.bungee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * BungeeCord configuration manager
 */
public class BungeeConfigManager {

    private final AminyoBungeePlugin plugin;
    private final File configFile;
    private net.md_5.bungee.config.Configuration config;

    public BungeeConfigManager(AminyoBungeePlugin plugin, String fileName) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), fileName);
        loadConfig();
    }

    private void loadConfig() {
        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }

            if (!configFile.exists()) {
                // Create default config from resources
                createDefaultConfig();
            }

            this.config = net.md_5.bungee.config.ConfigurationProvider
                    .getProvider(net.md_5.bungee.config.YamlConfiguration.class)
                    .load(configFile);

        } catch (Exception e) {
            plugin.getLogger().severe("Failed to load configuration: " + e.getMessage());
            this.config = new net.md_5.bungee.config.Configuration();
        }
    }

    private void createDefaultConfig() {
        try {
            // Try to copy from resources
            InputStream resourceStream = plugin.getResourceAsStream(configFile.getName());
            if (resourceStream != null) {
                Files.copy(resourceStream, configFile.toPath());
                resourceStream.close();
            } else {
                // Create empty config if resource doesn't exist
                configFile.createNewFile();
            }
        } catch (IOException e) {
            plugin.getLogger().warning("Could not create default config: " + e.getMessage());
            try {
                configFile.createNewFile();
            } catch (IOException ex) {
                plugin.getLogger().severe("Failed to create config file: " + ex.getMessage());
            }
        }
    }

    /**
     * Reload configuration
     */
    public void reload() {
        loadConfig();
    }

    /**
     * Save configuration
     */
    public void save() {
        try {
            net.md_5.bungee.config.ConfigurationProvider
                    .getProvider(net.md_5.bungee.config.YamlConfiguration.class)
                    .save(config, configFile);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to save configuration: " + e.getMessage());
        }
    }

    /**
     * Get configuration
     */
    public net.md_5.bungee.config.Configuration getConfig() {
        return config;
    }

    /**
     * Get string value
     */
    public String getString(String path, String defaultValue) {
        return config.getString(path, defaultValue);
    }

    /**
     * Get integer value
     */
    public int getInt(String path, int defaultValue) {
        return config.getInt(path, defaultValue);
    }

    /**
     * Get boolean value
     */
    public boolean getBoolean(String path, boolean defaultValue) {
        return config.getBoolean(path, defaultValue);
    }

    /**
     * Get double value
     */
    public double getDouble(String path, double defaultValue) {
        return config.getDouble(path, defaultValue);
    }

    /**
     * Get list value
     */
    public java.util.List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    /**
     * Set value
     */
    public void set(String path, Object value) {
        config.set(path, value);
    }
}