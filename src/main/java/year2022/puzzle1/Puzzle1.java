package year2022.puzzle1;

import util.Utils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Puzzle1 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2022/input1.txt");

        int[] splitIndexes = Stream.of(IntStream.of(-1),
                        IntStream.range(0, input.size()).filter(i -> input.get(i).isBlank()),
                        IntStream.of(input.size()))
                .flatMapToInt(s -> s).toArray();

        List<Integer> sortedCalories = IntStream.range(0, splitIndexes.length - 1)
                .mapToObj(i -> input.subList(splitIndexes[i] + 1, splitIndexes[i + 1]))
                .map(strings -> strings.stream()
                        .map(Integer::valueOf)
                        .reduce(0, Integer::sum))
                .sorted(Integer::compareTo)
                .sorted(Comparator.reverseOrder())
                .toList();

        System.out.println(sortedCalories.get(0));
        System.out.println(sortedCalories
                .get(0) + sortedCalories.get(1) + sortedCalories.get(2));
    }
}
