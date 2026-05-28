package io.github.magiccsacademy.csa_project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import java.util.*;
import org.javatuples.Pair;

/**
 * The class that manages the graphics on the screen during the game
 */
public class GameScreen extends InputAdapter implements Screen {


    /**
     * The Main class, used for transitioning screens and accessing some assets
     */
    private final Main game;

    /**
     * The uiViewport, used for the location of text
     */
    private Viewport uiViewport;

    /**
     * The map used to translate the shape number into an actual texture/image
     */
    private HashMap<Integer, Texture> map1;

    /**
     * The map used to convert the name of the shape to its respective number or color
     */
    private HashMap<String, Pair<Integer, Color>> map2;

    /**
     * The gameEngine that manages what level the player is on
     */
    private GameEngine controller;

    /**
     * Whether the level transition needs to be shown
     */
    private boolean showTransition;

    /**
     * The timer for the amount of time in transition
     */
    private float transitionTime;

    /**
     * The cat object for the player's lives, shields, etc.
     */
    private Cat c;

    /**
     * The texture for the background image
     */
    private Texture background;

    /**
     * The texture for the ghost image
     */
    private Texture ghost;

    /**
     * The sprite for the ghost Texture
     */
    private Sprite ghost2;

    /**
     * The Texture for the image of Mr. Fulk
     */
    private Texture fulkPic;

    /**
     * The sprite for the Mr. Fulk texture
     */
    private Sprite fulk;

    /**
     * The sprite for the fish texture
     */
    private Sprite fish;

    /**
     * The texture for the fish image
     */
    private Texture fishpic;

    /**
     * The texture for the shield ghost image
     */
    private Texture shieldGhostPic;
    /**
     * The sprite for the shield ghost texture
     */
    private Sprite shieldGhostSprite;

    /**
     * The texture for the cat image
     */
    private Texture cat;

    /**
     * The sprite for the cat texture
     */
    private Sprite cat2;

    /**
     * The texture for the heart image
     */
    private Texture heart;

    /**
     * The texture for the shield image
     */
    private Texture shield;

    /**
     * The Texture for the outline of the heart image
     */
    private Texture heartOutline;

    /**
     * The font used to display the text
     */
    private BitmapFont font;

    /**
     * The ArrayList used to draw the line on the screen
     */
    private ArrayList<Vector2> points;

    /**
     * Stores the points that are recognized as a shape
     */
    private Vector<Point> pts;

    /**
     * Whether the player is drawing
     */
    private boolean isDrawing = true;

    /**
     * The texture for the cat drawing circle
     */
    private Texture catCircle;

    /**
     * The timer for how long the cat changes
     */
    private float animationTimer = 0f;

    /**
     * The constant used for the duration of the animation
     */
    private final float ANIMATION_DURATION = 1.0f;

    /**
     * The renderer that draws the player's line on the screen
     */
    private ShapeRenderer shapeRenderer;

    /**
     * The recognizer that recognizes the player shape drawn
     */
    private Recognizer recognizer;

    /**
     * The color of the stroke the player makes (changes based on recognition)
     */
    private Color colorDrawing;

    /**
     * whether the game is currently paused
     */
    private boolean paused;

    /**
     * The texture for the pause image
     */
    private Texture pauseTexture;

    /**
     * The texture for the play image
     */
    private Texture playTexture;

    /**
     * The level objects for each level
     */
    private Level level1,level2,level3,level4,level5;;


    /**
     * Constructs a GameScreen object, initializing static fields
     * @param game the Main class used for textures and shifting screens.
     */
    public GameScreen(Main game) {
        this.game = game;
        uiViewport = new FitViewport(1600, 800);
        colorDrawing = Color.WHITE;
        paused = false;
        controller = new GameEngine();
        c = new Cat(2.6f, 1.1f);
        recognizer = new Recognizer();
        shapeRenderer = new ShapeRenderer();
        map1 = new HashMap<>();
        map2 = new HashMap<>();
        points = new ArrayList<Vector2>();
        pts = new Vector<Point>();
    }


