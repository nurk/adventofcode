package year2022.puzzle1;

import util.Utils;

import java.util.Comparator;
import java.util.List;

public class Puzzle1 {
    //69206
    //197400
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2022/input1.txt");

        List<Integer> sortedCalories = Utils.splitOnBlankLine(input)
                .stream()
                .map(subList -> subList.stream()
                        .map(Integer::valueOf)
                        .reduce(0, Integer::sum))
                .sorted(Comparator.reverseOrder())
                .toList();

        System.out.println(sortedCalories.get(0));
        System.out.println(sortedCalories.get(0) + sortedCalories.get(1) + sortedCalories.get(2));
    }
}
