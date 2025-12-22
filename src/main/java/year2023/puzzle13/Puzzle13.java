package year2023.puzzle13;

import util.Utils;

import java.util.List;

/**
 * Part A: 30575
 * Part B: 37478
 */
public class Puzzle13 {
    public static void main(String[] args) {
        List<LavaIsland> lavaIslands = Utils.splitOnBlankLine(Utils.getInput("2023/input13.txt")).stream()
                .map(LavaIsland::new)
                .toList();

        System.out.println("Part A: " + lavaIslands.stream()
                .map(LavaIsland::solvePartA)
                .reduce(0L, Long::sum));

        System.out.println("Part B: " + lavaIslands.stream()
                .map(LavaIsland::solvePartB)
                .reduce(0L, Long::sum));
    }
}
