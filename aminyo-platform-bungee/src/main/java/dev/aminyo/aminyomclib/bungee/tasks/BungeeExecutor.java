package dev.aminyo.aminyomclib.bungee.tasks;

import dev.aminyo.aminyomclib.core.tasks.PlatformTaskExecutor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.concurrent.TimeUnit;

public class BungeeExecutor implements PlatformTaskExecutor {
    private final Plugin plugin;

    public BungeeExecutor(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void runSync(Runnable task) {
        task.run();
    }

    @Override
    public void runAsync(Runnable task) {
        ProxyServer.getInstance().getScheduler().runAsync(plugin, task);
    }

    @Override
    public void runAsyncLater(Runnable task, long delayTicks) {
        long delayMillis = delayTicks * 50;
        ProxyServer.getInstance().getScheduler().schedule(plugin, task, delayMillis, TimeUnit.MILLISECONDS);
    }

    @Override
    public void runAsyncRepeating(Runnable task, long delayTicks, long periodTicks) {
        long delayMillis = delayTicks * 50;
        long periodMillis = periodTicks * 50;
        ProxyServer.getInstance().getScheduler().schedule(plugin, task, delayMillis, periodMillis, TimeUnit.MILLISECONDS);
    }

    @Override
    public void runSyncLater(Runnable task, long delayTicks) {
        runAsyncLater(task, delayTicks);
    }

    @Override
    public void runSyncRepeating(Runnable task, long delayTicks, long periodTicks) {
        runAsyncRepeating(task, delayTicks, periodTicks);
    }
}
