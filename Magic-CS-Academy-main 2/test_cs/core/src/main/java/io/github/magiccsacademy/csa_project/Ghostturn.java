package io.github.magiccsacademy.csa_project;

import java.util.*;
import java.lang.*;


/**
 * Represents a wave of ghosts during a level
 */
public class Ghostturn {

    /**The number of ghosts in this wave*/
    public int numGhosts;

    /**The difficulty of this wave (determines score additions)*/
    private int difficulty;

    /**The number of ghosts that haven't been killed in this wave*/
    public int numAlive;

    /**Constant value of the number of possible shapes*/
    private final int totshapes = 4;

    /** Array of the ghosts in this wave*/
    public ArrayList<Ghost> ghostspresent;

    private int circles = 0;
    private int horizontalLines = 0;
    private int verticalLines = 0;
    private int upsideDownVs = 0;
    private int normalVs = 0;

    /** Array of the x positions of the ghosts in this wave*/
    public ArrayList<Float> ghostx;

    /** Array of the y positions of the ghosts in this wave*/
    public ArrayList<Float> ghosty;

    /** Boolean that states whether the characters in this wave are fish*/
    private boolean fish;

    /**
     * Constructs a ghostTurn object
     *
     * @param nGhosts the number of ghosts in this wave
     * @param len the number of shapes for each ghost
     * @param diff the difficulty of this wave
     */
    public Ghostturn(int nGhosts, int len, int diff){
        numGhosts = nGhosts;
    //    strlen = len;
        numAlive = numGhosts;
        difficulty = diff;
        ghostx = new ArrayList<Float>(nGhosts);
        ghosty = new ArrayList<Float>(nGhosts);
        ghostspresent = new ArrayList<Ghost>();
        for(int i=0;i<nGhosts;i++){
            ghostspresent.add(new Ghost(len, totshapes));
        }
        fish = false;
    }

    /**
     * Constructs a ghostTurn object
     *
     * @param diff the difficulty of the wave
     * @param counts An array of the number of shapes in each ghost of the wave
     */
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

    /**
     * constructs a ghostTurn object
     *
     * @param diff the difficulty of this wave of ghosts
     * @param counts An array of the numberof shapes in each ghost of the wave
     * @param fulks An array of booleans that represents the ghosts in the wave that are Mr. Fulk
     */
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
                ghostspresent.get(i).makeFulk();
            }
        }
        fish = false;
    }


    /**
     * constructs a ghostTurn object
     *
     * @param nGhosts the number of ghosts in this wave
     * @param len the number of shapes for each ghost
     * @param diff the difficulty of this wave
     * @param allFish whether this wave has only fish
     * @param circle whether this wave has a shield
     * @param all whether this wave has only Mr. s
     */
    public Ghostturn(int nGhosts, int len, int diff, boolean allFish, boolean circle, boolean all){
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
            if(all){
                ghostspresent.get(i).makeFulk();
            }
            else if(allFish){
                ghostspresent.get(i).makeFish();
            }
        }
        if(circle){
            ghostspresent.add(new Ghost("4"));
            ghostspresent.get(ghostspresent.size()-1).isShield = true;
            numGhosts++;
            numAlive = numGhosts;
        }


    }

    /**
     * constructs a ghostTurn object with one ghost
     *
     * @param g the single ghost in this wave
     */
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


    /**
     * The representation of this turn as a string
     *
     * @return a representation of the turn as a string
     */
    public String toString(){
        String s = "";
        for(int i=0;i<ghostspresent.size();i++){
            if(ghostspresent.get(i).isAlive()){
                s = s + ghostspresent.get(i);
            }
        }
        return s;
    }


    /**
     * Removes the shape corresponding to shapeIndex from the end of the ghosts
     * Increments the scores if needed
     * Adds a shield to the cat if needed
     * Removes ghosts if needed
     *
     * @param shapeIndex the Index of the shape drawn
     * @param c the Cat object in the game (contains lives)
     */
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

    /**
     * checks if the ghostTurn is alive
     *
     * @return whether the ghostTurn is completed
     */
    public boolean isAlive(){return numAlive>0;}
    public int getCircles(){return circles;}
    public int getHorizontalLines(){return horizontalLines;}
    public int getVerticalLines(){return verticalLines;}
    public int getNormalVs(){return normalVs;}
    public int getUpsideDownVs(){return upsideDownVs;}

}
