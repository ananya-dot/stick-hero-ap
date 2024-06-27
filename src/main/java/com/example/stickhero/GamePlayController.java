package com.example.stickhero;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Controller class for the gameplay screen of Stick Hero game.
 */
public class GamePlayController {

    // JavaFX root element
    public AnchorPane root;

    // Scene reference
    private Scene scene;

    // Current selected character
    private Characters currentCharacter;

    // Character images and stick
    @FXML
    private ImageView ron1;
    @FXML
    private ImageView ron2;
    @FXML
    private ImageView ronStick1;
    @FXML
    private ImageView ronStick2;
    @FXML
    private ImageView hermione1;
    @FXML
    private ImageView hermione2;
    @FXML
    private ImageView hermioneStick1;
    @FXML
    private ImageView hermioneStick2;


    @FXML
    private ImageView harry1;

    @FXML
    private ImageView harry2;

    @FXML
    private ImageView harryStick1;
    @FXML
    private ImageView harryStick2;

    // Stick line
    @FXML
    private Line stick;

    // Pillar images
    @FXML
    private ImageView pillar1;
    @FXML
    private ImageView pillar2;
    @FXML
    private ImageView pillar3;
    @FXML
    private ImageView pillar4;
    @FXML
    private ImageView pillar5;

    // Timelines for animations
    private Timeline walkingTransition;
    private Timeline stickGrowingTimeline;
    private Timeline stickFallTimeline;
    private Timeline WalkTimeline;
    private Timeline fallTimeline;
    private Timeline wandWaping;


    // Rotation and movement control variables
    private double totalrotation=0;
    private boolean isGrowing=false;
    private boolean isMoving=false;
    private boolean jumping=false;
    private boolean visible=false;
    private volatile boolean running = true;

    // Game elements
    private Pillars pillars=null;
    private Stage stage;
    private String characterName;

    private SceneController sceneController=new SceneController();
    public AnchorPane getRoot() {
        return root;
    }

    public String getCharacterName() {
        return characterName;
    }

    // UI elements for score and time
    @FXML
    private Label timescoreText;
    private int time;
    @FXML
    private ImageView Snitch;
    @FXML
    private Label snitchScoreText;
    private int snitchScore;
    @FXML
    private Label scoreText;
    private int score;


    // UI elements for end game screen
    @FXML
    private ImageView endScroll;
    @FXML
    private ImageView endTimeLabel;
    @FXML
    private ImageView endScoreLabel;
    @FXML
    private ImageView endSnitch;
    @FXML
    private Text endDash;
    @FXML
    private ImageView endRestart;
    @FXML
    private ImageView endMainMenu;
    @FXML
    private ImageView endExit;

    @FXML
    private Label endTime;
    @FXML
    private Label endScore;
    @FXML
    private Label endSnitchScore;

    private boolean gameResumed;

    private ImageView currentPillar;

    private ImageView nextPillar;

    public ImageView getSnitch() {
        return Snitch;
    }

    public void setSnitch(ImageView snitch) {
        Snitch = snitch;
    }

    public ImageView getCurrentPillar() {
        return currentPillar;
    }

    public void setCurrentPillar(ImageView currentPillar) {
        this.currentPillar = currentPillar;
    }

    public ImageView getNextPillar() {
        return nextPillar;
    }

    public Characters getCurrentCharacter() {
        return currentCharacter;
    }

