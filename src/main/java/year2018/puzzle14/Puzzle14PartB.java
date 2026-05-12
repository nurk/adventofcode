package year2018.puzzle14;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Part B: 20279772
 */
public class Puzzle14PartB {
    static void main() {
        String input = "864801";

        List<Integer> recipes = new ArrayList<>(List.of(3, 7));

        int lastCheckedPosition = 0;

        int elfOne = 0;
        int elfTwo = 1;

        while (!matchTail(recipes, input, lastCheckedPosition)) {
            lastCheckedPosition = recipes.size();
            int newRecipe = recipes.get(elfOne) + recipes.get(elfTwo);

            if (newRecipe >= 10) {
                recipes.add(newRecipe / 10);
            }

            recipes.add(newRecipe % 10);


            elfOne = (elfOne + recipes.get(elfOne) + 1) % recipes.size();
            elfTwo = (elfTwo + recipes.get(elfTwo) + 1) % recipes.size();
        }

        System.out.println("Part B: " + StringUtils.substringBefore(recipes.stream()
                .map(String::valueOf)
                .collect(Collectors.joining()), input).length());
    }

    private static boolean matchTail(List<Integer> recipes, String input, int lastCheckedPosition) {
        int startCheckPosition = Math.max(0, lastCheckedPosition - input.length());

        return IntStream.range(startCheckPosition, recipes.size())
                .mapToObj(recipes::get)
                .map(String::valueOf)
                .collect(Collectors.joining()).contains(input);
    }
}
