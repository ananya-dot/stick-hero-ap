package com.example.stickhero;

import javafx.scene.image.ImageView;

/**
 * The Characters class represents a game character with multiple states and images.
 */
public class Characters {

    // Fields representing the character's attributes and states
    private String name;
    private ImageView char1;
    private ImageView char2;
    private ImageView charNstick1;
    private ImageView charNstick2;

    private boolean ischar1Visible = true;
    private boolean isMoving = false;
    private boolean isCharJumping = false;
    private boolean upsideDown = false;

    private double totalrotation = 0;

    /**
     * Constructor for the Characters class.
     *
     * @param name The name of the character.
     * @param char1 The first image of the character walking.
     * @param char2 The second image of the character walking.
     * @param charNstick1 The first image of the character with a stick.
     * @param charNstick2 The second image of the character with a stick.
     */
    public Characters(String name, ImageView char1, ImageView char2, ImageView charNstick1, ImageView charNstick2) {
        this.name = name;
        this.char1 = char1;
        this.char2 = char2;
        this.charNstick1 = charNstick1;
        this.charNstick2 = charNstick2;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageView getChar1() {
        return char1;
    }

    public void setChar1(ImageView char1) {
        this.char1 = char1;
    }

    public ImageView getChar2() {
        return char2;
    }

    public void setChar2(ImageView char2) {
        this.char2 = char2;
    }

    public ImageView getCharNstick1() {
        return charNstick1;
    }

    public void setCharNstick1(ImageView charNstick1) {
        this.charNstick1 = charNstick1;
    }

    public ImageView getCharNstick2() {
        return charNstick2;
    }

    public void setCharNstick2(ImageView charNstick2) {
        this.charNstick2 = charNstick2;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean isCharJumping() {
        return isCharJumping;
    }

    public void setCharJumping(boolean charJumping) {
        isCharJumping = charJumping;
    }

    public boolean isIschar1Visible() {
        return ischar1Visible;
    }

    public void setIschar1Visible(boolean ischar1Visible) {
        this.ischar1Visible = ischar1Visible;
    }

    public boolean isUpsideDown() {
        return upsideDown;
    }

    /**
     * Flips the character's images upside down.
     */
    public void flipUpsideDown() {
        char1.setScaleY(-1);
        char1.setLayoutY(char1.getLayoutY() + char1.getFitHeight() - 10);
        char2.setScaleY(-1);
        char2.setLayoutY(char2.getLayoutY() + char2.getFitHeight() - 10);
        upsideDown = true;
    }

    /**
     * Reverts the character's images to their original orientation.
     */
    public void revertUpsideDown() {
        char1.setScaleY(1);
        char1.setLayoutY(char1.getLayoutY() - char1.getFitHeight() + 10);
        char2.setScaleY(1);
        char2.setLayoutY(char2.getLayoutY() - char2.getFitHeight() + 10);
        upsideDown = false;
    }
}
