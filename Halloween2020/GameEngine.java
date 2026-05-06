
import java.util.*;
import java.lang.*;
import java.time.*;
import java.io.*;

public class GameEngine {
    public static void main(String[] args) throws IOException {
        Ghostturn turn = new Ghostturn(4, 3, 1, true);
        Scanner s = new Scanner(System.in);
        long startTime = System.currentTimeMillis();
        long duration = 8000;
        long endTime = startTime + duration;
        // System.currentTimeMillis()-startTime is how much time is remaining
        while (turn.isAlive() && System.currentTimeMillis() < endTime) {
            try {
                if (System.in.available() > 0) {
                    if (s.hasNextInt() && System.currentTimeMillis() < endTime) {
                        int num = s.nextInt();
                        turn.shapeDrawn(num);
                    }
                }
            } catch (IOException e) {

            }
        }
        if (turn.isAlive()) {
            System.out.println("you lost a life");
        } else {
            System.out.println("you won the game!");
        }

    }
}
