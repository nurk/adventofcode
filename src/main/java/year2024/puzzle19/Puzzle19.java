package year2024.puzzle19;

import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Part A: 327
 */
public class Puzzle19 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2024/input19.txt", (s) -> s));
        List<String> options = Arrays.asList(input.removeFirst().split(", "));

        input.removeFirst();

        int count = 0;
        for (String target : input) {
            System.out.println(input.indexOf(target));
            if (new Towel(options).isPossible(target)) {
                count++;
            }
        }

        System.out.println("Part A: " + count);
    }
}
