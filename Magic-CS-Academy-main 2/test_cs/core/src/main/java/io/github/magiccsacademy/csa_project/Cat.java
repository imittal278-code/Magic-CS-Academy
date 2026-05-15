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
    Texture cat;
    Sprite cat2;
    boolean alive;

    public Cat() {
        catx = 2.5f;
        caty = 0.9765f;
        lives = 3;
        alive = true;
        cat = new Texture("Momo2023.png");
        cat2 = new Sprite(cat);
        cat2.setSize(1f, 1.047f);
    }
    void draw(SpriteBatch batch) {
        cat2.setPosition(catx, caty);
        cat2.draw(batch);
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
    boolean isAlive() {
        return alive;
    }
    float getX() {
        return catx;
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
