package org.example;

public class Score {
    private int currentScore;
    private int bestScore;

    public Score() {
        this.currentScore = 0;
        this.bestScore = 0;
    }

    public void increaseScore() {
        currentScore++;
        if (currentScore > bestScore) {
            bestScore = currentScore;
        }
    }

    public void decreaseScore(){

    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void resetScore() {
        currentScore = 0;
    }
}