    public void setCurrentCharacter(Characters currentCharacter) {
        this.currentCharacter = currentCharacter;
    }


    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }


    public Label getScoreText() {
        return scoreText;
    }

    public void setScoreText(Label scoreText) {
        this.scoreText = scoreText;
    }

    public Label getSnitchScoreText() {
        return snitchScoreText;
    }

    public void setSnitchScoreText(Label snitchScoreText) {
        this.snitchScoreText = snitchScoreText;
    }

    public void setNextPillar(ImageView nextPillar) {
        this.nextPillar = nextPillar;
    }

    public boolean isGameResumed() {
        return gameResumed;
    }

    public void setGameResumed(boolean gameResumed) {
        this.gameResumed = gameResumed;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public void setRoot(AnchorPane root) {
        this.root = root;
        if (root != null) {
            System.err.println("Root set successfully.");

        } else {
            System.err.println("Root is still null. Initialization failed.");
        }
    }

    public Line getStick() {
        return stick;
    }

    public void setStick(Line stick) {
        this.stick = stick;
    }

    public void setStage(Stage stage) {
        this.stage=stage;
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     * @throws IOException If an input or output exception occurs.
     */
    @FXML
    private void initialize() throws IOException {
        // Initialize end game screen elements as hidden
        endScroll.setVisible(false);
        endTimeLabel.setVisible(false);
        endScoreLabel.setVisible(false);
        endSnitch.setVisible(false);
        endDash.setVisible(false);
        endRestart.setVisible(false);
        endMainMenu.setVisible(false);
        endExit.setVisible(false);
        endTime.setVisible(false);
        endScore.setVisible(false);
        endSnitchScore.setVisible(false);

        // Hide alternate character images and sticks
        ron2.setVisible(false);
        harry2.setVisible(false);
        hermione2.setVisible(false);
        harryStick1.setVisible(false);
        harryStick2.setVisible(false);
        ronStick1.setVisible(false);
        ronStick2.setVisible(false);
        hermioneStick1.setVisible(false);
        hermioneStick2.setVisible(false);

        // Simulate score updates
        simulateScoreUpdates();

    }

    /**
     * Sets up the initial character based on the selected character name.
     */
    public void CharacterSetting() {
        switch (characterName) {
            case "Harry":
                setupCharacter("Harry", harry1, harry2, harryStick1, harryStick2);
                break;
            case "Ron":
                setupCharacter("Ron", ron1, ron2, ronStick1, ronStick2);
                break;
            default:
                setupCharacter("Hermione", hermione1, hermione2, hermioneStick1, hermioneStick2);
                break;
        }
    }

    /**
     * Sets up the character with its initial appearance.
     * @param name The name of the character.
     * @param char1 First image view of the character.
     * @param char2 Second image view of the character.
     * @param stick1 First stick image view of the character.
     * @param stick2 Second stick image view of the character.
     */
    public void setupCharacter(String name, ImageView char1, ImageView char2, ImageView stick1, ImageView stick2) {
        currentCharacter = new Characters(name, char1, char2, stick1, stick2);
        hideOtherCharacters(char1, char2, stick1, stick2);
        System.out.println("hi!! this is " + name.toLowerCase());
        System.out.println("Game Resumed-"+gameResumed);
        pillars = new Pillars(root, pillar1, pillar2, pillar3, pillar4, pillar5, stick, currentCharacter, Snitch);
        // Initialize or resume pillars based on gameResumed flag
        if(gameResumed){
            pillars.setCurrentPillar(currentPillar);
            pillars.setNextPillar(nextPillar);

            pillars.ResumePillars(currentPillar.getLayoutX(),nextPillar.getLayoutX());
        }
        else{
            pillars.generatePillars();
        }
    }

    private void hideOtherCharacters(ImageView char1, ImageView char2, ImageView stick1, ImageView stick2) {
        // Loop through all character and stick image views, hide those not matching the current character
        for (ImageView iv : new ImageView[]{harry1, harry2, harryStick1, harryStick2, ron1, ron2, ronStick1, ronStick2, hermione1, hermione2, hermioneStick1, hermioneStick2}) {
            if (!iv.getId().equals(char1.getId()) && !iv.getId().equals(char2.getId()) && !iv.getId().equals(stick1.getId()) && !iv.getId().equals(stick2.getId())) {
                iv.setVisible(false);
            }
        }
    }



    public void setScene(Scene scene) {
        // Set the scene and setup key events for stick operations
        this.scene = scene;
        setupKeyEvent();
    }

    private void setupKeyEvent() {
        // Setup key event listeners for stick growing and character flipping
        scene.setOnKeyPressed(this::stickGrow);
        scene.setOnKeyReleased(this::stickGrow);
        scene.getRoot().requestFocus();
    }


    @FXML
    private void stickGrow(KeyEvent event) {
        // Handle key events for stick growing and character flipping
        System.out.println(currentCharacter.getName());
        if (event.getCode() == KeyCode.SPACE) {
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                // Start growing the stick when SPACE key is pressed
                System.out.println("Key_Pressed");
                startStickGrowing();
                isGrowing=true;
            } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                System.out.println("Key_Released");
                // Stop growing the stick when SPACE key is released
                stopStickGrowing();
                isGrowing=false;
            }
        }
        else if (event.getCode() == KeyCode.DOWN) {
            if(!currentCharacter.isUpsideDown()) {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    // Flip the character upside down
                    currentCharacter.flipUpsideDown();
                }
            }
        } else if (event.getCode() == KeyCode.UP) {
            if(currentCharacter.isUpsideDown()) {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    // Revert the character to normal orientation
                    currentCharacter.revertUpsideDown();
                }
            }
        }
    }
    private void startWalkingTransition() {
        // Start animation for character walking transition
        visible=currentCharacter.isIschar1Visible();
        walkingTransition = new Timeline(
                new KeyFrame(Duration.millis(100), e -> {
                    if (visible) {
                        currentCharacter.getChar1().setVisible(false);
                        currentCharacter.getChar2().setVisible(true);
                    } else {
                        currentCharacter.getChar1().setVisible(true);
                        currentCharacter.getChar2().setVisible(false);
                    }
                    visible = !visible;
                })
        );

        walkingTransition.setCycleCount(Timeline.INDEFINITE);
        walkingTransition.play();
    }

    private void startStickGrowing() {
        // Start growing the stick by increasing its length
        if (stickGrowingTimeline == null) {
            stickGrowingTimeline = new Timeline(new KeyFrame(Duration.millis(20), e -> {
                double endY = stick.getEndY() - 2.5;
                stick.setEndY(endY);

                stick.setEndX(stick.getEndX());

            }));
            stickGrowingTimeline.setCycleCount(Timeline.INDEFINITE);
            stickGrowingTimeline.play();
            abracadabara();
        }
    }

    private void stopStickGrowing() {
        // Stop growing the stick and start its fall animation
        if (stickGrowingTimeline != null) {
            stickGrowingTimeline.stop();
            abracadabraStop();
            stickGrowingTimeline = null;
            startStickFallTimeline();
        }
    }

    private void startStickFallTimeline() {
        // Once the stick reaches its maximum length, start the fall animation
        System.out.println("Stick Layout X" + stick.getLayoutX());
        System.out.println("Stick Layout Y" + stick.getLayoutY());

        double startX = stick.getStartX();
        double startY = stick.getStartY();
        double endX = stick.getEndX();
        double endY = stick.getEndY();
        double targetAngle = 90; // The target angle for the rotation
        stickFallTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {

                }),
                new KeyFrame(Duration.millis(20), e -> {
                    stick.getTransforms().add(new Rotate(1, startX, startY));
                    double rotatedAngle = ((Rotate) stick.getTransforms().get(0)).getAngle();
                    totalrotation+=rotatedAngle;

                    System.out.println("rotated angle"+totalrotation);
                    System.out.println("stick angle"+(Math.abs(rotatedAngle - targetAngle)));
                    if (totalrotation == 90) {
                        stickFallTimeline.stop();
                        totalrotation = 0;
                        startWalkingTransition();
                        startWalking();
                    }
                })
        );

        stickFallTimeline.setCycleCount(Animation.INDEFINITE);
        stickFallTimeline.setAutoReverse(false);

        stickFallTimeline.play();

    }

    private void startWalking() { //character class
        double startX = stick.getStartX();
        double startY = stick.getStartY();
        double endX = stick.getEndX();
        double endY = stick.getEndY();

        double new_X = newX(startX, startY, endX, endY);
        System.out.println("New X: " + Math.ceil(new_X));
        System.out.println("harry1X-" + currentCharacter.getChar1().getLayoutX());


        double walkDistance = currentCharacter.getChar1().getLayoutX() + (new_X - Math.ceil(startX))+50;
        double totalDistance= pillars.nextPillar.getLayoutX()+pillars.nextPillar.getFitWidth();
        System.out.println("Start X: " + stick.getStartX());
        System.out.println("Start Y: " + stick.getStartY());
        System.out.println("End X: " + stick.getEndX());
        System.out.println("End Y: " + stick.getEndY());


        System.out.println("pillars layX="+pillars.nextPillar.getLayoutX());

        System.out.println(pillars.nextPillar.getFitWidth());

        System.out.println("Walk Distance: " + walkDistance);
        if (walkDistance >= pillars.nextPillar.getLayoutX() && walkDistance <= totalDistance) {
            WalkTimeline = new Timeline(
                    new KeyFrame(Duration.millis(10), e -> {
                        double end = currentCharacter.getChar1().getLayoutX() + 2;
                        currentCharacter.getChar1().setLayoutX(end);
                        currentCharacter.getChar2().setLayoutX(end);
                        isMoving = true;
                        checkSnitchCollision();
                        if(!currentCharacter.isUpsideDown()) {
                            if (currentCharacter.getChar1().getLayoutX() >= Math.floor(totalDistance - 50) && currentCharacter.getChar2().getLayoutX() >= Math.floor(totalDistance - 50)) {
                                if (walkingTransition != null) {
                                    walkingTransition.stop();
                                    if (!currentCharacter.getChar1().isVisible()) {
                                        currentCharacter.getChar1().setVisible(true);
                                        currentCharacter.getChar2().setVisible(false);
                                    }
                                }
                                walkingTransition.stop();
                                WalkTimeline.stop();
                                isMoving = false;
                                System.out.println("im here");
                                resetStick(new_X, walkDistance);
                                increaseScore();
                            }
                        }else {
                            if (currentCharacter.getChar1().getLayoutX() >= Math.floor(totalDistance - 50- pillars.nextPillar.getFitWidth()) && currentCharacter.getChar2().getLayoutX() >= Math.floor(totalDistance - 50- pillars.nextPillar.getFitWidth())) {
                                if (walkingTransition != null) {
                                    walkingTransition.stop();
                                    if (!currentCharacter.getChar1().isVisible()) {
                                        currentCharacter.getChar1().setVisible(true);
                                        currentCharacter.getChar2().setVisible(false);
                                    }
                                }
                                walkingTransition.stop();
                                WalkTimeline.stop();
                                isMoving = false;
                                System.out.println("im here");
                                fallingAnimation();

                            }
                        }
                    })
            );
            WalkTimeline.setCycleCount(Timeline.INDEFINITE);
            WalkTimeline.play();
        } else{
            WalkTimeline = new Timeline(
                    new KeyFrame(Duration.millis(10), e -> {
                        double end = currentCharacter.getChar1().getLayoutX() + 2;
                        currentCharacter.getChar1().setLayoutX(end);
                        currentCharacter.getChar2().setLayoutX(end);
                        isMoving = true;
                        checkSnitchCollision();
                        if (currentCharacter.getChar1().getLayoutX() >= Math.floor(walkDistance-20) && currentCharacter.getChar2().getLayoutX() >= Math.floor(walkDistance-20)) {
                            walkingTransition.stop();
                            WalkTimeline.stop();
                            isMoving = false;
                            fallingAnimation();
                        }
                    })
            );
            WalkTimeline.setCycleCount(Timeline.INDEFINITE);
            WalkTimeline.play();
        }
    }

    private void resetStick(double newX,double walkdistance) { //character class
        stick.getTransforms().clear();
            stick.setStartX(newX+50);
            stick.setEndX(stick.getStartX());
            stick.setEndY(stick.getStartY());



            System.out.println("Stick Reset (After Rotation)");

        System.out.println("harrY layout"+currentCharacter.getChar1().getLayoutX()+currentCharacter.getChar2().getLayoutX());

        System.out.println(stick.getEndX());
        System.out.println(stick.getEndY());
        System.out.println(stick.getStartX());
        System.out.println(stick.getStartY());
        System.out.println(currentCharacter.getChar1().getX());
        System.out.println(currentCharacter.getChar2().getX());
        pillars.movePillars(walkdistance);
    }

    public void fallingAnimation(){ //character class
        fallTimeline=new Timeline(
                new KeyFrame(Duration.millis(10),e->{
                    currentCharacter.getChar1().setLayoutY(currentCharacter.getChar1().getLayoutY()+2);
                    currentCharacter.getChar2().setLayoutY(currentCharacter.getChar1().getLayoutY()+2);
                    if(currentCharacter.getChar1().getLayoutY()>=415 && currentCharacter.getChar2().getLayoutY()>=415){
                        fallTimeline.stop();
                        stopScoreUpdates();
                        endTime.setText(Integer.toString(time+1));
                        endGame();
                    }
                })
        );
        fallTimeline.setCycleCount(Timeline.INDEFINITE);
        fallTimeline.play();
    }

    private double newX(double startX,double startY,double EndX,double EndY){
        double pX = startX;
        double pY = startY;
        double rX = EndX;
        double rY = EndY;

        double x = pX + Math.sqrt(Math.pow(rX - pX, 2) + Math.pow(rY - pY, 2));
        System.out.println("The value of x is: " + x);
        return x;
    }




    public void abracadabara(){ //character class
        jumping=currentCharacter.isCharJumping();
//        jumping=true;
        currentCharacter.getChar1().setVisible(false);
        wandWaping=new Timeline(
                new KeyFrame(Duration.millis(200),e->{
                    if (jumping) {
                        currentCharacter.getCharNstick1().setVisible(false);
                        currentCharacter.getCharNstick2().setVisible(true);
                    } else {
                        currentCharacter.getCharNstick1().setVisible(true);
                        currentCharacter.getCharNstick2().setVisible(false);
                    }
                    jumping = !jumping;
                })
        );
        wandWaping.setCycleCount(Timeline.INDEFINITE);
        wandWaping.play();
    }

    public void abracadabraStop(){ //character class
            jumping=false;
            wandWaping.stop();
            currentCharacter.getCharNstick1().setVisible(false);
            currentCharacter.getCharNstick2().setVisible(false);
            currentCharacter.getChar1().setVisible(true);
    }

    public void switchToPauseMenu(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PauseMenu.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        PauseMenuController pauseMenuController = loader.getController();
        pauseMenuController.setStage(stage);
        pauseMenuController.setScene(scene);
        pauseMenuController.setRoot(root);
        pauseMenuController.setAvatar(currentCharacter.getName());
        ImageView pauseBar = (ImageView) scene.lookup("#pauseMenu");
        pauseMenuController.startMenuAnimation(pauseBar);
        pauseMenuController.setSnitch(Snitch);
        pauseMenuController.setPillars(pillars);
        pauseMenuController.setCurrentCharacter(currentCharacter);
        pauseMenuController.setScore(score);
        pauseMenuController.setSnitch_score(snitchScore);
        pauseMenuController.setStick(stick);
        pauseMenuController.setTime(time);
        pauseMenuController.checkingState();
    }

    private void simulateScoreUpdates() {
        new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                time+=1;

                updateScoreLabel();
            }

        }).start();
    }
    private void updateScoreLabel() {

        javafx.application.Platform.runLater(() -> {
            timescoreText.setText(Integer.toString(time));
        });
    }

    public void stopScoreUpdates() {
        running = false;
    }

    private void checkSnitchCollision() {
        if (Snitch.isVisible() &&
                Math.abs(currentCharacter.getChar1().getLayoutX() - Snitch.getLayoutX()) <= Snitch.getFitWidth() + currentCharacter.getChar1().getFitWidth()-20) {
            // Snitch collected!
            Snitch.setVisible(false);
            increaseSnitchScore();
        }
    }

    private void increaseSnitchScore() {
        snitchScore++;
        snitchScoreText.setText(Integer.toString(snitchScore)); // Update UI
    }

    private void increaseScore(){
        System.out.println("Current Pillars-"+pillars.currentPillar.getFitWidth());
        if(pillars.currentPillar.getFitWidth()<20){
            score+=20;
        }else score+=10;

        scoreText.setText(Integer.toString(score));
    }

    public void endGame(){
        endScore.setText(Integer.toString(score));
        endSnitchScore.setText(Integer.toString(snitchScore));
        endScroll.setVisible(true);
        endTimeLabel.setVisible(true);
        endScoreLabel.setVisible(true);
        endSnitch.setVisible(true);
        endDash.setVisible(true);
        endRestart.setVisible(true);
        endMainMenu.setVisible(true);
        endExit.setVisible(true);
        endTime.setVisible(true);
        endScore.setVisible(true);
        endSnitchScore.setVisible(true);
        gameOverAnimation(endScroll);
    }

    public void gameOverAnimation(ImageView imageView){
        TranslateTransition animation = new TranslateTransition();
        animation.setDuration(Duration.millis(1500)); // Adjust duration as needed
        animation.setAutoReverse(true);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setByY(5); // Adjust Y offset for floating animation
        animation.setNode(imageView);
        animation.play();
        imageView.setUserData(animation);
    }

    public void restart(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChooseCharacters.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public int getSnitchScore() {
        return snitchScore;
    }

    public void setSnitchScore(int snitchScore) {
        this.snitchScore = snitchScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void mainMenu(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartGame.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        SceneController controller= loader.getController();
        controller.flyingAnimation();
        if(!controller.isMusic()){
            controller.getMusicON().setVisible(false);
            controller.getMusicOFF().setVisible(true);
            controller.setMusic(false);
            System.out.println("oFF");
        }else if(controller.isMusic()){
            controller.getMusicOFF().setVisible(false);
            controller.getMusicON().setVisible(true);
            controller.setMusic(true);

            System.out.println("ON");
        }
        controller.getSettingButton2().setVisible(false);
        controller.getSettingBar().setVisible(false);
        controller.getVolumeSlider().setVisible(false);
        controller.getVolumeSlider().setValue(controller.getMediaPlayer().getVolume()*100);

    }

    public void exit(MouseEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}