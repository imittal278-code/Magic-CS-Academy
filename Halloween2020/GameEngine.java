import java.util.*;
import java.lang.*;

public class GameEngine {
    public static void main(String[] args) {
        Ghostturn turn = new Ghostturn(4, 3, 1, true);
        Scanner s = new Scanner(System.in);

        while(turn.isAlive()){
            int num = s.nextInt();
            turn.shapeDrawn(num);
        }   
    }
}
