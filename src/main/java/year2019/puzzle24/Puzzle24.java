package year2019.puzzle24;

import util.Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Part A: 18371095
 */
public class Puzzle24 {
    static void main() {
        List<String> input = Utils.getInput("2019/input24.txt");

        Bugs bugs = new Bugs(input);

        Set<String> seenLayouts = new HashSet<>();
        seenLayouts.add(bugs.toString());

        do {
            bugs.advance();
        } while (seenLayouts.add(bugs.toString()));

        System.out.println(bugs);
        System.out.println("Part A: " + bugs.getBioDiversity());
    }
}
