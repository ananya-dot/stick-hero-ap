package org.example;

public class Snitch implements Collectible{

    private int points;
    private double x;
    private double y;

    public Snitch(int points, double x, double y){
        this.points = points;
        this.x = x;
        this.y = y;
    }
    @Override
    public void collect() {

    }

    @Override
    public void applyEffect() {

    }
}
