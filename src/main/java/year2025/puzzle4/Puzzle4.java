package year2025.puzzle4;

import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 1416
 * Part B: 9086
 */
public class Puzzle4 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2025/input4.txt", (s) -> s));

        System.out.println("Part A: " + new Board(input).numberOfAccessiblePaperRolls());
        System.out.println("Part B: " + new Board(input).numberOfPaperRollsThatCanBeRemoved());
    }
}
