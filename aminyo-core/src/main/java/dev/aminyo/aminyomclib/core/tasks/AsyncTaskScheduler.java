package dev.aminyo.aminyomclib.core.tasks;

import java.util.ArrayList;

public class AsyncTaskScheduler {

    private final PlatformTaskExecutor executor;
    private final ArrayList<Runnable> runnables = new ArrayList<>();
    private boolean processing = false;
    private boolean running = false;

    public AsyncTaskScheduler(PlatformTaskExecutor executor) {
        this.executor = executor;
        startScheduler();
    }

    public void startScheduler() {
        if (running) return;

        running = true;

        executor.runAsyncRepeating(() -> {
            if (!processing) {
                processing = true;
                ArrayList<Runnable> copied = new ArrayList<>(runnables);
                runnables.clear();
                for (Runnable r : copied) {
                    try {
                        r.run();
                    } catch (Exception ignored) {}
                }
                processing = false;
            }
        }, 0L, 5L);
    }

    public void scheduleRunnable(Runnable runnable) {
        if (!processing) {
            runnables.add(runnable);
        } else {
            executor.runSyncLater(() -> runnables.add(runnable), 1L);
        }
    }

    public void stopScheduler() {
        running = false;
        runnables.clear();
        processing = false;
    }
}
