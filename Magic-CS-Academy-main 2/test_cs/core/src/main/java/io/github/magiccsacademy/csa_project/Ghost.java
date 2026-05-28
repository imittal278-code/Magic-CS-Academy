package io.github.magiccsacademy.csa_project;

import java.util.*;
import java.lang.*;

/**
 * The ghost class, that manages each individual ghost/moving sprite with shapes in the screen
 */
public class Ghost {
    /**
     * An arraylist of the shapes on top of the ghost (represented by a shape index 0-3)
     */
    private ArrayList<Integer> shapes;

    /**
     * The number of shapes to eliminate from the ghost
     */
    private int strlen;

    /**
     * whether this ghost object is Mr. Fulk
     */
    private boolean isFulk;

    /**
     * Whether this ghost object is a fish
     */
    private boolean isFish;

    /**
     * whether this ghost object is a ghost with a shield (circle)
     */
    private boolean isShield;

    /**
     * A timer used to manage the positioning of a ghost with a shield
     */
    private float stateTimer = 0;

    /**
     * Whether the ghost is still (only for shields)
     */
    private boolean isPausing = false;

    /**
     * The speed of the shield ghost in the horizontal direction
     */
    private float horizontalDirection = 1.0f;

    /**
     * Whether this ghost is currently dying
     */
    private boolean isDying = false;

    /**
     * A timer used to manage the death animation
     */
    private float deathTimer = 0f;

    /**
     * A constant for the length of the death animation
     */
    private final float DEATH_DURATION = 0.4f;

    /**
     * The location of the dead ghost
     */
    private float deathX, deathY;

    /**
     * whether the current ghost is alive
     */
    private boolean alive;

    /**
     * Constructs a ghost object with a defined number of random shapes.
     * isFulk, isFish, and isShield default to false
     *
     * @param len the number of shapes
     */
    public Ghost(int len){
        isFulk = false;
        isFish = false;
        isShield = false;
        alive = true;
        strlen = len;
        shapes = new ArrayList<Integer>();
        for(int i=0;i<strlen;i++){
            shapes.add((int) (4*Math.random()));
        }
    }

    /**
     * Constructs a ghost object based on the shapes in it.
     * Determines length using this string parameter
     * Defaults the booleans to false.
     *
     * @param s the shapes in this ghost
     */
    public Ghost (String s){
        isFulk = false;
        isFish = false;
        isShield = false;
        shapes = strtointarray(s.split(""));
        strlen = shapes.size();
        alive = true; 
    }

    /**
     * Constructs a ghost object based on the shapes in it and whether it is Mr. Fulk.
     * Sets the length based on the string parameter
     *
     * @param s the shapes in this ghost
     * @param fulk whether this ghost is Mr. Fulk
     */
    public Ghost (String s, boolean fulk){
        isFulk = fulk;
        isFish = false;
        isShield = false;
        shapes = strtointarray(s.split(""));
        strlen = shapes.size();
        alive = true;
    }


    /**
     * Helper method that converts an array of strings into an array of integers (the strings in this method are guaranteed to be integers)
     *
     * @param a The string arraylist to convert
     * @return an Integer arraylist of the same numbers given
     */
    private ArrayList<Integer> strtointarray(String[] a){
        ArrayList<Integer> ans = new ArrayList<Integer> ();
        for(String s : a){
            ans.add(Integer.parseInt(s));
        }
        return ans;
    }


    /**
     * Removes this ghost, setting alive to false
     */
    public void remove(){
        alive = false;
    }


    /**
     * Checks if the last shape in the arraylist is the target shape
     *
     * @param target the shape index to look for
     * @return whether the given shape index is the last shape
     */
    public boolean lastShapeEquals(int target){
        return (isAlive() && shapes.get(shapes.size()-1) == target);
    }

    /**
     * Removes the last element of the shapes array. If the array is already empty, it doesn't do anything
     */
    public void removeLast(){
        if (!shapes.isEmpty()) {
            shapes.remove(shapes.size() - 1);
        }
    }


