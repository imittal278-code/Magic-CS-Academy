package io.github.magiccsacademy.csa_project;
import java.util.*;
import java.lang.*;
import java.time.*;
import java.io.*;

/**
 * Manages the player's current level and handles level progression
 */
public class GameEngine {

    /**
     * The levels in the game
     */
    private ArrayList<Level> levels;

    /**
     * The current level index that the player is on (0-indexed)
     */
    private int curLevel;

    /**
     * Whether the player has completed all the levels
     */
    private boolean completed;


    /**
     * Constructs a GameEngine object, initializing fields
     * @param numLevels the number of levels in the game
     */
    public GameEngine(){
        this.curLevel = 0;
        completed = false;
        levels = new ArrayList<Level>(numLevels);
        //note that curLevel is zero indexed when stored in this class
    }

    /**
     * Adds a level to the controller
     * Used in initialization
     *
     * @param level the level to be added
     */
    public void addLevel(Level level){
        levels.add(level);
    }

    /**
     * Returns the current level object
     *
     * @return the current level
     */
    public Level getCurrentLevel(){
        return levels.get(curLevel);
    }

    /**
     * Returns the current level number, using 1-indexing.
     *
     * @return the current level number
     */
    public int getCurrentLevelNum(){
        return curLevel+1;
    }

    /**
     * Checks if the player is done with all levels
     *
     * @return whether the player is done with all levels
     */
    public boolean doneWithLevels(){
        return completed;
    }


    /**
     * Increments the current level index, progressing to the next level.
     */
    public void nextLevel(){
        if(curLevel>=levels.size()-1){
            completed = true;
        }
        else{
            curLevel++;
            levels.get(curLevel).startLevel();
        }
    }




}
