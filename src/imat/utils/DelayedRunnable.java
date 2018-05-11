package imat.utils;

import javafx.application.Platform;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class DelayedRunnable {

    private final Runnable runnable;

    public DelayedRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public void runLater(long delayMilliseconds) {
        ScheduledThreadPoolExecutor scheduledThreadExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledThreadExecutor.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(runnable);
                scheduledThreadExecutor.shutdown();
            }
        }, delayMilliseconds, TimeUnit.MILLISECONDS);
    }

    public Runnable getRunnable() {
        return runnable;
    }

}
