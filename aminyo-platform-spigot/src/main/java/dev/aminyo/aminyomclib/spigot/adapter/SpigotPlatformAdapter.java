package dev.aminyo.aminyomclib.spigot.adapter;

import dev.aminyo.aminyomclib.core.enums.PlatformType;
import dev.aminyo.aminyomclib.core.interfaces.PlatformAdapter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.CompletableFuture;

public class SpigotPlatformAdapter implements PlatformAdapter {

    private final JavaPlugin plugin;
    private final String version;

    public SpigotPlatformAdapter(JavaPlugin plugin) {
        this.plugin = plugin;
        this.version = detectMinecraftVersion();
    }

    @Override
    public PlatformType getPlatformType() {
        String serverName = Bukkit.getServer().getName().toLowerCase();

        if (serverName.contains("paper")) {
            return PlatformType.PAPER;
        } else if (serverName.contains("spigot")) {
            return PlatformType.SPIGOT;
        } else {
            return PlatformType.BUKKIT;
        }
    }

    @Override
    public String getMinecraftVersion() {
        return version;
    }

    @Override
    public CompletableFuture<Void> runAsync(Runnable task) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
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
        CompletableFuture<Void> future = new CompletableFuture<>();

        Bukkit.getScheduler().runTask(plugin, () -> {
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
    public String getDataFolder() {
        return plugin.getDataFolder().getAbsolutePath();
    }

    @Override
    public boolean supportsColors() {
        return true;
    }

    private String detectMinecraftVersion() {
        String version = Bukkit.getBukkitVersion();
        // Extract version from format like "1.21.8-R0.1-SNAPSHOT"
        if (version.contains("-")) {
            version = version.split("-")[0];
        }
        return version;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
