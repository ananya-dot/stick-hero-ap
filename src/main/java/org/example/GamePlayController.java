package org.example;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


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

    private double initialMouseY;
    public void switchToPauseMenu(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("PauseMenu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

//    public void grow(MouseEvent event){
//        stick.setEndY((stick.getEndY() - 20));
//    }

    public void onMousePressed(MouseEvent event) {
        // Store the initial mouse Y coordinate when the mouse is pressed
        initialMouseY = event.getY();
    }

    @FXML
    public void grow(MouseEvent event) {
        // Calculate the difference between the initial and current mouse Y coordinates
        double deltaY = event.getY() - initialMouseY;

        // Increase the length of the stick based on the mouse movement
        stick.setEndY(stick.getEndY() + deltaY);
    }



}
