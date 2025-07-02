package dev.aminyo.aminyomclib.core.tasks;

import dev.aminyo.aminyomclib.bukkit.tasks.BukkitExecutor;
import dev.aminyo.aminyomclib.bungee.tasks.BungeeExecutor;
import org.bukkit.plugin.Plugin;

public class AsyncTaskSchedulerFactory {
    public static AsyncTaskScheduler create(Object plugin) {
        if (plugin instanceof Plugin) { // Bukkit plugin
            return new AsyncTaskScheduler(new BukkitExecutor((Plugin) plugin));
        } else if (plugin instanceof net.md_5.bungee.api.plugin.Plugin) {
            return new AsyncTaskScheduler(new BungeeExecutor((net.md_5.bungee.api.plugin.Plugin) plugin));
        } else {
            throw new IllegalArgumentException("Unsupported plugin type");
        }
    }
}
