package com.example.stickhero;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Controller class for managing character selection and animations.
 */
public class CharacterController {

    // FXML fields for character images
    @FXML
    private ImageView Harry;

    @FXML
    private ImageView Ron;

    @FXML
    private ImageView Hermione;

    // Fields for managing stage, scene, and root layout
    private AnchorPane root;
    private Stage stage;
    private Scene scene;

    // Flags for tracking which character is currently selected
    private boolean SpawningHarry = false;
    private boolean SpawningRon = false;
    private boolean SpawningHermione = false;

    // Field for tracking the last selected character
    private ImageView lastSelectedCharacter;

    // Setters for root, stage, and scene
    public void setRoot(AnchorPane root) {
        this.root = root;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    // Method to handle Harry selection and animation
    @FXML
    public void spawnHarry(MouseEvent event) {
        setCharacterSelection(Harry);
        SpawningRon = false;
        SpawningHermione = false;
        SpawningHarry = true;
        startCharacterAnimation(Harry);
        System.out.println("Harry here");
        System.out.println(SpawningHarry);
    }

    // Method to handle Ron selection and animation
    @FXML
    public void spawnRon(MouseEvent event) {
        setCharacterSelection(Ron);
        SpawningRon = true;
        SpawningHermione = false;
        SpawningHarry = false;
        startCharacterAnimation(Ron);
        System.out.println("Ron here");
        System.out.println(SpawningRon);
    }

    // Method to handle Hermione selection and animation
    @FXML
    public void spawnHermione(MouseEvent event) {
        setCharacterSelection(Hermione);
        SpawningRon = false;
        SpawningHermione = true;
        SpawningHarry = false;
        startCharacterAnimation(Hermione);
        System.out.println("Hermione here");
        System.out.println(SpawningHermione);
    }

    // Method to set character selection and stop previous animation
    private void setCharacterSelection(ImageView imageView) {
        if (lastSelectedCharacter != null) {
            stopCharacterAnimation(lastSelectedCharacter); // Stop animation of previous selection
        }
        lastSelectedCharacter = imageView; // Update last selected character
    }

    // Method to start animation for the selected character
    private void startCharacterAnimation(ImageView imageView) {
        TranslateTransition animation = new TranslateTransition();
        animation.setDuration(Duration.millis(1000)); // Adjust duration as needed
        animation.setAutoReverse(true);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setByY(10); // Adjust Y offset for floating animation
        animation.setNode(imageView);
        animation.play();
        imageView.setUserData(animation);
    }

    // Method to stop animation for the previously selected character
    private void stopCharacterAnimation(ImageView imageView) {
        Animation animation = (Animation) imageView.getUserData();
        if (animation != null && animation.getStatus() == Animation.Status.RUNNING) {
            animation.stop();
        }
    }

    // Method to switch to the gameplay scene with the selected character
    public void switchToGamePlay(MouseEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        if (SpawningHarry) {
            sceneController.switchScenetoGameplay("/GamePlay.fxml", "Harry");
        } else if (SpawningRon) {
            sceneController.switchScenetoGameplay("/GamePlay.fxml", "Ron");
        } else {
            sceneController.switchScenetoGameplay("/GamePlay.fxml", "Hermione");
        }
    }
}
