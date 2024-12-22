package year2024.puzzle18;

import util.Utils;

import java.util.List;

/**
 * Part A: 272
 * Part B: 16,44
 */
public class Puzzle18 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2024/input18.txt", (s) -> s);
        //Board b = new Board(7, 12, input);
        Board b = new Board(71, 1024, input);

        int solutionA = b.solve();
        System.out.println("Part A: " + solutionA);

        int numberOfBytes = 0;
        Board bb = new Board(71, numberOfBytes, input);
        while (bb.solve() != Integer.MAX_VALUE) {
            numberOfBytes++;
            bb = new Board(71, numberOfBytes, input);
        }

        System.out.println("Part B: " + input.get(numberOfBytes - 1));
    }
}
