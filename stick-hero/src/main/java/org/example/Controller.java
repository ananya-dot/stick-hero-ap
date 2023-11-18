package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

public class Controller {
    @FXML
    private Circle circle;
    private double x;
    private double y;

    public void play(ActionEvent e){
//        System.out.println("Play");
        circle.setCenterY(y -= 1);
    }
}