    /**
     * Initializes textures, fonts, and fills up the fields initialized in the constructor
     */
    @Override
    public void show() {

        heart = new Texture("heart.png");
        heartOutline = new Texture("heart_outline.png");
        shield = new Texture("Shield.png");
        Gdx.input.setInputProcessor(this);
        ghost = new Texture("ghost2.png");
        ghost2 = new Sprite(ghost);
        shieldGhostPic = new Texture("ShieldSprite.png");
        shieldGhostSprite = new Sprite(shieldGhostPic);
        fulkPic = new Texture("Fulk.png");
        fulk = new Sprite(fulkPic);
        fishpic = new Texture("fish.png");
        fish = new Sprite(fishpic);
        cat = new Texture("Momo2023.png");
        cat2 = new Sprite(cat);
        font = new BitmapFont();
        font.getData().setScale(0.02f);
        font.setUseIntegerPositions(false);
        font.setColor(Color.ORANGE);
        background = game.getBackground();

        map1.put(0,game.getHorizontalLine());
        map1.put(1,game.getVerticalLine());
        map1.put(2,game.getNormalV());
        map1.put(3,game.getUpsideDownV());
        map1.put(4,game.getCircle());
        map2.put("horizontalLine", new Pair<Integer, Color>(0,Color.RED));
        map2.put("verticalLine", new Pair<Integer, Color>(1,Color.BLUE));
        map2.put("normalV", new Pair<Integer, Color>(2,Color.YELLOW));
        map2.put("upsideDownV", new Pair<Integer, Color>(3,Color.GREEN));
        map2.put("circle", new Pair<Integer, Color>(4,Color.CYAN));
        LevelSetup g = new LevelSetup(game);
        level1 = g.getLevel1();
        level2 = g.getLevel2();
        level3 = g.getLevel3();
        level4 = g.getLevel4();
        level5 = g.getLevel5();
        background = level1.getBackground();
        controller.addLevel(level1);
        controller.addLevel(level2);
        controller.addLevel(level3);
        controller.addLevel(level4);
        controller.addLevel(level5);
        showTransition = true;
        transitionTime = 2f;

        controller.getCurrentLevel().startLevel();
        pauseTexture = new Texture("pause.png");
        playTexture = new Texture("play.png");


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("comicsansmf.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 80;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(Color.ORANGE);
    }


    /**
     * Draws all the ghosts (and shapes) in the current ghostTurn. The current ghostTurn is accessed through the level.
     *
     * @param level the level to draw ghosts from
     */
    private void drawGhosts(Level level) {
        if (level.isCompleted()) {
            return;
        }
        Ghostturn currentTurn = level.getCurrentTurn();
        if (currentTurn.isCompletelyFinished()) {
            level.nextTurn();
            if (level.isCompleted()) {
                return;
            }
            currentTurn = level.getCurrentTurn();
        }
        float delta = Gdx.graphics.getDeltaTime();
        int numGhosts = currentTurn.getNumGhosts();
        for (int i = 0; i < numGhosts; i++) {
            Ghost g = currentTurn.getGhostspresent().get(numGhosts - i - 1);
            if (g.isDying()) {
                g.setDeathTimer(g.getDeathTimer()+delta);
                if (g.getDeathTimer() >= g.getDEATH_DURATION()) {
                    g.setDying(false);
                    continue;
                }
                Texture deathFrame;
                if (g.isFulk()) continue;
                else if (g.isFish()) {
                    deathFrame = (g.getDeathTimer() > g.getDEATH_DURATION() / 2f) ? game.getFishDeathFrame2() : game.getFishDeathFrame1();
                    float deathScale = 0.7f;
                    float xCent = g.getDeathX() - 0.02f;
                    float yCent = g.getDeathY() - 0.10f;
                    game.getBatch().draw(deathFrame, xCent, yCent,deathScale,deathScale);
                }
                else {
                    deathFrame = (g.getDeathTimer() > g.getDEATH_DURATION() / 2f) ? game.getGhostDeathFrame2() : game.getGhostDeathFrame1();
                    game.getBatch().draw(deathFrame, g.getDeathX(), g.getDeathY(), 0.6f, 0.666f);
                }
                continue;
            }
            if (g.isAlive()) {

                
                float x = currentTurn.getGhostx().get(numGhosts - i - 1);
                float y = currentTurn.getGhosty().get(numGhosts - i - 1);
                if(g.isFulk()){
                    fulk.setPosition(x, y);
                    fulk.setSize(0.58f, 0.58f);
                    fulk.draw(game.getBatch());
                }
                else if(g.isFish()){
                    fish.setPosition(x, y);
                    fish.setSize(0.7f, 0.25f);
                    fish.draw(game.getBatch());
                    int shapesLeft = g.getShapes().size();
                    float intitialpos = x-((float)shapesLeft/2)*0.15f+(shapesLeft%2==0?0.33f:0.32f);
                    for(int k = 0;k<shapesLeft;k++){
                        game.getBatch().draw(map1.get(g.getShapes().get(shapesLeft-k-1)),intitialpos+0.15f*k,y+0.3f,0.1f,0.1f);
                    }
                    continue;
                }
                else if (g.isShield()) {
                    shieldGhostSprite.setPosition(x, y);
                    shieldGhostSprite.setSize(0.46f, 0.5106f);
                    shieldGhostSprite.draw(game.getBatch());
                    int shapesLeft = g.getShapes().size();
                    game.getBatch().draw(game.getCircle(),x+0.13f,y+0.5f,0.2f,0.2f);
                    continue;
                }
                else{
                    ghost2.setPosition(x, y);
                    ghost2.setSize(0.6f, 0.666f);
                    ghost2.draw(game.getBatch());
                }

                int shapesLeft = g.getShapes().size();
                float intitialpos = x-((float)shapesLeft/2)*0.15f+(shapesLeft%2==0?0.33f:0.32f);
                for(int k = 0;k<shapesLeft;k++){
                    game.getBatch().draw(map1.get(g.getShapes().get(shapesLeft-k-1)),intitialpos+0.15f*k,y+0.6f,0.1f,0.1f);
                }
            }
        }

    }

    /**
     * Returns the recognizer that should be used right now (for example, if there are no circles left, it shouldn't recognize circles)
     *
     * @return the recognition that should be used at that current moment
     */
    private Recognizer createCurrentRecognizer() {
        Level current = controller.getCurrentLevel();
        boolean circle = current.getCircles();
        boolean upsideDownV = current.getUpsideDownVs();
        boolean normalV = current.getNormalVs();
        boolean horizontalLine = current.getHorizontalLines();
        boolean verticalLine = current.getVerticalLines();

        if (!circle && !upsideDownV && !normalV && !horizontalLine && !verticalLine) {
            return new Recognizer();
        }
        return new Recognizer(circle, upsideDownV, normalV, horizontalLine, verticalLine);
    }


    /**
     * Renders the image on the screen, using helper methods as necessary
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        background = controller.getCurrentLevel().getBackground();
         if(showTransition){
             transitionTime-=delta;
             ScreenUtils.clear(0,0,1,1);
             game.getMyViewport().apply();
             game.getBatch().setProjectionMatrix(game.getMyViewport().getCamera().combined);
             game.getBatch().begin();

             game.getBatch().draw(background, 0, 0,game.getMyViewport().getWorldWidth(),game.getMyViewport().getWorldHeight());

             game.getBatch().draw(game.getTransitionBackground().get(controller.getCurrentLevelNum()-1),-6f*transitionTime+6f,0f,6f,3.37f);
             game.getBatch().end();
            if(transitionTime<=0)showTransition = false;
            return;
         }


        ScreenUtils.clear(0, 0, 0, 1);
        game.getMyViewport().apply();
        

        game.getBatch().setProjectionMatrix(game.getMyViewport().getCamera().combined);

        game.getBatch().begin();

        game.getBatch().draw(background, 0, 0, game.getMyViewport().getWorldWidth(), game.getMyViewport().getWorldHeight());
        drawScore();

        if (!paused && animationTimer > 0) {
            animationTimer -= delta;
            if (animationTimer <= 0) c.setState(Cat.State.NORMAL);
        }
        Texture activeCatTexture = cat; 
        float catX = c.getX();
        float catY = c.getY();
        float finalWidth = 0.6f;
        float finalHeight = 0.6f;
        switch(c.getState()) {
            case HORIZONTAL:
                activeCatTexture = game.getCatHorizontal();
                finalWidth = 1.35f;
                finalHeight = 0.90f; 
                catX -= 0.375f;
                catY -= 0.165f;
                break;
            case VERTICAL:
                activeCatTexture = game.getCatVertical();
                finalWidth = 0.6f;
                finalHeight = 0.9f;
                catX += 0.0f;
                catY += -0.15f;
                break;
            case NORMAL_V:
                activeCatTexture = game.getCatNormalV();
                finalWidth = 1.2f;
                finalHeight = 0.80f;
                catX -= 0.30f;
                catY -= 0.115f;
                break;
            case UPSIDE_DOWN_V:
                activeCatTexture = game.getCatUpsideDownV();
                finalWidth = 0.70f;
                finalHeight = 0.96f;
                catX -= 0.035f;
                catY -= 0.15f;
                break;
            case CIRCLE:        activeCatTexture = catCircle; break;
            case NORMAL:        
            default:            activeCatTexture = cat; break;
        }
        cat2 = new Sprite(activeCatTexture);
        if(controller.getCurrentLevel() == level1) c.setPosition(2.6f,1.5f);
        else if(controller.getCurrentLevel() == level2) c.setPosition(0.2f, 1.1f);
        else if(controller.getCurrentLevel() == level3) c.setPosition(2.6f, 1.1f);
        else if(controller.getCurrentLevel() == level4) c.setPosition(2.6f, 1.5f);
        else c.setPosition(2.6f, 1.5f);
        cat2.setPosition(catX, catY);
        cat2.setSize(finalWidth, finalHeight);
        cat2.draw(game.getBatch());
        if (!paused) {
            controller.getCurrentLevel().update(delta, c);
        }
        if (c.hasShield()) {
            drawShield();
        }
         drawGhosts(controller.getCurrentLevel());
         drawHearts(c);
         drawPlayPause();


        game.getBatch().end();
        shapeRenderer.setProjectionMatrix(game.getMyViewport().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(colorDrawing);
        for (int i = 0; i < points.size() - 1; i++) {
            shapeRenderer.rectLine(points.get(i), points.get(i + 1), 0.05f);
        }
        if (isDrawing && points.size()>0) {
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(points.get(points.size()-1).x, points.get(points.size()-1).y, 0.04f, 24);
        }
        shapeRenderer.end();

        if (paused)drawPauseOverlay();

        if (!c.isAlive()) {
            this.dispose();
            game.setScreen(new EndScreen(game, c));
        }
        if (controller.getCurrentLevel().isCompleted()) {
            if (controller.doneWithLevels() && c.isAlive()) {
                this.dispose();
                game.setScreen(new EndScreen(game,c));
            }
            else if(!showTransition){
                showTransition = true;
                transitionTime = 2f;
                c.shieldOff();
                controller.nextLevel();
                if (controller.doneWithLevels() && c.isAlive()) {
                    this.dispose();
                    game.setScreen(new EndScreen(game, c));
                }
            }
        }


    }

    /**
     * Draws the shield at the cat's current position
     */
    private void drawShield() {
        game.getBatch().draw(shield, c.getX()-0.3f, c.getY()-0.3f, 1.2f, 1.2f);
    }

    /**
     * Draws the hearts in the corner of the screen based on the lives the cat has left
     * @param c the cat object for this game (contains info on lives)
     */
    private void drawHearts(Cat c) {
        int count = c.getLives();
        float adder = 0.2f;
        for(int i = 0;i<5;i++){
            if(count>0)game.getBatch().draw(heart,0f+adder,2.5f,0.3f,0.3f);
            else game.getBatch().draw(heartOutline,0f+adder,2.5f,0.3f,0.3f);
            adder+=0.3f;
            count--;
        }
    }


    /**
     * Draws the score in the corner of the screen, utilizing a different viewport and a FreeTypeFont
     */
    private void drawScore(){
        uiViewport.apply();
        game.getBatch().setProjectionMatrix(uiViewport.getCamera().combined);

        font.setColor(Color.ORANGE);
        font.getData().setScale(1f);
        font.draw(game.getBatch(), "" + c.getScore(), 1400, 750);
        game.getMyViewport().apply();
        game.getBatch().setProjectionMatrix(game.getMyViewport().getCamera().combined);
    }

    /**
     * Draws the play and pause buttons based on whether the game is currently running or paused
     */
    private void drawPlayPause(){
        Texture icon = (paused)?playTexture:pauseTexture;
        if(paused){
            game.getBatch().draw(icon, game.getMyViewport().getWorldWidth()-0.5f, 0.22f, 0.269f, 0.330f);
        }
        else{

            game.getBatch().draw(icon, game.getMyViewport().getWorldWidth()-0.7f, 0.1f, 0.6f, 0.6f);
        }
    }

    /**
     * Draws the image the player sees when the game is currently paused (with the text in the middle)
     */
    private void drawPauseOverlay(){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(game.getMyViewport().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f, 0f, 0f, 0.3f);
        shapeRenderer.rect(0, 0, game.getMyViewport().getWorldWidth(), game.getMyViewport().getWorldHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        uiViewport.apply();
        game.getBatch().setProjectionMatrix(uiViewport.getCamera().combined);
        game.getBatch().begin();
        font.getData().setScale(0.8f);
        GlyphLayout pausedLayout = new GlyphLayout(font, "Paused");
        float pausedX=(1600-pausedLayout.width)/2;
        float pausedY=470;
        font.setColor(Color.ORANGE);
        font.draw(game.getBatch(), "Paused", pausedX, pausedY);

        font.getData().setScale(1.2f);
        GlyphLayout resumeLayout = new GlyphLayout(font, "Resume");
        float resumeX=(1600-resumeLayout.width)/2;
        float resumeY=400;
        font.setColor(Color.WHITE);
        font.draw(game.getBatch(), "Resume", resumeX, resumeY);

        font.setColor(Color.ORANGE);
        font.getData().setScale(1f);
        game.getBatch().end();
        game.getMyViewport().apply();
        game.getBatch().setProjectionMatrix(game.getMyViewport().getCamera().combined);
        game.getBatch().begin();
        Texture icon=playTexture;
        game.getBatch().draw(icon, game.getMyViewport().getWorldWidth()-0.5f, 0.22f, 0.269f, 0.330f);
        game.getBatch().end();
    }


    /**
     * Resizes the screen based on the parameters
     *
     * @param width the width of the screen to resize to
     * @param height the height of the screen to resize to
     */
    @Override
    public void resize(int width, int height) {
        game.getStage().getViewport().update(width, height, true);
        uiViewport.update(width, height, true);
    }


    /**
     * Inherited method from the screen class that we are not using
     */
    @Override
    public void pause() {
    }

    /**
     * Inherited method from the screen class that we are not using
     */
    @Override
    public void resume() {
    }

    /**
     * Inherited method from the screen class that we are not using
     */
    @Override
    public void hide() {
    }

    /**
     * Inherited method from the screen class that we are not using
     */
    @Override
    public void dispose() {

    }

    /**
     * Inherited methods from the InputAdapter class that deal with whether the mouse is down (starts drawing points)
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button the button
     * @return always true in our case ( we need a boolean return to inherit from the libGDX class)
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        points.clear();
        pts.clear();
        Vector3 temp = new Vector3(screenX, screenY, 0);
        game.getMyViewport().unproject(temp);
        if (temp.x >= game.getMyViewport().getWorldWidth()-0.7f && temp.x <= game.getMyViewport().getWorldWidth() -0.1f && temp.y >= 0.1f && temp.y <=  0.7f) {
            paused = !paused;
            return true;
        }
        if (paused) return true;
        points.add(new Vector2(temp.x, temp.y));
        pts.add(new Point(temp.x, temp.y));
        isDrawing = true;
        colorDrawing = Color.WHITE;
        return true;

    }


    /**
     * Called when the mouse is dragged on the screen. In this method, we apply the recognizer live and change the color based on the current recognized stroke
     *
     * @param screenX the x location on the screen
     * @param screenY the y location on the screen
     * @param pointer the pointer for the event.
     * @return always true in our case ( we need a boolean return to inherit from the libGDX class)
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 temp = new Vector3(screenX, screenY, 0);
        game.getMyViewport().unproject(temp);
        if (isDrawing) {
            points.add(new Vector2(temp.x, temp.y));
            pts.add(new Point(temp.x, temp.y));

        }
        if(points.size()>10){
            recognizer = createCurrentRecognizer();
            Result r = recognizer.Recognize(pts);
            if (r != null && map2.containsKey(r.Name)&&r.Score>0.77) {
                if(r.Score>0.90 && (r.Name.equals("horizontalLine")||r.Name.equals("verticalLine"))){
                    if(Math.abs((points.get(points.size()-1).x-points.get(0).x)) > Math.abs(points.get(points.size()-1).y-points.get(0).y)){
                        if(controller.getCurrentLevel().getHorizontalLines()) {
                            colorDrawing = Color.RED;
                        }
                        else colorDrawing = Color.WHITE;
                    }
                    else{
                        if(controller.getCurrentLevel().getVerticalLines()) {
                            colorDrawing = Color.BLUE;
                        }
                        else colorDrawing = Color.WHITE;
                    }
                }
                else colorDrawing = map2.get(r.Name).getValue1();
            }
            else if(r != null && map2.containsKey(r.Name)&&r.Name.equals("circle")&&r.Score>0.70){
                colorDrawing = Color.CYAN;
            }
            else colorDrawing = Color.WHITE;

        }
        else if(points.size()>1){
            if(Math.abs((points.get(points.size()-1).x-points.get(0).x)) > Math.abs(points.get(points.size()-1).y-points.get(0).y)){
                if(controller.getCurrentLevel().getHorizontalLines()) {
                    colorDrawing = Color.RED;
                }
            }
            else{
                if(controller.getCurrentLevel().getVerticalLines()) {
                    colorDrawing = Color.BLUE;
                }
            }
        }
        return true;
    }


    /**
     * When a drag or touch is removed from the screen, this is called. Calls the shapeDrawn level method based on the shape drawn, and also updates the cat's costume
     *
     * @param screenX the x position of the screen
     * @param screenY the y position of the screen
     * @param pointer the pointer for the event.
     * @param button the button
     * @return false if the level is completed, otherwise true
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button){
        if(controller.getCurrentLevel().isCompleted()) return false;
        isDrawing = false;
        if(points.size()>10){
            recognizer = createCurrentRecognizer();
            Result r = recognizer.Recognize(pts);
            if (r != null && map2.containsKey(r.Name)&&r.Score>0.77) {
                if(r.Score>0.90 && (r.Name.equals("horizontalLine")||r.Name.equals("verticalLine"))){
                    if(Math.abs((points.get(points.size()-1).x-points.get(0).x)) > Math.abs(points.get(points.size()-1).y-points.get(0).y)){
                        if(controller.getCurrentLevel().getHorizontalLines()) {
                            controller.getCurrentLevel().shapeDrawn(0, c);
                            c.setState(Cat.State.HORIZONTAL);
                            animationTimer = ANIMATION_DURATION;
                        }

                    }
                    else{
                        if(controller.getCurrentLevel().getVerticalLines()) {
                            controller.getCurrentLevel().shapeDrawn(1, c);
                            c.setState(Cat.State.VERTICAL);
                            animationTimer = ANIMATION_DURATION;
                        }

                    }
                }
                else {
                    controller.getCurrentLevel().shapeDrawn(map2.get(r.Name).getValue0(), c);
                    int shapeCode = map2.get(r.Name).getValue0();
                    if(shapeCode == 2) c.setState(Cat.State.NORMAL_V);
                    if(shapeCode == 3) c.setState(Cat.State.UPSIDE_DOWN_V);
                    animationTimer = ANIMATION_DURATION;
                }
            }
            else if(r != null && map2.containsKey(r.Name)&&r.Name.equals("circle")&&r.Score>0.70) {
                 controller.getCurrentLevel().shapeDrawn(4, c);
            }

        }
        else if(points.size()>1){
            if(Math.abs((points.get(points.size()-1).x-points.get(0).x)) > Math.abs(points.get(points.size()-1).y-points.get(0).y)){
                if(controller.getCurrentLevel().getHorizontalLines()) {
                    controller.getCurrentLevel().shapeDrawn(0, c);
                    c.setState(Cat.State.HORIZONTAL);
                    animationTimer = ANIMATION_DURATION;
                }
            }
            else{
                if(controller.getCurrentLevel().getVerticalLines()) {
                    controller.getCurrentLevel().shapeDrawn(1, c);
                    c.setState(Cat.State.VERTICAL);
                    animationTimer = ANIMATION_DURATION;
                }
            }
        }

        return true;
    }
}
