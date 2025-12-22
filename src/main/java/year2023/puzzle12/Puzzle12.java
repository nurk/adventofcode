package year2023.puzzle12;

import util.Utils;

import java.util.List;

/**
 * Part A: 6827
 * Part B: 1537505634471
 */
public class Puzzle12 {
    public static void main(String[] args) {
        List<Spring> springs = Utils.getInput("2023/input12.txt", Spring::new);
        List<SpringAI> aiSprings = Utils.getInput("2023/input12.txt", SpringAI::new);

        System.out.println("Part A: " + springs.stream()
                .map(Spring::solvePartA)
                .mapToLong(value -> value)
                .sum());

        System.out.println("Part B: " + aiSprings.stream()
                .map(SpringAI::solvePartB)
                .mapToLong(value -> value)
                .sum());

    }
}
