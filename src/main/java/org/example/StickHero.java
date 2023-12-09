package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URISyntaxException;
import java.util.Objects;



public class StickHero extends Application {

    public static MediaPlayer mediaPlayer;
    public ImageView character;
    @Override
    public void start(Stage primaryStage) throws Exception {

//        addMusic();
        Parent startPage = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("StartScreen.fxml")));
        Scene scene = new Scene(startPage);
        primaryStage.setTitle("Stick Hero");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void addMusic() throws URISyntaxException {
        Media sound = new Media((getClass().getClassLoader().getResource("HarryPotterPrologue.mp3")).toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setStartTime(Duration.seconds(1));
        mediaPlayer.setStopTime(Duration.seconds(50));
        mediaPlayer.play();
    }

//    public static ImageView chooseCharacter() {
//        return
//    }



    public ImageView getCharacter() {
        return character;
    }



    public static void main(String[] args) {
        launch(args);
    }
}
