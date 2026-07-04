package year2019.puzzle14;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class Recipe {
    @Getter
    private final Map<String, Integer> ingredients = new HashMap<>();
    @Getter
    private final String name;
    @Getter
    private final int amount;

    public Recipe(String line) {
        //5 QXHFH, 3 LCDVR, 24 MWFP, 1 MSFV, 1 BPDJL, 3 LQGXD, 2 DVGW => 2 KCPSH
        String[] split = line.split(" => ");
        this.name = split[1].split(" ")[1];
        this.amount = Integer.parseInt(split[1].split(" ")[0]);

        for (String ingredient : split[0].split(", ")) {
            ingredients.put(ingredient.split(" ")[1], Integer.parseInt(ingredient.split(" ")[0]));
        }
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "ingredients=" + ingredients +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }

    public int getOreAmount(int amountNeeded) {
        if (ingredients.size() > 1 && !ingredients.containsKey("ORE")) {
            throw new IllegalArgumentException();
        }

        int amountOfOre = ingredients.get("ORE");

        return getTimesRecipeNeeded(amountNeeded) * amountOfOre;
    }

    public int getTimesRecipeNeeded(int amountNeeded) {
        return (amountNeeded + amount - 1) / amount;
    }
}
