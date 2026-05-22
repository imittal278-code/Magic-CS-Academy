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

public class endScreen implements Screen{
    private final Main game;
    private boolean win;
    private String score;
    private Texture background;
    private Texture homeIcon;
    private Texture replayIcon;
    private Texture Hover;
    private Texture nonHover;
    private Sprite home;
    private Sprite replay;
    private BitmapFont font;
    private Viewport uiViewport;

    public endScreen(Main game, Cat c){
        uiViewport = new FitViewport(600, 300);

        this.game = game;
        this.win = c.isAlive();
        this.score = String.valueOf(c.getScore());
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(Color.WHITE);
    }





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

    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0, 1);
        game.myViewport.apply();
        game.batch.setProjectionMatrix(game.myViewport.getCamera().combined);


        game.batch.begin();


        game.batch.draw(background, 0, 0,game.myViewport.getWorldWidth(),game.myViewport.getWorldHeight());


        home.setSize(0.8f,0.8f);
        replay.setSize(0.8f,0.8f);
        replay.setPosition(3.3f,0.5f);
        home.setPosition(4.6f,0.5f);


        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.myViewport.getCamera().unproject(mousePos);




        home.setColor(1f, 1f, 1f, 1f);
        replay.setColor(1f, 1f, 1f, 1f);
        if (home.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
            home.setColor(0.5f, 0.5f, 0.5f, 1f);
            replay.setColor(Color.WHITE);
            if (Gdx.input.justTouched()) {
                game.setScreen(new homeScreen(game));
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
        home.draw(game.batch);
        replay.draw(game.batch);










        game.batch.end();
        drawScore();
    }

    private void drawScore(){
        uiViewport.apply();
        game.batch.setProjectionMatrix(uiViewport.getCamera().combined);
        GlyphLayout layout = new GlyphLayout();
        String text = ""+score;
        layout.setText(font, text);

        float targetX = 440;
        game.batch.begin();
        font.draw(game.batch, layout, targetX - (layout.width / 2), 200);
        game.batch.end();
    }


    @Override
    public void resize(int x, int y){
        game.stage.getViewport().update(x, y, true);
        uiViewport.update(x, y, true);
    }

    @Override
    public void pause(){}

    @Override
    public void resume(){}

    @Override
    public void hide(){}

    @Override
    public void dispose(){

    }
}