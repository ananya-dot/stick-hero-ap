package org.example;

import javafx.scene.canvas.GraphicsContext;

public abstract class AbstractGameObject {
    protected double x;
    protected double y;

    public AbstractGameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Abstract method for updating the game object.
    public abstract void update();

    // Abstract method for drawing the game object.
    public abstract void draw(GraphicsContext gc);


}
