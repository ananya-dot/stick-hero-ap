package org.example;


import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
    private Label snitchScore;
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

    @FXML
    private ImageView Snitch;

    private Timeline[] growTimelines;

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
//        gamePlayRoot.getChildren().remove(pillar1);
//        gamePlayRoot.getChildren().remove(pillar2);
//        createPillars();
//        movePillars();
        originalPillar1 = pillar1;
        originalPillar2 = pillar2;
        simulateScoreUpdates();
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
                        growButton.setVisible(true);
                        System.out.println("Actions-"+actionsCompleted);
                        System.out.println("run later");
                        movePillars();
                        resetStickAndHarry();
                        growButton.setVisible(true);
                    });
                }).whenComplete((result, throwable) -> {
                    actionsCompleted = false;
//                    resetStickAndHarry();

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

        movePillarsTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(originalPillar1.layoutXProperty(), originalPillar1.getLayoutX())),
                new KeyFrame(Duration.millis(durationInMillis),
                        new KeyValue(pillar1.layoutXProperty(), originalPillar1.getLayoutX() - pixelsToMove)),
                new KeyFrame(Duration.ZERO, new KeyValue(harry.layoutXProperty(), harry.getLayoutX())),
                new KeyFrame(Duration.millis(durationInMillis),
                        new KeyValue(harry.layoutXProperty(), harry.getLayoutX() - pixelsToMove)),
                new KeyFrame(Duration.ZERO, new KeyValue(originalPillar2.layoutXProperty(), originalPillar2.getLayoutX())),
                new KeyFrame(Duration.millis(durationInMillis),
                        new KeyValue(originalPillar2.layoutXProperty(), originalPillar2.getLayoutX() - pixelsToMove))

        );

        movePillarsTimeline.setOnFinished(event -> {

//
//            // After the animation finishes, synchronize pillar1 with pillar2
//            pillar1.setLayoutX(pillar2.getLayoutX());
//            pillar1.setLayoutY(pillar2.getLayoutY());
//            pillar1.setWidth(pillar2.getWidth());
//            pillar1.setHeight(pillar2.getHeight());
//            pillar1.setFill(pillar2.getFill());

            System.out.println("p1 X-"+pillar1.getLayoutX());
            System.out.println("p2 X-"+pillar2.getLayoutX());

            // Update the position of pillar2
            Random random = new Random();
            int randomDistance = random.nextInt(3) + 1;
            double newXPosition = 3 * 100; // Assuming each unit is 100 pixels

            // Update the layout and setX of pillar2
//            pillar1.setLayoutX(pillar2.);
//            pillar2.setLayoutX(newXPosition);
//            pillar2.setLayoutY(pillar1.getLayoutY()); // Align the Y position with pillar1

            // Optionally, you can also update other properties of pillar2 if needed

            // Create a TranslateTransition to slide pillar1 forward to the random distance
            TranslateTransition slideForward = new TranslateTransition(Duration.seconds(1), pillar1);
            slideForward.setToX(newXPosition);
            slideForward.setInterpolator(Interpolator.EASE_BOTH);
            slideForward.play();
            System.out.println(newXPosition);
//            pillar1.setLayoutX(newXPosition+pillar1.getLayoutX());

//            pillar1.setLayoutX(0);
//            Rectangle temp = pillar1;
//            pillar1 = pillar2;
//            pillar2 = temp;
            pillar1.setX(newXPosition+pillar1.getLayoutX());


//            pillar2.setLayoutX(newXPosition);
//            Rectangle temp = pillar1;
//            pillar1 = pillar2;
//            pillar2 = temp;
//            pillar2.setLayoutX(pillar1.getLayoutX());
            System.out.println("p1 X-"+pillar1.getLayoutX());
            System.out.println("p2 X-"+pillar2.getLayoutX());


//            resetPillars(pillar1,pillar2);


        });

        movePillarsTimeline.play();
    }



    private void stopPillarsTimeline(){
        if(movePillarsTimeline!=null){
            movePillarsTimeline.stop();
        }
    }

    private void createPillars(){
        Random random=new Random();
        Pillars = FXCollections.observableArrayList();
        Rectangle rect = new Rectangle(60, 160, Color.rgb(176, 129, 97));
        rect.setLayoutX(-1); // Set layout based on the loop index
        rect.setLayoutY(240);
        Pillars.add(rect);
        gamePlayRoot.getChildren().add(rect);


        for(int i=1;i<=5;i++){
            Rectangle rectangle = new Rectangle(30*(random.nextInt(5)), 160, Color.rgb(176, 129, 97));
            rectangle.setLayoutX(i * 100); // Set layout based on the loop index
            rectangle.setLayoutY(240);
            rectangle.setTranslateY(160);
            Pillars.add(rectangle);
            gamePlayRoot.getChildren().add(rectangle);
        }

    }

    private void hasCollected(double x){
        if(longEnough){
            if(Snitch.getLayoutX()<x){
                if(x - Snitch.getLayoutX() <= 5) {
                    Snitch.setVisible(false);
                    snitchScore.setText(Integer.toString(score));
                    System.out.println("hi");
                }
            }
        }
    }

