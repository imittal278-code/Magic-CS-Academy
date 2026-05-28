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
import java.util.*;

/**
 * Manages location of the cat on the screen, its lives, score, and whether it has its shield
 */
public class Cat {

    /**
     * The x and y positions of the cat
     */
    float catx, caty;

    /**
     * The number of lives the player has
     */
    int lives;

    /**
     * whether the player is alive
     */
    boolean alive;

    /**
     * whether the cat has its shield
     */
    boolean hasShield;

    /**
     * The score the player currently has
     */
    private int score;

    public enum State { NORMAL, HORIZONTAL, VERTICAL, NORMAL_V, UPSIDE_DOWN_V, CIRCLE }
    private State currentState = State.NORMAL;

    /**
     * Constructs a cat object and initializes its fields
     *
     * @param catx the x position of the cat
     * @param caty the y position of the cat
     */
    public Cat(float catx, float caty) {
        this.catx = catx;
        this.caty = caty;
        lives = 5;
        alive = true;
        hasShield = false;
        score = 0;
    }

    /**
     * Decrements the cat's lives by 1
     */
    public void loseLife() {
        lives--;
        if (lives<=0) {
            alive = false;
        }
    }

    /**
     * Increments the player's score by the parameter
     *
     * @param amount the amount to increase the score by
     */
    void addScore(int amount) {
        score+=amount;
    }

    /**
     * Returns the player's current score
     *
     * @return the player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns whether the cat has its shield
     *
     * @return whether the cat has its shield
     */
    public boolean hasShield(){
        return hasShield;
    }

    /**
     * Returns whether the player still has hearts
     *
     * @return whether the cat is alive
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Returns the x position of the cat
     *
     * @return the x position of the cat
     */
    public float getX() {
        return catx;
    }

    /**
     * Turns the cat's shield on
     */
    public void shieldOn(){
        hasShield = true;
    }

    /**
     * Turns the cat's shield off
     */
    void shieldOff(){
        hasShield = false; 
    }

    /**
     * Returns the y position of the cat
     *
     * @return the y position of the cat
     */
    public float getY() {
        return caty;
    }

    /**
     * Returns the number of lives the player has remaining
     *
     * @return the number of lives the player has remaining
     */
    public int getLives() {
        return lives;
    }

    /**
     * Sets the cat's position to the given values
     *
     * @param x the x position of the cat
     * @param y the y position of the cat
     */
    void setPosition(float x, float y) {
        catx = x;
        caty = y;
    }

    /**
     * Sets the cat's current texture
     * 
     * @param state the new state of the cat's texture
     */
    public void setState(State state) {
        this.currentState = state;
    }

    /**
     * Returns the cat's current texture state
     * 
     * @return the cat's current texture state
     */
    public State getState() {
        return this.currentState;
    }

}
