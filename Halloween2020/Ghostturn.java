import java.util.*;
import java.lang.*;

public class Ghostturn {
    int numGhosts;
    int strlen;
    int difficulty;
    int totshapes;
    ArrayList<Ghost> ghostspresent;
    public Ghostturn(int nGhosts, int len, int diff, boolean spiral){
        numGhosts = nGhosts;
        strlen = len;
        difficulty = diff;
        for(int i=0;i<nGhosts;i++){
            ghostspresent.add(new Ghost(strlen, totshapes));
        }
        if(spiral){
            ghostspresent.add(new Ghost());
        }


    }

    

    public void shapeDrawn(int shapeIndex){
        for(int i=0;i<ghostspresent.size();i++){
            if(ghostspresent.get(i).lastShapeEquals(shapeIndex)){
                ghostspresent.get(i).removeLast();
                //update the Ghost's display
                //hurt the Ghost animation
                
                if(!ghostspresent.get(i).isAlive()){
                    //remove the Ghost from the screen
                }
            }
        }
    }

}
