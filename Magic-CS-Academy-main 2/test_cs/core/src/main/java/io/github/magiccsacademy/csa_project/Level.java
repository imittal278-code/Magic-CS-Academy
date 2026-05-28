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
     * Whether the current turn still contains circle shapes.
     */
    private boolean circles;

    /**
     * Whether the current turn still contains horizontal line shapes.
     */
    private boolean horizontalLines;

    /**
     * Whether the current turn still contains vertical line shapes.
     */
    private boolean verticalLines;

    /**
     * Whether the current turn still contains normal V shapes.
     */
    private boolean normalVs;

    /**
     * Whether the current turn still contains upside-down V shapes.
     */
    private boolean upsideDownVs;


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
        this.ghostSpeed = 0.2f+(difficulty*0.05f);
        this.turns = new ArrayList<>();
        this.game = game;

        if (levelNumber==1) {
            background = game.getBackground();
        }
        else if (levelNumber==2) {
            background = game.getOcean();
        }
        else if (levelNumber==3) {
            background = game.getDesert();
        }
        else if (levelNumber==4) {
            background = game.getForest();
        }
        else if (levelNumber==5) {
            background = game.getCircuit();
        }

        circles = false;
        horizontalLines = false;
        verticalLines = false;
        normalVs = false;
        upsideDownVs = false;
    }



    /** initializes the spawning positions of all the ghosts in all the turns of this Level*/
    public void startLevel() {
        currentTurnIndex = 0;
        completed = false;
        updateCounts();
        for (int i=0; i<turns.size(); i++) {
            ghostPos(i);
        }
    }

    /**
     * Adds a GhostTurn to the level
     *
     * @param turn the ghostTurn to add
     */
    public void addTurn(Ghostturn turn) {
        turns.add(turn);
    }

    /**
     * Updates which shape types are still present in the current turn.
     */
    private void updateCounts() {

        if (turns.isEmpty() || currentTurnIndex >= turns.size()) {
            circles=false;
            horizontalLines = false;
            verticalLines = false;
            normalVs = false;
            upsideDownVs = false;
            return;
        }
        Ghostturn current = turns.get(currentTurnIndex);
        current.updateCounts();
        circles = current.getCircles()>0;
        horizontalLines = current.getHorizontalLines()>0;
        verticalLines = current.getVerticalLines()>0;
        normalVs = current.getNormalVs()>0;
        upsideDownVs = current.getUpsideDownVs()>0;
    }


    /**
     * Returns the current Ghostturn that is happening
     *
     * @return the ghostTurn object that is happening right now
     */
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
            game.getSound1().play(1.0f);
        }
        else if(shapeIndex==1){
            game.getSound2().play(1.0f);
        }
        else if(shapeIndex==2){
            game.getSound3().play(1.0f);
        }
        else if(shapeIndex==3){
            game.getSound5().play(1.0f);
        }
        else if(shapeIndex==4){
            game.getSound4().play(1.0f);
        }

        Ghostturn curr = turns.get(currentTurnIndex);
        if(curr.shapeDrawn(shapeIndex, c)) game.getGhostdeath().play(1.0f);
        updateCounts();
        if (curr.isCompletelyFinished()) {
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
        ArrayList<Float> currentGhostX = turns.get(currentTurnIndex).getGhostX();
        ArrayList<Float> currentGhostY = turns.get(currentTurnIndex).getGhostY();
        for (int i=0; i<curr.getGhostspresent().size(); i++) {
            Ghost ghost = curr.getGhostspresent().get(i);
            if (ghost.isAlive()) {
                if (ghost.isShield()) {
                    ghost.shieldPause(delta);
                    currentGhostY.set(i, 0.4f);
                    if (!ghost.isPausing()) {
                        float currentX = currentGhostX.get(i);
                        float nextX = currentX + (ghost.getHorizontalDirection() * 0.3f * delta);
                        currentGhostX.set(i, nextX);
                    }
                    if (currentGhostX.get(i) > 6.0f || currentGhostX.get(i) < -1.0f) {
                        ghost.remove();
                        curr.decrementNumAlive();
                        updateCounts();
                    }
                    continue;
                }
                float dx = c.getX()-currentGhostX.get(i);
                float dy = c.getY()-currentGhostY.get(i);
                float distance = (float)Math.sqrt(dx*dx+dy*dy);
                if (distance>0.30f) {
                    float updateSpeed = ghostSpeed*curr.getSpeedModifier();
                    float moveX = (dx/distance)*updateSpeed*delta;
                    float moveY = (dy/distance)*updateSpeed*delta;
                    currentGhostX.set(i, currentGhostX.get(i)+moveX);
                    currentGhostY.set(i, currentGhostY.get(i)+moveY);
                }
                else{
                    if(c.hasShield()){
                        c.shieldOff();
                    }
                    else{
                        c.loseLife();
                    }
                    game.getGhostdeath().play();
                    ghost.remove();
                    curr.decrementNumAlive();
                }
            }
        }
    }

    /**
     * Changes the current turn index to the next one
     */
    public void nextTurn() {
        currentTurnIndex++;
        if (currentTurnIndex >= turns.size()) {
            completed = true;
            currentTurnIndex = 0;
            updateCounts();
            return;
        }
        updateCounts();

    }

    /**
     * Checks if the current turn is done
     *
     * @return a boolean that checks if the current turn is done
     */
    public boolean currentTurnFinished() {
        return currentTurnIndex >= turns.size();
    }

    /**
     * Checks if the level is completed
     *
     * @return a boolean that checks if the level is completed
     */
    public boolean isCompleted() {
        return completed;
    }

  /**
     * Checks whether the player has lost.
     *
     * @param c the cat/player object
     * @return true if the cat is no longer alive, false otherwise
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

    /**
     * Finds where a ray at the given angle intersects the edge of the screen.
     *
     * @param angle the angle of the ray
     * @param width the width of the game world
     * @param height the height of the game world
     * @param wave how far outside the screen the ghost should spawn
     * @return the spawn position for the ghost
     */
    private Point intersectRay(float angle, float width, float height, double wave){
        double atan = Math.atan(width/height);
        wave = wave/2;
        if(angle > atan){
            return new Point(width+wave, (float)(height/2.0f - (width/2.0f)/Math.tan(angle)));
        }
        else if (angle < -atan){
            return new Point(-wave, (float)(height/2.0f + (width/2.0f)/Math.tan(angle)));
        }
        else{
            return new Point((float)(width/2.0f + (height/2.0f)*Math.tan(angle)), -wave);
        }
    }



    /**
     * Initializes spawning positions for the specified ghostTurn
     *
     * @param turnInd the ghostTurn# you are initializing the positions of
     */
    public void ghostPos(int turnInd) {

        Ghostturn turn = turns.get(turnInd);
        turn.getGhostX().clear();
        turn.getGhostY().clear();
        if(levelNumber==2){
            float adder = -1f;
            if(turn.getGhostspresent().size()==1){
                if (turn.getGhostspresent().get(0).isShield()) {
                    turn.getGhostY().add(-0.5f);
                    turn.getGhostY().add(0.2f);
                    turn.getGhostspresent().get(0).setHorizontalDirection(1.0f);
                } else {
                turn.getGhostX().add(6f);
                turn.getGhostY().add(1.5f);
                }
                return;
            }
            for (int i=0; i<turn.getGhostspresent().size(); i++) {
                if (turn.getGhostspresent().get(i).isShield()) {
                    turn.getGhostX().add(-0.5f);
                    turn.getGhostY().add(0.2f);
                    turn.getGhostspresent().get(i).setHorizontalDirection(1.0f);
                } else {
                if(i%3==0)adder+=0.5f;
                turn.getGhostX().add(6f+adder);
                turn.getGhostY().add(0f+(i%4)+adder);
                }
            }
            return;
        }
        else {
            float angle =(float)( Math.PI-Math.atan(5.2/2.2));
            float anglerange = 2*angle/(Math.min(9,turn.getGhostspresent().size()));
            for (int i=0; i<turn.getGhostspresent().size(); i++) {
                if (turn.getGhostspresent().get(i).isShield()) {
                    turn.getGhostX().add(-0.5f);
                    turn.getGhostY().add(0.2f);
                    turn.getGhostspresent().get(i).setHorizontalDirection(1.0f);
                } else {
                float ghostAngle = (float)(Math.random()*0.5*(anglerange)+(-angle+(((i%9)+0.25)*anglerange)));
                Point ghostPos = intersectRay(ghostAngle, 5.2f, 2.2f, (int)(i/8)*3.0);
                turn.getGhostX().add((float) ghostPos.X);
                turn.getGhostY().add((float) ghostPos.Y);
                }

            }
        }
    }

    /**
     * Whether there are still circles in some ghostTurns
     *
     * @return whether the player is allowed to draw a circle right now
     */
    public boolean getCircles(){return circles;}

    /**
     * Whether there are still horizontalLines in some ghostTurns
     *
     * @return whether the player is allowed to draw a horizontalLine right now
     */
    public boolean getHorizontalLines(){return horizontalLines;}

    /**
     * Whether there are still verticalLines in some ghostTurns
     *
     * @return whether the player is allowed to draw a verticalLine right now
     */
    public boolean getVerticalLines(){return verticalLines;}

    /**
     * Whether there are still normalVs in some ghostTurns
     *
     * @return whether the player is allowed to draw a normalV right now
     */
    public boolean getNormalVs(){return normalVs;}

    /**
     * Whether there are still upsideDownVs in some ghostTurns
     *
     * @return whether the player is allowed to draw an upsideDownV right now
     */
    public boolean getUpsideDownVs(){return upsideDownVs;}
}
