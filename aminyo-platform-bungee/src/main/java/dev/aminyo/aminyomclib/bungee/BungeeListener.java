package dev.aminyo.aminyomclib.bungee;

import net.md_5.bungee.api.ProxyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BungeeCord listener base class
 */
public abstract class BungeeListener implements net.md_5.bungee.api.plugin.Listener {

    protected final AminyoBungeePlugin plugin;
    protected final Logger logger;

    public BungeeListener() {
        this.plugin = AminyoBungeePlugin.getInstance();
        this.logger = LoggerFactory.getLogger(getClass());
    }

    /**
     * Register this listener
     */
    public void register() {
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
    }

    /**
     * Unregister this listener
     */
    public void unregister() {
        ProxyServer.getInstance().getPluginManager().unregisterListener(this);
    }
}
