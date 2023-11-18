package org.example;

import javafx.scene.canvas.GraphicsContext;

public class Stick extends AbstractGameObject {

    private double length;
    private Pillar pillar;

    public Stick(double x, double y, double length, Pillar pillar) {
        super(x, y);
        this.length = length;
        this.pillar = pillar;
    }

    @Override
    public void update() {
    }


    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public void draw(GraphicsContext gc) {
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

