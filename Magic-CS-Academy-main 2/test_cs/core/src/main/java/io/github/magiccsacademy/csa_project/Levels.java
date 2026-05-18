package io.github.magiccsacademy.csa_project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
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

public class Levels {
    private final Main game;
    private int levelNumber;
    private int difficulty;
    private int currentTurnIndex;
    private boolean completed;
    private float ghostSpeed;
    private Texture background;
    private ArrayList<Ghostturn> turns;
    Music music = Gdx.audio.newMusic(Gdx.files.internal("ghostdeath.mp3"));


    public Levels(Main game, int levelNumber, int difficulty) {
        this.game = game;
        this.levelNumber = levelNumber;
        this.difficulty = difficulty;
        this.currentTurnIndex = 0;
        this.completed = false;
        this.ghostSpeed = 0.2f+(difficulty*0.05f);
        this.turns = new ArrayList<>();

        // SET NUM BACkGROUNDS AND BACKGROUND IMAGE FOR EACH LEEVL
        if (levelNumber==1) {
            background = new Texture("background.png");
        }
        else if (levelNumber==2) {
            //background = new Texture("level2.png");
        }
        else if (levelNumber==3) {
            //background = new Texture("level3.png");
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
    public void shapeDrawn(int shapeIndex) {
        Ghostturn curr = turns.get(currentTurnIndex);
        curr.shapeDrawn(shapeIndex);
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
                if (distance>0.70f) {
                    float moveX = (dx/distance)*ghostSpeed*delta;
                    float moveY = (dy/distance)*ghostSpeed*delta;
                    currentGhostX.set(i, currentGhostX.get(i)+moveX);
                    currentGhostY.set(i, currentGhostY.get(i)+moveY);
                }
                else{
                    c.loseLife();
                    music.play();
                    ghost.remove();
                    curr.numAlive--;
                    System.out.println("Ghost reached the cat! Life lost.");
                }
            }
        }
        if (!curr.isAlive()) {
            nextTurn();
            if (currentTurnFinished()) {
                completed = true;
                System.out.println("Level " + levelNumber + " Completed");
            }
        }
    }
    public void nextTurn() {

        if (currentTurnIndex>=turns.size()-1){
            completed = true;
        }
        else{
            currentTurnIndex++;
        }
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
        for (int i=0; i<turn.ghostspresent.size(); i++) {
            if (levelNumber==1) {
                if (turnInd==0) {
                    turn.ghostx.add(0f);
                    turn.ghosty.add(0f);
                    turn.ghostx.add(6f);
                    turn.ghosty.add(0f);
                    turn.ghostx.add(0f);
                    turn.ghosty.add(3f);
                    turn.ghostx.add(6f);
                    turn.ghosty.add(3f);
                }
                else if (turnInd==1) {
                    //turn.ghostx.add(5.0f+(i*0.5f));
                    turn.ghosty.add(5.0f);
                }
                else if (turnInd==2) {
                    //turn.ghostx.add(5.0f+(i*0.5f));
                    turn.ghosty.add(5.0f);
                }
            }
            else if (levelNumber==2) {
                if (turnInd==0) {
                    //turn.ghostx.add(5.0f+(i*0.5f));
                    //turn.ghosty.add(5.0f);
                }
                else if (turnInd==1) {
                    //turn.ghostx.add(5.0f+(i*0.5f));
                    turn.ghosty.add(5.0f);
                }
                else if (turnInd==2) {
                    //turn.ghostx.add(5.0f+(i*0.5f));
                    turn.ghosty.add(5.0f);
                }
            }
            else if (levelNumber==3) {
                if (turnInd==0) {
                    //turn.ghostx.add(5.0f+(i*0.5f));
                    //turn.ghosty.add(5.0f);
                }
                else if (turnInd==1) {
                    //turn.ghostx.add(5.0f+(i*0.5f));
                    turn.ghosty.add(5.0f);
                }
                else if (turnInd==2) {
                    //turn.ghostx.add(5.0f+(i*0.5f));
                    turn.ghosty.add(5.0f);
                }
            }
        }
    }
}
