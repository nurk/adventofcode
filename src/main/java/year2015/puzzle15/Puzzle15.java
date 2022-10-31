package year2015.puzzle15;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.List;

public class Puzzle15 {

    public static void main(String[] args) {
        List<Ingredient> input = Utils.getInput("2015/input15.txt", Ingredient::new);

        long currentMax = 0;
        long caloriesMax = 0;

        for (int i = 0; i <= 100; i++) {
            for (int j = 0; j <= 100 - i; j++) {
                for (int k = 0; k <= 100 - j - i; k++) {
                    for (int l = 0; l <= 100 - k - j - i; l++) {

                        input.get(0).setTeaspoons(i);
                        input.get(1).setTeaspoons(j);
                        input.get(2).setTeaspoons(k);
                        input.get(3).setTeaspoons(l);

                        long max = Math.max(input.get(0).getCapacity() + input.get(1).getCapacity() + input.get(2)
                                .getCapacity() + input.get(3).getCapacity(), 0L) *
                                Math.max(input.get(0).getDurability() + input.get(1).getDurability() + input.get(2)
                                        .getDurability() + input.get(3).getDurability(), 0L) *
                                Math.max(input.get(0).getFlavor() + input.get(1).getFlavor() + input.get(2)
                                        .getFlavor() + input.get(3).getFlavor(), 0L) *
                                Math.max(input.get(0).getTexture() + input.get(1).getTexture() + input.get(2)
                                        .getTexture() + input.get(3).getTexture(), 0L);
                        if (max > currentMax) {
                            currentMax = max;
                        }
                        if (input.get(0).getCalories() + input.get(1).getCalories() + input.get(2)
                                .getCalories() + input.get(3).getCalories() == 500L && max > caloriesMax) {
                            caloriesMax = max;
                        }
                    }
                }
            }
        }
        input.forEach(System.out::println);
        System.out.println(currentMax);
        System.out.println(caloriesMax);
    }

    private static class Ingredient {
        private final int capacity;
        private final int durability;
        private final int flavor;
        private final int texture;
        private final int calories;
        private int teaspoons;
        private final String name;

        public Ingredient(String line) {
            //Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
            name = StringUtils.substringBefore(line, ":");
            String[] split = StringUtils.substringAfter(line, ":").strip().split(", ");
            capacity = Integer.parseInt(split[0].split(" ")[1]);
            durability = Integer.parseInt(split[1].split(" ")[1]);
            flavor = Integer.parseInt(split[2].split(" ")[1]);
            texture = Integer.parseInt(split[3].split(" ")[1]);
            calories = Integer.parseInt(split[4].split(" ")[1]);
        }

        public void setTeaspoons(int teaspoons) {
            this.teaspoons = teaspoons;
        }

        public int getCapacity() {
            return capacity * teaspoons;
        }

        public int getDurability() {
            return durability * teaspoons;
        }

        public int getFlavor() {
            return flavor * teaspoons;
        }

        public int getTexture() {
            return texture * teaspoons;
        }

        public int getCalories() {
            return calories * teaspoons;
        }

        @Override
        public String toString() {
            return "Ingredient{" +
                    "capacity=" + capacity +
                    ", durability=" + durability +
                    ", flavor=" + flavor +
                    ", texture=" + texture +
                    ", calories=" + calories +
                    ", teaspoons=" + teaspoons +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
