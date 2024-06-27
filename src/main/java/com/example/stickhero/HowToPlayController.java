package com.example.stickhero;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * The HowToPlayController class manages the "How to Play" scenes and animations.
 */
public class HowToPlayController {

    // FXML elements for train images
    @FXML
    private ImageView Train1;

    @FXML
    private ImageView Train2;

    // SceneController instance to handle scene switching
    private SceneController sceneController = new SceneController();

    // Getters and Setters for Train1
    public ImageView getTrain1() {
        return Train1;
    }

    public void setTrain1(ImageView train1) {
        Train1 = train1;
    }

    // Getters and Setters for Train2
    public ImageView getTrain2() {
        return Train2;
    }

    public void setTrain2(ImageView train2) {
        Train2 = train2;
    }

    /**
     * Switches to the first "How to Play" scene and starts the train animation.
     *
     * @param event The MouseEvent triggering the switch.
     */
    public void switchToHowToPlayOne(MouseEvent event) {
        sceneController.switchScene("/HowToPlayOne.fxml");
        sceneController.trainAnimation(Train1);
    }

    /**
     * Switches to the second "How to Play" scene and starts the train animation.
     *
     * @param event The MouseEvent triggering the switch.
     */
    public void switchToHowToPlayTwo(MouseEvent event) {
        sceneController.switchScene("/HowToPlayTwo.fxml");
        sceneController.trainAnimation(Train2);
    }
}
