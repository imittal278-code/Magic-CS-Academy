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

    /**
     * The SpriteBatch for the entire game, (used for drawing objects on the screen)
     */
    public SpriteBatch batch;

    /**
     * The stage for the entire game
     */
    public Stage stage;

    /**
     * The viewport used for the positioning of the entire game
     */
    public FitViewport myViewport;

    /**
     * The background music played during the game
     */
    public Music music;

    /**
     * The sound made when the ghost dies
     */
    public Music ghostdeath;

    /**
     * The sounds played for each stroke shape
     */
    public Sound sound1,sound2,sound3,sound4,sound5;

    /**
     * the background used for a level
     */
    public Texture background,ocean,desert,forest,circuit;
    /**
     * The CS classroom background
     */
    public Texture csclass;

    /**
     * Textures used for shapes
     */
    public Texture normalV,upsideDownV,verticalLine,horizontalLine,circle;

    /**
     * The texture used for the ghost that has a shield
     */
    public Texture shieldGhost;

    /**
     * An arraylist of the level transition rectangle the flies by for each level
     */
    public ArrayList<Texture> transitionBackground;

    /**
     * The texture for cat drawing horizontal line
     */
    public Texture catHorizontal;

    /**
     * The texture for cat drawing vertical line
     */
    public Texture catVertical;

    /**
     * The texture for cat drawing normal V
     */
    public Texture catNormalV;

    /**
     * The texture for cat drawing upside down V
     */
    public Texture catUpsideDownV;
    /**
     * The first death frame for ghost
     */
    public Texture ghostDeathFrame1;

    /**
     * The second death frame for ghost
     */
    public Texture ghostDeathFrame2;

    /**
     * The first death frame for fish
     */
    public Texture fishDeathFrame1;

    /**
     * The second death frame for fish
     */
    public Texture fishDeathFrame2;

    /**
     * Creates the game, initializes a lot of fields, turns on music, and switches to the homeScreen class
     */
    public void create(){
        batch = new SpriteBatch();
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        sound1 =  Gdx.audio.newSound(Gdx.files.internal("sound1.mp3"));
        sound2 =  Gdx.audio.newSound(Gdx.files.internal("sound2.mp3"));
        sound3 =  Gdx.audio.newSound(Gdx.files.internal("sound3.mp3"));
        sound4 =  Gdx.audio.newSound(Gdx.files.internal("sound4.mp3"));
        sound5 =  Gdx.audio.newSound(Gdx.files.internal("sound5.mp3"));
        catHorizontal = new Texture("horiz_cat.png");
        catVertical = new Texture("vert_cat.png");
        catNormalV = new Texture("v_cat.png");
        catUpsideDownV = new Texture("caret_cat.png");
        ghostDeathFrame1 = new Texture("ghost_death1.png");
        ghostDeathFrame2 = new Texture("ghost_death2.png");
        fishDeathFrame1 = new Texture("fish_death1.png");
        fishDeathFrame2 = new Texture("fish_death2.png");
        normalV = new Texture("normalV.png");
        upsideDownV = new Texture("upsideDownV.png");
        verticalLine = new Texture("verticalLine.png");
        horizontalLine = new Texture("horizontalLine.png");
        circle = new Texture("circle.png");
        shieldGhost = new Texture("ShieldSprite.png");
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
