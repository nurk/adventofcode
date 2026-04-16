package year2016.puzzle8;

import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 110
 * Part B: ZJHRKCPLYJ
 */
public class Puzzle8 {
    static void main() {
        List<String> lines = new ArrayList<>(Utils.getInput("2016/input8.txt", (s) -> s));

        Screen screen = new Screen(6, 50);
        lines.forEach(line -> {
            screen.perform(line);
            System.out.println(screen);
            System.out.println();
        });

        System.out.println("Part A: " + screen.getOnPixelCount());
    }
}
