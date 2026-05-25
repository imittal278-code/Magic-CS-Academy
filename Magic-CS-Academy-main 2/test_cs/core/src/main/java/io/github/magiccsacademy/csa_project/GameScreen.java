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

public class GameScreen extends InputAdapter implements Screen {
    private final int numLevels = 5;
    private final Main game;
    private Viewport uiViewport;

    //shape declarations



    private HashMap<Integer, Texture> map1;
    private HashMap<String, Pair<Integer, Color>> map2;
    private GameEngine controller;
    private boolean showTransition;
    private float transitionTime;
    private Cat c;
    private Texture background;
    private Texture ghost;
    private Sprite ghost2;
    private Texture fulkPic;
    private Sprite fulk;
    private Sprite fish;
    private Texture fishpic;
    private Texture cat;
    private Sprite cat2;
    private Texture heart;
    private Texture shield;
    private Texture heartOutline;
    private BitmapFont font;
    TextButton button;
    private ArrayList<Vector2> points = new ArrayList<Vector2>();
    private Vector<Point> pts = new Vector<Point>();
    private boolean isDrawing = true;
    private ShapeRenderer shapeRenderer;
    private Recognizer recognizer;
    private Color colorDrawing;
    private boolean paused;
    private Texture pauseTexture;
    private Texture playTexture;
    private Level level1;
    private Level level2;
    private Level level3;
    private Level level4;
    private Level level5;

    public GameScreen(Main game) {
        this.game = game;

    }


