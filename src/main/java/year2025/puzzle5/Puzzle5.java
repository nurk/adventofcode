package year2025.puzzle5;

import util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Part A: 840
 * Part B: 359913027576322
 */
public class Puzzle5 {
    public static void main(String[] args) {
        List<List<String>> lists = Utils.splitOnBlankLine(Utils.getInput("2025/input5.txt"));

        List<IngredientRange> ingredientRanges = new ArrayList<>(lists.getFirst()
                .stream()
                .map(IngredientRange::new)
                .toList());

        List<String> ingredients = lists.get(1);

        long partA = ingredients.stream()
                .mapToLong(Long::parseLong)
                .filter(ingredient -> ingredientRanges.stream().anyMatch(range -> range.isInRange(ingredient)))
                .count();

        System.out.println("Part A: " + partA);

        boolean merged;
        Collections.sort(ingredientRanges);
        do {
            merged = false;
            for (int i = ingredientRanges.size() - 1; i >= 1; i--) {
                IngredientRange last = ingredientRanges.remove(i);
                IngredientRange secondToLast = ingredientRanges.remove(i - 1);

                if (last.overlapsWith(secondToLast)) {
                    ingredientRanges.add(secondToLast.mergeWith(last));
                    merged = true;
                } else {
                    ingredientRanges.add(secondToLast);
                    ingredientRanges.add(last);
                }
                Collections.sort(ingredientRanges);
            }
        } while (merged);

        long partB = ingredientRanges.stream()
                .map(IngredientRange::numberOfIngredients)
                .mapToLong(Long::longValue)
                .sum();

        System.out.println("Part B: " + partB);
    }
}
