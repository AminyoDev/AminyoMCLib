package dev.aminyo.aminyomclib.bukkit.tasks;

import dev.aminyo.aminyomclib.core.tasks.PlatformTaskExecutor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BukkitExecutor implements PlatformTaskExecutor {
    private final Plugin plugin;

    public BukkitExecutor(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void runSync(Runnable task) {
        Bukkit.getScheduler().runTask(plugin, task);
    }

    @Override
    public void runAsync(Runnable task) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }

    @Override
    public void runAsyncLater(Runnable task, long delayTicks) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delayTicks);
    }

    @Override
    public void runAsyncRepeating(Runnable task, long delayTicks, long periodTicks) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task, delayTicks, periodTicks);
    }

    @Override
    public void runSyncLater(Runnable task, long delayTicks) {
        Bukkit.getScheduler().runTaskLater(plugin, task, delayTicks);
    }

    @Override
    public void runSyncRepeating(Runnable task, long delayTicks, long periodTicks) {
        Bukkit.getScheduler().runTaskTimer(plugin, task, delayTicks, periodTicks);
    }
}
