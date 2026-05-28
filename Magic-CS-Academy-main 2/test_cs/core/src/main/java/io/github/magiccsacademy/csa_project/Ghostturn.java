package io.github.magiccsacademy.csa_project;

import java.util.*;
import java.lang.*;


/**
 * Represents a wave of ghosts during a level
 */
public class Ghostturn {

    /**The number of ghosts in this wave*/
    private int numGhosts;

    /**The difficulty of this wave (determines score additions)*/
    private int difficulty;

    /**The number of ghosts that haven't been killed in this wave*/
    private int numAlive;

    /**Constant value of the number of possible shapes*/
    private final int totshapes = 4;

    /** Array of the ghosts in this wave*/
    private ArrayList<Ghost> ghostspresent;

    /**The number of circles this ghostTurn has currently (across living ghosts)*/
    private int circles = 0;

    /**The number of horizontal lines this ghostTurn has currently (across living ghosts)*/
    private int horizontalLines = 0;

    /**The number of vertical lines this ghostTurn has currently (across living ghosts)*/
    private int verticalLines = 0;

    /**The number of upside down V's this ghostTurn has currently (across living ghosts)*/
    private int upsideDownVs = 0;

    /**The number of normal V's this ghostTurn has currently (across living ghosts)*/
    private int normalVs = 0;

    /** Array of the x positions of the ghosts in this wave*/
    private ArrayList<Float> ghostx;

    /** Array of the y positions of the ghosts in this wave*/
    private ArrayList<Float> ghosty;

    /** Boolean that states whether the characters in this wave are fish*/
    private boolean fish;

    /**Speed modifier for the ghosts */
    private float speedModifier = 1.0f;

    /**
     * Constructs a ghostTurn object
     *
     * @param nGhosts the number of ghosts in this wave
     * @param len the number of shapes for each ghost
     * @param diff the difficulty of this wave
     */
    public Ghostturn(int nGhosts, int len, int diff){
        numGhosts = nGhosts;
        numAlive = numGhosts;
        difficulty = diff;
        ghostx = new ArrayList<Float>(nGhosts);
        ghosty = new ArrayList<Float>(nGhosts);
        ghostspresent = new ArrayList<Ghost>();
        for(int i=0;i<nGhosts;i++){
            ghostspresent.add(new Ghost(len));
        }
        fish = false;
        this.speedModifier();
    }

    /**
     * Constructs a ghostTurn object
     *
     * @param diff the difficulty of the wave
     * @param counts An array of the number of shapes in each ghost of the wave
     */
    public Ghostturn(int diff, int[] counts){
        numGhosts = counts.length;
        numAlive = numGhosts;
        difficulty = diff;
        ghostx = new ArrayList<Float>(numGhosts);
        ghosty = new ArrayList<Float>(numGhosts);
        ghostspresent = new ArrayList<Ghost>();
        for(int i=0;i<numGhosts;i++){
            ghostspresent.add(new Ghost(counts[i]));
        }
        fish = false;
        this.speedModifier();
    }

    /**
     * constructs a ghostTurn object
     *
     * @param diff the difficulty of this wave of ghosts
     * @param counts An array of the number of shapes in each ghost of the wave
     * @param fulks An array of booleans that represents the ghosts in the wave that are Mr. Fulk
     */
    public Ghostturn(int diff, int[] counts,boolean[] fulks){
        numGhosts = counts.length;
        numAlive = numGhosts;
        difficulty = diff;
        ghostx = new ArrayList<Float>(numGhosts);
        ghosty = new ArrayList<Float>(numGhosts);
        ghostspresent = new ArrayList<Ghost>();
        for(int i=0;i<numGhosts;i++){
            ghostspresent.add(new Ghost(counts[i]));
            if(fulks[i]){
                ghostspresent.get(i).setFulk(true);
            }
        }
        fish = false;
        this.speedModifier();
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
        fish = allFish;
        difficulty = diff;
        ghostx = new ArrayList<Float>(nGhosts);
        ghosty = new ArrayList<Float>(nGhosts);
        ghostspresent = new ArrayList<Ghost>();
        for(int i=0;i<nGhosts;i++){
            ghostspresent.add(new Ghost(len));
            if(all){
                ghostspresent.get(i).setFish(true);
            }
            else if(allFish){
                ghostspresent.get(i).setFish(true);
            }
        }
        if(circle){
            ghostspresent.add(new Ghost("4"));
            ghostspresent.get(ghostspresent.size()-1).setShield(true);
            numGhosts++;
            numAlive = numGhosts;
        }

        this.speedModifier();
    }

    /**
     * constructs a ghostTurn object with one ghost
     *
     * @param g the single ghost in this wave
     */
    public Ghostturn (Ghost g){
        numGhosts = 1;
        difficulty = 1;
        numAlive = 1;
        ghostspresent = new ArrayList<Ghost>();
        ghostspresent.add(g);
        ghostx = new ArrayList<Float>(numGhosts);
        ghosty = new ArrayList<Float>(numGhosts);
        this.speedModifier();
    }

    /**
     * Initializes the circle, horizontalLines, normalVs, etc.  parameters to their initial values.
     * Method is used in Level class
     */
    public void updateCounts(){
        circles = 0;
        horizontalLines = 0;
        verticalLines = 0;
        upsideDownVs = 0;
        normalVs = 0;
        for (Ghost ghost : ghostspresent) {
            if (ghost.lastShapeEquals(0)) {
                horizontalLines++;
            } else if (ghost.lastShapeEquals(1)) {
                verticalLines++;
            } else if (ghost.lastShapeEquals(2)) {
                normalVs++;
            } else if (ghost.lastShapeEquals(3)) {
                upsideDownVs++;
            } else if (ghost.lastShapeEquals(4)) {
                circles++;
            }
        }
    }


