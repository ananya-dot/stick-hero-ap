package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Character extends AbstractGameObject{

    private Image image;
    private String name;

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

    public Character(double x, double y, Image image, String name) {
        super(x, y);
        this.image = image;
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


    @Override
    public void draw(GraphicsContext gc) {
    }
}
