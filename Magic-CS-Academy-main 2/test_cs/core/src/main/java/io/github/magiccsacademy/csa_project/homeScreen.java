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

public class homeScreen implements Screen{
    private final Main game; // no ghost or something should be present here, like it 

    private float catx = 0.0f, caty = 1.0f;
    private ArrayList<Float> ghostx, ghosty;
    private Texture background;
    private Texture title;
    private Texture ghost;
    private Sprite ghost2;
    private Texture cat;
    private Sprite cat2;
    private Sprite play;
    private Texture playTexture;
    private float ghostSpeed = 0.2f; // the ghostSpeed should (1) not be constant b/c well have slower bosses, (2) should be time dependent instead.
    private BitmapFont font;
    private Texture circle;
    private HashMap<Integer,Texture> map;
    TextButton button;
    private Ghostturn turn;
    private Ghost ghostright;
    private Ghost ghostleft;
    private float timesum;
    private float lasttime;
    private float timesum2;

    public homeScreen(Main game){
        this.game = game;
        
    }

    @Override
    public void show(){
        //button = new TextButton("Click Me!", skin);
        circle = new Texture("circle.png");
        timesum=0f;
        map = new HashMap<Integer,Texture>();
        map.put(0,game.horizontalLine);
        map.put(1,game.verticalLine);
        map.put(2,game.normalV);
        map.put(3,game.upsideDownV);
        map.put(4,circle);
        lasttime=0f;
        background = game.csclass;
        ghost = new Texture("ghost2.png");
        ghost2 = new Sprite(ghost);
        cat = new Texture("Momo2023.png");
        cat2 = new Sprite(cat);
        ghostleft = new Ghost(4,5);
        ghostright = new Ghost(4,5);
        title = new Texture("Title.png");
        playTexture = new Texture("play.png");
        play = new Sprite(playTexture);
        timesum2 = 0f;
    }

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

    //helper method that draws a ghost
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



    @Override public void resize(int width, int height) {
        game.stage.getViewport().update(width, height,true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    
    @Override
    public void dispose() {
        background.dispose();
        title.dispose();
        ghost.dispose();
        cat.dispose();
    }
}
