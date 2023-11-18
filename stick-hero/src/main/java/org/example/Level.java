package org.example;

import java.util.ArrayList;

public class Level {
    private int currentLevel;
    private double width;
    private ArrayList<Pillar> pillarsInLevel;

    public Level(double width) {
        this.currentLevel = 1;
        this.width = width;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void increaseLevel() {
        currentLevel++;

    }

    public void restartLevel(){
    }

    public int noOfPillarsCovered(){
        return 0;
    }

    public void addPillar(){

    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public ArrayList<Pillar> getPillarsInLevel() {
        return pillarsInLevel;
    }

    public void setPillarsInLevel(ArrayList<Pillar> pillarsInLevel) {
        this.pillarsInLevel = pillarsInLevel;
    }

    public void removePillar(){

    }
}
