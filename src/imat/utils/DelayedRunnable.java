package imat.utils;

import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public final class DelayedRunnable {

    private final Runnable runnable;

    public DelayedRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public void runLater(long delayMilliseconds) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(runnable);
                timer.cancel();
            }
        }, delayMilliseconds);
    }

    public Runnable getRunnable() {
        return runnable;
    }

}
