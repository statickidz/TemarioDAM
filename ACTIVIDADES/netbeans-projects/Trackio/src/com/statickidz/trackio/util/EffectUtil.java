package com.statickidz.trackio.util;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.SequentialTransitionBuilder;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Various utilities for applying different effects to nodes.
 *
 * @author Adrián Barrio Andrés
 * @email statickidz@gmail.com
 * @web https://statickidz.com
 */
public class EffectUtil {

    private static double x;
    private static double y;

    private static double initialX;
    private static double initialY;

    /**
     * makes a stage draggable using a given node
     *
     * @param n
     * @param primaryStage
     */
    public static void addDragListeners(final Node n, Stage primaryStage) {

        n.setOnMousePressed((MouseEvent mouseEvent) -> {
            EffectUtil.x = n.getScene().getWindow().getX() - mouseEvent.getScreenX();
            EffectUtil.y = n.getScene().getWindow().getY() - mouseEvent.getScreenY();
        });

        n.setOnMouseDragged((MouseEvent mouseEvent) -> {
            primaryStage.setX(mouseEvent.getScreenX() + EffectUtil.x);
            primaryStage.setY(EffectUtil.y + mouseEvent.getScreenY());
        });
    }

    public static void addDragListeners(final Node n) {
        n.setOnMousePressed((MouseEvent me) -> {
            if (me.getButton() != MouseButton.MIDDLE) {
                initialX = me.getSceneX();
                initialY = me.getSceneY();
            } else {
                n.getScene().getWindow().centerOnScreen();
                initialX = n.getScene().getWindow().getX();
                initialY = n.getScene().getWindow().getY();
            }
        });

        n.setOnMouseDragged((MouseEvent me) -> {
            if (me.getButton() != MouseButton.MIDDLE) {
                n.getScene().getWindow().setX(me.getScreenX() - initialX);
                n.getScene().getWindow().setY(me.getScreenY() - initialY);
            }
        });
    }

    public static SequentialTransition fadeTransition(final ImageView iv, final Image img) {
        FadeTransition fadeOutTransition
                = new FadeTransition(Duration.seconds(0.4), iv);
        fadeOutTransition.setFromValue(1.0);
        fadeOutTransition.setToValue(0.3);
        fadeOutTransition.setOnFinished((ActionEvent arg0) -> {
            iv.setImage(img);
        });
        
        FadeTransition fadeInTransition
                = new FadeTransition(Duration.seconds(0.2), iv);
        fadeInTransition.setFromValue(0.3);
        fadeInTransition.setToValue(1.0);
        SequentialTransition sequentialTransition
                = SequentialTransitionBuilder
                .create()
                .children(fadeOutTransition, fadeInTransition)
                .build();

        return sequentialTransition;
    }
    
    public static SequentialTransition translateTransition(final ImageView iv, final Image img, int fromX, int toY) {
        TranslateTransition translateOutTransition
                = new TranslateTransition(Duration.seconds(0.4), iv);
        translateOutTransition.setFromX(fromX);
        translateOutTransition.setToX(toY);
        translateOutTransition.setOnFinished((ActionEvent arg0) -> {
            iv.setImage(img);
        });
        
        TranslateTransition translateInTransition
                = new TranslateTransition(Duration.seconds(0.4), iv);
        translateInTransition.setFromX(toY);
        translateInTransition.setToX(fromX);
        SequentialTransition sequentialTransition
                = SequentialTransitionBuilder
                .create()
                .children(translateOutTransition, translateInTransition)
                .build();

        return sequentialTransition;
    }

    /**
     * records relative x and y co-ordinates.
     */
    private static class Delta {

        double x, y;
    }
}
