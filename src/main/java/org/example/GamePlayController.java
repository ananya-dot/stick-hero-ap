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
    private int currentPillarIndex = 1;
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

    public void switchToPauseMenu(MouseEvent event) throws IOException { //method to switch to pause menu
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("PauseMenu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(){
        gameStatus = true;   //setting initial game status = true
        harryXCoordinate = harry.getLayoutX();  //storing harrys initial coordinates to reset them later
        harryYCoordinate = harry.getLayoutY();
//        stickX = pillar1.getBoundsInParent().getMinX() + pillar1.getWidth();
        stickX = harry.getBoundsInParent().getMaxX();  // same for stick
        growButton.setOnMousePressed(event -> growingActions());  // setting event handlers for grow button
        growButton.setOnMouseReleased(event -> stopGrowingActions());
        System.out.println("initialized");
        simulateScoreUpdates();
        startGame();

    }

    private void startGame() {
        if(gameStatus){
            resetHarry(); // resetting harrys position
            growButton.setVisible(true);
            gameStatus = false;
        }
        else return;
    }

    private void resetHarry() {
        harry.setLayoutY(harryYCoordinate);
        harry.setLayoutX(harryXCoordinate);
//        stick.setLayoutX(stickX);
        deltaHarry =  pillar1.getWidth() - harry.getBoundsInParent().getCenterX();   //calculating delta so that harry moves the right distance

    }
    private void movePillars() {  //method to move pillar 2 and 1 backwards
        double targetX = 0;
        double moveStep = 1.0;

        movePillarsTimeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            double pillar1X = pillar1.getLayoutX();
            double newPillar1X = Math.max(targetX, pillar1X - moveStep);
            pillar1.setVisible(false);

            double pillar2X = pillar2.getLayoutX();
            double newPillar2X = Math.max(targetX, pillar2X - moveStep);
            pillar2.setLayoutX(newPillar2X);

            double harryX = harry.getLayoutX();
            double newHarryX = Math.max(targetX, harryX - moveStep);
            harry.setLayoutX(newHarryX);

            if (newPillar1X == targetX && newPillar2X == targetX && newHarryX == targetX) {
                Snitch.setVisible(true);
                stopMovePillarsTimeline();
            }
        }));

        movePillarsTimeline.setCycleCount(Timeline.INDEFINITE);
        movePillarsTimeline.play();
    }


    private void stopMovePillarsTimeline() {
        movePillarsTimeline.stop();
        generateRandomPillar();
        resetStick();
//        resetHarry();

//        deltaHarry = pillar1.getBoundsInParent().getMinX() + pillar1.getWidth() - harry.getLayoutX();

    }

    private void generateRandomPillar() {   //generate new pilar
        double randomWidth = Math.random() * (150 - 20) + 20;
        double randomDistance = Math.random() * (200 - 20) + 20;
        Rectangle newPillar = new Rectangle(randomWidth, pillar2.getHeight());

        newPillar.setLayoutX(pillar2.getBoundsInParent().getMinX() + pillar2.getWidth() + randomDistance);
        newPillar.setLayoutY(pillar2.getLayoutY());
        newPillar.setFill(Color.web("#b08161"));

        gamePlayRoot.getChildren().add(newPillar);

        movePillarsToNewPositions(newPillar);  //swap pillars
    }

    private void movePillarsToNewPositions(Rectangle newPillar) {

        pillar1 = pillar2;
        pillar2 = newPillar;
//        System.out.println(pillar2.getLayoutX());
    }

    private void hasCollected(double x){   //used for collection of snitch
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

    public void growingActions(){  //all actions to execute when the mouse is  pressed
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

    private void fallHarry() {  //making character fall to end of screen
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




    private void startGrowTimeline() { // to grow stick

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private void startFallTimeline() {  // to make the stick rotate by 90 degres
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

    private boolean isStickLongEnough() {  //checking if the stick is long enough
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



    @FXML
    private void moveHarry() { //move harry to the end of stick
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
                    Thread.sleep(500);
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

            Stage currentStage = (Stage) harry.getScene().getWindow();

            currentStage.setScene(endGameScene);

            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}