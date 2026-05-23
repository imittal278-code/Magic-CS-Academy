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


/**
 * Represents a singular Level in the game, managing the ghostTurns in the level
 *
 */
public class Level {

    /**the number of this level (from 1-5)*/
    private int levelNumber;

    /** the difficulty of the level (which determines speed)*/
    private int difficulty;

    /** the current turn number that the level is on*/
    private int currentTurnIndex;

    /** whether the level is completed or not*/
    private boolean completed;

    /** the speeds of the ghosts in this level (determined by difficulty)*/
    private float ghostSpeed;

    /** the background for this level */
    private Texture background;

    /** an array of the ghostTurns in this level*/
    private ArrayList<Ghostturn> turns;

    /** the Main class, which contains some fields like sound and music that are important */
    private Main game;


    /**
     * Constructs a level using the parameters given and sets the background field based on the level number
     *
     * @param levelNumber the level number
     * @param difficulty  the difficulty of the level
     * @param game the Main class
     */
    public Level(int levelNumber, int difficulty, Main game) {
        this.levelNumber = levelNumber;
        this.difficulty = difficulty;
        this.currentTurnIndex = 0;
        this.completed = false;
        this.ghostSpeed = 0.2f+(difficulty*0.05f); //FIX THIS !!!!!!!!!
        this.turns = new ArrayList<>();
        this.game = game;








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



    /** initializes the spawning positions of all the ghosts in all the turns of this Level*/
    public void startLevel() {
        currentTurnIndex = 0;
        completed = false;
        for (int i=0; i<turns.size(); i++) {
            ghostPos(i);
        }
    }

    /**
     * Adds a GhostTurn to the level
     *
     * @param turn the ghostturn to add
     */
    public void addTurn(Ghostturn turn) {
        turns.add(turn);
    }
    public Ghostturn getCurrentTurn() {
        return turns.get(currentTurnIndex);
    }

    /**
     * Calls the ghostTurn shapeDrawn based on the index of the shape and plays the shapes' respective noise.
     * Also increments turn if the current turn is completed, and sets completed to true if the level is completed.
     *
     * @param shapeIndex the index of the shape to be removed from the ghosts
     * @param c the cat class value (used for lives)
     */
    public void shapeDrawn(int shapeIndex, Cat c) {
        if(shapeIndex==0){
            game.sound1.play(1.0f);
        }
        else if(shapeIndex==1){
            game.sound2.play(1.0f);
        }
        else if(shapeIndex==2){
            game.sound3.play(1.0f);
        }
        else if(shapeIndex==3){
            game.sound5.play(1.0f);
        }
        else if(shapeIndex==4){
            game.sound4.play(1.0f);
        }

        Ghostturn curr = turns.get(currentTurnIndex);
        curr.shapeDrawn(shapeIndex, c);
        if (!curr.isAlive()) {
            nextTurn();
            if (currentTurnFinished()) completed = true;
        }
    }


    /**
     * First checks if the turn is completed, and returns if so.
     * Then it changes the position of ghostx and ghosty for each ghost based on the speed (determined by delta time and difficulty) and location.
     * Checks whether the ghost is alive based on if it is too close to the cat and removes the ghost if it is not alive.
     * Also checks if the current turn is completed or if the level is completed and takes the appropriate actions based on that
     *
     * @param delta the change in time between the last frame and this frame
     * @param c the cat object (that manages lives)
     */
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
                    game.ghostdeath.play();
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

    /**
     * changes the current turn index to the next one
     */
    public void nextTurn() {
        currentTurnIndex++;
    }

    /**
     * checks if the current turn is done
     */
    public boolean currentTurnFinished() {
        return currentTurnIndex >= turns.size();
    }

    /**
     * checks if the level is completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * checks if you lost
     */
    public boolean isGameOver(Cat c) {
        return !c.isAlive();
    }

    /**
     * Retrieves the current level number
     *
     * @return the current level number
     */
    public int getLevelNumber() {
        return levelNumber;
    }

    /**
     * Retrieves the difficulty
     *
     * @return the difficulty of the level
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Retrieves the background
     *
     * @return the level's background
     */
    public Texture getBackground() {
        return background;
    }

    // **** NEED TO DETERMINE POSITIOSN OF TEH GHOSTS IN EACH TURN AND WAVE *****
    private Point intersectRay(float angle, float width, float height){
        double atan = Math.atan(width/height);
        if(angle > atan){
            //intersect with right edge
            return new Point(width, (float)(height/2.0f - (width/2.0f)/Math.tan(angle)));
        }
        else if (angle < -atan){
            //intersect with left edge
            return new Point(0f, (float)(height/2.0f + (width/2.0f)/Math.tan(angle)));
        }
        else{
            //intersect with bottom edge
            return new Point((float)(width/2.0f + (height/2.0f)*Math.tan(angle)), 0f);
        }
    }



    /**
     * Initializes spawning positions for the specified ghostTurn
     *
     * @param turnInd the ghostTurn# you are intializing the positions of
     */
    public void ghostPos(int turnInd) {
        Ghostturn turn = turns.get(turnInd);
        if(levelNumber==2){//FIX THIS !!!!!!!!!
            float adder = -1f;
            if(turn.ghostspresent.size()==1){
                turn.ghostx.add(6f);
                turn.ghosty.add(1.5f);
                return;
            }
            for (int i=0; i<turn.ghostspresent.size(); i++) {
                if(i%3==0)adder+=0.5f;
                turn.ghostx.add(6f+adder);
                turn.ghosty.add(0f+(i%4));
            }
            return;
        }
        else if(levelNumber != 5){
            float angle =(float)( Math.PI-Math.atan(5.2/2.2));//degrees
            float anglerange = 2*angle/(turn.ghostspresent.size());
            for (int i=0; i<turn.ghostspresent.size(); i++) {
                //choose a random float in range -angle+i*anglerange to -angle+(i+1)*anglerange
                float ghostAngle = (float)(Math.random()*(anglerange)+(-angle+(i*anglerange)));//Make 0.5 Math.random
                System.out.print(ghostAngle+" " );
                Point ghostPos = intersectRay(ghostAngle, 5.2f, 2.2f);
                turn.ghostx.add((float) ghostPos.X);
                turn.ghosty.add((float) ghostPos.Y);
                System.out.print(ghostAngle+" "+ghostPos.X+" "+ghostPos.Y+" ,");

            }
            System.out.println("");
        }
        else{

            //TANISH CODE THIS LATER!!!!
        }
    }
}
