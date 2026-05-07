package io.github.tanishkolhe.csa_project;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen
{
    private final Main game;
    
    private Texture background;
    private Texture ghost;
    private Sprite ghost2;
    TextButton button;
    



    



    public GameScreen(Main game){
        this.game = game;
        ghost = new Texture("Ghost.png");
        ghost2 = new Sprite(ghost);
    }



    @Override
    public void show(){
        
        ghost = new Texture("Ghost.png");
        ghost2 = new Sprite(ghost);
    }



     @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        game.myViewport.apply();
        game.batch.setProjectionMatrix(game.myViewport.getCamera().combined);

        

        game.batch.setColor(0.4f, 0.4f, 0.4f, 1f);
        
        game.batch.begin();
        ghost2.setPosition(2.5f,1.1f);
        ghost2.setSize(1f, 1.11f);

        game.batch.setColor(0.4f, 0.4f, 0.4f, 1f);
        game.batch.draw(background, 0, 0,game.myViewport.getWorldWidth(),game.myViewport.getWorldHeight());
        game.batch.setColor(1f, 1f, 1f, 1f);
        ghost2.draw(game.batch);

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