//private void movePillars() {
//    if (currentPillarIndex < Pillars.size()) {
//        Rectangle currPillar = Pillars.get(0);
//        // Move the character to the next pillar (You can implement this logic)
//        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), currPillar);
//        transition.setToY(0); // Move the pillar to the desired Y position                                            //workable
//        transition.setCycleCount(1);
//        transition.play();
//
//
//
//        // Simulate a delay for the character movement (for demonstration purposes)
//        PauseTransition pause = new PauseTransition(Duration.seconds(2));
//        pause.setOnFinished(event -> {
//            // After the character reaches the pillar, spawn the next pillar
//            spawnNextPillar();
//        });
//        pause.play();
//    }
//}

private void spawnNextPillar(){
    System.out.println("hello pillars");
    if (currentPillarIndex < Pillars.size()) {
        Rectangle nextPillar = Pillars.get(currentPillarIndex);
        double startingY = gamePlayRoot.getHeight(); // Set the starting Y position below the screen

        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), nextPillar);
        transition.setToY(0); // Move the pillar to the desired Y position                                            //workable
        transition.setCycleCount(1);

        // Increment the index for the next pillar after the current animation finishes
        transition.setOnFinished(event -> {
            currentPillarIndex++;
            movePillars(); // Move the next pillar
        });

        // Start the transition animation
        transition.play();
    }
}
//private void movePillars() {
//    System.out.println("hello pillars");
//    if (currentPillarIndex < Pillars.size()) {
//        Rectangle nextPillar = Pillars.get(currentPillarIndex);
//        double startingY = gamePlayRoot.getHeight(); // Set the starting Y position below the screen
//
//        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), nextPillar);
//        transition.setToY(0); // Move the pillar to the desired Y position                                            //workable
//        transition.setCycleCount(1);
//
//        // Increment the index for the next pillar after the current animation finishes
//        transition.setOnFinished(event -> {
//            currentPillarIndex++;
//            movePillars(); // Move the next pillar
//        });
//
//        // Start the transition animation
//        transition.play();
//    }
//}

//    if (currentPillarIndex < Pillars.size()) {
//        Rectangle nextPillar = Pillars.get(currentPillarIndex);
//        // Move the character to the next pillar (You can implement this logic)
//
//        // Simulate a delay for the character movement (for demonstration purposes)
//        PauseTransition pause = new PauseTransition(Duration.seconds(2));
//        pause.setOnFinished(event -> {
//            // After the character reaches the pillar, spawn the next pillar
//            spawnNextPillar();
//        });
//        pause.play();
//    } else {
//        movePillars();
//    }
//}
//
//    private void spawnNextPillar() {
//        if (currentPillarIndex < Pillars.size()) {
//            Rectangle nextPillar = Pillars.get(currentPillarIndex);
//            double startingY = gamePlayRoot.getHeight(); // Set the starting Y position below the screen
//
//            TranslateTransition transition = new TranslateTransition(Duration.seconds(2), nextPillar);
//            transition.setToY(-160); // Move the pillar to the desired Y position
//            transition.setCycleCount(1);
//            transition.play();
//
//            // Increment the index for the next pillar
//            currentPillarIndex++;
//        }
//    }


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
        stick.getTransforms().clear();
        harry.getTransforms().clear();
        stick.setStartX(stick.getStartX());
        stick.setEndX(stick.getStartX());
    }

//    public void resetPillars(Rectangle pillarOne,Rectangle pillarTwo){
//        originalPillar1.setLayoutX(pillarOne.getLayoutX());
//    }

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

//        actionsCompleted = false;
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
            actionsCompleted = true;



