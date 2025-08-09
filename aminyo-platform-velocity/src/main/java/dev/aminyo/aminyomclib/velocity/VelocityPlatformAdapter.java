package dev.aminyo.aminyomclib.velocity;

import com.velocitypowered.api.proxy.ProxyServer;

import dev.aminyo.aminyomclib.core.enums.PlatformType;
import dev.aminyo.aminyomclib.core.interfaces.PlatformAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;


/**
 * Velocity platform adapter implementation
 */
public class VelocityPlatformAdapter implements PlatformAdapter {

    private final Object plugin;
    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;

    public VelocityPlatformAdapter(Object plugin, ProxyServer server, Path dataDirectory) {
        this.plugin = plugin;
        this.server = server;
        this.dataDirectory = dataDirectory;
        this.logger = LoggerFactory.getLogger(VelocityPlatformAdapter.class);
    }

    @Override
    public PlatformType getPlatformType() {
        return PlatformType.VELOCITY;
    }

    @Override
    public String getMinecraftVersion() {
        // Velocity doesn't have a single MC version - it supports multiple
        // Return the proxy version instead
        return server.getVersion().getVersion();
    }

    @Override
    public CompletableFuture<Void> runAsync(Runnable task) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        server.getScheduler()
                .buildTask(plugin, () -> {
                    try {
                        task.run();
                        future.complete(null);
                    } catch (Exception e) {
                        future.completeExceptionally(e);
                    }
                })
                .schedule();

        return future;
    }

    @Override
    public CompletableFuture<Void> runSync(Runnable task) {
        // Velocity doesn't have a main thread concept like Bukkit/Spigot
        // All tasks run asynchronously, but we can simulate sync behavior
        return runAsync(task);
    }

    @Override
    public String getDataFolder() {
        return dataDirectory.toAbsolutePath().toString();
    }

    @Override
    public boolean supportsColors() {
        return true;
    }

    /**
     * Get the plugin instance
     */
    public Object getPlugin() {
        return plugin;
    }

    /**
     * Get ProxyServer instance
     */
    public ProxyServer getServer() {
        return server;
    }
}
