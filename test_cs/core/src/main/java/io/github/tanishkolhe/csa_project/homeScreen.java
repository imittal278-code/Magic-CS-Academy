package io.github.tanishkolhe.csa_project;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class homeScreen implements Screen{
    private final Main game;
    
    private Texture background;
    TextButton button;
    



    



    public homeScreen(Main game){
        this.game = game;
        
    }



    @Override
    public void show(){
        //button = new TextButton("Click Me!", skin);
        background = new Texture("csclassroom.jpg");
        
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
