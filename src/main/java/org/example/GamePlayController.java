package org.example;


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
import javafx.util.Duration;


import java.io.IOException;
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
    private double lengthOfStick;
    private double y;
    private boolean isMousePressed;
    private Timeline fallTimeline;

    private Timeline moveCharacterTimeline;
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
        startGrowTimeline(startTime);
    }

    @FXML

    public void stopGrowing(MouseEvent event) {

        stopGrowTimeline();
        startFallTimeline();
        moveHarry();
//        startMoveCharacterTimeline();
//        System.out.println("stopped");
    }



    private void startGrowTimeline(long startTime) {
        System.out.println("start x of stick - " + stick.getStartX());
        System.out.println("start y of stick - " + stick.getStartY());
        System.out.println("end x of stick - " + stick.getEndX());
        System.out.println("end y of stick - " + stick.getEndY());
        growTimeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            stick.setEndY(stick.getEndY() - 5);
//            lengthOfStick = stick.getEndY() - stick.getStartY();
        }));
        growTimeline.setCycleCount(Timeline.INDEFINITE);

        growTimeline.setOnFinished(e -> {
            if (isMousePressed()) {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - this.startTime;
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
        lengthOfStick = radius;

        fallTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {


            double newX = centerX + radius;

            double rotationAngle = Math.toDegrees(Math.atan2(0.0, newX - centerX));
            stick.getTransforms().clear();
            stick.getTransforms().add(new Rotate(rotationAngle, centerX, centerY));

            stick.setEndX(newX);
            stick.setEndY(centerY);

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


    @FXML
    private void moveHarry() {
        double endX = -1 * stick.getEndY();
        System.out.println("end x stick - " + stick.getEndX());
        System.out.println("end y stick - " + stick.getEndY());


        // Calculate the total distance to move the character
        double totalDistance = -1 * stick.getEndY() - harry.getX();

        moveCharacterTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            // Calculate the distance to move in each frame
            double moveStep = 5.0;

            // Check if there is still distance to cover
            if (Math.abs(totalDistance) >= moveStep) {
                // Update the position of the character based on the moveStep
                if (totalDistance > 0) {
                    harry.setX(harry.getX() + moveStep);
                } else {
                    harry.setX(harry.getX() - moveStep);
                }
            }

            if(harry.getX() >= totalDistance) stopMoveCharacterTimeline();
        }));
        moveCharacterTimeline.setCycleCount(Timeline.INDEFINITE);


        moveCharacterTimeline.play();
    }

    private void stopMoveCharacterTimeline() {
        // Stop the move character timeline
        if (moveCharacterTimeline != null) {
            moveCharacterTimeline.stop();
        }
    }





}
