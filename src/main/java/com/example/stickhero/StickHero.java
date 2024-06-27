package com.example.stickhero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class for the StickHero application which extends the JavaFX Application class.
 * It is responsible for initializing and launching the game window.
 */
public class StickHero extends Application {

    /**
     * The main entry point for all JavaFX applications. The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * @param primaryStage the primary stage for this application, onto which the application scene can be set.
     * @throws IOException if loading the FXML file fails.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            // Load the StartGame.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartGame.fxml"));
            AnchorPane root = loader.load();

            // Get the controller associated with the FXML file
            SceneController controller = loader.getController();

            // Check if root is not null and set it in the controller
            if (root != null) {
                controller.setRoot(root);
            } else {
                System.err.println("0. Root is null. Initialization failed.");
            }

            // Create a new scene with the loaded root layout
            Scene scene = new Scene(root);

            // Set various properties in the controller
            controller.setLoader(loader);
            controller.setScene(scene);
            controller.setStage(primaryStage);

            // Set the minimum and maximum width and height for the primary stage
            primaryStage.setMinWidth(600); // Set your desired width
            primaryStage.setMaxWidth(600);
            primaryStage.setMinHeight(400); // Set your desired height
            primaryStage.setMaxHeight(400);

            // Set the scene for the primary stage and display it
            primaryStage.setScene(scene);
            primaryStage.show();

            // Call methods in the controller to initialize animations and music settings
            controller.flyingAnimation();
            controller.addMusic();
            controller.setMusic(true);
            controller.getMusicOFF().setVisible(false);
            controller.getSettingButton2().setVisible(false);
            controller.getSettingBar().setVisible(false);
            controller.getVolumeSlider().setVisible(false);
            controller.getVolumeSlider().setValue(controller.getMediaPlayer().getVolume() * 100);

        } catch (Exception e) {
            // Print stack trace if any exception occurs
            e.printStackTrace();
        }
    }

    /**
     * The main method for launching the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
