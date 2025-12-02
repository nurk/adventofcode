package year2025.puzzle2;

import util.Utils;

import java.util.Arrays;
import java.util.List;

/**
 * Part A: 23701357374
 * Part B: 34284458938
 */
public class Puzzle2 {
    public static void main(String[] args) {
        List<Range> ranges = Arrays.stream(Utils.getInput("2025/input2.txt").getFirst().split(","))
                .map(Range::new)
                .toList();

        long partA = ranges.stream()
                .map(Range::sumInvalidIdsPartA)
                .mapToLong(value -> value)
                .sum();
        System.out.println("Part A: " + partA);

        long partB = ranges.stream()
                .map(Range::sumInvalidIdsPartB)
                .mapToLong(value -> value)
                .sum();
        System.out.println("Part B: " + partB);
    }
}
