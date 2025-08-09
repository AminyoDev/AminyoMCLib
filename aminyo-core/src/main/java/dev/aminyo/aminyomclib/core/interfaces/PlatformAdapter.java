package it.aminyo.aminyomclib.core.interfaces;


import it.aminyo.aminyomclib.core.enums.PlatformType;

import java.util.concurrent.CompletableFuture;

public interface PlatformAdapter {
    /**
     * Get the platform type
     * @return platform type
     */
    PlatformType getPlatformType();

    /**
     * Get the Minecraft version
     * @return version string
     */
    String getMinecraftVersion();

    /**
     * Schedule a task to run asynchronously
     * @param task the task to run
     * @return future result
     */
    CompletableFuture<Void> runAsync(Runnable task);

    /**
     * Schedule a task to run on the main thread
     * @param task the task to run
     * @return future result
     */
    CompletableFuture<Void> runSync(Runnable task);

    /**
     * Get data folder for configurations
     * @return data folder path
     */
    String getDataFolder();

    /**
     * Check if platform supports colors
     * @return true if supports colors
     */
    boolean supportsColors();
}


