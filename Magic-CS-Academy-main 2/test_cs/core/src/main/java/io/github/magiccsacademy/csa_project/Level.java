package io.github.magiccsacademy.csa_project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import java.util.*;

public class Level {
    private int levelNumber;
    private int difficulty;
    private int currentTurnIndex;
    private boolean completed;
    private float ghostSpeed;
    private Texture background;
    private ArrayList<Ghostturn> turns;
    private Sound sound1 =  Gdx.audio.newSound(Gdx.files.internal("sound1.mp3"));
    private Sound sound2 =  Gdx.audio.newSound(Gdx.files.internal("sound2.mp3"));
    private Sound sound3 =  Gdx.audio.newSound(Gdx.files.internal("sound3.mp3"));
    private Sound sound4 =  Gdx.audio.newSound(Gdx.files.internal("sound4.mp3"));
    private Sound sound5 =  Gdx.audio.newSound(Gdx.files.internal("sound5.mp3"));
    Music music = Gdx.audio.newMusic(Gdx.files.internal("ghostdeath.mp3"));


    public Level(int levelNumber, int difficulty) {
        this.levelNumber = levelNumber;
        this.difficulty = difficulty;
        this.currentTurnIndex = 0;
        this.completed = false;
        this.ghostSpeed = 0.2f+(difficulty*0.05f); //FIX THIS !!!!!!!!!
        this.turns = new ArrayList<>();

        sound1 =  Gdx.audio.newSound(Gdx.files.internal("sound1.mp3"));
        sound2 =  Gdx.audio.newSound(Gdx.files.internal("sound2.mp3"));
        sound3 =  Gdx.audio.newSound(Gdx.files.internal("sound3.mp3"));
        sound4 =  Gdx.audio.newSound(Gdx.files.internal("sound4.mp3"));
        sound5 =  Gdx.audio.newSound(Gdx.files.internal("sound5.mp3"));







        // SET NUM BACkGROUNDS AND BACKGROUND IMAGE FOR EACH LEEVL
        if (levelNumber==1) {
            background = new Texture("background.png");
        }
        else if (levelNumber==2) {
            background = new Texture("ocean.jpg");
        }
        else if (levelNumber==3) {
            background = new Texture("desertBackground.jpg");
        }
        else if (levelNumber==4) {
            background = new Texture("forest.jpg");
        }
        else if (levelNumber==5) {
            background = new Texture("circuitBackground.jpg");
        }
    }

    public void startLevel() {
        currentTurnIndex = 0;
        completed = false;
        for (int i=0; i<turns.size(); i++) {
            ghostPos(i);
        }
    }
    public void addTurn(Ghostturn turn) {
        turns.add(turn);
    }
    public Ghostturn getCurrentTurn() {
        return turns.get(currentTurnIndex);
    }
    public void shapeDrawn(int shapeIndex, Cat c) {
        if(shapeIndex==0){
            sound1.play(1.0f);
        }
        else if(shapeIndex==1){
            sound2.play(1.0f);
        }
        else if(shapeIndex==2){
            sound3.play(1.0f);
        }
        else if(shapeIndex==3){
            sound5.play(1.0f);
        }
        else if(shapeIndex==4){
            sound4.play(1.0f);
        }

        Ghostturn curr = turns.get(currentTurnIndex);
        curr.shapeDrawn(shapeIndex, c);
        if (!curr.isAlive()) {
            nextTurn();
            if (currentTurnFinished()) completed = true;
        }
    }
    public void update(float delta, Cat c) {
        if (currentTurnIndex>=turns.size() || currentTurnFinished()) return;
        Ghostturn curr = turns.get(currentTurnIndex);
        ArrayList<Float> currentGhostX = turns.get(currentTurnIndex).ghostx;
        ArrayList<Float> currentGhostY = turns.get(currentTurnIndex).ghosty;
        for (int i=0; i<curr.ghostspresent.size(); i++) {
            Ghost ghost = curr.ghostspresent.get(i);
            if (ghost.isAlive()) {
                float dx = c.getX()-currentGhostX.get(i);
                float dy = c.getY()-currentGhostY.get(i);
                float distance = (float)Math.sqrt(dx*dx+dy*dy);
                if (distance>0.30f) {
                    float moveX = (dx/distance)*ghostSpeed*delta;
                    float moveY = (dy/distance)*ghostSpeed*delta;
                    currentGhostX.set(i, currentGhostX.get(i)+moveX);
                    currentGhostY.set(i, currentGhostY.get(i)+moveY);
                }
                else{
                    if(c.hasShield){
                        c.shieldOff();
                    }
                    else{
                        c.loseLife();
                    }
                    music.play();
                    ghost.remove();
                    curr.numAlive--;
                }
            }
        }
        if (!curr.isAlive()) {
            /*try{
            Thread.sleep(1000);
            }
            catch(InterruptedException e){

            }*/
            nextTurn();
            if (currentTurnFinished()) {
                completed = true;
                System.out.println("Level " + levelNumber + " Completed");
                currentTurnIndex = 0;
            }
        }
    }
    public void nextTurn() {
        currentTurnIndex++;
    }
    public boolean currentTurnFinished() {
        return currentTurnIndex >= turns.size();
    }
    public boolean isCompleted() {
        return completed;
    }
    
    public boolean isGameOver(Cat c) {
        return !c.isAlive();
    }
    public int getLevelNumber() {
        return levelNumber;
    }
    public int getDifficulty() {
        return difficulty;
    }
    
    public Texture getBackground() {
        return background;
    }

    // **** NEED TO DETERMINE POSITIOSN OF TEH GHOSTS IN EACH TURN AND WAVE *****

    public void ghostPos(int turnInd) {
        Ghostturn turn = turns.get(turnInd);
        if(levelNumber==2){
            float adder = -1f;
            float yadder=0f;
            for (int i=0; i<turn.ghostspresent.size(); i++) {
                if(i%3==0){
                    adder+=0.5f;
                    adder+=0.5f;
                }
                turn.ghostx.add(6f+adder);
                turn.ghosty.add(0f+(i%4)+(yadder%1f));
            }
            return;
        }
        float adder = -1f;
        for (int i=0; i<turn.ghostspresent.size(); i++) {
            if(i%6==0){
                adder++;
            }
            turn.ghostx.add(0f + i%6);
            turn.ghosty.add(0f-adder);
        }
    }
}
