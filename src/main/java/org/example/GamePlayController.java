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


        startGrowTimeline();
    }

    @FXML

    public void stopGrowing(MouseEvent event) {

        stopGrowTimeline();
        startFallTimeline();
        moveHarry();
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

    private void moveHarry() {
        moveCharacterTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            // Move the character to the end of the stick
            double characterX = stick.getEndX() - harry.getBoundsInLocal().getWidth() / 2;
            double characterY = stick.getEndY() - harry.getBoundsInLocal().getHeight() / 2;

            // Ensure the character remains within the visible area
            characterX = Math.max(0, Math.min(characterX, gamePlayRoot.getWidth() - harry.getBoundsInLocal().getWidth()));
            characterY = Math.max(0, Math.min(characterY, gamePlayRoot.getHeight() - harry.getBoundsInLocal().getHeight()));

            harry.setX(characterX);
            harry.setY(characterY);
        }));
        moveCharacterTimeline.setCycleCount(Timeline.INDEFINITE);

        // Start the timeline to move the character
        moveCharacterTimeline.play();
    }


}
