package org.example;

public class Settings  {
    private int screenWidth;
    private int screenHeight;
    private boolean soundOn;

    public Settings(int screenWidth, int screenHeight, boolean soundOn) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.soundOn = soundOn;
    }


    public int getScreenWidth() {
        return screenWidth;
    }


    public int getScreenHeight() {
        return screenHeight;
    }


    public boolean isSoundOn() {
        return soundOn;
    }
}

