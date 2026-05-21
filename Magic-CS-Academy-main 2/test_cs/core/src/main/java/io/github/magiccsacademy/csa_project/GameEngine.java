package io.github.magiccsacademy.csa_project;
import java.util.*;
import java.lang.*;
import java.time.*;
import java.io.*;

public class GameEngine {

        public ArrayList<Level> levels;
        private final int numLevels;
        private int curLevel;
        private boolean completed;

        public GameEngine(int numLevels){
            this.numLevels = numLevels;
            this.curLevel = 0;
            completed = false;
            levels = new ArrayList<Level>(numLevels);
            //note that curLevel is zero indexed when stored in this class

        }


        public void addLevel(Level level){
            levels.add(level);
        }


        public Level getCurrentLevel(){
            return levels.get(curLevel);
        }

        public int getCurrentLevelNum(){
            return curLevel+1;
        }

        public boolean doneWithLevels(){
            return completed;
        }


        public void nextLevel(){
            if(curLevel>=numLevels-1){
                completed = true;
            }
            else{
                curLevel++;
                levels.get(curLevel).startLevel();
            }
        }




}
