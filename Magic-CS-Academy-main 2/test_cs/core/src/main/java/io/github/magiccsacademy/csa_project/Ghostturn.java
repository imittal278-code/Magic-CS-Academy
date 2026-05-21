package io.github.magiccsacademy.csa_project;

import java.util.*;
import java.lang.*;

public class Ghostturn {
    int numGhosts;
   // int strlen;
    int difficulty;
    int numAlive;
    int totshapes = 4;
    ArrayList<Ghost> ghostspresent;
    public ArrayList<Float> ghostx;
    public ArrayList<Float> ghosty;

    public Ghostturn(int nGhosts, int len, int diff, boolean spiral){
        numGhosts = nGhosts;
    //    strlen = len;
        numAlive = numGhosts;
        difficulty = diff;
        ghostx = new ArrayList<Float>(nGhosts);
        ghosty = new ArrayList<Float>(nGhosts);
        ghostspresent = new ArrayList<Ghost>();
        for(int i=0;i<((spiral)?nGhosts-1:nGhosts);i++){
            ghostspresent.add(new Ghost(len, totshapes));
        }
        System.out.println(this);

    }
    public Ghostturn(int nGhosts, int len, int diff){
        numGhosts = nGhosts;
        //strlen = 0;
        numAlive = numGhosts;
        difficulty = diff;
        ghostx = new ArrayList<Float>(nGhosts);
        ghosty = new ArrayList<Float>(nGhosts);
        ghostspresent = new ArrayList<Ghost>();
        for(int i=0;i<nGhosts;i++){
            ghostspresent.add(new Ghost(len, totshapes));
        }
        System.out.println(this);

    }
    public Ghostturn(int nGhosts, int len, int diff, boolean spiral, boolean circle){
        numGhosts = nGhosts+1;
        //strlen = len;
        numAlive = numGhosts;
        difficulty = diff;
        ghostx = new ArrayList<Float>(nGhosts);
        ghosty = new ArrayList<Float>(nGhosts);
        ghostspresent = new ArrayList<Ghost>();
        for(int i=0;i<((spiral)?nGhosts-1:nGhosts);i++){
            ghostspresent.add(new Ghost(len, totshapes));
        }
        ghostspresent.add(new Ghost("4"));
        System.out.println(this);

    }
    public Ghostturn (Ghost g){
        numGhosts = 1;
        //strlen = g.strlen;
        difficulty = 1;
        numAlive = 1;
        ghostspresent = new ArrayList<Ghost>();
        ghostspresent.add(g);
        ghostx = new ArrayList<Float>(numGhosts);
        ghosty = new ArrayList<Float>(numGhosts);
    }

 /*   public void add(){
        ghostspresent.add(new Ghost(strlen, totshapes));
    }*/

    public String toString(){
        String s = "";
        for(int i=0;i<ghostspresent.size();i++){
            if(ghostspresent.get(i).isAlive()){
                s = s + ghostspresent.get(i);
            }
        }
        return s;
    }

    public void shapeDrawn(int shapeIndex, Cat c){
        
        for(int i=0;i<ghostspresent.size();i++){
            if(ghostspresent.get(i).lastShapeEquals(shapeIndex)){
                if(shapeIndex <=3){
                    ghostspresent.get(i).removeLast();
                    int increment = 10;
                    if(difficulty==2) increment = 20;
                    else if(difficulty==3) increment = 100;
                    c.addScore(increment);
                    if(!ghostspresent.get(i).isAlive()){
                        ghostspresent.get(i).remove();
                        numAlive--;
                    }
                    else{
                    ghostspresent.get(i).hurt();
                    }
                    System.out.println(this);
                }
                else{
                    if(shapeIndex == 4){
                        //Shield
                        ghostspresent.get(i).removeLast();
                        numAlive--;
                        ghostspresent.get(i).remove();
                        c.shieldOn();
                       // System.out.println("")
                        //Make ghost move toward the cat. 
                    }
                }
            }
        }
    }

    public boolean isAlive(){return numAlive>0;}

}
