package io.github.magiccsacademy.csa_project;
import java.util.*;
import java.lang.*;
import java.time.*;
import java.io.*;

/**
 * Used to manage which level the player is on, and switch levels
 */
public class GameEngine {

    /**
     * An arraylist of Levels in the game
     */
    public ArrayList<Level> levels;

    /**
     * The constant number of levels in the game
     */
    private final int numLevels;

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
    public GameEngine(int numLevels){
        this.numLevels = numLevels;
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
     * Returns the current level number
     * @return the current level # (1-5)
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
        if(curLevel>=numLevels-1){
            completed = true;
        }
        else{
            curLevel++;
            levels.get(curLevel).startLevel();
        }
    }




}
