package dev.aminyo.aminyomclib.bukkit.core.models;

import java.lang.module.Configuration;

public interface FileModel {
    void reloadConfig();
    Configuration getConfig();
    void saveConfig();
    void createFile();
    void autoUpdateConfig();
}
