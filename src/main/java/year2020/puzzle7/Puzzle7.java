package year2020.puzzle7;

import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Puzzle7 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String searchColor = "shiny gold";

        List<Bag> bags = new ArrayList<>();
        Files.readAllLines(Utils.getInputPath("2020/input7.txt")).forEach(
                line -> {
                    bags.add(new Bag((line)));
                }
        );

        Set<String> colors = new HashSet<>();
        colors.add(searchColor);
        int currentAmount = 0;
        while (currentAmount != colors.size()) {
            currentAmount = colors.size();
            for (String color : colors.toArray(new String[0])) {
                for (Bag bag : bags) {
                    if (bag.callHoldColor(color)) {
                        colors.add(bag.getColor());
                    }
                }
            }
        }

        colors.remove(searchColor);

        System.out.println("PuzzleA: " + colors.size());

        solveB(bags);
    }

    private static void solveB(List<Bag> bags) {
        Bag shinyGoldBag = findBag(bags, "shiny gold");
        System.out.println(shinyGoldBag.countBags(bags) - 1);
    }

    private static Bag findBag(List<Bag> bags, String color) {
        for (Bag bag : bags) {
            if (bag.getColor().equals(color)) {
                return bag;
            }
        }
        return null;
    }
}
