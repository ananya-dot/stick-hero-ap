package com.example.stickhero;

import javafx.scene.image.ImageView;

/**
 * The Hermione class represents the Hermione character in the game.
 * It extends the Characters class and initializes with specific image views.
 */
public class Hermione extends Characters {

    /**
     * Constructor for Hermione class.
     *
     * @param charWalk1   ImageView for Hermione's walking animation frame 1
     * @param charWalk2   ImageView for Hermione's walking animation frame 2
     * @param charNstick1 ImageView for Hermione's standing with stick frame 1
     * @param charNstick2 ImageView for Hermione's standing with stick frame 2
     */
    public Hermione(ImageView charWalk1, ImageView charWalk2, ImageView charNstick1, ImageView charNstick2) {
        // Call the superclass constructor (Characters) with the character name and image views
        super("Hermione", charWalk1, charWalk2, charNstick1, charNstick2);
    }
}
