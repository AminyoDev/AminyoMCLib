package dev.aminyo.aminyomclib.core.tasks;

import java.util.concurrent.CompletableFuture;

public final class TaskUtils {

    private final PlatformTaskExecutor executor;

    public TaskUtils(PlatformTaskExecutor executor) {
        this.executor = executor;
    }

    public void runSync(Runnable task) {
        executor.runSync(task);
    }

    public void runAsync(Runnable task) {
        executor.runAsync(task);
    }

    public void runSyncLater(Runnable task, long delayTicks) {
        executor.runSyncLater(task, delayTicks);
    }

    public void runSyncRepeating(Runnable task, long delayTicks, long periodTicks) {
        executor.runSyncRepeating(task, delayTicks, periodTicks);
    }

    public void runAsyncLater(Runnable task, long delayTicks) {
        executor.runAsyncLater(task, delayTicks);
    }

    public void runAsyncRepeating(Runnable task, long delayTicks, long periodTicks) {
        executor.runAsyncRepeating(task, delayTicks, periodTicks);
    }

    public <T> CompletableFuture<T> runAsyncTask(AsyncTask<T> task) {
        CompletableFuture<T> future = new CompletableFuture<>();

        runAsync(() -> {
            try {
                T result = task.run();
                runSync(() -> future.complete(result));
            } catch (Exception e) {
                runSync(() -> future.completeExceptionally(e));
            }
        });

        return future;
    }

    @FunctionalInterface
    public interface AsyncTask<T> {
        T run() throws Exception;
    }
}