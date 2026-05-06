package io.github.tanishkolhe.csa_project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    private SpriteBatch spriteBatch;
    FitViewport viewport;
    private Texture image;
    private Texture ghost;
    private Texture backgroundTexture;
    private Sprite ghost1;

    @Override
    public void create() {
        backgroundTexture = new Texture("cs classroom.jpg");
        ghost = new Texture("Ghost.png");
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(6, 3);
        ghost1 = new Sprite(ghost);
        
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        
        ghost1.draw(spriteBatch);
        float worldWidth = viewport.getWorldWidth();

        //the height of the viewport
        float worldHeight = viewport.getWorldHeight();
        spriteBatch.draw(backgroundTexture, 0, 0,worldWidth,worldHeight);
        
        
        
        

        spriteBatch.end();
    }

    private void logic() {
        
    }

    private void input() {
        
    }

    @Override
    public void dispose() {
        
    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){

    }

    @Override
    public void resize(int width,int height){
        viewport.update(width, height,true);
    }
}
