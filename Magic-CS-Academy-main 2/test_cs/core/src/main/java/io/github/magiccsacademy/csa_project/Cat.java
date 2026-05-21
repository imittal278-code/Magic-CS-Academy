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

public class Cat {
    float catx, caty;
    int lives;
    boolean alive;
    boolean hasShield;
    private int score;

    public Cat(float catx, float caty) {
        this.catx = catx;
        this.caty = caty;
        lives = 5;
        alive = true;
        hasShield = false; 
        score = 0;
    }
    void loseLife() {
        lives--;
        if (lives<=0) {
            alive = false;
        }
    }
    void gainLife() {
        lives++;
    }
    void addScore(int amount) {
        score+=amount;
    }
    int getScore() {
        return score;
    }
    boolean hasShield(){
        return hasShield;
    }
    boolean isAlive() {
        return alive;
    }
    float getX() {
        return catx;
    }
    void shieldOn(){
        hasShield = true;
    }
    void shieldOff(){
        hasShield = false; 
    }
    float getY() {
        return caty;
    }
    int getLives() {
        return lives;
    }
    void setPosition(float x, float y) {
        catx = x;
        caty = y;
    }

}
