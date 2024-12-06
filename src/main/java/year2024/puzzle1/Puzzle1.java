package year2024.puzzle1;

import util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Part A: 1319616
 * Part B: 27267728
 */
public class Puzzle1 {

    public static void main(String[] args) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        Utils.getInput("2024/input1.txt").forEach(line -> {
            String[] s = line.split(" {3}");
            left.add(Integer.valueOf(s[0]));
            right.add(Integer.valueOf(s[1]));
        });

        Collections.sort(left);
        Collections.sort(right);

        int sum = 0;

        for (int i = 0; i < left.size(); i++) {
            sum += Math.abs(left.get(i) - right.get(i));
        }

        System.out.println("Part A: " + sum);

        long similarity = 0;

        for (Integer i : left) {
            similarity += i *right.stream()
                    .filter(r -> r.equals(i))
                    .count();
        }

        System.out.println("Part B: " + similarity);
    }
}
