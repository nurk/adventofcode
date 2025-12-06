package year2025.puzzle6;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Part A: 5335495999141
 * Part B: 10142723156431
 */
public class Puzzle6 {
    public static void main(String[] args) {
        List<String> input = normalizeInputs(Utils.getInput("2025/input6.txt"));

        List<String> operators = splitOnOperator(Arrays.stream(input.removeLast().split("")).toList())
                .stream()
                .map(list -> String.join("", list))
                .toList();

        List<Homework> homeworks = constructHomework(operators, input);

        long partA = homeworks.stream()
                .map(Homework::calculatePartA)
                .mapToLong(Long::longValue)
                .sum();

        System.out.println("Part A: " + partA);

        long partB = homeworks.stream()
                .map(Homework::calculatePartB)
                .mapToLong(Long::longValue)
                .sum();

        System.out.println("Part B: " + partB);
    }

    private static List<Homework> constructHomework(List<String> operators, List<String> input) {
        List<Homework> homeworks = new ArrayList<>();

        for (String operator : operators) {
            List<String> numbers = new ArrayList<>();
            List<String> newInput = new ArrayList<>();
            for (String line : input) {
                numbers.add(Strings.CS.removeEnd(line.substring(0, operator.length()), " "));
                line = line.substring(operator.length());
                newInput.add(line);
            }
            input = newInput;
            homeworks.add(new Homework(numbers, operator.trim()));
        }
        return homeworks;
    }

    private static List<String> normalizeInputs(List<String> input) {
        Integer maxLength = input.stream()
                .map(String::length)
                .max(Integer::compareTo)
                .orElse(0);

        input = new ArrayList<>(input.stream()
                .map(s -> StringUtils.rightPad(s, maxLength + 1, " "))
                .toList());
        return input;
    }

    public static List<List<String>> splitOnOperator(List<String> input) {
        List<List<String>> output = new ArrayList<>();
        List<String> subList = new ArrayList<>();

        for (String line : input) {
            if (Strings.CS.equals(line, "+") || Strings.CS.equals(line, "*")) {
                if (!subList.isEmpty()) {
                    output.add(subList);
                }
                subList = new ArrayList<>();
                subList.add(line);
            } else {
                subList.add(line);
            }
        }

        output.add(subList);

        return output;
    }
}
