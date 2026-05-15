package io.github.magiccsacademy.csa_project;

import java.util.*;
import java.lang.*;

public class Ghost {
    ArrayList<Integer> shapes;
    int strlen;
    public int totaltypeshapes; // Number of types of shapes (e.g. o, -, |, etc.)
    boolean alive;
    public Ghost(int len, int totshapes){
        alive = true;
        strlen = len;
        totaltypeshapes = totshapes;
        shapes = new ArrayList<Integer>();
        for(int i=0;i<strlen;i++){
            shapes.add((int) (totshapes*Math.random()));
        }
    }
    public Ghost (int totshapes){
        shapes = new ArrayList<Integer>();
        alive = true;
        strlen = 1;
        totaltypeshapes = totshapes;
        shapes.add(totshapes-1);
    }

    public void hurt(){
        System.out.println(shapes + "hurt");
    }

    public void remove(){
        System.out.println("removed");
    }

    public int shapeAt(int index){
        if(index>=shapes.size()) return -1;
        return shapes.get(index);
    }

    public boolean lastShapeEquals(int target){
        return (isAlive() && shapes.get(shapes.size()-1) == target);
    }

    public void removeLast(){
        shapes.removeLast();
    }

    public boolean isAlive(){ return shapes.size()>0; }

        public String toString() {
            return shapes.toString();
        }
}
