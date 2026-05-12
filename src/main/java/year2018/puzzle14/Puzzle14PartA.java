package year2018.puzzle14;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Part A: 1611732174
 */
public class Puzzle14PartA {
    static void main() {
        String input = "864801";
        int target = Integer.parseInt(input);

        List<Integer> recipes = new ArrayList<>(List.of(3, 7));

        int elfOne = 0;
        int elfTwo = 1;

        while (recipes.size() < target + 10) {
            int newRecipe = recipes.get(elfOne) + recipes.get(elfTwo);

            if (newRecipe >= 10) {
                recipes.add(newRecipe / 10);
            }

            recipes.add(newRecipe % 10);


            elfOne = (elfOne + recipes.get(elfOne) + 1) % recipes.size();
            elfTwo = (elfTwo + recipes.get(elfTwo) + 1) % recipes.size();
        }

        System.out.println("Part A: " + IntStream.range(target, target + 10)
                .mapToObj(recipes::get)
                .map(String::valueOf)
                .collect(Collectors.joining()));

    }
}
