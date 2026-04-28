import java.util.*;
import java.lang.*;

public class Ghost {
    ArrayList<Integer> shapes;
    int strlen;
    int spiralIndex;
    int totaltypeshapes; // Number of types of shapes (e.g. o, -, |, etc.)
    boolean alive;
    public Ghost(int len, int totshapes){
        alive = true;
        strlen = len;
        totaltypeshapes = totshapes;
        for(int i=0;i<strlen;i++){
            shapes.add((int) (totshapes*Math.random()));
        }
    }
    public Ghost (){
        strlen = 1;
        shapes.add(spiralIndex);
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
}
