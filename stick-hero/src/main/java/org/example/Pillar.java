package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Pillar extends AbstractGameObject {
    private double width;
    private double height;
    private double x;
    private double y;



    public Pillar(double width, double height, double x, double y) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    @Override
    public void update() {

    }

    void isPassed(){

    }

    public double getMidPoint(){
        return this.width / 2;
    }

    @Override
    public void draw(GraphicsContext gc) {

    }


}
