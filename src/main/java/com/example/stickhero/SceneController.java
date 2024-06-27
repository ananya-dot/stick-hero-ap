package com.example.stickhero;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * The controller class for handling scene changes, animations, and music settings.
 */
public class SceneController {
    private static Stage stage; // The primary stage
    private Scene scene; // The current scene
    private AnchorPane root; // The root layout
    private FXMLLoader loader; // The FXMLLoader for loading FXML files
    private Timeline TrainTimeline; // The timeline for the train animation
    public boolean music = true; // Flag for music status

    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }

    private Button exitButton; // The exit button

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setRoot(AnchorPane root) {
        this.root = root;
    }

    public void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public Parent getRoot() {
        return root;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    private PauseMenuController pauseMenuController; // The pause menu controller
    private String avatar; // The character avatar

    public String getCharacter() {
        return avatar;
    }

    public void setCharacter(String character) {
        this.avatar = character;
    }

    public static MediaPlayer mediaPlayer; // The media player for background music

    @FXML
    private ImageView FlyingHarry; // The ImageView for the flying animation

    @FXML
    private ImageView Train1; // The first train ImageView

    @FXML
    private ImageView Train2; // The second train ImageView

    @FXML
    private ImageView SettingButton; // The settings button
    @FXML
    private ImageView SettingButton2; // The alternate settings button

    @FXML
    private ImageView MusicON; // The music on button
    @FXML
    private ImageView MusicOFF; // The music off button

    @FXML
    private Slider volumeSlider; // The volume slider

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static void setMediaPlayer(MediaPlayer mediaPlayer) {
        SceneController.mediaPlayer = mediaPlayer;
    }

    @FXML
    private ImageView SettingBar; // The settings bar

    public ImageView getSettingButton2() {
        return SettingButton2;
    }

    public void setSettingButton2(ImageView settingButton2) {
        SettingButton2 = settingButton2;
    }

    public Slider getVolumeSlider() {
        return volumeSlider;
    }

    public void setVolumeSlider(Slider volumeSlider) {
        this.volumeSlider = volumeSlider;
    }

    public ImageView getSettingBar() {
        return SettingBar;
    }

    public void setSettingBar(ImageView settingBar) {
        SettingBar = settingBar;
    }

    public ImageView getSettingButton() {
        return SettingButton;
    }

    public void setSettingButton(ImageView settingButton) {
        SettingButton = settingButton;
    }

    public ImageView getMusicON() {
        return MusicON;
    }

    public void setMusicON(ImageView musicON) {
        MusicON = musicON;
    }

    public ImageView getMusicOFF() {
        return MusicOFF;
    }

    public void setMusicOFF(ImageView musicOFF) {
        MusicOFF = musicOFF;
    }

    /**
     * Switches the current scene to the specified FXML file.
     *
     * @param fxmlFileName the name of the FXML file to switch to
     */
    public void switchScene(String fxmlFileName) {
        try {
            if (fxmlFileName.equals("/ChooseCharacters.fxml")) {
                FXMLLoader loader = new FXMLLoader(CharacterController.class.getResource(fxmlFileName));
                root = loader.load();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                CharacterController controller = loader.getController();
                controller.setRoot(root);
                controller.setStage(stage);
                controller.setScene(scene);
            } else {
                root = FXMLLoader.load(SceneController.class.getResource(fxmlFileName));
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches to the "How to Play One" scene.
     *
     * @param event the mouse event
     */
    public void switchToHowToPlayOne(MouseEvent event) {
        try {
            root = FXMLLoader.load(SceneController.class.getResource("/HowToPlayOne.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            ImageView train = (ImageView) scene.lookup("#Train1");
            trainAnimation(train);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches to the "How to Play Two" scene.
     *
     * @param event the mouse event
     */
    public void switchToHowToPlayTwo(MouseEvent event) {
        try {
            root = FXMLLoader.load(SceneController.class.getResource("/HowToPlayTwo.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            ImageView train = (ImageView) scene.lookup("#Train2");
            trainAnimation(train);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches to the "Choose Character" scene.
     *
     * @param event the mouse event
     */
    public void switchToChooseCharacter(MouseEvent event) {
        System.out.println(getStage() != null);
        switchScene("/ChooseCharacters.fxml");
    }

    /**
     * Switches the scene to the gameplay scene with the specified character.
     *
     * @param FxmlFile the FXML file name for the gameplay scene
     * @param Character the character name
     * @throws IOException if loading the FXML file fails
     */
    public void switchScenetoGameplay(String FxmlFile, String Character) throws IOException {
        System.out.println(Character);
        if (FxmlFile.equals("/GamePlay.fxml")) {
            System.out.println(Character);
            FXMLLoader loader = new FXMLLoader(GamePlayController.class.getResource(FxmlFile));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            GamePlayController controller = loader.getController();
            controller.setRoot(root);
            controller.setStage(stage);
            controller.setScene(scene);
            controller.setCharacterName(Character);
            controller.CharacterSetting();
            controller.setGameResumed(false);
        }
    }

    /**
     * Starts the flying animation for the Harry character.
     */
    public void flyingAnimation() {
        TranslateTransition animation = new TranslateTransition();
        animation.setDuration(Duration.millis(1000)); // Adjust duration as needed
        animation.setAutoReverse(true);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setByY(10); // Adjust Y offset for floating animation
        animation.setNode(FlyingHarry);
        animation.play();
        FlyingHarry.setUserData(animation);
    }

    /**
     * Starts the train animation for the specified train ImageView.
     *
     * @param Train the train ImageView
     */
    public void trainAnimation(ImageView Train) {
        TrainTimeline = new Timeline(
                new KeyFrame(Duration.millis(10), e -> {
                    double end = Train.getLayoutX() + 2;
                    Train.setLayoutX(end);
                    if (Train.getLayoutX() >= 600) {
                        TrainTimeline.stop();
                    }
                })
        );
        TrainTimeline.setCycleCount(Timeline.INDEFINITE);
        TrainTimeline.play();
    }

    /**
     * Adds background music to the scene.
     *
     * @throws URISyntaxException if the URI syntax is incorrect
     */
    public void addMusic() throws URISyntaxException {
        Media sound = new Media((Objects.requireNonNull(getClass().getClassLoader().getResource("HarryPotterPrologue.mp3"))).toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setStartTime(Duration.seconds(1));
        mediaPlayer.setStopTime(Duration.seconds(50));
        mediaPlayer.play();
    }

    /**
     * Turns off the background music.
     *
     * @param event the mouse event
     */
    public void turnOFFmusic(MouseEvent event) {
        if (MusicON.isVisible()) {
            MusicON.setVisible(false);
            MusicOFF.setVisible(true);
            mediaPlayer.stop();
            music = false;
        }
    }

    /**
     * Turns on the background music.
     *
     * @param event the mouse event
     */
    public void turnONmusic(MouseEvent event) {
        if (MusicOFF.isVisible()) {
            MusicOFF.setVisible(false);
            MusicON.setVisible(true);
            mediaPlayer.play();
            music = true;
        }
    }

    /**
     * Shows the settings menu.
     *
     * @param event the mouse event
     */
    public void turnONsetting(MouseEvent event) {
        if (!SettingButton2.isVisible() && !volumeSlider.isVisible() && !SettingBar.isVisible()) {
            SettingButton.setVisible(false);
            SettingButton2.setVisible(true);
            volumeSlider.setVisible(true);
            SettingBar.setVisible(true);
            volumeSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(volumeSlider.getValue() / 100);
                }
            });
        }
    }

    /**
     * Hides the settings menu.
     *
     * @param event the mouse event
     */
    public void turnOFFsetting(MouseEvent event) {
        if (!SettingButton.isVisible()) {
            SettingButton.setVisible(true);
            SettingButton2.setVisible(false);
            volumeSlider.setVisible(false);
            SettingBar.setVisible(false);
        }
    }

    /**
     * Sets the FXMLLoader.
     *
     * @param loader the FXMLLoader to set
     */
    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }
}
