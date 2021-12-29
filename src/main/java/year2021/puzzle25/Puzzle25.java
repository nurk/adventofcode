package year2021.puzzle25;

import util.Utils;

import java.util.ArrayList;
import java.util.List;

public class Puzzle25 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2021/input25.txt", (s) -> s));

        Board b = new Board(input);
        int steps = 0;
        boolean movedEast = true;
        boolean movedSouth = true;
        while (movedEast || movedSouth) {
            steps++;
            movedEast = b.doEastStep();
            movedSouth = b.doSouthStep();
        }
        System.out.println(steps);
    }
}
