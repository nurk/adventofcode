package year2024.puzzle5;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Part A: 5713
 * Part B: 5180
 */
public class Puzzle5 {

    private static final List<Rule> rules = new ArrayList<>();
    private static final List<List<Integer>> updates = new ArrayList<>();

    public static void main(String[] args) {
        Utils.getInput("2024/input5.txt")
                .stream()
                .filter(line -> !line.isEmpty())
                .forEach(line -> {
                    if (StringUtils.contains(line, "|")) {
                        rules.add(new Rule(line));
                    } else {
                        updates.add(new ArrayList<>(Arrays.stream(line.split(","))
                                .map(Integer::parseInt)
                                .toList()));
                    }
                });

        AtomicLong sum = new AtomicLong(0);
        AtomicLong sumBroken = new AtomicLong(0);

        updates.forEach(u -> {
            if (rules.stream().allMatch(rule -> rule.satisfies(u))) {
                sum.getAndAdd(getMiddle(u));
            } else {
                do {
                    rules.forEach(rule -> rule.swapIfNotSatisfies(u));
                } while (!rules.stream().allMatch(rule -> rule.satisfies(u)));
                sumBroken.getAndAdd(getMiddle(u));
            }
        });

        System.out.println("Part A: " + sum.get());
        System.out.println("Part B: " + sumBroken.get());

    }

    public static Integer getMiddle(List<Integer> update) {
        return update.get(update.size() / 2);
    }
}
