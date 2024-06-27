package com.example.stickhero;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * The PauseMenuController class manages the pause menu and transitions back to the game.
 */
public class PauseMenuController {

    // Properties for managing the scene and stage
    private SceneController sceneController = new SceneController();
    private AnchorPane root;
    private Scene scene;
    private Stage stage;

    // Game state properties
    private String avatar;
    private Characters currentCharacter;
    private Pillars pillars;
    private Line stick;
    private int time;
    private int score;
    private int snitch_score;

    @FXML
    private ImageView pauseMenu;
    private ImageView snitch;

    // Getters and Setters
    public AnchorPane getRoot() {
        return root;
    }

    public void setRoot(AnchorPane root) {
        this.root = root;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Characters getCurrentCharacter() {
        return currentCharacter;
    }

    public void setCurrentCharacter(Characters currentCharacter) {
        this.currentCharacter = currentCharacter;
    }

    public Pillars getPillars() {
        return pillars;
    }

    public void setPillars(Pillars pillars) {
        this.pillars = pillars;
    }

    public Line getStick() {
        return stick;
    }

    public void setStick(Line stick) {
        this.stick = stick;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSnitch_score() {
        return snitch_score;
    }

    public void setSnitch_score(int snitch_score) {
        this.snitch_score = snitch_score;
    }

    public ImageView getSnitch() {
        return snitch;
    }

    public void setSnitch(ImageView snitch) {
        this.snitch = snitch;
    }

    /**
     * Starts a floating animation for the given ImageView.
     *
     * @param imageView The ImageView to animate.
     */
    public void startMenuAnimation(ImageView imageView) {
        TranslateTransition animation = new TranslateTransition();
        animation.setDuration(Duration.millis(1000)); // Duration of the animation
        animation.setAutoReverse(true); // Animation reverses after completing a cycle
        animation.setCycleCount(Animation.INDEFINITE); // Loop indefinitely
        animation.setByY(10); // Vertical movement offset
        animation.setNode(imageView);
        animation.play();
        imageView.setUserData(animation);
    }

    /**
     * Logs the current state of the game for debugging purposes.
     */
    public void checkingState() {
        System.out.println(currentCharacter.getName());
        System.out.println("Current pillar - " + pillars.getCurrentPillar().getFitWidth());
        System.out.println("Next pillar - " + pillars.getNextPillar().getFitWidth());
        System.out.println(stick.getStartX());
        System.out.println(time);
        System.out.println(score);
        System.out.println(snitch_score);
    }

    /**
     * Switches to the game scene and resumes the game.
     *
     * @param event The MouseEvent triggering the switch.
     * @throws IOException if the FXML file cannot be loaded.
     */
    public void switchToGame(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(GamePlayController.class.getResource("/GamePlay.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        GamePlayController controller = loader.getController();
        controller.setRoot(root);
        controller.setStage(stage);
        controller.setScene(scene);
        controller.setCharacterName(currentCharacter.getName());

        if (!controller.isGameResumed()) {
            controller.setGameResumed(true);
        }

        controller.setTime(time);
        controller.setScore(score);
        controller.setSnitchScore(snitch_score);
        controller.getScoreText().setText(Integer.toString(score));
        controller.getSnitchScoreText().setText(Integer.toString(snitch_score));
        controller.setCurrentPillar(pillars.getCurrentPillar());
        controller.setNextPillar(pillars.getNextPillar());
        controller.getStick().setStartX(stick.getStartX());
        controller.getStick().setEndX(stick.getEndX());
        controller.CharacterSetting();
        controller.getCurrentCharacter().getChar1().setLayoutX(currentCharacter.getChar1().getLayoutX());
        controller.getCurrentCharacter().getChar2().setLayoutX(currentCharacter.getChar2().getLayoutX());
        controller.getCurrentCharacter().getCharNstick1().setLayoutX(currentCharacter.getCharNstick1().getLayoutX());
        controller.getCurrentCharacter().getCharNstick2().setLayoutX(currentCharacter.getCharNstick2().getLayoutX());

        controller.getSnitch().setLayoutX(snitch.getLayoutX());
        controller.getSnitch().setLayoutY(snitch.getLayoutY());
    }

    /**
     * Switches to the character selection scene.
     *
     * @param event The MouseEvent triggering the switch.
     * @throws IOException if the FXML file cannot be loaded.
     */
    public void switchToGamePlay(MouseEvent event) throws IOException {
        sceneController.switchScene("/ChooseCharacters.fxml");
    }

    /**
     * Closes the application.
     *
     * @param event The MouseEvent triggering the close.
     */
    public void exit(MouseEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
