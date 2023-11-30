package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Stick extends AbstractGameObject {

    private double length;
    private Pillar pillar;

    private Timeline growTimeline;
    private boolean isMousePressed;

    private Line stick;
    public Stick(double x, double y, double length, Pillar pillar) {
        super(x, y);
        this.length = length;
        this.pillar = pillar;
    }

    @Override
    public void update() {
    }

    private void startGrowTimeline(long startTime) {
        growTimeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            stick.setEndY(stick.getEndY() - 5);
//            lengthOfStick = stick.getEndY() - stick.getStartY();
        }));
        growTimeline.setCycleCount(Timeline.INDEFINITE);

        growTimeline.setOnFinished(e -> {
            if (isMousePressed()) {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - startTime;
                int cycles = (int) (elapsedTime / 50);
                growTimeline.setCycleCount(cycles);
                growTimeline.playFromStart();
            }
        });


        growTimeline.play();
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


}

