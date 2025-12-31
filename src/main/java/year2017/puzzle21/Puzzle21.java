package year2017.puzzle21;

import util.Utils;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;

/**
 * Part A: 155
 * Part B: 2449665
 */
public class Puzzle21 {
    public static void main(String[] args) {
        List<Rule> rules = Utils.getInput("2017/input21.txt", Rule::new);

        Fractal fractalPartA = new Fractal(rules);
        Fractal fractalPartB = new Fractal(rules);
        System.out.println("Part A: " + fractalPartA.evaluate());
        System.out.println("Part B: " + fractalPartB.evaluate(18));

    }

    public static String gridToString(String[][] grid) {
        return Arrays.stream(grid)
                .map(points -> String.join("", points))
                .collect(joining("\n"));
    }
}
