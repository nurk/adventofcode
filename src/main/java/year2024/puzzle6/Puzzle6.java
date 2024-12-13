package year2024.puzzle6;

import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 4752
 * Part B: 1719
 */
public class Puzzle6 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2024/input6.txt", (s) -> s));

        Board b = new Board(input);

        System.out.println("Part A: " + b.traverse());
        System.out.println("Part B: " + b.b());
    }
}
