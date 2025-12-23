package year2023.puzzle14;

import util.Utils;

/**
 * Part A: 109466
 * Part B: 94585
 */
public class Puzzle14 {
    public static void main(String[] args) {
        Platform platform = new Platform(Utils.getInput("2023/input14.txt"));

        System.out.println("Part A: " + platform.solvePartA());
        System.out.println("Part B: " + platform.solvePartB());
    }
}
