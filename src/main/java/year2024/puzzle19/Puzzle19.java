package year2024.puzzle19;

import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Part A: 327
 * Part B: 772696486795255
 */
public class Puzzle19 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2024/input19.txt", (s) -> s));
        List<String> options = Arrays.asList(input.removeFirst().split(", "));

        input.removeFirst();

        int count = 0;
        List<String> possibleTargets = new ArrayList<>();
        for (String target : input) {
            if (new Towel(options).isPossible(target)) {
                possibleTargets.add(target);
                count++;
            }
        }

        System.out.println("Part A: " + count);

        long sum = 0;
        for (String target : possibleTargets) {
            long i = new TowelB(options).countPossibilities(target);
            if (i > 0) {
                sum += i;
            }
        }

        System.out.println("Part B: " + sum);
    }
}
