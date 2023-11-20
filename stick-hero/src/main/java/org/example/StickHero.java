package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class StickHero extends Application {
    @Override
    public void start(Stage Pstage) throws Exception {

        Stage stage = new Stage();
//        stage.setScene(scene);
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("StartScreen.fxml"));

//        FXMLLoader loader = new FXMLLoader(StickHero.class.getResource("stickhero.fxml"));
        Scene scene = new Scene(root);
//        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }
}
