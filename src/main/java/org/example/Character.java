package org.example;


import javafx.scene.image.Image;

//Singleton Class made for On character

public class Character extends AbstractGameObject {

    private static Character instance;

    private Image image;
    private String name;

    private Character(double x, double y, Image image, String name) {
        super(x, y);
        this.image = image;
        this.name = name;
    }

    public static Character getInstance(double x, double y, Image image, String name) {
        if (instance == null) {
            instance = new Character(x, y, image, name);
        }
        return instance;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void update() {
        // Update logic here
    }

    public void walk() {
        // Walking logic here
    }

    public void fall() {
        // Falling logic here
    }

    public void invert() {
        // Inverting logic here
    }

    public void collect() {
        // Collection logic here
    }
}
