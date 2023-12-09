package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Character extends AbstractGameObject{
    private GamePlayController gamePlayController;
    private Character harry;
    private Stick stick;
    private String name;

    private Timeline moveCharacterTimeline;
    private boolean longEnough;

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Character(String name, ImageView imageView) {
        super(imageView);
        this.name = name;

    }

    @Override
    public void update() {

    }

    public void walk(){

    }

    public void fall(){

    }

    public void invert(){

    }

    public void collect(){

    }

//    private void moveHarry() {
//
//        double totalDistance = -1 * stick.getStick().getEndY() - harry.getImageView().getX();
//
//
//        moveCharacterTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
//
//            double moveStep = 5.0;
//
//
//            if (Math.abs(totalDistance) >= moveStep) {
//
//                if (totalDistance > 0) {
//                    harry.getImageView().setX(harry.getImageView().getX() + moveStep);
//                } else {
//                    harry.getImageView().setX(harry.getImageView().getX() - moveStep);
//                }
//            }
//
//
//            if(harry.getImageView().getX() >= totalDistance){
//                stopMoveCharacterTimeline();
//            }
//        }));
//        moveCharacterTimeline.setCycleCount(Timeline.INDEFINITE);
//        moveCharacterTimeline.play();
//
//        longEnough = stick.isStickLongEnough();
//        if(longEnough) gamePlayController.setGameStatus(false);
//
//    }
//
//    private void stopMoveCharacterTimeline() {
//
//        if (moveCharacterTimeline != null) {
//
//            moveCharacterTimeline.stop();
//            stick.initializeFallCheckTimeline();
//        }
//    }
//    private void stopFallCharacterTimeline() {
//        if(fallCharacterTimeline != null) {
//            System.out.println("Character has fallen");
//            fallCharacterTimeline.stop();
//            gameStatus=true;
//            gameLoop.stop();
//
//        }
//
//    }
//    private void stopFallCheckTimeline() {
//
//        if (fallCheckTimeline != null) {
//            fallCheckTimeline.stop();
//        }
//    }
//    private void fallHarry() {
//        double totalDistance = pillar2.getHeight();
//
//        fallCharacterTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
//
//            double moveStep = 5.0;
//
//
//
//
//            if ((totalDistance) >= moveStep) {
//                harry.setY(harry.getY() + moveStep);
//            }
//
//
//            if(harry.getY() == totalDistance){
//                stopFallCheckTimeline();
//                stopFallCharacterTimeline();
//                actionsCompleted = true;
//            }
//
//
//
//        }));
//        fallCharacterTimeline.setCycleCount(Timeline.INDEFINITE);
//        fallCharacterTimeline.play();
//
//    }

}
