package year2019.puzzle14;

import util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Puzzle14 {

    static void main() {
        List<Recipe> recipes = Utils.getInput("2019/input14-test.txt", Recipe::new);


        Recipe fuel = recipes.stream()
                .filter(recipe -> recipe.getName().equals("FUEL"))
                .findFirst()
                .orElseThrow();

        Set<String> oreIngredients = recipes.stream()
                .filter(recipe -> recipe.getIngredients().containsKey("ORE"))
                .map(Recipe::getName)
                .collect(Collectors.toSet());

        Map<String, Integer> oreIngredientsNeeded = new HashMap<>(fuel.getIngredients());

        while (!oreIngredients.containsAll(oreIngredientsNeeded.keySet())) {
            Map<String, Integer> newIngredientsNeeded = new HashMap<>(oreIngredientsNeeded);
            oreIngredientsNeeded.forEach((key, value) -> {
                if (!oreIngredients.contains(key)) {
                    newIngredientsNeeded.remove(key);
                    Recipe recipeForIngredient = recipes.stream()
                            .filter(recipe -> recipe.getName().equals(key))
                            .findFirst()
                            .orElseThrow();

                    int multiplier = recipeForIngredient.getTimesRecipeNeeded(value);
                    recipeForIngredient.getIngredients().forEach((key1, value1) -> newIngredientsNeeded.merge(key1,
                            value1 * multiplier,
                            Integer::sum));
                }
            });

            newIngredientsNeeded.entrySet().removeIf(entry -> entry.getValue() == 0);
            oreIngredientsNeeded = newIngredientsNeeded;
        }

        Integer oreNeeded = oreIngredientsNeeded.entrySet()
                .stream().map(entry -> recipes.stream()
                        .filter(recipe -> recipe.getName().equals(entry.getKey()))
                        .findFirst()
                        .orElseThrow()
                        .getOreAmount(entry.getValue())
                )
                .reduce(Integer::sum)
                .orElseThrow();

        System.out.println("Part A:  " + oreNeeded);
    }
}
