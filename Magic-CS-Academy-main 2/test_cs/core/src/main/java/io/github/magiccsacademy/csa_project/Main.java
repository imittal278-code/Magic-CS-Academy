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

/**
 * Main game class that loads shared assets, creates the viewport and stage,
 * starts the background music, and opens the home screen.
 */
public class Main extends Game {

    /**
     * The SpriteBatch for the entire game, (used for drawing objects on the screen)
     */
    private SpriteBatch batch;

    /**
     * The stage for the entire game
     */
    private Stage stage;

    /**
     * The viewport used for the positioning of the entire game
     */
    private FitViewport myViewport;

    /**
     * The background music played during the game
     */
    private Music music;

    /**
     * The sound made when the ghost dies
     */
    private Sound ghostdeath;

    /**
     * The sounds played for each stroke shape
     */
    private Sound sound1,sound2,sound3,sound4,sound5;

    /**
     * The background textures used for the different levels.
     */
    private Texture background,ocean,desert,forest,circuit;
    /**
     * The CS classroom background
     */
    private Texture csclass;

    /**
     * Textures used for shapes
     */
    private Texture normalV,upsideDownV,verticalLine,horizontalLine,circle;

    /**
     * The texture used for the ghost that has a shield
     */
    private Texture shieldGhost;

    /**
     * An arraylist of the level transition rectangle the flies by for each level
     */
    private ArrayList<Texture> transitionBackground;

    /**
     * The texture for cat drawing horizontal line
     */
    private Texture catHorizontal;

    /**
     * The texture for cat drawing vertical line
     */
    private Texture catVertical;

    /**
     * The texture for cat drawing normal V
     */
    private Texture catNormalV;

    /**
     * The texture for cat drawing upside down V
     */
    private Texture catUpsideDownV;
    /**
     * The first death frame for ghost
     */
    private Texture ghostDeathFrame1;

    /**
     * The second death frame for ghost
     */
    private Texture ghostDeathFrame2;

    /**
     * The first death frame for fish
     */
    private Texture fishDeathFrame1;

    /**
     * The second death frame for fish
     */
    private Texture fishDeathFrame2;

    /**
     * Creates the game by loading shared assets, starting the background music,
     * setting up the viewport and stage, and switching to the home screen.
     */
    @Override
    public void create() {
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
        ghostdeath = Gdx.audio.newSound(Gdx.files.internal("ghostdeath.mp3"));
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
        setScreen(new HomeScreen(this));
        
        
    }

    /**
     * The SpriteBatch for the entire game, (used for drawing objects on the screen)
     *
     * @return the game's SpriteBatch
     */
    public SpriteBatch getBatch() {
        return batch;
    }

    /**
     * The stage for the entire game
     *
     * @return the game's Stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * The viewport used for the positioning of the entire game
     *
     * @return the game's FitViewport
     */
    public FitViewport getMyViewport() {
        return myViewport;
    }

    /**
     * The background music played during the game
     *
     * @return the background Music object
     */
    public Music getMusic() {
        return music;
    }

    /**
     * The sound made when the ghost dies
     *
     * @return the ghost death Sound object
     */
    public Sound getGhostdeath() {
        return ghostdeath;
    }

    /**
     * The sound played for the first stroke shape
     *
     * @return the Sound object for sound1
     */
    public Sound getSound1() {
        return sound1;
    }

    /**
     * The sound played for the second stroke shape
     *
     * @return the Sound object for sound2
     */
    public Sound getSound2() {
        return sound2;
    }

    /**
     * The sound played for the third stroke shape
     *
     * @return the Sound object for sound3
     */
    public Sound getSound3() {
        return sound3;
    }

    /**
     * The sound played for the fourth stroke shape
     *
     * @return the Sound object for sound4
     */
    public Sound getSound4() {
        return sound4;
    }

    /**
     * The sound played for the fifth stroke shape
     *
     * @return the Sound object for sound5
     */
    public Sound getSound5() {
        return sound5;
    }

    /**
     * The default background texture used for levels.
     *
     * @return the background Texture
     */
    public Texture getBackground() {
        return background;
    }

    /**
     * The ocean background texture used for levels.
     *
     * @return the ocean background Texture
     */
    public Texture getOcean() {
        return ocean;
    }

    /**
     * The desert background texture used for levels.
     *
     * @return the desert background Texture
     */
    public Texture getDesert() {
        return desert;
    }

    /**
     * The forest background texture used for levels.
     *
     * @return the forest background Texture
     */
    public Texture getForest() {
        return forest;
    }

    /**
     * The circuit background texture used for levels.
     *
     * @return the circuit background Texture
     */
    public Texture getCircuit() {
        return circuit;
    }

    /**
     * The CS classroom background
     *
     * @return the CS classroom background Texture
     */
    public Texture getCsclass() {
        return csclass;
    }

    /**
     * Texture used for the normal V shape
     *
     * @return the normal V shape Texture
     */
    public Texture getNormalV() {
        return normalV;
    }

    /**
     * Texture used for the upside down V shape
     *
     * @return the upside down V shape Texture
     */
    public Texture getUpsideDownV() {
        return upsideDownV;
    }

    /**
     * Texture used for the vertical line shape
     *
     * @return the vertical line shape Texture
     */
    public Texture getVerticalLine() {
        return verticalLine;
    }

    /**
     * Texture used for the horizontal line shape
     *
     * @return the horizontal line shape Texture
     */
    public Texture getHorizontalLine() {
        return horizontalLine;
    }

    /**
     * Texture used for the circle shape
     *
     * @return the circle shape Texture
     */
    public Texture getCircle() {
        return circle;
    }

    /**
     * The texture used for the ghost that has a shield
     *
     * @return the shield ghost Texture
     */
    public Texture getShieldGhost() {
        return shieldGhost;
    }

    /**
     * An arraylist of the level transition rectangle the flies by for each level
     *
     * @return the list of transition background Textures
     */
    public ArrayList<Texture> getTransitionBackground() {
        return transitionBackground;
    }

    /**
     * The texture for cat drawing horizontal line
     *
     * @return the cat horizontal drawing Texture
     */
    public Texture getCatHorizontal() {
        return catHorizontal;
    }

    /**
     * The texture for cat drawing vertical line
     *
     * @return the cat vertical drawing Texture
     */
    public Texture getCatVertical() {
        return catVertical;
    }

    /**
     * The texture for cat drawing normal V
     *
     * @return the cat normal V drawing Texture
     */
    public Texture getCatNormalV() {
        return catNormalV;
    }

    /**
     * The texture for cat drawing upside down V
     *
     * @return the cat upside down V drawing Texture
     */
    public Texture getCatUpsideDownV() {
        return catUpsideDownV;
    }

    /**
     * The first death frame for ghost
     *
     * @return the first ghost death frame Texture
     */
    public Texture getGhostDeathFrame1() {
        return ghostDeathFrame1;
    }

    /**
     * The second death frame for ghost
     *
     * @return the second ghost death frame Texture
     */
    public Texture getGhostDeathFrame2() {
        return ghostDeathFrame2;
    }

    /**
     * The first death frame for fish
     *
     * @return the first fish death frame Texture
     */
    public Texture getFishDeathFrame1() {
        return fishDeathFrame1;
    }

    /**
     * The second death frame for fish
     *
     * @return the second fish death frame Texture
     */
    public Texture getFishDeathFrame2() {
        return fishDeathFrame2;
    }
}
