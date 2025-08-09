package dev.aminyo.aminyomclib.bungee;

import dev.aminyo.aminyomclib.core.AminyoMCLibImpl;
import dev.aminyo.aminyomclib.core.interfaces.AminyoMCLib;
import net.md_5.bungee.api.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main BungeeCord plugin class that initializes AminyoMCLib
 */
public class AminyoBungeePlugin extends Plugin {

    private static AminyoBungeePlugin instance;
    private BungeePlatformAdapter platformAdapter;
    private AminyoMCLib aminyoMCLib;
    private Logger logger;

    @Override
    public void onEnable() {
        instance = this;
        this.logger = LoggerFactory.getLogger(AminyoBungeePlugin.class);

        try {
            // Initialize platform adapter
            this.platformAdapter = new BungeePlatformAdapter(this);

            // Initialize AminyoMCLib
            this.aminyoMCLib = AminyoMCLibImpl.initialize(platformAdapter);

            logger.info("AminyoMCLib enabled for BungeeCord!");
            logger.info("Platform: {} | Version: {}",
                    platformAdapter.getPlatformType(),
                    platformAdapter.getMinecraftVersion());

        } catch (Exception e) {
            logger.error("Failed to enable AminyoMCLib for BungeeCord", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        try {
            if (aminyoMCLib != null) {
                aminyoMCLib.shutdown();
            }

            logger.info("AminyoMCLib disabled for BungeeCord!");

        } catch (Exception e) {
            logger.error("Error during BungeeCord plugin shutdown", e);
        } finally {
            instance = null;
        }
    }

    /**
     * Get plugin instance
     */
    public static AminyoBungeePlugin getInstance() {
        return instance;
    }

    /**
     * Get platform adapter
     */
    public BungeePlatformAdapter getPlatformAdapter() {
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
    public BungeeScheduler getScheduler() {
        return new BungeeScheduler(this);
    }
}
