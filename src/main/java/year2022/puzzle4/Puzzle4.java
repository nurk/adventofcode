package year2022.puzzle4;

import util.Utils;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Puzzle4 {
    public static void main(String[] args) {
        System.out.println(Utils.getInputAsStream("2022/input4.txt", s -> s.split(","))
                .map(split -> new Object() {
                    final Set<Integer> left = rangeToSet(split[0]);
                    final Set<Integer> right = rangeToSet(split[1]);
                })
                .filter(v -> v.left.containsAll(v.right) || v.right.containsAll(v.left))
                .count());

        System.out.println(Utils.getInputAsStream("2022/input4.txt", s -> s.split(","))
                .map(split -> new Object() {
                    final Set<Integer> left = rangeToSet(split[0]);
                    final Set<Integer> right = rangeToSet(split[1]);
                })
                .filter(v -> v.left.stream().anyMatch(v.right::contains))
                .count());
    }

    private static Set<Integer> rangeToSet(String range) {
        String[] split = range.split("-");
        return IntStream.range(Integer.parseInt(split[0]), Integer.parseInt(split[1]) + 1)
                .boxed()
                .collect(Collectors.toSet());
    }
}
