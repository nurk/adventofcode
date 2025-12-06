package year2025.puzzle6;

import java.util.Arrays;
import java.util.List;

public class Homework {
    private final List<String> numbersString;
    private final String operation;

    public Homework(List<String> numbersString, String operation) {
        this.numbersString = numbersString;
        this.operation = operation;
    }

    public long calculatePartA() {
        return doCalculation(numbersString);
    }

    public long calculatePartB() {
        String[] transposedNumbers = new String[numbersString.getFirst().length()];
        Arrays.fill(transposedNumbers, "");

        for (int i = 0; i < numbersString.getFirst().length(); i++) {
            for (String s : numbersString) {
                transposedNumbers[i] += s.charAt(i);
            }
        }

        return doCalculation(Arrays.asList(transposedNumbers));
    }

    private long doCalculation(List<String> numbersString) {
        List<Long> numbers = numbersString.stream()
                .map(String::trim)
                .map(Long::parseLong)
                .toList();
        if ("+".equals(operation)) {
            return numbers.stream()
                    .mapToLong(Long::longValue)
                    .sum();
        } else {
            return numbers.stream()
                    .mapToLong(Long::longValue)
                    .reduce(1, (a, b) -> a * b);
        }
    }

    @Override
    public String toString() {
        return "Homework{" +
                "numbers=" + numbersString +
                ", operation='" + operation + '\'' +
                '}';
    }
}
