package year2016.puzzle24;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.iterators.PermutationIterator;
import org.javatuples.Pair;
import util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Part A: 428
 * Part B: 680
 */
public class Puzzle24 {
    static void main() {
        List<String> input = new ArrayList<>(Utils.getInput("2016/input24.txt", (s) -> s));

        HVAC hvac = new HVAC(input);
        List<String> numbers = hvac.getAllNumbers();

        Map<Pair<String, String>, Long> shortestPaths = new HashMap<>();

        Lists.cartesianProduct(numbers, numbers)
                .stream()
                .filter(pair -> !pair.getFirst().equals(pair.get(1)))
                .forEach(pair -> shortestPaths.put(Pair.with(pair.getFirst(), pair.get(1)),
                        hvac.shortestPathLength(pair.getFirst(), pair.get(1))));

        List<String> others = new ArrayList<>(numbers);
        others.remove("0");

        long shortestOverall = IteratorUtils.stream(new PermutationIterator<>(others))
                .mapToLong(permutation -> getPermutationLength(permutation, shortestPaths, false))
                .min()
                .orElseThrow();

        System.out.println("Part A: " + shortestOverall);


        shortestOverall = IteratorUtils.stream(new PermutationIterator<>(others))
                .mapToLong(permutation -> getPermutationLength(permutation, shortestPaths, true))
                .min()
                .orElseThrow();

        System.out.println("Part B: " + shortestOverall);
    }

    private static long getPermutationLength(List<String> permutation,
                                             Map<Pair<String, String>, Long> shortestPaths,
                                             boolean isPartB) {
        List<String> perm = new ArrayList<>(permutation);
        if (isPartB) {
            perm.add("0");
        }
        long total = 0;
        String current = "0";
        for (String next : perm) {
            total += shortestPaths.get(Pair.with(current, next));
            current = next;
        }
        return total;
    }
}