    /**
     * Checks if this ghost is alive
     *
     * @return whether the ghost is alive
     */
    public boolean isAlive(){ return alive && !shapes.isEmpty(); }

    /**
     * Changes isPausing for this ghost based on time (only for shields)
     *
     * @param delta the delta time in between frames
     */
    public void shieldPause (float delta) {
        stateTimer += delta;
        if (isPausing && stateTimer >= 1.5f) {
            isPausing = false;
            stateTimer = 0;
        } else {
            if (stateTimer >= 2.0f) {
                isPausing = true;
                stateTimer = 0;
            }
        }
    }

    /**
     * Changes the field isFulk based on the parameter
     *
     * @param fulk whether it is Fulk
     */
    public void setFulk(boolean fulk){
        isFulk = fulk;
    }

    /**
     * Returns whether this ghost is a fish
     *
     * @return whether this ghost is a fish
     */
    public boolean isFish(){
        return isFish;
    }

    /**
     * Changes the field isFish based on the parameter
     *
     * @param fish whether it is a fish
     */
    public void setFish(boolean fish){
        isFish = fish;
    }

    /**
     * Returns whether this ghost is Mr. Fulk
     *
     * @return whether this ghost is Mr. Fulk
     */
    public boolean isFulk(){
        return isFulk;
    }

    /**
     * returns the x position of the ghost's death
     *
     * @return the x position of death
     */
    public float getDeathX(){
        return  deathX;
    }

    /**
     * returns the x position of the ghost's death
     *
     * @return the x position of death
     */
    public float getDeathY(){
        return deathY;
    }

    /**
     * Returns the number of shapes in the ghost
     *
     * @return the number of shapes in this ghost
     */
    public int getStrlen(){
        return strlen;
    }

    /**
     * Returns the value of the field horizontal direction
     *
     * @return the value of horizontal direction
     */
    public float getHorizontalDirection(){
        return horizontalDirection;
    }

    /**
     * Returns whether this ghost is a shield ghost
     *
     * @return whether this ghost is a shield ghost
     */
    public boolean isShield(){
        return isShield;
    }

    /**
     * Changes this ghost to make it either a shielded or non-shielded ghost
     *
     * @param shield whether this ghost will become a shield ghost
     */
    public void setShield(boolean shield){
        isShield = shield;
    }

    /**
     * Returns the duration of a death
     *
     * @return the duration of a death (in seconds)
     */
    public float getDEATH_DURATION(){
        return DEATH_DURATION;
    }

    /**
     * Returns whether this ghost is paused
     *
     * @return whether this ghost is paused (used for shields)
     */
    public boolean isPausing(){
        return isPausing;
    }

    /**
     * Sets the field horizontal direction to the parameter given
     *
     * @param dir the direction to set to
     */
    public void setHorizontalDirection(float dir){
        horizontalDirection = dir;
    }

    /**
     * Returns whether this ghost is dying
     *
     * @return whether this ghost is currently dying
     */
    public boolean isDying(){
        return isDying;
    }

    /**
     * sets the ghost to dying or not dying
     *
     * @param dying whether the ghost is dying
     */
    public void setDying(boolean dying){
        isDying = dying;
    }

    /**
     * Returns the value of the death timer
     *
     * @return the value of the death timer
     */
    public float getDeathTimer(){
        return deathTimer;
    }

    /**
     * Setes the death timer to a specified value
     *
     * @param time the value of the death timer to set to
     */
    public void setDeathTimer(float time){
        deathTimer = time;
    }


    /**
     * Sets up this ghost's parameters during death (prior to the death animation)
     *
     * @param x the x position of this players death
     * @param y the y position of this players death
     */
    public void deathAnimation(float x, float y) {
        this.alive = false;
        this.isDying = true;
        this.deathTimer = 0f;
        this.deathX = x;
        this.deathY = y;
    }

    /**
     * Returns the ArrayList of shapes on top of the ghost.
     *
     * @return an ArrayList of integers representing the shape indices
     */
    public ArrayList<Integer> getShapes() {
        return shapes;
    }
}
