package year2017.puzzle19;

import org.apache.commons.lang3.tuple.Pair;
import util.Utils;

import java.util.List;

/**
 * Part A: LOHMDQATP
 * Part B: 16492
 */
public class Puzzle19 {
    public static void main(String[] args) {
        List<String> lines = Utils.getInput("2017/input19.txt");

        Tubes tubes = new Tubes(lines);
        Pair<String, Integer> solve = tubes.solve();
        System.out.println("Part A: " + solve.getLeft());
        System.out.println("Part B: " + solve.getRight());
    }
}
