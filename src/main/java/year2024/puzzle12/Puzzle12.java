package year2024.puzzle12;

import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 1471452
 * Part B: 863366
 */
public class Puzzle12 {

    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2024/input12.txt", (s) -> s));

        Board board = new Board(input);

        List<Region> regions = board.getRegions();

        System.out.println("Part A: " + regions.stream()
                .map(Region::priceA)
                .mapToLong(Long::longValue)
                .sum());

        System.out.println("Part B: " + regions.stream()
                .map(Region::priceB)
                .mapToLong(Long::longValue)
                .sum());
    }
}
