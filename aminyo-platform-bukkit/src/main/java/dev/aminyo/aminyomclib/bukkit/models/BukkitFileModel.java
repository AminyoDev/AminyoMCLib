package dev.aminyo.aminyomclib.bukkit.models;

import dev.aminyo.aminyomclib.core.models.FileModel;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class BukkitFileModel implements FileModel {
    protected File file;
    protected FileConfiguration config;

    @Override
    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    @Override
    public void saveConfig() {
        if (config == null || file == null) {
            return;
        }
        try {
            getConfig().save(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public abstract void createFile();
    @Override
    public abstract void autoUpdateConfig();
}
