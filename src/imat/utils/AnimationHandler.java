package imat.utils;

import com.sun.istack.internal.Nullable;
import imat.interfaces.IAction;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.util.Duration;

import java.util.Arrays;

public final class AnimationHandler {

    public static Timeline getAnimation(KeyFrame... keyFrames) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(Arrays.asList(keyFrames));
        return timeline;
    }

    public static Timeline getAnimation(@Nullable IAction<Void> onFinishedAction, KeyFrame... keyFrames) {
        return setOnFinishedEvent(getAnimation(keyFrames), onFinishedAction);
    }

    public static Timeline getOpacityChangeAnimation(
            Node target,
            double durationInMillis,
            double endOpacity,
            @Nullable IAction<Void> onFinishedAction) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(getOpacityChangeKeyFrame(target, durationInMillis, endOpacity));
        return setOnFinishedEvent(timeline, onFinishedAction);
    }

    public static Timeline getHeightChangeAnimation(
            Region target,
            double durationInMillis,
            double endHeight,
            @Nullable IAction<Void> onFinishedAction) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(getHeightChangeKeyFrame(target, durationInMillis, endHeight));
        return setOnFinishedEvent(timeline, onFinishedAction);
    }

    public static Timeline getWidthChangeAnimation(
            Region target,
            double durationInMillis,
            double endWidth,
            @Nullable IAction<Void> onFinishedAction) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(getWidthChangeKeyFrame(target, durationInMillis, endWidth));
        return setOnFinishedEvent(timeline, onFinishedAction);
    }

    public static KeyFrame getOpacityChangeKeyFrame(Node target, double durationInMillis, double endOpacity) {
        return getAnimationKeyFrame(target.opacityProperty(), durationInMillis, endOpacity);
    }

    public static KeyFrame getHeightChangeKeyFrame(Region target, double durationInMillis, double endHeight) {
        return getAnimationKeyFrame(target.prefHeightProperty(), durationInMillis, endHeight);
    }

    public static KeyFrame getWidthChangeKeyFrame(Region target, double durationInMillis, double endWidth) {
        return getAnimationKeyFrame(target.prefWidthProperty(), durationInMillis, endWidth);
    }

    public static KeyFrame getXTranslationKeyFrame(Region target, double durationInMillis, double endX) {
        return getAnimationKeyFrame(target.translateXProperty(), durationInMillis, endX);
    }

    public static KeyFrame getYTranslationKeyFrame(Region target, double durationInMillis, double endY) {
        return getAnimationKeyFrame(target.translateXProperty(), durationInMillis, endY);
    }


    public static KeyFrame getAnimationKeyFrame(
            DoubleProperty propertyTarget,
            double durationInMillis,
            double endValue) {
        return new KeyFrame(Duration.millis(durationInMillis),
                new KeyValue(propertyTarget, endValue, Interpolator.EASE_BOTH));
    }

    private static Timeline setOnFinishedEvent(Timeline timeline, @Nullable IAction<Void> onFinishedAction) {
        if (onFinishedAction != null) {
            timeline.setOnFinished(event -> onFinishedAction.execute(null));
        }
        return timeline;
    }

}