//            else{
////                System.out.println("hemlo2");
//                stopFallCheckTimeline();
//                stopFallCharacterTimeline();
//            }


        }));
        fallCharacterTimeline.setCycleCount(Timeline.INDEFINITE);
        fallCharacterTimeline.play();

    }

//    private void fallHarry() {
//
//        double moveStep = 5.0;
//        final int[] currentPillarIndex = {0};
//
//        fallCharacterTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
//            double currentPillarHeight = Pillars.get(currentPillarIndex[0]).getHeight();
//
//            if (harry.getY() < currentPillarHeight) {
//                harry.setY(harry.getY() + moveStep);
//            } else {
//                if (currentPillarIndex[0] < Pillars.size() - 1) {
//                    currentPillarIndex[0]++;
//                } else {
//                    // Character has reached the last pillar
//                    stopFallCharacterTimeline();
//                    actionsCompleted = true;
//                    gameStatus = false;
//                    characterHasFallen = true;
//                    return;
//                }
//            }
//
//            double nextPillarHeight = Pillars.get(currentPillarIndex[0]).getHeight();
//            if (harry.getY() + moveStep >= nextPillarHeight) {
//                // The character reached the next pillar, stop the fall or handle accordingly
//                stopFallCharacterTimeline();
//                // Additional logic after reaching the next pillar
//            }
//        }));
//
//        fallCharacterTimeline.setCycleCount(Timeline.INDEFINITE);
//        fallCharacterTimeline.play();
//
//    }
//
//




    private void startGrowTimeline() {

        growTimeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            stick.setEndY(stick.getEndY() - 5);
//            lengthOfStick = stick.getEndY() - stick.getStartY();
//            System.out.println("stick length-"+stick.getEndY());

        }));
        growTimeline.setCycleCount(Timeline.INDEFINITE);

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
//private void startGrowTimeline() {
//    growTimelines = new Timeline[Pillars.size()];
//    double durationInMillis = 50; // Duration for each cycle in milliseconds
//
//    for (int i = 0; i < Pillars.size(); i++) {
//        Rectangle currentPillar = Pillars.get(i);
//
//        growTimelines[i] = new Timeline(new KeyFrame(Duration.millis(durationInMillis), event -> {
//            stick.setEndY(stick.getEndY() - 5);
//
////            currentPillar.setHeight(currentPillar.getHeight() - 5);
//            // Modify other properties of the rectangle as needed
//        }));
//        growTimelines[i].setCycleCount(Timeline.INDEFINITE);
//
//        final int index = i;
//        growTimelines[i].setOnFinished(e -> {
//            if (isMousePressed()) {
//                long currentTime = System.currentTimeMillis();
//                long elapsedTime = currentTime - this.startTime;
//                int cycles = (int) (elapsedTime / durationInMillis);
//                growTimelines[index].setCycleCount(cycles);
//                growTimelines[index].setAutoReverse(false);
//                growTimelines[index].playFromStart();
//            }
//        });
//
//        growTimelines[i].play();
//    }
//}


    private void stopGrowTimeline() {
//        System.out.println("entered stop growing");

        if (growTimeline != null) {
            growTimeline.stop();

        }
    }
//private void stopGrowTimeline() {
//    if (growTimelines != null) {
//        for (Timeline timeline : growTimelines) {
//            if (timeline != null) {
//                timeline.stop();
//            }
//        }
//    }
//}

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
        System.out.println("harry moving");
        System.out.println("pillar1's X:"+pillar1.getX());
        System.out.println("pillar1's layout X:"+pillar1.getLayoutX());
        System.out.println("pillar2's X:"+pillar2.getX());
        System.out.println("pillar2's layout X:"+pillar2.getLayoutX());

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
             hasCollected(harry.getX());


            if(harry.getX() >= totalDistance){
                stopMoveCharacterTimeline();
                actionsCompleted = true;
            }

        }));
        moveCharacterTimeline.setCycleCount(Timeline.INDEFINITE);
        moveCharacterTimeline.play();

        longEnough = isStickLongEnough();

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
        // JavaFX UI components should be updated on the JavaFX Application Thread
        // Use Platform.runLater() to execute the update on the UI thread
        javafx.application.Platform.runLater(() -> {
            scoreText.setText(Integer.toString(score));
        });
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
                    gameLoop.stop();
                }
                else {
                    actionsCompleted = true;
                    gameLoop.stop();
                }




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
