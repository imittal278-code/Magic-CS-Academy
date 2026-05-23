package io.github.magiccsacademy.csa_project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public SpriteBatch batch;
    public Stage stage;
    public FitViewport myViewport;
    public Music music;
    public Music ghostdeath;
    public Sound sound1;
    public Sound sound2;
    public Sound sound3;
    public Sound sound4;
    public Sound sound5;


    public void create(){
        batch = new SpriteBatch();
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        sound1 =  Gdx.audio.newSound(Gdx.files.internal("sound1.mp3"));
        sound2 =  Gdx.audio.newSound(Gdx.files.internal("sound2.mp3"));
        sound3 =  Gdx.audio.newSound(Gdx.files.internal("sound3.mp3"));
        sound4 =  Gdx.audio.newSound(Gdx.files.internal("sound4.mp3"));
        sound5 =  Gdx.audio.newSound(Gdx.files.internal("sound5.mp3"));
        ghostdeath = Gdx.audio.newMusic(Gdx.files.internal("ghostdeath.mp3"));
        music.setVolume(0.1f);
        music.setLooping(true);
        music.play();
        myViewport = new FitViewport(6, 3);
        stage = new Stage(myViewport);
        setScreen(new homeScreen(this));
        
        
    }
}
