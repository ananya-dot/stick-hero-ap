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
//        Group root = new Group();
//        Scene scene = new Scene(root, 800, 550);
        Stage stage = new Stage();
//        stage.setScene(scene);
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("StickHero.fxml"));

//        FXMLLoader loader = new FXMLLoader(StickHero.class.getResource("stickhero.fxml"));
        Scene scene = new Scene(root);
//        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
//        Parent root = loader.load();
//
//        loader.setRoot(new AnchorPane());
//        Parent root = fxmlLoader.load();

//        Text text = new Text();
//        text.setText("hemlo");
//        text.setX(400);
//        text.setY(50);

//        Image image = new Image();

//        Line line = new Line();
//        line.setStartX(0);
//        line.setEndX(800);
//        line.setStartY(100);
//        line.setEndY(100);


//        root.getChildrenUnmodifiable().add(text);
//        root.getChildrenUnmodifiable().add(line);
//        stage.setScene(new Scene(root, 600, 600));
//        stage.show();



    }
}
