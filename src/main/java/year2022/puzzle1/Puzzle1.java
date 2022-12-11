package year2022.puzzle1;

import util.Utils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class Puzzle1 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2022/input1.txt");

        int[] splitIndexes = Utils.getSplitIndexes(input);

        List<Integer> sortedCalories = IntStream.range(0, splitIndexes.length - 1)
                .mapToObj(i -> input.subList(splitIndexes[i] + 1, splitIndexes[i + 1]))
                .map(strings -> strings.stream()
                        .map(Integer::valueOf)
                        .reduce(0, Integer::sum))
                .sorted(Comparator.reverseOrder())
                .toList();

        System.out.println(sortedCalories.get(0));
        System.out.println(sortedCalories.get(0) + sortedCalories.get(1) + sortedCalories.get(2));
    }
}
