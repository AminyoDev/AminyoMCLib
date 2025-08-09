package dev.aminyo.aminyomclib.velocity;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.aminyo.aminyomclib.core.AminyoMCLibImpl;
import dev.aminyo.aminyomclib.core.interfaces.AminyoMCLib;
import org.slf4j.Logger;

import java.nio.file.Path;

/**
 * Main Velocity plugin class that initializes AminyoMCLib
 */
@Plugin(
        id = "aminyo-mclib",
        name = "AminyoMCLib",
        version = "1.0.0",
        description = "AminyoMCLib for Velocity",
        authors = {"Aminyo"}
)
public class AminyoVelocityPlugin {

    private static AminyoVelocityPlugin instance;
    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;

    private VelocityPlatformAdapter platformAdapter;
    private AminyoMCLib aminyoMCLib;

    public AminyoVelocityPlugin(ProxyServer server, Logger logger, Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        instance = this;
    }

    public void onProxyInitialization() {
        try {
            // Initialize platform adapter
            this.platformAdapter = new VelocityPlatformAdapter(this, server, dataDirectory);

            // Initialize AminyoMCLib
            this.aminyoMCLib = AminyoMCLibImpl.initialize(platformAdapter);

            logger.info("AminyoMCLib enabled for Velocity!");
            logger.info("Platform: {} | Version: {}",
                    platformAdapter.getPlatformType(),
                    platformAdapter.getMinecraftVersion());

        } catch (Exception e) {
            logger.error("Failed to enable AminyoMCLib for Velocity", e);
            throw new RuntimeException(e);
        }
    }

    public void onProxyShutdown() {
        try {
            if (aminyoMCLib != null) {
                aminyoMCLib.shutdown();
            }

            logger.info("AminyoMCLib disabled for Velocity!");

        } catch (Exception e) {
            logger.error("Error during Velocity plugin shutdown", e);
        } finally {
            instance = null;
        }
    }

    /**
     * Get plugin instance
     */
    public static AminyoVelocityPlugin getInstance() {
        return instance;
    }

    /**
     * Get platform adapter
     */
    public VelocityPlatformAdapter getPlatformAdapter() {
        return platformAdapter;
    }

    /**
     * Get AminyoMCLib instance
     */
    public AminyoMCLib getAminyoMCLib() {
        return aminyoMCLib;
    }

    /**
     * Get scheduler wrapper
     */
    public VelocityScheduler getScheduler() {
        return new VelocityScheduler(this, server);
    }

    /**
     * Get proxy server
     */
    public ProxyServer getServer() {
        return server;
    }

    /**
     * Get logger
     */
    public Logger getLogger() {
        return logger;
    }
}
