package dev.aminyo.aminyomclib.bungee.models;

import dev.aminyo.aminyomclib.core.models.FileModel;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class BungeeFileModel implements FileModel {

    protected File file;
    protected Configuration config;

    @Override
    public void reloadConfig() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    @Override
    public void saveConfig() {
        if (file == null || config == null) return;
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public abstract void createFile();
    @Override
    public abstract void autoUpdateConfig();
}

