package dev.aminyo.aminyomclib.core.tasks;

public interface PlatformTaskExecutor {
    void runSync(Runnable task);
    void runAsync(Runnable task);
    void runAsyncLater(Runnable task, long delayTicks);
    void runAsyncRepeating(Runnable task, long delayTicks, long periodTicks);
    void runSyncLater(Runnable task, long delayTicks);
    void runSyncRepeating(Runnable task, long delayTicks, long periodTicks);
}

