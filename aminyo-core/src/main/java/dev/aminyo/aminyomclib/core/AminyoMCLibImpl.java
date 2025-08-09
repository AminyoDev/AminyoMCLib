package it.aminyo.aminyomclib.core;

import it.aminyo.aminyomclib.core.interfaces.AminyoMCLib;
import it.aminyo.aminyomclib.core.interfaces.PlatformAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AminyoMCLibImpl implements AminyoMCLib {
    private static final Logger LOGGER = LoggerFactory.getLogger(AminyoMCLibImpl.class);
    private static final String VERSION = "1.0.0";
    private static AminyoMCLibImpl instance;

    private final PlatformAdapter platformAdapter;
    private final ExecutorService executorService;

    private AminyoMCLibImpl(PlatformAdapter platformAdapter) {
        this.platformAdapter = platformAdapter;
        this.executorService = Executors.newCachedThreadPool(r -> {
            Thread thread = new Thread(r, "AminyoMCLib-Thread");
            thread.setDaemon(true);
            return thread;
        });

        LOGGER.info("AminyoMCLib v{} initialized for platform: {}",
                VERSION, platformAdapter.getPlatformType());
    }

    /**
     * Initialize AminyoMCLib with platform adapter
     * @param platformAdapter the platform adapter
     * @return AminyoMCLib instance
     */
    public static AminyoMCLib initialize(PlatformAdapter platformAdapter) {
        if (instance == null) {
            instance = new AminyoMCLibImpl(platformAdapter);
        }
        return instance;
    }

    /**
     * Get the current instance
     * @return current instance or null if not initialized
     */
    public static AminyoMCLib getInstance() {
        return instance;
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public PlatformAdapter getPlatformAdapter() {
        return platformAdapter;
    }

    @Override
    public void shutdown() {
        LOGGER.info("Shutting down AminyoMCLib...");

        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }

        instance = null;
        LOGGER.info("AminyoMCLib shutdown complete");
    }

    /**
     * Get the executor service for async operations
     * @return executor service
     */
    public ExecutorService getExecutorService() {
        return executorService;
    }
}
