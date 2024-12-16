package year2024.puzzle7;

import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 7885693428401
 * Part B: 348360680516005
 */
public class Puzzle7 {

    private static final List<Formula> formulas = new ArrayList<>();

    public static void main(String[] args) {
        Utils.getInput("2024/input7.txt")
                .forEach(line -> formulas.add(new Formula(line)));

        System.out.println("Part A: " + formulas.stream()
                .filter(Formula::isPossibleA)
                .map(Formula::getResult)
                .reduce(Long::sum)
                .orElseThrow());

        System.out.println("Part B: " + formulas.stream()
                .filter(Formula::isPossibleB)
                .map(Formula::getResult)
                .reduce(Long::sum)
                .orElseThrow());
    }
}
