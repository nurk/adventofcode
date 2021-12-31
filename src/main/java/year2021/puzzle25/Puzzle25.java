package year2021.puzzle25;

import util.Utils;

import java.util.ArrayList;
import java.util.List;

public class Puzzle25 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2021/input25.txt", (s) -> s));

        Board b = new Board(input);
        int steps = 0;
        boolean moved = true;
        while (moved) {
            steps++;
            moved = b.doStep(">", row -> row, col -> (col + 1) % b.getCols());
            moved = b.doStep("v", row -> ((row + 1) % b.getRows()), col -> col) || moved;
        }
        System.out.println(steps);
    }
}
