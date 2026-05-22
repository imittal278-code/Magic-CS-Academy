package io.github.magiccsacademy.csa_project;

import com.badlogic.gdx.Game;
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

public class GameScreen extends InputAdapter implements Screen {
    private BitmapFont font;
    private final int numLevels = 1;
    private final Main game;
    private Viewport uiViewport;

    //shape declarations
    private Texture verticalLine;
    private Texture horizontalLine;
    private Texture upsideDownV;
    private ArrayList<Texture> transitionBackground;
    private Texture normalV;
    private Texture circle;
    private HashMap<Integer, Texture> map;
    private GameEngine controller;
    private int lastLevel;
    private boolean showTransition;
    private boolean firstLevel;
    private float transitionTime;
    private Cat c;
    // private float catx = 2.5f, caty = 1.5f;
    private ArrayList<Float> ghostx, ghosty;
    private Texture background;
    private Texture ghost;
    private Sprite ghost2;
    private Texture cat;
    private Sprite cat2;
    private Texture heart;
    private Texture shield;
    private Texture heartOutline;
    private boolean start = true;
    private float ghostSpeed = 0.2f; // the ghostSpeed should (1) not be constant b/c well have slower bosses, (2) should be time dependent instead.
    TextButton button;
    private ArrayList<Vector2> points = new ArrayList<Vector2>();
    private Vector<Point> pts = new Vector<Point>();
    private boolean isDrawing = true;
    private ShapeRenderer shapeRenderer;
    private Recognizer recognizer;
    private Color colorDrawing;

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
        uiViewport = new FitViewport(800, 400);
        colorDrawing = Color.WHITE;
        controller = new GameEngine(numLevels);
        //button = new TextButton("Click Me!", skin);
        c = new Cat(2.6f, 1.1f);
        recognizer = new Recognizer();
        lastLevel = 0;
        heart = new Texture("heart.png");
        heartOutline = new Texture("heart_outline.png");
        shield = new Texture("Sheild.png");
        Gdx.input.setInputProcessor(this);
        shapeRenderer = new ShapeRenderer();
        ghost = new Texture("ghost2.png");
        ghost2 = new Sprite(ghost);
        cat = new Texture("Momo2023.png");
        cat2 = new Sprite(cat);

        background = new Texture("background.png");
        
        /*for(int i=0;i<turn.ghostspresent.size();i++){
            turn.ghosty.add(((float)i)*0.5f);
            turn.ghostx.add(1.0f+i);
        }*/
        normalV = new Texture("normalV.png");
        upsideDownV = new Texture("upsideDownV.png");
        verticalLine = new Texture("verticalLine.png");
        horizontalLine = new Texture("horizontalLine.png");
        circle = new Texture("circle.png");

        map = new HashMap<Integer, Texture>();
        map.put(0, horizontalLine);
        map.put(1, verticalLine);
        map.put(2, normalV);
        map.put(3, upsideDownV);
        map.put(4, circle);
        GameThing g = new GameThing();

        level1 = g.l1;
        level2 = g.l2;
        level3 = g.l3;
        level4 = g.l4;

        background = level1.getBackground();


        controller.addLevel(level1);
        controller.addLevel(level2);
        controller.addLevel(level3);
        controller.addLevel(level4);

        showTransition = true;
        transitionTime = 2f;
        firstLevel = true;

        transitionBackground = new ArrayList<Texture>(5);
        transitionBackground.add(new Texture("level1.png"));
        transitionBackground.add(new Texture("level2.png"));
        transitionBackground.add(new Texture("level3.png"));
        transitionBackground.add(new Texture("level4.png"));
        transitionBackground.add(new Texture("level5.png"));
        controller.getCurrentLevel().startLevel();

