package dev.aminyo.aminyomclib.velocity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Velocity listener base class
 */
public abstract class VelocityListener {

    protected final AminyoVelocityPlugin plugin;
    protected final Logger logger;

    public VelocityListener() {
        this.plugin = AminyoVelocityPlugin.getInstance();
        this.logger = LoggerFactory.getLogger(getClass());
    }

    /**
     * Register this listener
     */
    public void register() {
        plugin.getServer().getEventManager().register(plugin, this);
    }

    /**
     * Unregister this listener
     */
    public void unregister() {
        plugin.getServer().getEventManager().unregisterListener(plugin, this);
    }
}