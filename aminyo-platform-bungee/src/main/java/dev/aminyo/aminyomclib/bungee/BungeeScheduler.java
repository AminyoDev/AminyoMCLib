package dev.aminyo.aminyomclib.bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.TaskScheduler;

import java.util.concurrent.TimeUnit;

/**
 * BungeeCord scheduler wrapper
 */
public class BungeeScheduler {

    private final TaskScheduler scheduler;
    private final Plugin plugin;

    public BungeeScheduler(Plugin plugin) {
        this.plugin = plugin;
        this.scheduler = ProxyServer.getInstance().getScheduler();
    }

    /**
     * Run task asynchronously
     */
    public void runAsync(Runnable task) {
        scheduler.runAsync(plugin, task);
    }

    /**
     * Run task with delay (async)
     */
    public void runDelayed(Runnable task, long delay, TimeUnit unit) {
        scheduler.schedule(plugin, task, delay, unit);
    }

    /**
     * Run repeating task
     */
    public void runRepeating(Runnable task, long delay, long period, TimeUnit unit) {
        scheduler.schedule(plugin, task, delay, period, unit);
    }

    /**
     * Cancel all tasks for plugin
     */
    public void cancelAllTasks() {
        scheduler.cancel(plugin);
    }
}
