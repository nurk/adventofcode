package year2021.puzzle7;

import util.Utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class Puzzle7 {
    public static void main(String[] args) {
        List<Integer> input = Utils.getInput("2021/input7.txt",
                        line -> Arrays.stream(line.split(",")).map(Integer::valueOf))
                .stream()
                .flatMap(s -> s)
                .toList();

        Integer min = input.stream().min(Comparator.naturalOrder()).orElseThrow();
        Integer max = input.stream().max(Comparator.naturalOrder()).orElseThrow();

        System.out.println(minimumAlignmentCost(input, min, max, i -> i));
        System.out.println(minimumAlignmentCost(input, min, max, i -> IntStream.rangeClosed(0, i).sum()));
    }

    private static Integer minimumAlignmentCost(List<Integer> input,
                                                Integer min,
                                                Integer max,
                                                IntUnaryOperator fuelStepCostConverter) {
        return IntStream.rangeClosed(min, max)
                .map(position -> alignmentCost(input, position, fuelStepCostConverter))
                .min().orElseThrow();
    }

    private static Integer alignmentCost(List<Integer> input,
                                         Integer position,
                                         IntUnaryOperator fuelStepCostConverter) {
        return input.stream()
                .map(i -> fuelStepCostConverter.applyAsInt(Math.abs(i - position)))
                .mapToInt(Integer::intValue)
                .sum();
    }
}
