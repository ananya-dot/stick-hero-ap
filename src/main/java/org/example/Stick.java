package org.example;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Stick extends AbstractGameObject {

    private double length;
    private Pillar pillar;

    private Timeline growTimeline;
    private boolean isMousePressed;

    private Line stick;
    public Stick(double length, Pillar pillar,Line stick,ImageView imageView) {
        super(imageView);
        this.length = length;
        this.pillar = pillar;
        this.stick=stick;
    }

    @Override
    public void update() {
    }



    private boolean isMousePressed() {
        return growTimeline != null && growTimeline.getStatus() == Timeline.Status.RUNNING;
    }


    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public boolean isGrowing(){
        return false;
    }

    public boolean isAtMiddle(){
        return pillar.getMidPoint() == this.length;
    }

    public boolean isSafe(){
        return false;
    }

    public Line getStick() {
        return stick;
    }

    public void setStick(Line stick) {
        this.stick = stick;
    }

//    public void grow(MouseEvent event) {
//        isMousePressed = true;
//        growingActions();
//
//    }

//    public void growingActions(){
//        actionsCompleted = false;
//        startTime = System.currentTimeMillis();
//        startGrowTimeline(startTime);
//
//    }
//
//    private void startGrowTimeline(long startTime) {
//
//        growTimeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
//            stick.setEndY(stick.getEndY() - 5);
//        }));
//        growTimeline.setCycleCount(Timeline.INDEFINITE);
//
//        growTimeline.setOnFinished(e -> {
//            if (isMousePressed()) {
//                long currentTime = System.currentTimeMillis();
//                long elapsedTime = currentTime - startTime;
//                int cycles = (int) (elapsedTime / 50);
//                growTimeline.setCycleCount(cycles);
//            }
//        });
//
//        growTimeline.play();
//    }
//    public void stopGrowing(MouseEvent event) {
//        stopGrowingActions();
//    }
//
//    public void stopGrowingActions(){
//        isGrowing = false;
//        stopGrowTimeline();
//        startFallTimeline();
//        growButton.setVisible(false);
//        moveHarry();
//
//    }
//    private void stopGrowTimeline() {
//
//        if (growTimeline != null) {
//            growTimeline.stop();
//        }
//    }
//
//    private void startFallTimeline() {
//        double centerX = stick.getStartX();
//        double centerY = stick.getStartY();
//        double radius = stick.getEndY();
//        lengthOfStick = radius;
//
//        fallTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
//
//
//            double newX = centerX + radius;
//
//            double rotationAngle = Math.toDegrees(Math.atan2(0.0, newX - centerX));
//            stick.getTransforms().clear();
//            stick.getTransforms().add(new Rotate(rotationAngle, centerX, centerY));
//
//            stick.setEndX(newX);
//            stick.setEndY(centerY);
//
//            if (stick.getEndY() - centerY >= radius) {
//                stopFallTimeline();
//            }
//        }));
//        fallTimeline.setCycleCount(Timeline.INDEFINITE);
//
//        fallTimeline.play();
//
//    }
//
//    private void stopFallTimeline() {
//        if (fallTimeline != null) {
//            fallTimeline.stop();
//        }
//    }
//    boolean isStickLongEnough() {
//
//
//        double endY = stick.getEndY();
//
//
//
//        double stickLength = -1 * endY;
//        double pillarX = pillar2.getBoundsInParent().getCenterX();
//
//        double pillarWidth = pillar2.getWidth();
//
//        pillarX -= pillarWidth / 2;
//
//
//
//        double distanceToPillar = pillarX - pillar1.getBoundsInParent().getCenterX() - pillar1.getWidth() / 2;
//
//        return stickLength >= distanceToPillar && stickLength <= distanceToPillar + pillar2.getWidth();
//
//    }
//    void initializeFallCheckTimeline() {
//
//        fallCheckTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
//
//            if (harry.getX() >= -1 * lengthOfStick) {
//                if(!longEnough){
//                    fallHarry();
//                    stopFallCheckTimeline();
//                }
//                else actionsCompleted = true;
//            }
//        }));
//        fallCheckTimeline.setCycleCount(Animation.INDEFINITE);
//        fallCheckTimeline.play();
//    }
}

