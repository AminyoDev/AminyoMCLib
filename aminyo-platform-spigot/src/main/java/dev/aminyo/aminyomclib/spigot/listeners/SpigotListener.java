package dev.aminyo.aminyomclib.spigot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Spigot event listener base class
 */
public abstract class SpigotListener implements Listener {

    protected final JavaPlugin plugin;

    public SpigotListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Register this listener
     */
    public void register() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Send colored message to player
     */
    protected void sendMessage(Player player, String message) {
        SpigotPlayerUtils.sendMessage(player, message);
    }

    protected JavaPlugin getPlugin() {
        return plugin;
    }
}
