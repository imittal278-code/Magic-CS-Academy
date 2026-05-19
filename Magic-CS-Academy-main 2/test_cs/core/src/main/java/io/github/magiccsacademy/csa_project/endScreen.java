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
    private Main game;
    private Cat cat;
    private boolean win;
    private Texture background;
    private Texture winText;
    private Texture loseText;

    public endScreen(Main game, Cat c){
        this.game = game;
        this.cat = c;
    }





    @Override
    public void show(){
        win = cat.isAlive();
        background = new Texture("csclassroom.jpg");
        winText = new Texture("WinText.png");
        loseText = new Texture("loseText.png");
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