package io.github.magiccsacademy.csa_project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
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
 * Displays the screen that a player sees first with the big center play button
 */
public class homeScreen implements Screen{

    /**
     * The shared game class (used for shifting screens or accessing some assets)
     */
    private final Main game; // no ghost or something should be present here, like it

    /**
     * The background of the home screen
     */
    private Texture background;

    /**
     * The texture that is initialized with game name
     */
    private Texture title;

    /**
     * The ghost image texture
     */
    private Texture ghost;

    /**
     *  The sprite for the ghost
     */
    private Sprite ghost2;

    /**
     * The texture for tha cat image
     */
    private Texture cat;

    /**
     * The sprite for the cat
     */
    private Sprite cat2;

    /**
     * The sprite for the play texture
     */
    private Sprite play;

    /**
     * The texture for the play image
     */
    private Texture playTexture;

    /**
     * The map used to connect a number to its respective shape
     */
    private HashMap<Integer,Texture> map;

    /**
     * The ghost object used for the right ghost on the home screen
     */
    private Ghost ghostright;

    /**
     * The ghost object used for the left ghost on the home screen
     */
    private Ghost ghostleft;

    /**
     * The amount of time that has passed used for the ghosts' shapes
     */
    private float timesum;

    /**
     * The last time the ghosts' shapes was reset
     */
    private float lasttime;

    /**
     * The sum of time used for the oscillating size of the play button
     */
    private float timesum2;


    /**
     * Constructs the home screen and initializes its main assets.
     *
     * @param game the shared Main object used for assets, drawing, and screen switching
     */
    public homeScreen(Main game){
        this.game = game;
        map = new HashMap<Integer,Texture>();
        map.put(0,game.horizontalLine);
        map.put(1,game.verticalLine);
        map.put(2,game.normalV);
        map.put(3,game.upsideDownV);
        map.put(4,game.circle);
        background = game.csclass;
        ghost = new Texture("ghost2.png");
        ghost2 = new Sprite(ghost);
        cat = new Texture("Momo2023.png");
        cat2 = new Sprite(cat);
        title = new Texture("Title.png");
        playTexture = new Texture("play.png");
        play = new Sprite(playTexture);
    }

    /**
     * Initializes the non-static fields
     */
    @Override
    public void show(){


        timesum = 0f;
        lasttime = 0f;
        timesum2 = 0f;
        ghostleft = new Ghost(4, 5);
        ghostright = new Ghost(4, 5);
    }


    /**
     * renders (or draws) the screen, and uses delta to control movements
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        timesum2+=delta;
        ScreenUtils.clear(0, 0, 0, 1);
        game.myViewport.apply();
        game.batch.setProjectionMatrix(game.myViewport.getCamera().combined);
        game.batch.begin();
        cat2.setPosition(4f, 0.75f);
        cat2.setSize(0.75f, 0.8325f);
        game.batch.setColor(0.4f, 0.4f, 0.4f, 1f);
        game.batch.draw(background, 0, 0,game.myViewport.getWorldWidth(),game.myViewport.getWorldHeight());
        game.batch.setColor(1f, 1f, 1f, 1f);
        game.batch.draw(title,1f,2f,4.29f,1f);
        drawGhost(ghostleft,0.1f,0.75f);
        drawGhost(ghostright,1f,0.75f);
        timesum+=delta;

        if(ghostleft.shapes.size()==0){
            timesum = 0f;
            lasttime = 0f;
            ghostleft = new Ghost(4,5);
            ghostright = new Ghost(4,5);
        }
        else{
            if(timesum-lasttime>=0.5f){
                timesum = lasttime;
                ghostleft.removeLast();
                ghostright.removeLast();
            }
        }

        cat2.draw(game.batch);
         Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
         game.myViewport.getCamera().unproject(mousePos);
         float scale = 1f + 0.1f * (float)Math.sin(timesum2 * 3f);
         float width = 0.8f * scale;
         float height = 0.976f * scale;
         float x = game.myViewport.getWorldWidth() / 2f - width / 2f+0.1f;
         float y = game.myViewport.getWorldHeight() / 2f - height / 2f-0.4f;
         play.setSize(width,height);
         play.setPosition(x,y);
         play.draw(game.batch);
         if (Gdx.input.justTouched()) {
             Vector3 touchPoint = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
             game.myViewport.getCamera().unproject(touchPoint);
             if (play.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)) {
                 this.dispose();
                 game.setScreen(new GameScreen(game));
             }
         }
         game.batch.end();
    }

    /**
     * Draws a ghost with the given parameters on the screen
     * @param g the ghost object to be drawn
     * @param x the x position of the ghost
     * @param y the y position of the ghost
     */
    private void drawGhost(Ghost g,float x,float y){
        ghost2.setPosition(x,y);
        ghost2.setSize(0.8f,0.8f);
        ghost2.draw(game.batch);
        int shapesLeft = g.shapes.size();
        if(shapesLeft%2==0){
            float intitialpos = x-(float)(shapesLeft/2)*0.15f+0.45f;
            for(int k = 0;k<shapesLeft;k++){
                game.batch.draw(map.get(g.shapes.get(shapesLeft-k-1)),intitialpos+0.15f*k,y+0.75f,0.1f,0.1f);
            }
        }
        else{
            float intitialpos = x-((float)shapesLeft/2)*0.15f+0.45f;
            for(int k = 0;k<shapesLeft;k++){
                game.batch.draw(map.get(g.shapes.get(shapesLeft-k-1)),intitialpos+0.15f*k,y+0.75f,0.1f,0.1f);
            }
        }
    }


    /**
     * Resizes the screen using the parameters
     * @param width the width of the screen to resize to
     * @param height the height of the screen to resize to
     */
    @Override public void resize(int width, int height) {
        game.stage.getViewport().update(width, height,true);
    }

    /**
     * Inherited method from the Screen interface that is not in use
     */
    @Override
    public void pause() {}

    /**
     * Inherited method from the Screen interface that is not in use
     */
    @Override
    public void resume() {
    }

    /**
     * Inherited method from the Screen interface that is not in use
     */
    @Override
    public void hide() {

    }

    /**
     * Inherited method from the Screen interface that is not in use
     */
    @Override
    public void dispose() {

    }
}
