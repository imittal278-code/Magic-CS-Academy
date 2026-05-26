package io.github.magiccsacademy.csa_project;

import java.util.*;
import java.lang.*;

public class Ghost {
    ArrayList<Integer> shapes;
    public int strlen;
    public boolean isFulk;
    public boolean isFish;
    public boolean isShield;
    public float stateTimer = 0;
    public boolean isPausing = false;
    public float horizontalDirection = 1.0f;
   // public int totaltypeshapes; // Number of types of shapes (e.g. o, -, |, etc.)
    boolean alive;
    public Ghost(int len, int totshapes){
        isFulk = false;
        isFish = false;
        isShield = false;
        alive = true;
        strlen = len;
       // totaltypeshapes = totshapes;
        shapes = new ArrayList<Integer>();
        for(int i=0;i<strlen;i++){
            shapes.add((int) (4*Math.random()));
        }
    }
 /*    public Ghost (int totshapes){
        shapes = new ArrayList<Integer>();
        alive = true;
        strlen = 1;
        totaltypeshapes = totshapes;
        shapes.add(totshapes-1);
    }*/

    public Ghost (String s){
        isFulk = false;
        isFish = false;
        isShield = false;
        shapes = strtointarray(s.split(""));
     //   totaltypeshapes = 4;
        strlen = shapes.size();
        alive = true; 
    }

    public Ghost (String s, boolean fulk){
        isFulk = fulk;
        isFish = false;
        isShield = false;
        shapes = strtointarray(s.split(""));
        //   totaltypeshapes = 4;
        strlen = shapes.size();
        alive = true;
    }

    private ArrayList<Integer> strtointarray(String[] a){
        ArrayList<Integer> ans = new ArrayList<Integer> ();
        for(String s : a){
            ans.add(Integer.parseInt(s));
        }
        return ans;
    }


    public void hurt(){

    }
    public void remove(){
        alive = false;
    }

    public int shapeAt(int index){
        if(index>=shapes.size()) return -1;
        return shapes.get(index);
    }

    public boolean lastShapeEquals(int target){
        return (isAlive() && shapes.get(shapes.size()-1) == target);
    }

    public void removeLast(){
        if (!shapes.isEmpty()) {
            shapes.remove(shapes.size() - 1);
        }
    }

    public boolean isAlive(){ return alive && shapes.size()>0; }

        public String toString() {
            return shapes.toString();
        }

    public void shieldPause (float delta) {
        stateTimer += delta;
        if (isPausing && stateTimer >= 1.5f) {
            isPausing = false;
            stateTimer = 0;
        } else {
            if (stateTimer >= 2.0f) {
                isPausing = true;
                stateTimer = 0;
            }
        }
    }
}
