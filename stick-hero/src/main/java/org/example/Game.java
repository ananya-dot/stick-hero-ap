package org.example;

import java.util.ArrayList;

public class Game {
    private int id;
    private Character character;
    private Level level;
    private Score score;

    public Game(int id, Character character, Level level, Score score){
        this.id = id;
        this.character = character;
        this.level = level;
        this.score = score;
    }

    public void restart(){

    }

    public void pause(){

    }

    public void resume(){

    }

    public void over(){

    }

    public void save(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
