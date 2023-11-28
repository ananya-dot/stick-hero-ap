package org.example;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.Duration;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GamePlayController {
    @FXML
    private AnchorPane gamePlayRoot;
    @FXML
    private ImageView pauseButton;
    @FXML
    private ImageView harry;
//    private Stick stick;
    @FXML
    private Line stick;
    private double y;
    private boolean isMousePressed;
    private Timeline fallTimeline;

    private Timeline growTimeline;
    private long startTime;
    public void switchToPauseMenu(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("PauseMenu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void grow(MouseEvent event) {
        startTime = System.currentTimeMillis();


        startGrowTimeline();
    }

    @FXML

    public void stopGrowing(MouseEvent event) {

        stopGrowTimeline();
        startFallTimeline();
        System.out.println("stopped");
    }



    private void startGrowTimeline() {
        growTimeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            stick.setEndY(stick.getEndY() - 5);
        }));
        growTimeline.setCycleCount(Timeline.INDEFINITE);

        growTimeline.setOnFinished(e -> {
            if (isMousePressed()) {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - startTime;
                int cycles = (int) (elapsedTime / 50);
                growTimeline.setCycleCount(cycles);
                growTimeline.playFromStart();
            }
        });


        growTimeline.play();
    }

    private void stopGrowTimeline() {

        if (growTimeline != null) {
            growTimeline.stop();
        }
    }

    private boolean isMousePressed() {

        return growTimeline != null && growTimeline.getStatus() == Timeline.Status.RUNNING;
    }


    private void startFallTimeline() {
        double centerX = stick.getStartX();
        double centerY = stick.getStartY();
        double radius = stick.getEndY(); // Use the length of the stick as the radius

        fallTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {

            double angle = 90.0;
            double newX = centerX + radius * Math.cos(angle);
            double newY = centerY + radius * Math.sin(angle);

            double rotationAngle = Math.toDegrees(Math.atan2(newY - centerY, newX - centerX));
            stick.getTransforms().clear();
            stick.getTransforms().add(new Rotate(rotationAngle, centerX, centerY));

            stick.setEndX(newX);
            stick.setEndY(newY);

            if (stick.getEndY() - centerY >= radius) {
                stopFallTimeline();
            }
        }));
        fallTimeline.setCycleCount(Timeline.INDEFINITE);

        fallTimeline.play();
    }

    private void stopFallTimeline() {
        if (fallTimeline != null) {
            fallTimeline.stop();
        }
    }


}
