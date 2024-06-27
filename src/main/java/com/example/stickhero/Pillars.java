package com.example.stickhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Pillars class handles the creation and movement of pillars in the game.
 */
public class Pillars {

    // Root container for pillars
    private AnchorPane pillarroot;

    // Timeline for moving pillars
    private Timeline movePillarsTimeline;

    // List to hold the ImageView objects representing the pillars
    private List<ImageView> pillars = new ArrayList<>();

    // Random number generator for positioning pillars
    private Random random = new Random();

    // Number of pillars in the game
    private final int NUM_PILLARS = 5;

    // ImageViews for each pillar
    private ImageView pillar1;
    private ImageView pillar2;
    private ImageView pillar3;
    private ImageView pillar4;
    private ImageView pillar5;

    // Line representing the stick in the game
    private Line stick;

    // Current and next pillars in the game
    public ImageView currentPillar;
    public ImageView nextPillar;

    // Timeline for transitioning between pillars
    private Timeline transitionTimeline;

    // Flag to check if the stick is growing
    private boolean isStickGrowing = false;

    // Current character in the game
    private Characters currentCharacter;

    // ImageView for the Snitch
    private ImageView Snitch;

    // Constructor for Pillars class
    public Pillars(AnchorPane root) {
        this.pillarroot = root;
    }

    // Getter and setter for pillarroot
    public AnchorPane getPillarroot() {
        return pillarroot;
    }

    public void setPillarroot(AnchorPane pillarroot) {
        this.pillarroot = pillarroot;
    }

    // Getter and setter for currentPillar and nextPillar
    public ImageView getCurrentPillar() {
        return currentPillar;
    }

    public ImageView getNextPillar() {
        return nextPillar;
    }

    public void setCurrentPillar(ImageView currentPillar) {
        this.currentPillar = currentPillar;
    }

    public void setNextPillar(ImageView nextPillar) {
        this.nextPillar = nextPillar;
    }

    // Full constructor for Pillars class
    public Pillars(AnchorPane pillarroot, ImageView pillar1, ImageView pillar2, ImageView pillar3, ImageView pillar4, ImageView pillar5, Line stick, Characters currentCharacter, ImageView Snitch) {
        this.pillarroot = pillarroot;
        this.pillar1 = pillar1;
        this.pillar2 = pillar2;
        this.pillar3 = pillar3;
        this.pillar4 = pillar4;
        this.pillar5 = pillar5;
        this.currentCharacter = currentCharacter;
        this.stick = stick;
        this.Snitch = Snitch;
    }

    // Method to generate initial pillars
    public void generatePillars() {
        pillars.add(pillar1);
        pillars.add(pillar2);
        pillars.add(pillar3);
        pillars.add(pillar4);
        pillars.add(pillar5);
        pillar1.setLayoutY(264);
        pillar2.setLayoutY(264);
        pillar3.setLayoutY(264);
        pillar4.setLayoutY(264);
        pillar5.setLayoutY(264);

        double initialX1 = 0; // Initial X position of pillar1
        Random random = new Random();
        double randomDouble = random.nextDouble() * 200 + 100;
        double initialX2 = randomDouble; // Initial X position of pillar2 (spaced 200 units from pillar1)

        // Set initial positions for the first two pillars
        pillar1.setLayoutX(initialX1);
        pillar2.setLayoutX(initialX2);
        currentPillar = pillar1;
        nextPillar = pillar2;

        // Hide other pillars initially
        pillar3.setVisible(false);
        pillar4.setVisible(false);
        pillar5.setVisible(false);

        System.out.println("hehehe");
        System.out.println(nextPillar.getLayoutX());
    }

    // Method to resume pillars from saved positions
    public void ResumePillars(double cP, double nP) {
        if (pillars.isEmpty()) {
            pillars.add(pillar1);
            pillars.add(pillar2);
            pillars.add(pillar3);
            pillars.add(pillar4);
            pillars.add(pillar5);
        }

        System.out.println("Resuming pillars...");
        System.out.println(currentPillar.getId());
        System.out.println(nextPillar.getId());

        for (ImageView pillar : pillars) {
            if (pillar.getId().equals(currentPillar.getId())) {
                pillar.setVisible(true);
                currentPillar = pillar;
                System.out.println("Current Pillar: " + pillar);
            } else if (pillar.getId().equals(nextPillar.getId())) {
                pillar.setVisible(true);
                nextPillar = pillar;
                System.out.println("Next Pillar: " + pillar);
            } else {
                pillar.setVisible(false);
                System.out.println("Hiding Pillar: " + pillar);
            }
        }

        // Set positions for current and next pillars
        currentPillar.setLayoutX(cP);
        nextPillar.setLayoutX(nP);

        // Log the positions to ensure they are correct
        System.out.println("Current Pillar Position: X=" + currentPillar.getLayoutX() + ", Y=" + currentPillar.getLayoutY());
        System.out.println("Next Pillar Position: X=" + nextPillar.getLayoutX() + ", Y=" + nextPillar.getLayoutY());
    }

