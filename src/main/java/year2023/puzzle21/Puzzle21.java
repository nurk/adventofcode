package year2023.puzzle21;

import org.javatuples.Pair;
import util.Utils;

import java.util.*;

/**
 * Part A: 3724
 */
public class Puzzle21 {
    static void main() {
        List<String> input = new ArrayList<>(Utils.getInput("2023/input21.txt", (s) -> s));

        Garden garden = new Garden(input);

        System.out.println("Part A: " + solve(garden, 64));
    }

    static long solve(Garden garden, int steps) {
        Set<Pair<Integer, Integer>> gardenPlots = new HashSet<>();
        gardenPlots.add(garden.getStartPosition());

        for (int i = 0; i < steps; i++) {
            Set<Pair<Integer, Integer>> newGardenPlots = new HashSet<>();
            for (Pair<Integer, Integer> plot : gardenPlots) {
                int row = plot.getValue0();
                int column = plot.getValue1();

                Pair<Integer, Integer> up = new Pair<>(row - 1, column);
                Pair<Integer, Integer> down = new Pair<>(row + 1, column);
                Pair<Integer, Integer> left = new Pair<>(row, column - 1);
                Pair<Integer, Integer> right = new Pair<>(row, column + 1);

                if (garden.isGardenPlot(up)) {
                    newGardenPlots.add(up);
                }
                if (garden.isGardenPlot(down)) {
                    newGardenPlots.add(down);
                }
                if (garden.isGardenPlot(left)) {
                    newGardenPlots.add(left);
                }
                if (garden.isGardenPlot(right)) {
                    newGardenPlots.add(right);
                }
            }
            gardenPlots = newGardenPlots;
        }

        return gardenPlots.size();
    }
}
