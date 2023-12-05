package year2023.puzzle1;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/*
Part A: 55488
Part B: 55614
 */
public class Puzzle1 {

    private static final Map<String, String> MAPPING = Map.of("one",
            "1",
            "two",
            "2",
            "three",
            "3",
            "four",
            "4",
            "five",
            "5",
            "six",
            "6",
            "seven",
            "7",
            "eight",
            "8",
            "nine",
            "9");

    public static void main(String[] args) {
        partA();
        partB();
    }

    private static void partA() {
        AtomicLong result = new AtomicLong(0L);
        Utils.getInput("2023/input1.txt").forEach(line -> {
            String firstDigit = null;
            String lastDigit = null;
            for (String s : line.split("")) {
                if (StringUtils.isNumeric(s)) {
                    if (firstDigit == null) {
                        firstDigit = s;
                    }
                    lastDigit = s;
                }
            }
            long number = Integer.parseInt(firstDigit + lastDigit);
            result.addAndGet(number);
        });

        System.out.println("Part A: " + result.get());
    }

    private static void partB() {
        AtomicLong result = new AtomicLong(0L);
        Utils.getInput("2023/input1.txt").forEach(originalLine -> {
            var variables = new Object() {
                String firstDigit = null;
                String lastDigit = null;
                String line = originalLine;

                @Override
                public String toString() {
                    return "$classname{" +
                            "firstDigit='" + firstDigit + '\'' +
                            ", lastDigit='" + lastDigit + '\'' +
                            ", line='" + line + '\'' +
                            '}';
                }
            };

            while (variables.firstDigit == null && !variables.line.isEmpty()) {
                MAPPING.keySet()
                        .stream()
                        .filter(variables.line::startsWith)
                        .findFirst()
                        .ifPresentOrElse(
                                letters -> variables.firstDigit = MAPPING.get(letters),
                                () -> {
                                    String firstChar = String.valueOf(variables.line.charAt(0));
                                    if (StringUtils.isNumeric(firstChar)) {
                                        variables.firstDigit = firstChar;
                                    }
                                    variables.line = StringUtils.removeStart(variables.line, firstChar);
                                });
            }

            variables.line = originalLine;
            while (variables.lastDigit == null && !variables.line.isEmpty()) {
                MAPPING.keySet()
                        .stream()
                        .filter(variables.line::endsWith)
                        .findFirst()
                        .ifPresentOrElse(
                                letters -> variables.lastDigit = MAPPING.get(letters),
                                () -> {
                                    String lastChar = String.valueOf(variables.line.charAt(variables.line.length() - 1));
                                    if (StringUtils.isNumeric(lastChar)) {
                                        variables.lastDigit = lastChar;
                                    }
                                    variables.line = StringUtils.removeEnd(variables.line, lastChar);
                                });
            }
            long number = Integer.parseInt(variables.firstDigit + variables.lastDigit);
            result.addAndGet(number);
        });

        System.out.println("Part B: " + result.get());
    }
}