    @Override
    public void show() {
        uiViewport = new FitViewport(1600, 800);
        colorDrawing = Color.WHITE;
        paused = false;
        controller = new GameEngine(numLevels);
        c = new Cat(2.6f, 1.1f);
        recognizer = new Recognizer();
        heart = new Texture("heart.png");
        heartOutline = new Texture("heart_outline.png");
        shield = new Texture("Shield.png");
        Gdx.input.setInputProcessor(this);
        shapeRenderer = new ShapeRenderer();
        ghost = new Texture("ghost2.png");
        ghost2 = new Sprite(ghost);
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
        background = game.background;

        map1 = new HashMap<Integer,Texture>();
        map2 = new HashMap<String, Pair<Integer, Color>>();
        map1.put(0,game.horizontalLine);
        map1.put(1,game.verticalLine);
        map1.put(2,game.normalV);
        map1.put(3,game.upsideDownV);
        map1.put(4,game.circle);
        map2.put("horizontalLine", new Pair<Integer, Color>(0,Color.RED));
        map2.put("verticalLine", new Pair<Integer, Color>(1,Color.BLUE));
        map2.put("normalV", new Pair<Integer, Color>(2,Color.YELLOW));
        map2.put("upsideDownV", new Pair<Integer, Color>(3,Color.GREEN));
        map2.put("circle", new Pair<Integer, Color>(4,Color.CYAN));
        LevelSetup g = new LevelSetup(game);
        level1 = g.l1;
        level2 = g.l2;
        level3 = g.l3;
        level4 = g.l4;
        level5 = g.l5;
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

        //font setup stuff dont worry about the red errors, they dont matter
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("comicsansmf.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 80;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(Color.ORANGE);
    }


    //helper method that draws a ghost
    private void drawGhosts(Level level) {
        if (level.isCompleted()) {
            return;
        }
        int numGhosts = level.getCurrentTurn().numGhosts;
        for (int i = 0; i < numGhosts; i++) {
            if (level.getCurrentTurn().ghostspresent.get(numGhosts - i - 1).isAlive()) {

                Ghost g = level.getCurrentTurn().ghostspresent.get(numGhosts - i - 1);
                float x = level.getCurrentTurn().ghostx.get(numGhosts - i - 1);
                float y = level.getCurrentTurn().ghosty.get(numGhosts - i - 1);
                if(g.isFulk){
                    fulk.setPosition(x, y);
                    fulk.setSize(0.58f, 0.58f);
                    fulk.draw(game.batch);
                }
                else if(g.isFish){
                    fish.setPosition(x, y);
                    fish.setSize(0.7f, 0.25f);
                    fish.draw(game.batch);
                    int shapesLeft = g.shapes.size();
                    float intitialpos = x-((float)shapesLeft/2)*0.15f+(shapesLeft%2==0?0.33f:0.32f);
                    for(int k = 0;k<shapesLeft;k++){
                        game.batch.draw(map1.get(g.shapes.get(shapesLeft-k-1)),intitialpos+0.15f*k,y+0.3f,0.1f,0.1f);
                    }
                    continue;
                }
                else{
                    ghost2.setPosition(x, y);
                    ghost2.setSize(0.6f, 0.666f);
                    ghost2.draw(game.batch);
                }

                int shapesLeft = g.shapes.size();
                float intitialpos = x-((float)shapesLeft/2)*0.15f+(shapesLeft%2==0?0.33f:0.32f);
                for(int k = 0;k<shapesLeft;k++){
                    game.batch.draw(map1.get(g.shapes.get(shapesLeft-k-1)),intitialpos+0.15f*k,y+0.6f,0.1f,0.1f);
                }
            }
        }

    }

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


    @Override
    public void render(float delta) {
        //keep this code at the top
        background = controller.getCurrentLevel().getBackground();
         if(showTransition){
             transitionTime-=delta;
             ScreenUtils.clear(0,0,1,1);
             game.myViewport.apply();
             game.batch.setProjectionMatrix(game.myViewport.getCamera().combined);
             game.batch.begin();

             game.batch.draw(background, 0, 0,game.myViewport.getWorldWidth(),game.myViewport.getWorldHeight());

             game.batch.draw(game.transitionBackground.get(controller.getCurrentLevelNum()-1),-6f*transitionTime+6f,0f,6f,3.37f);
             game.batch.end();
            if(transitionTime<=0)showTransition = false;
            return;
         }

        //set background based on current Level

        ScreenUtils.clear(0, 0, 0, 1);
        game.myViewport.apply();
        
        //Gdx.app.log("VIEWPORT", "worldW=" + game.myViewport.getWorldWidth() + ", worldH=" + game.myViewport.getWorldHeight()); //REMOVE
        //Gdx.app.log("", "piSCREENxelsW=" + Gdx.graphics.getWidth() + ", pixelsH=" + Gdx.graphics.getHeight()); //REMOVE
        game.batch.setProjectionMatrix(game.myViewport.getCamera().combined);

        game.batch.begin();

        //draw the background first
        game.batch.draw(background, 0, 0, game.myViewport.getWorldWidth(), game.myViewport.getWorldHeight());
        drawScore();

        //set cat's position and size and draw it
        cat2 = new Sprite(cat);
        if(controller.getCurrentLevel() == level1) c.setPosition(2.6f,1.5f);
        else if(controller.getCurrentLevel() == level2) c.setPosition(0.2f, 1.1f);
        else if(controller.getCurrentLevel() == level3) c.setPosition(2.6f, 1.1f);
        else if(controller.getCurrentLevel() == level4) c.setPosition(2.6f, 1.5f);
        //else c.setPosition();
        cat2.setPosition(c.getX(), c.getY());
        cat2.setSize(0.6f, 0.6f);
        cat2.draw(game.batch);
        if (!paused) {
            controller.getCurrentLevel().update(delta, c);
        }
        if (c.hasShield) {
            drawShield();
        }
         drawGhosts(controller.getCurrentLevel());
         drawHearts(c);
         drawPlayPause();


        game.batch.end();
        shapeRenderer.setProjectionMatrix(game.myViewport.getCamera().combined);
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

        //You lost
        if (!c.isAlive()) {
            this.dispose();
            game.setScreen(new endScreen(game, c));
        }
        if (controller.getCurrentLevel().isCompleted()) {
            if (controller.doneWithLevels() && c.isAlive()) {
                this.dispose();
                game.setScreen(new endScreen(game,c));
            }
            else if(!showTransition){
                showTransition = true;
                transitionTime = 2f;
                c.shieldOff();
                controller.nextLevel();
                if (controller.doneWithLevels() && c.isAlive()) {
                    this.dispose();
                    game.setScreen(new endScreen(game, c));
                }
            }
        }


    }

    private void drawShield() {
        game.batch.draw(shield, c.getX()-0.3f, c.getY()-0.3f, 1.2f, 1.2f);
    }

    private void drawHearts(Cat c) {
        int count = c.getLives();
        float adder = 0.2f;
        for(int i = 0;i<5;i++){
            if(count>0)game.batch.draw(heart,0f+adder,2.5f,0.3f,0.3f);
            else game.batch.draw(heartOutline,0f+adder,2.5f,0.3f,0.3f);
            adder+=0.3f;
            count--;
        }
    }

    private void drawScore(){
        uiViewport.apply();
        game.batch.setProjectionMatrix(uiViewport.getCamera().combined);

        font.setColor(Color.ORANGE);
        font.getData().setScale(1f);
        font.draw(game.batch, "" + c.getScore(), 1400, 750);
        game.myViewport.apply();
        game.batch.setProjectionMatrix(game.myViewport.getCamera().combined);
    }

    private void drawPlayPause(){
        Texture icon = (paused)?playTexture:pauseTexture;
        if(paused){
            game.batch.draw(icon, game.myViewport.getWorldWidth()-0.5f, 0.22f, 0.269f, 0.330f);
        }
        else{

            game.batch.draw(icon, game.myViewport.getWorldWidth()-0.7f, 0.1f, 0.6f, 0.6f);
        }
    }

    private void drawPauseOverlay(){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(game.myViewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f, 0f, 0f, 0.3f);
        shapeRenderer.rect(0, 0, game.myViewport.getWorldWidth(), game.myViewport.getWorldHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        uiViewport.apply();
        game.batch.setProjectionMatrix(uiViewport.getCamera().combined);
        game.batch.begin();
        font.getData().setScale(0.8f);
        GlyphLayout pausedLayout = new GlyphLayout(font, "Paused");
        float pausedX=(1600-pausedLayout.width)/2;
        float pausedY=470;
        font.setColor(Color.ORANGE);
        font.draw(game.batch, "Paused", pausedX, pausedY);

        font.getData().setScale(1.2f);
        GlyphLayout resumeLayout = new GlyphLayout(font, "Resume");
        float resumeX=(1600-resumeLayout.width)/2;
        float resumeY=400;
        font.setColor(Color.WHITE);
        font.draw(game.batch, "Resume", resumeX, resumeY);

        font.setColor(Color.ORANGE);
        font.getData().setScale(1f);
        game.batch.end();
        game.myViewport.apply();
        game.batch.setProjectionMatrix(game.myViewport.getCamera().combined);
        game.batch.begin();
        Texture icon=playTexture;
        game.batch.draw(icon, game.myViewport.getWorldWidth()-0.5f, 0.22f, 0.269f, 0.330f);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.stage.getViewport().update(width, height, true);
        uiViewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) { //Called when the screen was touched or a mouse button was pressed.
        points.clear();
        pts.clear();
        Vector3 temp = new Vector3(screenX, screenY, 0);
        game.myViewport.unproject(temp);
        if (temp.x >= game.myViewport.getWorldWidth()-0.7f && temp.x <= game.myViewport.getWorldWidth() -0.1f && temp.y >= 0.1f && temp.y <=  0.7f) {
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


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {//Called when a finger or the mouse was dragged.
        Vector3 temp = new Vector3(screenX, screenY, 0);
        game.myViewport.unproject(temp);
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
                        //horizontal
                        if(controller.getCurrentLevel().getHorizontalLines()) {
                            colorDrawing = Color.RED;
                        }
                        else colorDrawing = Color.WHITE;
                    }
                    else{
                        //vertical
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
                //horizontal
                if(controller.getCurrentLevel().getHorizontalLines()) {
                    colorDrawing = Color.RED;
                }
            }
            else{
                //vertical
                if(controller.getCurrentLevel().getVerticalLines()) {
                    colorDrawing = Color.BLUE;
                }
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button){//Called when a finger was lifted or a mouse button was released.
        if(controller.getCurrentLevel().isCompleted()) return false;
        isDrawing = false;
        if(points.size()>10){
            recognizer = createCurrentRecognizer();
            Result r = recognizer.Recognize(pts);
            if (r != null && map2.containsKey(r.Name)&&r.Score>0.77) {
                if(r.Score>0.90 && (r.Name.equals("horizontalLine")||r.Name.equals("verticalLine"))){
                    if(Math.abs((points.get(points.size()-1).x-points.get(0).x)) > Math.abs(points.get(points.size()-1).y-points.get(0).y)){
                        //horizontal
                        if(controller.getCurrentLevel().getHorizontalLines()) {
                            controller.getCurrentLevel().shapeDrawn(0, c);
                        }

                    }
                    else{
                        //vertical
                        if(controller.getCurrentLevel().getVerticalLines()) {
                            controller.getCurrentLevel().shapeDrawn(1, c);
                        }

                    }
                }
                else controller.getCurrentLevel().shapeDrawn(map2.get(r.Name).getValue0(), c);
            }
            else if(r != null && map2.containsKey(r.Name)&&r.Name.equals("circle")&&r.Score>0.70) {
                 controller.getCurrentLevel().shapeDrawn(4, c);
            }

        }
        else if(points.size()>1){
            if(Math.abs((points.get(points.size()-1).x-points.get(0).x)) > Math.abs(points.get(points.size()-1).y-points.get(0).y)){
                //horizontal
                if(controller.getCurrentLevel().getHorizontalLines()) {
                    controller.getCurrentLevel().shapeDrawn(0, c);
                }
            }
            else{
                //vertical
                if(controller.getCurrentLevel().getVerticalLines()) {
                    controller.getCurrentLevel().shapeDrawn(1, c);
                }
            }
        }

        return true;
    }
}
