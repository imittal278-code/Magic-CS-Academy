package io.github.magiccsacademy.csa_project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.*;

/**
 * The screen that a player sees after they either win or lose the game
 */
public class EndScreen implements Screen{

    /**
     * The shared Main class used for assets and switching screens
     */
    private final Main game;

    /**
     * Whether the player has won
     */
    private boolean win;

    /**
     * The score the player ended with
     */
    private String score;

    /**
     * The Texture for the background image
     */
    private Texture background;

    /**
     * The Texture for the home Image
     */
    private Texture homeIcon;

    /**
     * The texture for the replay image
     */
    private Texture replayIcon;

    /**
     * The sprite for the home texture
     */
    private Sprite home;

    /**
     * the sprite for the replay texture
     */
    private Sprite replay;

    /**
     * the font that writes text
     */
    private BitmapFont font;

    /**
     * The viewport for writing text
     */
    private Viewport uiViewport;

    /**
     * Constructs the EndScreen, initializing non-static fields and setting up the font
     * @param game the shared Main class
     * @param c the Cat from GameScreen
     */
    public EndScreen(Main game, Cat c){
        uiViewport = new FitViewport(1200,600);

        this.game = game;
        this.win = c.isAlive();
        this.score = String.valueOf(c.getScore());
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("comicsansmf.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 96;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(Color.WHITE);
    }


    /**
     * Sets up the Textures and Sprites in the screen
     */
    @Override
    public void show(){
        if(win){
            background = new Texture("winBackground.png");
        }
        else{
            background = new Texture("loseBackground.png");
        }

        homeIcon = new Texture("homeIcon.png");
        replayIcon = new Texture("replayIcon.png");
        home = new Sprite(homeIcon);
        replay = new Sprite(replayIcon);
    }


    /**
     * Draws the image onto the screen, using delta to control animations
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0, 1);
        game.getMyViewport().apply();
        game.getBatch().setProjectionMatrix(game.getMyViewport().getCamera().combined);


        game.getBatch().begin();


        game.getBatch().draw(background, 0, 0,game.getMyViewport().getWorldWidth(),game.getMyViewport().getWorldHeight());


        home.setSize(0.8f,0.8f);
        replay.setSize(0.8f,0.8f);
        replay.setPosition(3.3f,0.5f);
        home.setPosition(4.6f,0.5f);


        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.getMyViewport().getCamera().unproject(mousePos);




        home.setColor(1f, 1f, 1f, 1f);
        replay.setColor(1f, 1f, 1f, 1f);
        if (home.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
            home.setColor(0.5f, 0.5f, 0.5f, 1f);
            replay.setColor(Color.WHITE);
            if (Gdx.input.justTouched()) {
                game.setScreen(new HomeScreen(game));
            }
        }
        else if (replay.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
            replay.setColor(0.5f, 0.5f, 0.5f, 1f);
            home.setColor(Color.WHITE);
            if (Gdx.input.justTouched()) {
                game.setScreen(new GameScreen(game));
            }
        }
        else {
            home.setColor(Color.WHITE);
            replay.setColor(Color.WHITE);
        }
        home.draw(game.getBatch());
        replay.draw(game.getBatch());










        game.getBatch().end();
        drawScore();
    }


    /**
     * Draws the score on the screen
     */
    private void drawScore(){
        uiViewport.apply();
        game.getBatch().setProjectionMatrix(uiViewport.getCamera().combined);
        GlyphLayout layout = new GlyphLayout();
        String text = ""+score;
        layout.setText(font, text);

        float targetX = 880;
        game.getBatch().begin();
        font.draw(game.getBatch(), layout, targetX - (layout.width / 2), 400);
        game.getBatch().end();
    }


    /**
     * Resizes the screen to the new size
     * @param x the new width
     * @param y the new height
     */
    @Override
    public void resize(int x, int y){
        game.getStage().getViewport().update(x, y, true);
        uiViewport.update(x, y, true);
    }

    /**
     * Inherited method from the Screen interface that is not in use
     */
    @Override
    public void pause(){}

    /**
     * Inherited method from the Screen interface that is not in use
     */
    @Override
    public void resume(){}

    /**
     * Inherited method from the Screen interface that is not in use
     */
    @Override
    public void hide(){}

    /**
     * Inherited method from the Screen interface that is not in use
     */
    @Override
    public void dispose(){

    }
}