        //font setup stuff dont worry about the red errors, they dont matter
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(Color.YELLOW);
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
                ghost2.setPosition(x, y);
                ghost2.setSize(0.6f, 0.666f);
                ghost2.draw(game.batch);
                int shapesLeft = g.shapes.size();
                if (shapesLeft % 2 == 0) {
                    float intitialpos = x - (float) (shapesLeft / 2) * 0.15f + 0.33f;
                    for (int k = 0; k < shapesLeft; k++) {
                        game.batch.draw(map.get(g.shapes.get(shapesLeft - k - 1)), intitialpos + 0.15f * k, y + 0.6f, 0.1f, 0.1f);
                    }
                } else {
                    float intitialpos = x - ((float) shapesLeft / 2) * 0.15f + 0.32f;
                    for (int k = 0; k < shapesLeft; k++) {
                        game.batch.draw(map.get(g.shapes.get(shapesLeft - k - 1)), intitialpos + 0.15f * k, y + 0.6f, 0.1f, 0.1f);
                    }
                }
            }
        }

    }


    @Override
    public void render(float delta) {

        //keep this code at the top
        if (showTransition) {
            transitionTime -= delta;

            ScreenUtils.clear(0, 0, 1, 1);
            game.myViewport.apply();
            game.batch.setProjectionMatrix(game.myViewport.getCamera().combined);

            game.batch.begin();


            game.batch.setColor(0.4f, 0.4f, 0.4f, 1f);
            game.batch.draw(background, 0, 0, game.myViewport.getWorldWidth(), game.myViewport.getWorldHeight());
            game.batch.setColor(1f, 1f, 1f, 1f);
            game.batch.draw(transitionBackground.get(controller.getCurrentLevelNum() - 1), -6f * transitionTime + 6f, 1f, 6f, 1f);


            game.batch.end();

            if (transitionTime <= 0) showTransition = false;
            return;
        }
        start = false;

        //set background based on current Level
        background = controller.getCurrentLevel().getBackground();

        ScreenUtils.clear(0, 0, 0, 1);
        game.myViewport.apply();
        game.batch.setProjectionMatrix(game.myViewport.getCamera().combined);

        game.batch.setColor(0.4f, 0.4f, 0.4f, 1f);

        game.batch.begin();

        /*
        for(int i=0;i<10;i++){
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_0+i))turn.shapeDrawn(i);
        }
        */





        //draw the background first
        game.batch.setColor(0.4f, 0.4f, 0.4f, 1f);
        game.batch.draw(background, 0, 0, game.myViewport.getWorldWidth(), game.myViewport.getWorldHeight());
        game.batch.setColor(1f, 1f, 1f, 1f);

        //set cat's position and size and draw it
        cat2.setPosition(c.getX(), c.getY());
        cat2.setSize(0.6f, 0.6f);
        cat2.draw(game.batch);
        controller.getCurrentLevel().update(delta, c);
        if (c.hasShield) {
            drawShield();
        }

        drawGhosts(controller.getCurrentLevel());

        drawHearts(c);


        game.batch.end();
        shapeRenderer.setProjectionMatrix(game.myViewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(colorDrawing);
        for (int i = 0; i < points.size() - 1; i++) {
            shapeRenderer.rectLine(points.get(i), points.get(i + 1), 0.05f);
        }
        shapeRenderer.end();

        //You lost
        if (!c.isAlive()) {
            this.dispose();
            game.setScreen(new endScreen(game, c));
        }
        if (controller.getCurrentLevel().isCompleted()) {
            if (controller.doneWithLevels() && c.isAlive()) {
                this.dispose();
                game.setScreen(new endScreen(game, c));
            } else if (!showTransition) {
                firstLevel = false;
                showTransition = true;
                transitionTime = 2f;
                controller.nextLevel();
                if (controller.doneWithLevels() && c.isAlive()) {
                    this.dispose();
                    game.setScreen(new endScreen(game, c));
                }
            }
        }

        drawScore();
    }

    private void drawShield() {
        game.batch.draw(shield, c.getX(), c.getY(), 0.7f, 0.7f);
    }

    private void drawHearts(Cat c) {
        int count = c.getLives();
        float adder = 0.2f;
        for (int i = 0; i < 5; i++) {
            if (count > 0) {
                game.batch.draw(heart, 0f + adder, 2.5f, 0.3f, 0.3f);
            } else {
                game.batch.draw(heartOutline, 0f + adder, 2.5f, 0.3f, 0.3f);
            }
            adder += 0.3f;
            count--;
        }
    }

    private void drawScore(){
        uiViewport.apply();
        game.batch.setProjectionMatrix(uiViewport.getCamera().combined);

        game.batch.begin();
        font.draw(game.batch, "" + c.getScore(), 600, 360);
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

        verticalLine.dispose();
        horizontalLine.dispose();
        upsideDownV.dispose();
        normalV.dispose();
        circle.dispose();

        ghost.dispose();
        cat.dispose();

        heart.dispose();
        heartOutline.dispose();
        background.dispose();
        for (Texture t : transitionBackground) {
            t.dispose();
        }


        shapeRenderer.dispose();
        font.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) { //Called when the screen was touched or a mouse button was pressed.
        points.clear();
        pts.clear();
        Vector3 temp = new Vector3(screenX, screenY, 0);
        game.myViewport.unproject(temp);

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
        if (points.size() > 10) {

            Result r = recognizer.Recognize(pts);
            System.out.println(r.Name + " " + r.Score);
            switch (r.Name) {
                case "caret CW": // v
                    colorDrawing = Color.YELLOW;
                    break;
                case "caret CCW": // upside down v
                    colorDrawing = Color.GREEN;
                    break;
                case "circle CW":
                case "circle CCW": // circles
                    colorDrawing = Color.CYAN;
                    break;
                case "line left":
                case "line right": //horizontal line 
                    colorDrawing = Color.RED;
                    break;
                case "lineup":
                case "linedown": // vertical line 
                    colorDrawing = Color.BLUE;
                    break;
            }


        }
        return true;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {//Called when a finger was lifted or a mouse button was released.
        if (controller.getCurrentLevel().isCompleted()) {
            return false;
        }
        isDrawing = false;
        if (points.size() > 10) {

            Result r = recognizer.Recognize(pts);
            System.out.println(r.Name + " " + r.Score);
            switch (r.Name) {
                case "caret CW": // v
                    controller.getCurrentLevel().shapeDrawn(2, c);
                    break;
                case "caret CCW": // upside down v
                    controller.getCurrentLevel().shapeDrawn(3, c);
                    break;
                case "circle CW":
                case "circle CCW": // circles
                    controller.getCurrentLevel().shapeDrawn(4, c);
                    break;
                case "line left":
                case "line right": //horizontal line 
                    controller.getCurrentLevel().shapeDrawn(0, c);
                    break;
                case "lineup":
                case "linedown": // vertical line ]
                    controller.getCurrentLevel().shapeDrawn(1, c);
                    break;
            }


        }
        return true;
    }


}


