package dev.aminyo.aminyomclib.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.Scheduler;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Velocity scheduler wrapper
 */
public class VelocityScheduler {

    private final Scheduler scheduler;
    private final Object plugin;

    public VelocityScheduler(Object plugin, ProxyServer server) {
        this.plugin = plugin;
        this.scheduler = server.getScheduler();
    }

    /**
     * Run task asynchronously
     */
    public void runAsync(Runnable task) {
        scheduler.buildTask(plugin, task).schedule();
    }

    /**
     * Run task with delay
     */
    public void runDelayed(Runnable task, long delay, TimeUnit unit) {
        scheduler.buildTask(plugin, task)
                .delay(Duration.ofMillis(unit.toMillis(delay)))
                .schedule();
    }

    /**
     * Run repeating task
     */
    public void runRepeating(Runnable task, long delay, long period, TimeUnit unit) {
        scheduler.buildTask(plugin, task)
                .delay(Duration.ofMillis(unit.toMillis(delay)))
                .repeat(Duration.ofMillis(unit.toMillis(period)))
                .schedule();
    }
}
