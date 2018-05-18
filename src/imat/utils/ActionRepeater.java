package imat.utils;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Callback;
import javafx.util.Duration;

import java.util.concurrent.Callable;

public class ActionRepeater implements ChangeListener<Boolean> {
    private Timeline loopTimeline;
    private Runnable action;
    private double rate = 1;

    public ActionRepeater(Runnable action, double delay) {
        this(action, delay,1);
    }

    public ActionRepeater(Runnable action, double delay, double targetSpeedMultiplier) {
        this.action = action;
        loopTimeline = new Timeline(new KeyFrame(Duration.millis(delay), actionEvent-> {
            try {
                action.run();
                rate = (targetSpeedMultiplier * 0.05 + rate * 0.95);
                loopTimeline.setRate(rate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        loopTimeline.setCycleCount(Animation.INDEFINITE);
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if( newValue) {
            action.run();
            loopTimeline.play();
        } else {
            loopTimeline.stop();
            rate = 1;
            loopTimeline.setRate(1);
        }
    }
}
