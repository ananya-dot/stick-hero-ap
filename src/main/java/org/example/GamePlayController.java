package org.example;


import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;


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
//    private Stick stick;
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
    private boolean isGrowing;
    private CompletableFuture<Void> fallFuture;

    private Timeline moveCharacterTimeline;
    private Timeline growTimeline;
    @FXML
    private Button growButton;
    private boolean longEnough;

    private boolean gameStatus;
    private AnimationTimer gameLoop;
    private long startTime=0;
    boolean actionsCompleted;

    boolean characterHasFallen;
    public void switchToPauseMenu(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("PauseMenu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(){
        gameStatus = true;
//        growButton.setOnMousePressed(event -> growingActions());
//        growButton.setOnMouseReleased(event -> stopGrowingActions());
        startGameLoop();



    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!actionsCompleted) {
                    return;
                }

                CompletableFuture.runAsync(() -> {
                    Platform.runLater(() -> {
                        resetStickAndHarry();
                        growButton.setVisible(true);
                        movePillars();
                    });
                }).whenComplete((result, throwable) -> {
                    actionsCompleted = false;
                });

                if (!gameStatus) {
                    gameLoop.stop();
                }
            }
        };

        // Set OnMousePressed and OnMouseReleased handlers for growButton
        growButton.setOnMousePressed(event -> growingActions());
        growButton.setOnMouseReleased(event -> stopGrowingActions());

        gameLoop.start();
    }

    private void movePillars() {
        double rightEdgeOfPillar1 = pillar1.getLayoutX() + pillar1.getWidth();
        double leftEdgeOfPillar2 = pillar2.getLayoutX();
        double diff = leftEdgeOfPillar2;
        double durationInMillis = 1000; // Adjust the duration as needed
        double pixelsToMove = diff; // Adjust the number of pixels to move

        Timeline movePillarsTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(pillar1.layoutXProperty(), pillar1.getLayoutX())),
                new KeyFrame(Duration.millis(durationInMillis),
                        new KeyValue(pillar1.layoutXProperty(), pillar1.getLayoutX() - pixelsToMove)),
                new KeyFrame(Duration.ZERO, new KeyValue(harry.layoutXProperty(), harry.getLayoutX())),
                new KeyFrame(Duration.millis(durationInMillis),
                        new KeyValue(harry.layoutXProperty(), harry.getLayoutX() - pixelsToMove)),
                new KeyFrame(Duration.ZERO, new KeyValue(pillar2.layoutXProperty(), pillar2.getLayoutX())),
                new KeyFrame(Duration.millis(durationInMillis),
                        new KeyValue(pillar2.layoutXProperty(), pillar2.getLayoutX() - pixelsToMove))

        );

        movePillarsTimeline.play();
    }

    private void updateGameState() {
        // Perform game-related logic here

        // For example, check if the character has fallen
        if (!characterHasFallen) {
            gameStatus = true;
            System.out.println("game going on ");
            // Perform actions when the character falls, e.g., show game over screen
//            showGameOverScreen();
        }

        resetStickAndHarry();
        stick.setVisible(true);
        growButton.setVisible(true);
        // Other game logic...

        // Set actionsCompleted to true to indicate that this frame's actions are completed
        actionsCompleted = true;
    }




    private void resetStickAndHarry() {
         stick.setEndX(0);
         stick.setEndY(0);
         stick.setStartX(0);
         stick.setStartY(0);
//         harry.setX(0);
//         harry.setY(0);
         stick.getTransforms().clear();
         harry.getTransforms().clear();


    }

