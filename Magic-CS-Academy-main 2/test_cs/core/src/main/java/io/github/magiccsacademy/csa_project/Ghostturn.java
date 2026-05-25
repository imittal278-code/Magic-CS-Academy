package io.github.magiccsacademy.csa_project;

import java.util.*;
import java.lang.*;

public class Ghostturn {
    int numGhosts;
   // int strlen;
    int difficulty;
    int numAlive;
    int totshapes = 4;
    int circles = 0;
    int horizontalLines = 0;
    int verticalLines = 0;
    int upsideDownVs = 0;
    int normalVs = 0;
    ArrayList<Ghost> ghostspresent;
    public ArrayList<Float> ghostx;
    public ArrayList<Float> ghosty;
    private boolean fish;


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
        fish = false;
    }
    public Ghostturn(int diff, int[] counts){
        //counts.length = nGhosts
        numGhosts = counts.length;
        //strlen = 0;
        numAlive = numGhosts;
        difficulty = diff;
        ghostx = new ArrayList<Float>(numGhosts);
        ghosty = new ArrayList<Float>(numGhosts);
        ghostspresent = new ArrayList<Ghost>();
        for(int i=0;i<numGhosts;i++){
            ghostspresent.add(new Ghost(counts[i], totshapes));
        }
        fish = false;
    }

    public Ghostturn(int diff, int[] counts,boolean[] fulks){
        //counts.length = nGhosts
        numGhosts = counts.length;
        //strlen = 0;
        numAlive = numGhosts;
        difficulty = diff;
        ghostx = new ArrayList<Float>(numGhosts);
        ghosty = new ArrayList<Float>(numGhosts);
        ghostspresent = new ArrayList<Ghost>();
        for(int i=0;i<numGhosts;i++){
            ghostspresent.add(new Ghost(counts[i], totshapes));
            if(fulks[i]){
                ghostspresent.get(i).isFulk=true;
            }
        }
        fish = false;
    }

    public Ghostturn(int nGhosts, int len, int diff, boolean allFish, boolean circle, boolean allFulk){
        numGhosts = nGhosts;
        numAlive = numGhosts;
        //strlen = len;
        fish = allFish;
        difficulty = diff;
        ghostx = new ArrayList<Float>(nGhosts);
        ghosty = new ArrayList<Float>(nGhosts);
        ghostspresent = new ArrayList<Ghost>();
        for(int i=0;i<nGhosts;i++){
            ghostspresent.add(new Ghost(len, totshapes));
            if(allFulk){
                ghostspresent.get(i).isFulk=true;
            }
            else if(allFish){
                ghostspresent.get(i).isFish = true;
            }
        }
        if(circle){
            ghostspresent.add(new Ghost("4"));
            numGhosts++;
            numAlive = numGhosts;
        }


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


    public void updateCounts(){
        circles = 0;
        horizontalLines = 0;
        verticalLines = 0;
        upsideDownVs = 0;
        normalVs = 0;
        for(int i=0;i<ghostspresent.size();i++){
           if(ghostspresent.get(i).lastShapeEquals(0)){
               horizontalLines++;
           }
           else if(ghostspresent.get(i).lastShapeEquals(1)){
                verticalLines++;
           }
           else if(ghostspresent.get(i).lastShapeEquals(2)){
               normalVs++;
           }
           else if(ghostspresent.get(i).lastShapeEquals(3)){
               upsideDownVs++;
           }
           else if(ghostspresent.get(i).lastShapeEquals(4)){
               circles++;
           }
        }
    }
    /*private determineSpeed(){
        //if()
    }*/

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


                float xpos = ghostx.get(i)-((float)(ghostspresent.get(i).shapes.size())/2)*0.15f+((ghostspresent.get(i).shapes.size())%2==0?0.33f:0.32f);
                float ypos = 0.6f+ghosty.get(i);
                if(fish){
                    ypos = 0.3f+ghosty.get(i);
                }
                if(xpos<0f||xpos>6f||ypos<0f||ypos>3f){
                    continue;
                }
                if(ghostspresent.get(i).lastShapeEquals(0)){
               horizontalLines--;
           }
           else if(ghostspresent.get(i).lastShapeEquals(1)){
                verticalLines--;
           }
           else if(ghostspresent.get(i).lastShapeEquals(2)){
               normalVs--;
           }
           else if(ghostspresent.get(i).lastShapeEquals(3)){
               upsideDownVs--;
           }
           else if(ghostspresent.get(i).lastShapeEquals(4)){
               circles--;
           }
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
                        if(ghostspresent.get(i).lastShapeEquals(0)){
               horizontalLines++;
           }
           else if(ghostspresent.get(i).lastShapeEquals(1)){
                verticalLines++;
           }
           else if(ghostspresent.get(i).lastShapeEquals(2)){
               normalVs++;
           }
           else if(ghostspresent.get(i).lastShapeEquals(3)){
               upsideDownVs++;
           }
           else if(ghostspresent.get(i).lastShapeEquals(4)){
               circles++;
           }
                    }
                }
                else{
                    if(shapeIndex == 4){
                        //Shield
                        ghostspresent.get(i).removeLast();
                        numAlive--;
                        ghostspresent.get(i).remove();
                        c.shieldOn();
                      
                        //Make ghost move toward the cat.  *TODO*
                    }
                }
            }
        }
    }

    public boolean isAlive(){return numAlive>0;}
    public int getCircles(){return circles;}
    public int getHorizontalLines(){return horizontalLines;}
    public int getVerticalLines(){return verticalLines;}
    public int getNormalVs(){return normalVs;}
    public int getUpsideDownVs(){return upsideDownVs;}

}
