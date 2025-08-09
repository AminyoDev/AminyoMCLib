package dev.aminyo.aminyomclib.spigot.tasks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Spigot task scheduler utility
 */
public final class SpigotScheduler {

    private final JavaPlugin plugin;

    public SpigotScheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Run task synchronously
     */
    public void runSync(Runnable task) {
        Bukkit.getScheduler().runTask(plugin, task);
    }

    /**
     * Run task asynchronously
     */
    public void runAsync(Runnable task) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }

    /**
     * Run task later (sync)
     */
    public void runLater(Runnable task, long delayTicks) {
        Bukkit.getScheduler().runTaskLater(plugin, task, delayTicks);
    }

    /**
     * Run task later (async)
     */
    public void runLaterAsync(Runnable task, long delayTicks) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delayTicks);
    }

    /**
     * Run repeating task (sync)
     */
    public int runRepeating(Runnable task, long delayTicks, long periodTicks) {
        return Bukkit.getScheduler().runTaskTimer(plugin, task, delayTicks, periodTicks).getTaskId();
    }

    /**
     * Run repeating task (async)
     */
    public int runRepeatingAsync(Runnable task, long delayTicks, long periodTicks) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task, delayTicks, periodTicks).getTaskId();
    }

    /**
     * Cancel task by ID
     */
    public void cancelTask(int taskId) {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    /**
     * Cancel all tasks for plugin
     */
    public void cancelAllTasks() {
        Bukkit.getScheduler().cancelTasks(plugin);
    }

    /**
     * Convert milliseconds to ticks (20 ticks = 1 second)
     */
    public static long millisecondsToTicks(long milliseconds) {
        return milliseconds / 50; // 1000ms / 20 ticks = 50ms per tick
    }

    /**
     * Convert seconds to ticks
     */
    public static long secondsToTicks(double seconds) {
        return (long) (seconds * 20);
    }
}
