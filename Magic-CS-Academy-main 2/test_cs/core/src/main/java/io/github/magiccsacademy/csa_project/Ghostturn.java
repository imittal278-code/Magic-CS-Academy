package io.github.magiccsacademy.csa_project;

import java.util.*;
import java.lang.*;

public class Ghostturn {
    int numGhosts;
    int strlen;
    int difficulty;
    int numAlive;
    int totshapes = 6;
    ArrayList<Ghost> ghostspresent;
    public Ghostturn(int nGhosts, int len, int diff, boolean spiral){
        numGhosts = nGhosts;
        strlen = len;
        numAlive = numGhosts;
        difficulty = diff;
        ghostspresent = new ArrayList<Ghost>();
        for(int i=0;i<((spiral)?nGhosts-1:nGhosts);i++){
            ghostspresent.add(new Ghost(strlen, totshapes));
        }
        if(spiral){
            ghostspresent.add(new Ghost(totshapes));
        }
        System.out.println(this);

    }

    public void add(){
        ghostspresent.add(new Ghost(strlen, totshapes));
    }

    public String toString(){
        String s = "";
        for(int i=0;i<ghostspresent.size();i++){
            if(ghostspresent.get(i).isAlive()){
                s = s + ghostspresent.get(i);
            }
        }
        return s;
    }

    public void shapeDrawn(int shapeIndex){
        for(int i=0;i<ghostspresent.size();i++){
            if(ghostspresent.get(i).lastShapeEquals(shapeIndex)){
                ghostspresent.get(i).removeLast();
                if(!ghostspresent.get(i).isAlive()){
                    ghostspresent.get(i).remove();
                    numAlive--;
                }
                else{
                ghostspresent.get(i).hurt();
                }
                System.out.println(this);
            }
        }
    }

    public boolean isAlive(){return numAlive>0;}

}
