package io.github.magiccsacademy.csa_project;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.*;

public class DrawingDetector extends InputAdapter{
    private ArrayList<Vector2> points = new ArrayList<Vector2>();
    private boolean isDrawing = true; 
    private ShapeRenderer shapeRenderer;
    public boolean touchDown(int screenX, int screenY, int pointer, int button){ //Called when the screen was touched or a mouse button was pressed.
        points.clear();
        points.add(new Vector2(screenX, screenY));
        shapeRenderer = new ShapeRenderer();
        isDrawing = true;
        return true;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer){//Called when a finger or the mouse was dragged.
        if(isDrawing){
            points.add(new Vector2(screenX,screenY));
        }
        return true;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button){//Called when a finger was lifted or a mouse button was released.
        isDrawing = false;
        if(points.size()>10){
            //recognize gesture
        }
        return true;
    }
}