//    private void moveHarryToNextPillar() {
//        // Example logic to move harry to the next pillar
//        double nextPillarX = pillar2.getBoundsInParent().getCenterX() + pillar2.getWidth() / 2 + harry.getFitWidth() / 2;
//        double nextPillarY = pillar2.getBoundsInParent().getMaxY() - harry.getFitHeight();
//
//        harry.setX(nextPillarX);
//        harry.setY(nextPillarY);
//    }
    private void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }




        public void grow(MouseEvent event) {
            System.out.println("grow function called");
        isMousePressed = true;
        growingActions();

    }

    public void growingActions(){

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
        System.out.println("stop growing actions called");
        isGrowing = false;
        stopGrowTimeline();
        startFallTimeline();
        growButton.setVisible(false);
        moveHarry();

    }

    private void fallHarry() {


         double totalDistance = pillar2.getHeight();
//

         fallCharacterTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {

            double moveStep = 5.0;


            if ((totalDistance) >= moveStep) {
                harry.setY(harry.getY() + moveStep);

            }

            if(harry.getY() == totalDistance){
//                System.out.println(harry.getY());
                System.out.println("reached");
                stopFallCheckTimeline();
                stopFallCharacterTimeline();
                actionsCompleted = true;
                gameStatus = false;
                characterHasFallen=true;

            }



//            else{
////                System.out.println("hemlo2");
//                stopFallCheckTimeline();
//                stopFallCharacterTimeline();
//            }


        }));
        fallCharacterTimeline.setCycleCount(Timeline.INDEFINITE);
        fallCharacterTimeline.play();

    }






    private void startGrowTimeline() {

        growTimeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            stick.setEndY(stick.getEndY() - 5);
//            lengthOfStick = stick.getEndY() - stick.getStartY();
//            System.out.println("stick length-"+stick.getEndY());

        }));
        growTimeline.setCycleCount(Timeline.INDEFINITE);
//        growTimeline.setAutoReverse(true);

        growTimeline.setOnFinished(e -> {
            if (isMousePressed()) {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - this.startTime;
                int cycles = (int) (elapsedTime / 50);
                growTimeline.setCycleCount(cycles);
                growTimeline.setAutoReverse(false);
                growTimeline.playFromStart();
            }
        });


        growTimeline.play();
    }

    private void stopGrowTimeline() {
//        System.out.println("entered stop growing");

        if (growTimeline != null) {
            growTimeline.stop();

        }
    }

    private boolean isMousePressed() {

        return growTimeline != null && growTimeline.getStatus() == Timeline.Status.RUNNING;
    }


    private void startFallTimeline() {
        double centerX = stick.getStartX();
        double centerY = stick.getStartY();
        double radius = stick.getEndY();
        lengthOfStick = radius;

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
        if (fallTimeline != null) {
            fallTimeline.stop();
//            harryMoved = true;
        }
    }

    private boolean isStickLongEnough() {


        double endY = stick.getEndY();



        double stickLength = -1 * endY;
        double pillarX = pillar2.getBoundsInParent().getCenterX();

        double pillarWidth = pillar2.getWidth();

        pillarX -= pillarWidth / 2;



        double distanceToPillar = pillarX - pillar1.getBoundsInParent().getCenterX() - pillar1.getWidth() / 2;

        return stickLength >= distanceToPillar && stickLength <= distanceToPillar + pillar2.getWidth();

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

//


        double totalDistance = -1 * stick.getEndY() - harry.getX();

        moveCharacterTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {

            double moveStep = 5.0;


            if (Math.abs(totalDistance) >= moveStep) {

                if (totalDistance > 0) {
                    harry.setX(harry.getX() + moveStep);
                } else {
                    harry.setX(harry.getX() - moveStep);
                }
            }

            if(harry.getX() >= totalDistance){


            stopMoveCharacterTimeline();
//            actionsCompleted = true;
            }
        }));
        moveCharacterTimeline.setCycleCount(Timeline.INDEFINITE);
        moveCharacterTimeline.play();

        longEnough = isStickLongEnough();
    }

    private void stopMoveCharacterTimeline() {

        if (moveCharacterTimeline != null) {

            moveCharacterTimeline.stop();
            initializeFallCheckTimeline();
        }
    }

    private void initializeFallCheckTimeline() {
//

         fallCheckTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {

            if (harry.getX() >= -1 * lengthOfStick) {
//                System.out.println("i am in fall check");
                if(!longEnough){
                    fallHarry();
                    stopFallCheckTimeline();
                }
                else actionsCompleted = true;




            }
        }));
        fallCheckTimeline.setCycleCount(Animation.INDEFINITE);
        fallCheckTimeline.play();
    }

    private void stopFallCharacterTimeline() {
        if(fallCharacterTimeline != null) {
            fallCharacterTimeline.stop();
            fallCheckTimeline.stop();
        }

    }

    private void stopFallCheckTimeline() {

        if (fallCheckTimeline != null) {
            fallCheckTimeline.stop();
        }
    }



}