    /**
     * Removes the shape corresponding to shapeIndex from the end of the ghosts
     * Increments the scores if needed
     * Adds a shield to the cat if needed
     * Removes ghosts if needed
     * Also updates the five shape fields based on the shape removed
     *
     * @return true if at least one ghost died.
     * @param shapeIndex the Index of the shape drawn
     * @param c the Cat object in the game (contains lives)
     */
    public boolean shapeDrawn(int shapeIndex, Cat c){
        boolean ans = false;
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
                        float deathX = ghostx.get(i);
                        float deathY = ghosty.get(i);
                        ghostspresent.get(i).deathAnimation(deathX, deathY);
                        ans = true;
                        ghostspresent.get(i).remove();
                        numAlive--;
                    }
                    else{
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
                        ghostspresent.get(i).removeLast();
                        numAlive--;
                        ghostspresent.get(i).remove();
                        c.shieldOn();

                    }
                }
            }
        }
        return ans;
    }

    /**
     * Modifies the speed by a factor based on the number of shapes and number of ghosts
     */
    public void speedModifier() {
        int len = 0;
        for (Ghost g : ghostspresent) {
            if (!g.isShield()) len += g.getStrlen();
        }
        float avgLength = ghostspresent.isEmpty() ? 1 : (float)len/ghostspresent.size();
        int count = numGhosts;
        if (count<= 2 && avgLength <= 2) {
            speedModifier = 1.4f;
        } else if (count <= 4 && avgLength<= 3) {
            speedModifier = 1.2f;
        } else if (count == 1) {
            speedModifier = 1.0f;
        } else {
            speedModifier = 1.5f;
        }
    }

    /**
     * Checks whether any ghost in this turn is still alive.
     *
     * @return true if at least one ghost is alive
     */
    public boolean hasAliveGhosts() {
        for (Ghost ghost:ghostspresent) {
            if (ghost.isAlive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether any ghost in this turn is still showing its death animation.
     *
     * @return true if at least one ghost is still dying
     */
    public boolean hasDyingGhosts() {
        for (Ghost ghost:ghostspresent) {
            if (ghost.isDying()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether this turn is fully finished, including any death animations.
     *
     * @return true if no ghosts are alive and no death animations remain
     */
    public boolean isCompletelyFinished() {
        return !hasAliveGhosts() && !hasDyingGhosts();
    }

    /**
     * checks if the ghostTurn is alive
     *
     * @return whether the ghostTurn is completed
     */
    public boolean isAlive(){return numAlive>0;}

    /**
     * Returns the number of circles that are still available to be drawn
     * @return the number of circles
     */
    public int getCircles(){return circles;}

    /**
     * Returns the number of horizontalLines that are still available to be drawn
     * @return the number of horizontalLines
     */
    public int getHorizontalLines(){return horizontalLines;}

    /**
     * Returns the number of verticalLines that are still available to be drawn
     * @return the number of verticalLines
     */
    public int getVerticalLines(){return verticalLines;}

    /**
     * Returns the number of normalVs that are still available to be drawn
     * @return the number of normalVs
     */
    public int getNormalVs(){return normalVs;}

    /**
     * Returns the number of upsideDownVs that are still available to be drawn
     * @return the number of upsideDownVs
     */
    public int getUpsideDownVs(){return upsideDownVs;}

    /**
     * Returns the number of ghosts in this turn
     *
     * @return the number of ghosts in this turn
     */
    public int getNumGhosts(){
        return numGhosts;
    }

    /**
     * Returns an array of the ghosts present in this ghostTurn
     * @return an array of the ghosts in this ghostTurn
     */
    public ArrayList<Ghost> getGhostspresent(){
        return ghostspresent;
    }

    /**
     * Returns an arrayList of the x positions of the ghosts present in this ghostTurn
     * @return an arrayList of the x positions of the ghosts in this ghostTurn
     */
    public ArrayList<Float> getGhostX(){
        return ghostx;
    }

    /**
     * Returns the speed modifier
     *
     * @return this ghost object's speed modifier
     */
    public float getSpeedModifier(){
        return speedModifier;
    }

    /**
     * Returns an arrayList of the y positions of the ghosts present in this ghostTurn
     * @return an arrayList of the y positions of the ghosts in this ghostTurn
     */
    public ArrayList<Float> getGhostY(){
        return ghosty;
    }



    /**
     * Returns the number of ghosts that are still alive in this wave.
     *
     * @return the number of living ghosts
     */
    public int getNumAlive() {
        return numAlive;
    }

    /**
     * Decrements the count of living ghosts in this wave by one.
     * This can be used when a ghost is explicitly defeated or removed.
     */
    public void decrementNumAlive() {
        this.numAlive--;
    }




    /**
     * Returns the list of x positions for the ghosts in this wave.
     *
     * @return the array list of x positions
     */
    public ArrayList<Float> getGhostx() {
        return ghostx;
    }


    /**
     * Returns the list of y positions for the ghosts in this wave.
     *
     * @return the array list of y positions
     */
    public ArrayList<Float> getGhosty() {
        return ghosty;
    }







}
