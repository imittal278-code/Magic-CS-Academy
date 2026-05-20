package io.github.magiccsacademy.csa_project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private Texture background;
    private Texture winText;
    private Texture loseText;
    private Texture Hover;
    private Texture nonHover;
    private Sprite non;
    private Sprite hov;

    public endScreen(Main game, boolean win){
        this.game = game;
        this.win = win;
    }





    @Override
    public void show(){
        background = new Texture("csclassroom.jpg");
        winText = new Texture("WinText.png");
        loseText = new Texture("loseText.png");
        Hover = new Texture("Hover2.png");
        nonHover = new Texture("nonHover2.png");
        non = new Sprite(nonHover);
        hov = new Sprite(Hover);
    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0, 1);
        game.myViewport.apply();
        game.batch.setProjectionMatrix(game.myViewport.getCamera().combined);



        game.batch.begin();

        game.batch.setColor(0.4f, 0.4f, 0.4f, 1f);
        game.batch.draw(background, 0, 0,game.myViewport.getWorldWidth(),game.myViewport.getWorldHeight());
        game.batch.setColor(1f, 1f, 1f, 1f);


        if(win) {
            game.batch.draw(winText, 0f, -0.2f, 6f, 4f);
        }
        else{
            game.batch.draw(loseText, 0f, -0.2f, 6f, 4f);
        }


        non.setSize(2.7f,1.5f);
        hov.setSize(2.7f,1.5f);
        non.setPosition(1.6f,0f);
        hov.setPosition(1.6f,0f);
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.myViewport.getCamera().unproject(mousePos);

        if (hov.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
            hov.draw(game.batch);
            if(Gdx.input.justTouched()){
                this.dispose();
                game.setScreen(new GameScreen(game));
            }
        }
        else{
            non.draw(game.batch);
        }





        game.batch.end();
    }

    @Override
    public void resize(int x, int y){

    }

    @Override
    public void pause(){}

    @Override
    public void resume(){}

    @Override
    public void hide(){}

    @Override
    public void dispose(){
        background.dispose();
        winText.dispose();
    }
}