    // Method to move pillars based on character's walking distance
    public void movePillars(double walkdistance) {
        System.out.println("Harry's layout x: " + currentCharacter.getChar1().getLayoutX());
        System.out.println("Current pillar layoutX: " + currentPillar.getLayoutX());
        System.out.println("Next pillar layoutX: " + nextPillar.getLayoutX());

        if (currentCharacter.getChar1().getLayoutX() + walkdistance >= nextPillar.getLayoutX()) {
            System.out.println("Pillar difference: " + Math.abs(currentPillar.getFitWidth() - nextPillar.getFitWidth()));
            System.out.println("Reached second pillar");
            currentPillar.setVisible(false);
            currentPillar = nextPillar; // Update current pillar

            transitionTimeline = new Timeline(
                    new KeyFrame(Duration.millis(20), e -> {
                        System.out.println("Harry X: " + currentCharacter.getChar1().getX());
                        System.out.println("Next Pillar X: " + nextPillar.getLayoutX());

                        if (currentPillar.getFitWidth() > 50) {
                            if (currentPillar.getLayoutX() > 0) { // Check if pillar still needs to move
                                currentPillar.setLayoutX(currentPillar.getLayoutX() - 5);
                                currentCharacter.getChar1().setLayoutX(currentCharacter.getChar1().getLayoutX() - 5);
                                currentCharacter.getChar2().setLayoutX(currentCharacter.getChar2().getLayoutX() - 5);
                            } else {
                                transitionTimeline.stop();
                                System.out.println("Current pillar: " + currentPillar.getLayoutX());
                                System.out.println("Current pillar width: " + currentPillar.getFitWidth());
                                stick.setStartX(-303 + currentPillar.getFitWidth());
                                stick.setEndX(-303 + currentPillar.getFitWidth());

                                nextPillarTransition();
                            }
                        } else {
                            double randomX = Math.random() * 10 + 50;
                            if (currentPillar.getLayoutX() > randomX) {
                                currentPillar.setLayoutX(currentPillar.getLayoutX() - 5);
                                currentCharacter.getChar1().setLayoutX(currentCharacter.getChar1().getLayoutX() - 5);
                                currentCharacter.getChar2().setLayoutX(currentCharacter.getChar2().getLayoutX() - 5);
                                currentCharacter.getCharNstick1().setLayoutX(currentPillar.getLayoutX() - 30);
                                currentCharacter.getCharNstick2().setLayoutX(currentPillar.getLayoutX() - 30);
                            } else {
                                transitionTimeline.stop();
                                System.out.println("Current pillar: " + currentPillar.getLayoutX());
                                System.out.println("Current pillar width: " + currentPillar.getFitWidth());
                                stick.setStartX(-307 + currentPillar.getFitWidth() + randomX);
                                stick.setEndX(-307 + currentPillar.getFitWidth() + randomX);

                                nextPillarTransition();
                            }
                        }
                    })
            );
            transitionTimeline.setCycleCount(Timeline.INDEFINITE);
            transitionTimeline.play();
        }
    }

    // Method to transition to the next pillar
    public void nextPillarTransition() {
        if (transitionTimeline != null) {
            transitionTimeline.stop();
        }

        ImageView previousNextPillar = nextPillar;

        do {
            int randomIndex = random.nextInt(pillars.size());
            nextPillar = pillars.get(randomIndex);
        } while (nextPillar == currentPillar || nextPillar == previousNextPillar);

        if (!nextPillar.isVisible()) {
            nextPillar.setVisible(true);
            double distance = random.nextDouble() * 200 + 150;
            double nextPillarX = currentPillar.getLayoutX() + distance;

            nextPillar.setLayoutX(nextPillarX);
            nextPillar.setLayoutY(currentPillar.getLayoutY());
        } else {
            double distance = random.nextDouble() * 200 + 100;
            double nextPillarX = currentPillar.getLayoutX() + distance;

            nextPillar.setLayoutX(nextPillarX);
            nextPillar.setLayoutY(currentPillar.getLayoutY());
        }

        // Position the Snitch when transitioning to the next pillar
        positionSnitch();
    }

    // Method to position the Snitch between the current and next pillars
    public void positionSnitch() {
        Snitch.setVisible(true);
        System.out.println(Snitch != null);

        // Check if Snitch is present and nextPillar is visible
        if (Snitch != null && nextPillar.isVisible()) {
            double currentPillarX = currentPillar.getLayoutX() + currentPillar.getFitWidth();
            double nextPillarX = nextPillar.getLayoutX();
            double snitchWidth = Snitch.getFitWidth();

            // Calculate a random position between the right edge of the current pillar and the left edge of the next pillar
            double randomOffset = currentPillarX + Math.random() * (nextPillarX - currentPillarX - snitchWidth);
            double Yoffset = Math.random() * 50;

            // Set Snitch position based on the calculated X coordinate and current pillar Y coordinate
            Snitch.setLayoutX(randomOffset);
        }
    }
}
