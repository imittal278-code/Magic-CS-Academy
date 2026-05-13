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

public class homeScreen implements Screen{
    private final Main game;

    private float catx = 0.0f, caty = 1.0f;
    private ArrayList<Float> ghostx, ghosty;
    private Texture background;
    private Texture ghost;
    private Sprite ghost2;
    private Texture cat;
    private Sprite cat2;
    private float ghostSpeed = 0.2f; // the ghostSpeed should (1) not be constant b/c well have slower bosses, (2) should be time dependent instead.
    private BitmapFont font;
    private Texture verticalLine;
    private Texture horizontalLine;
    private Texture upsideDownV;
    private Texture normalV;
    private HashMap<Integer,Texture> map;
    TextButton button;
    private Ghostturn turn;
    



    



    public homeScreen(Main game){
        this.game = game;
        
    }



    @Override
    public void show(){
        //button = new TextButton("Click Me!", skin);
        normalV = new Texture("normalV.png");
        upsideDownV = new Texture("upsideDownV.png");
        verticalLine = new Texture("verticalLine.png");
        horizontalLine = new Texture("horizontalLine.png");
        
        map = new HashMap<Integer,Texture>();
        map.put(0,horizontalLine);
        map.put(1,verticalLine);
        map.put(2,normalV);
        map.put(3,upsideDownV);

        background = new Texture("csclassroom.jpg");
        ghost = new Texture("Ghost.png");
        ghost2 = new Sprite(ghost);
        cat = new Texture("Momo2023.png");
        cat2 = new Sprite(cat);
        font = new BitmapFont();
        font.getData().setScale(0.02f);
        font.setUseIntegerPositions(false);
        font.setColor(Color.YELLOW);
        turn = new Ghostturn(4, 3, 1, true);
        ghostx = new ArrayList<Float> ();
        ghosty = new ArrayList<Float>();
        for(int i=0;i<turn.ghostspresent.size();i++){
            ghosty.add(((float)i)*0.5f);
            ghostx.add(1.0f+i);
        }
    }



     @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);
        game.myViewport.apply();
        game.batch.setProjectionMatrix(game.myViewport.getCamera().combined);

        game.batch.setColor(0.4f, 0.4f, 0.4f, 1f);
        
        game.batch.begin();
        for(int i=0;i<10;i++){
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_0+i))turn.shapeDrawn(i);
        }

        cat2.setPosition(catx, caty);
        cat2.setSize(1f, 1.11f);

        game.batch.setColor(0.4f, 0.4f, 0.4f, 1f);
        game.batch.draw(background, 0, 0,game.myViewport.getWorldWidth(),game.myViewport.getWorldHeight());
        game.batch.setColor(1f, 1f, 1f, 1f);

        for(int i=0;i<turn.ghostspresent.size();i++){
            Ghost g=turn.ghostspresent.get(i);
            if(g.isAlive()){
                float dx = catx - ghostx.get(i);
                float dy = caty - ghosty.get(i);

                float distance = (float) Math.sqrt(dx*dx+dy*dy);
                if(distance>0.01f){
                    ghostx.set(i,ghostx.get(i)+(dx/distance)*ghostSpeed*delta); // delta is amt o/ time since last frame
                    ghosty.set(i,ghosty.get(i)+(dy/distance)*ghostSpeed*delta);
                }

                //helper method written below
                drawGhost(g,ghostx.get(i),ghosty.get(i));
                
                
            }
        }

        cat2.draw(game.batch);

        game.batch.end();
    }

    //helper method that draws a ghost
    private void drawGhost(Ghost g,float x,float y){
        ghost2.setPosition(x,y);
        ghost2.setSize(1f,1.11f);
        ghost2.draw(game.batch);
        int shapesLeft = g.shapes.size();
        if(shapesLeft%2==0){
            float intitialpos = x-(float)(shapesLeft/2)*0.15f+0.3f;
            for(int k = 0;k<shapesLeft;k++){
                game.batch.draw(map.get(g.shapes.get(k)),intitialpos+0.15f*k,y+0.75f,0.1f,0.1f);
            }
        }
        else{
            float intitialpos = x-((float)shapesLeft/2)*0.15f+0.3f;
            for(int k = 0;k<shapesLeft;k++){
                game.batch.draw(map.get(g.shapes.get(k)),intitialpos+0.15f*k,y+0.75f,0.1f,0.1f);
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
        
    }
}
