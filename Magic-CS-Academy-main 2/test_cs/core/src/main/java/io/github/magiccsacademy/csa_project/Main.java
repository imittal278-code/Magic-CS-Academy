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

import java.util.ArrayList;

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
    public Texture background;
    public Texture ocean;
    public Texture desert;
    public Texture forest;
    public Texture circuit;
    public Texture csclass;
    public Texture normalV;
    public Texture upsideDownV;
    public Texture verticalLine;
    public Texture horizontalLine;
    public Texture circle;
    public ArrayList<Texture> transitionBackground;


    public void create(){
        batch = new SpriteBatch();
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        sound1 =  Gdx.audio.newSound(Gdx.files.internal("sound1.mp3"));
        sound2 =  Gdx.audio.newSound(Gdx.files.internal("sound2.mp3"));
        sound3 =  Gdx.audio.newSound(Gdx.files.internal("sound3.mp3"));
        sound4 =  Gdx.audio.newSound(Gdx.files.internal("sound4.mp3"));
        sound5 =  Gdx.audio.newSound(Gdx.files.internal("sound5.mp3"));
        normalV = new Texture("normalV.png");
        upsideDownV = new Texture("upsideDownV.png");
        verticalLine = new Texture("verticalLine.png");
        horizontalLine = new Texture("horizontalLine.png");
        circle = new Texture("circle.png");
        background = new Texture("background.png");
        ocean= new Texture("ocean.jpg");
        desert = new Texture("desertBackground.jpg");
        forest = new Texture("forest.jpg");
        circuit = new Texture("circuitBackground.jpg");
        csclass = new Texture("csclassroom.jpg");
        ghostdeath = Gdx.audio.newMusic(Gdx.files.internal("ghostdeath.mp3"));
        transitionBackground = new ArrayList<Texture>(5);
        transitionBackground.add(new Texture("level1.png"));
        transitionBackground.add(new Texture("level2.png"));
        transitionBackground.add(new Texture("level3.png"));
        transitionBackground.add(new Texture("level4.png"));
        transitionBackground.add(new Texture("level5.png"));
        music.setVolume(0.1f);
        music.setLooping(true);
        music.play();
        myViewport = new FitViewport(6, 3);
        stage = new Stage(myViewport);
        setScreen(new homeScreen(this));
        
        
    }
}
