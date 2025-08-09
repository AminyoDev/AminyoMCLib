package dev.aminyo.aminyomclib.bungee;

import dev.aminyo.aminyomclib.core.*;
import dev.aminyo.aminyomclib.core.enums.PlatformType;
import dev.aminyo.aminyomclib.core.interfaces.PlatformAdapter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.api.scheduler.TaskScheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public class BungeePlatformAdapter implements PlatformAdapter {
    private final Plugin plugin;
    private final Logger logger;

    public BungeePlatformAdapter(Plugin plugin) {
        this.plugin = plugin;
        this.logger = LoggerFactory.getLogger(BungeePlatformAdapter.class);
    }

    @Override
    public PlatformType getPlatformType() {
        return PlatformType.BUNGEECORD;
    }

    @Override
    public String getMinecraftVersion() {
        return ProxyServer.getInstance().getVersion();
    }

    @Override
    public CompletableFuture<Void> runAsync(Runnable task) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        ProxyServer.getInstance().getScheduler().runAsync(plugin, () -> {
            try {
                task.run();
                future.complete(null);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    @Override
    public CompletableFuture<Void> runSync(Runnable task) {
        // BungeeCord doesn't have a main thread concept like Bukkit/Spigot
        // All tasks run asynchronously, but we can simulate sync behavior
        return runAsync(task);
    }

    @Override
    public String getDataFolder() {
        return plugin.getDataFolder().getAbsolutePath();
    }

    @Override
    public boolean supportsColors() {
        return true;
    }

    /**
     * Get the plugin instance
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Get ProxyServer instance
     */
    public ProxyServer getProxy() {
        return ProxyServer.getInstance();
    }
}
