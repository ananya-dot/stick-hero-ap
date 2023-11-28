package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class WalkController implements Initializable {

    @FXML
    private ImageView harryWalkOne;

    @FXML
    private ImageView harryWalkTwo;

    private boolean isLeftLegForward = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Set initial visibility
        harryWalkOne.setVisible(isLeftLegForward);
        harryWalkTwo.setVisible(!isLeftLegForward);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(8);

        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(150),
                event -> {

                    harryWalkOne.setVisible(!isLeftLegForward);
                    harryWalkTwo.setVisible(isLeftLegForward);

                    // Adjust X-coordinate for the forward movement
                    harryWalkOne.setTranslateX(isLeftLegForward ? harryWalkOne.getTranslateX() + 20 : harryWalkOne.getTranslateX());
                    harryWalkTwo.setTranslateX(isLeftLegForward ? harryWalkTwo.getTranslateX() + 20 : harryWalkTwo.getTranslateX());

                    isLeftLegForward = !isLeftLegForward;
                }
        );

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }
}
