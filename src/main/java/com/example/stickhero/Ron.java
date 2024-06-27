package com.example.stickhero;

import javafx.scene.image.ImageView;

/**
 * The Ron class represents the character Ron in the StickHero game.
 * It extends the Characters class and initializes the character-specific images.
 */
public class Ron extends Characters {

    /**
     * Constructor for the Ron class.
     *
     * @param charWalk1 the ImageView for Ron's first walking image
     * @param charWalk2 the ImageView for Ron's second walking image
     * @param charNstick1 the ImageView for Ron's first image with stick
     * @param charNstick2 the ImageView for Ron's second image with stick
     */
    public Ron(ImageView charWalk1, ImageView charWalk2, ImageView charNstick1, ImageView charNstick2) {
        // Call the superclass constructor with the character's name and images
        super("Ron", charWalk1, charWalk2, charNstick1, charNstick2);
    }
}
