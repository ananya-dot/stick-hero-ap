package org.example;


import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;


import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class GamePlayController {
    @FXML
    private AnchorPane gamePlayRoot;
    @FXML
    private ImageView pauseButton;
    @FXML
    private ImageView harry;
    private Timeline fallCheckTimeline;
    @FXML
    private Line stick;
    private double lengthOfStick;
    private double y;
    private boolean isMousePressed;
    private Timeline fallTimeline;
    private Timeline fallCharacterTimeline;
    @FXML
    private Rectangle pillar2;
    @FXML
    private Rectangle pillar1;

    private boolean spaceBarPressed = false;


    @FXML
    private Label scoreText;

    private int score;

    @FXML
    private Label snitchScoreText;

    private int snitchScore;

    private boolean isGrowing;
    private ObservableList<Rectangle> Pillars;

    private Timeline moveCharacterTimeline;
    private Timeline growTimeline;
    private Timeline movePillarsTimeline;
    @FXML
    private Button growButton;
    private boolean longEnough;

    private boolean gameStatus;
    private AnimationTimer gameLoop;
    private long startTime=0;
    boolean actionsCompleted;
    private boolean snitchCollected;

    boolean characterHasFallen;
    private int currentPillarIndex = 1; // Tracks the current pillar index
    private Rectangle originalPillar1;
    private Rectangle originalPillar2;

    private Scene scene;

    private double harryXCoordinate;
    private double harryYCoordinate;

    private double deltaHarry ;

    @FXML
    private ImageView Snitch;
    private boolean lengthEnough;
    private double stickX;


//    private Timeline[] growTimelines;

    public void switchToPauseMenu(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("PauseMenu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(){
        gameStatus = true;
        harryXCoordinate = harry.getLayoutX();
        harryYCoordinate = harry.getLayoutY();
//        stickX = pillar1.getBoundsInParent().getMinX() + pillar1.getWidth();
        stickX = harry.getBoundsInParent().getMaxX();
        growButton.setOnMousePressed(event -> growingActions());
        growButton.setOnMouseReleased(event -> stopGrowingActions());
        System.out.println("initialized");
        simulateScoreUpdates();
        startGame();

    }

    private void startGame() {
        if(gameStatus){
            resetHarry();
            growButton.setVisible(true);
            gameStatus = false;
        }
        else return;
    }

    private void resetHarry() {
        harry.setLayoutY(harryYCoordinate);
        harry.setLayoutX(harryXCoordinate);
        stick.setLayoutX(stickX);
        deltaHarry = pillar1.getBoundsInParent().getMinX() + pillar1.getWidth() - harry.getBoundsInParent().getCenterX();

    }
    private void movePillars() {
        double targetX = 0; // The target x-coordinate for the left corner of pillar2
        double moveStep = 1.0; // Number of pixels to move in each step

        movePillarsTimeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            // Move pillar1
            double pillar1X = pillar1.getLayoutX();
            double newPillar1X = Math.max(targetX, pillar1X - moveStep);
            pillar1.setVisible(false);

            // Move pillar2
            double pillar2X = pillar2.getLayoutX();
            double newPillar2X = Math.max(targetX, pillar2X - moveStep);
            pillar2.setLayoutX(newPillar2X);

            // Move harry along with pillar2
            double harryX = harry.getLayoutX();
            double newHarryX = Math.max(targetX, harryX - moveStep);
            harry.setLayoutX(newHarryX);

            // Check if pillars and harry have reached the target position
            if (newPillar1X == targetX && newPillar2X == targetX && newHarryX == targetX) {
                stopMovePillarsTimeline();
            }
        }));

        movePillarsTimeline.setCycleCount(Timeline.INDEFINITE);
        movePillarsTimeline.play();
    }


    private void stopMovePillarsTimeline() {
        movePillarsTimeline.stop();
        generateRandomPillar();

    }

    private void generateRandomPillar() {
        double randomWidth = Math.random() * (150 - 20) + 20;
        double randomDistance = Math.random() * (200 - 20) + 20;
        Rectangle newPillar = new Rectangle(randomWidth, pillar2.getHeight());

        // Position the new pillar to the right of pillar2 with the random distance
        newPillar.setLayoutX(pillar2.getBoundsInParent().getMinX() + pillar2.getWidth() + randomDistance);
        newPillar.setLayoutY(pillar2.getLayoutY());
        newPillar.setFill(Color.web("#b08161"));

        gamePlayRoot.getChildren().add(newPillar);

        // Move pillar1 to pillar2 and pillar2 to the new pillar
        movePillarsToNewPositions(newPillar);
    }

    private void movePillarsToNewPositions(Rectangle newPillar) {

        pillar1 = pillar2;
        pillar1.setVisible(true);
        pillar2 = newPillar;
    }

    private void hasCollected(double x){
        if(isStickLongEnough()){
//            System.out.println("x-"+x);
//            System.out.println("snitch X:"+Snitch.getLayoutX());
            if(Snitch.getLayoutX()<x){
//                System.out.println("hi");

                if(x - Snitch.getLayoutX() <= 5) {
//                    System.out.println("hi");
                    Snitch.setVisible(false);
                    snitchScore++;
                    snitchScoreText.setText(Integer.toString(snitchScore));
//                    System.out.println("hi");
                }
            }
        }
    }





    public void grow(MouseEvent event) {
        System.out.println("grow function called");
        isMousePressed = true;
        growingActions();

    }

    public void growingActions(){
        growButton.setVisible(true);

//        isGrowing = true;
        System.out.println("growing action function called");
        actionsCompleted = false;
        startTime = System.currentTimeMillis();
        startGrowTimeline();

    }



    @FXML
    public void stopGrowing(MouseEvent event) {
        isMousePressed = false;
        System.out.println("stop growing function called");
        stopGrowingActions();

    }

    public void stopGrowingActions(){
        stopGrowTimeline();
        System.out.println("stop growing actions called");
        isGrowing = false;
        startFallTimeline();
        growButton.setVisible(false);
//        moveHarry();

    }

    private void fallHarry() {
        double totalDistance = pillar2.getHeight();
        System.out.println(totalDistance + " total distance");
//        System.out.println(harry.getLayoutY());
//        System.out.println(gamePlayRoot.getHeight());

        fallCharacterTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            double moveStep = 5.0;

            if ((harry.getLayoutY() <= gamePlayRoot.getHeight())) {
                harry.setLayoutY(harry.getLayoutY() + moveStep);
            }
            else{
//
                System.out.println("reached");
                stopFallCharacterTimeline();
                gameStatus = false;
            }
            
            actionsCompleted = true;
        }));

        fallCharacterTimeline.setCycleCount(Timeline.INDEFINITE);
        fallCharacterTimeline.play();
    }




    private void startGrowTimeline() {

        growTimeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            stick.setEndY(stick.getEndY() - 5);
            System.out.println("stick is goriwng");


        }));
        growTimeline.setCycleCount(Timeline.INDEFINITE);


        growTimeline.play();
    }

    private void stopGrowTimeline() {

        if (growTimeline != null) {
            growTimeline.stop();

        }
    }


    private void startFallTimeline() {
        double centerX = stick.getStartX();
        double centerY = stick.getStartY();
        double radius = stick.getEndY();
//        lengthOfStick = radius;

        fallTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {


            double newX = centerX + radius;

            double rotationAngle = Math.toDegrees(Math.atan2(0.0, newX - centerX));
            stick.getTransforms().clear();
            stick.getTransforms().add(new Rotate(rotationAngle, centerX, centerY));

            stick.setEndX(newX);
            stick.setEndY(centerY);

            if (stick.getEndY() - centerY >= radius) {
                stopFallTimeline();
            }
        }));
        fallTimeline.setCycleCount(Timeline.INDEFINITE);
        fallTimeline.play();

    }

    private void stopFallTimeline() {
        System.out.println("stop fall stick");
        if (fallTimeline != null) {
            fallTimeline.stop();
            moveHarry();
//            harryMoved = true;
        }
    }

    private boolean isStickLongEnough() {
        System.out.println("length of stick" + stick.getEndX());
//        System.out.println("stick start x" + stick.getStartX());
//        System.out.println("stick start y" + stick.getStartY());
        System.out.println("stick end x -> " + stick.getEndX());
//        System.out.println("stick end y " + stick.getEndY());
        double pillar1X = pillar1.getBoundsInParent().getMinX() + pillar1.getWidth();
        double pillar2X = pillar2.getBoundsInParent().getMinX();
        System.out.println("pillar diff" + (pillar2X - pillar1X));

        System.out.println(((-1* stick.getEndX()) - stick.getStartX()) - (pillar2X - pillar1X));

        //trial

        double stickLength = ((-1* stick.getEndX()) - stick.getStartX());
        lengthOfStick = stickLength;
        double pillarDiff = (pillar2X - pillar1X);
        return stickLength >= pillarDiff && stickLength <= pillarDiff + pillar2.getWidth();



    }


    private void initiateFallAnimation() {
        double endX = stick.getStartX();
        double endY = stick.getStartY();

        double fallSpeed = 5.0; // Adjust the fall speed as needed

        fallTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            double newY = harry.getY() + fallSpeed;


            harry.setY(newY);

            if (newY >= endY) {
                stopFallTimeline();
            }
        }));
        fallTimeline.setCycleCount(Animation.INDEFINITE);


        fallTimeline.play();
    }


    @FXML
    private void moveHarry() {
        System.out.println("called move harry");
//        System.out.println(deltaHarry);
        boolean flag = (isStickLongEnough());

        moveCharacterTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {

            double moveStep = 5.0;


            if (Math.abs(lengthOfStick + deltaHarry) >= moveStep) {
                    harry.setLayoutX(harry.getLayoutX() + moveStep);
            }
            hasCollected(harry.getLayoutX());


            if(harry.getLayoutX() >= lengthOfStick + deltaHarry){
                stopMoveCharacterTimeline();
                actionsCompleted = true;
            }

        }));
        moveCharacterTimeline.setCycleCount(Timeline.INDEFINITE);
        moveCharacterTimeline.play();


    }

    private void simulateScoreUpdates() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500); // Simulating a delay in score update (1 second in this case)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                score+=1;

                // Update the score on the UI
                updateScoreLabel();
            }

        }).start();
    }
    private void updateScoreLabel() {

        javafx.application.Platform.runLater(() -> {
            scoreText.setText(Integer.toString(score));
        });
    }

    private void stopMoveCharacterTimeline() {

        if (moveCharacterTimeline != null) {
            moveCharacterTimeline.stop();
            System.out.println("is stick long enough" + isStickLongEnough());
            if(isStickLongEnough()) {
                gameStatus = true;
                System.out.println("stick is long enough");
                resetStick();
                movePillars();
                growButton.setVisible(true);

//                resetHarry();
//                growingActions();
            }
            else{
                fallHarry();
                gameStatus = false;
            }
        }
    }

    private void resetStick() {
        stick.getTransforms().clear();
        stick.setStartX(stick.getStartX());
        stick.setEndX(stick.getStartX());
        stick.setStartY(stick.getStartY());
        stick.setEndY(stick.getEndY());


    }


    private void stopFallCharacterTimeline() {
        if(fallCharacterTimeline != null) {
            fallCharacterTimeline.stop();
            endGame();
//            fallCheckTimeline.stop();
        }

    }

    private void endGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EndGameScreen.fxml"));
            Parent root = loader.load();
            Scene endGameScene = new Scene(root);

            // Get the current stage
            Stage currentStage = (Stage) harry.getScene().getWindow();

            // Set the new scene on the stage
            currentStage.setScene(endGameScene);

            // Show the stage with the end game scene
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., show an error message)
        }
    }


}