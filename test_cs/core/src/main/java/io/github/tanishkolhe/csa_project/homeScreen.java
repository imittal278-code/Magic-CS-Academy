package io.github.tanishkolhe.csa_project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class homeScreen implements Screen{
    private final Main game;
    
    private Texture background;
    private Texture ghost;
    private Texture ghostflip;
    private Sprite ghost2;
    private Sprite ghost1;
    private Texture title;
    private Texture button;
    private Sprite click;
    private Sprite click2;
    private Texture button2;
    BitmapFont font;
    
    



    



    public homeScreen(Main game){
        this.game = game;
        
    }



    @Override
    public void show(){
        //button = new TextButton("Click Me!", skin);
        background = new Texture("csclassroom.jpg");
        ghost = new Texture("Ghost.png");
        ghost2 = new Sprite(ghost);
        ghostflip = new Texture("Ghostflip.png");
        ghost1 = new Sprite(ghostflip);
        font =  new BitmapFont();
        title = new Texture("title.png");
        button = new Texture("play.png");
        click = new Sprite(button);
        click.setPosition(250f, 100f);
        click.setSize(110, 50);
        button2 = new Texture("play2.png");
        click2 = new Sprite(button2);
        click2.setPosition(250f, 100f);
        click2.setSize(110, 50);
    }



     @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        game.myViewport.apply();
        game.batch.setProjectionMatrix(game.myViewport.getCamera().combined);

        

        game.batch.setColor(0.4f, 0.4f, 0.4f, 1f);
        
        game.batch.begin();






        
        game.batch.setColor(0.4f, 0.4f, 0.4f, 1f);
        game.batch.draw(background, 0, 0,game.myViewport.getWorldWidth(),game.myViewport.getWorldHeight());
        game.batch.setColor(1f, 1f, 1f, 1f);




        
        ghost1.setPosition(100f,110f);
        ghost1.setSize(100f, 111f);
        ghost1.draw(game.batch);
        ghost2.setPosition(400f,110f);
        ghost2.setSize(100f, 111f);
        ghost2.draw(game.batch);
        game.batch.draw(title, 120, 230);

        Vector2 touch = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        game.myViewport.unproject(touch);

        if (click.getBoundingRectangle().contains(touch.x, touch.y)) {
            click2.draw(game.batch);
        }
        else{
            click.draw(game.batch);
        }


        

        game.batch.end();


        
        
        






        
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
