package year2021.puzzle8;

import util.Utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Puzzle8 {
    public static void main(String[] args) {
        List<String[]> input = new ArrayList<>(Utils.getInput("2021/input8.txt", (s) -> s.split(" \\| ")));

        partA(input);
        partB(input);
    }

    private static void partB(List<String[]> input) {
        long sum = input.stream().map(s -> {
                    String[] signals = s[0].split(" ");

                    Map<Long, String> digitsMap = new HashMap<>();
                    LongStream.rangeClosed(0, 9).forEach(l -> digitsMap.put(l, ""));

                    Arrays.stream(signals).map(Puzzle8::sortedString).forEach(ss -> {
                        switch (ss.length()) {
                            case 2 -> digitsMap.replace(1L, ss);
                            case 4 -> digitsMap.replace(4L, ss);
                            case 3 -> digitsMap.replace(7L, ss);
                            case 7 -> digitsMap.replace(8L, ss);
                        }
                    });

                    Arrays.stream(signals).map(Puzzle8::sortedString)
                            .filter(ss -> ss.length() == 6)
                            .forEach(ss -> {
                                        if (!hasSameSegments(ss, digitsMap.get(1L))) {
                                            digitsMap.replace(6L, ss);
                                        } else if (hasSameSegments(ss, digitsMap.get(4L))) {
                                            digitsMap.replace(9L, ss);
                                        } else {
                                            digitsMap.replace(0L, ss);
                                        }
                                    }
                            );

                    Arrays.stream(signals).map(Puzzle8::sortedString)
                            .filter(ss -> ss.length() == 5)
                            .forEach(ss -> {
                                if (hasSameSegments(ss, digitsMap.get(7L))) {
                                    digitsMap.replace(3L, ss);
                                } else if (hasSameSegments(digitsMap.get(6L), ss)) {
                                    digitsMap.replace(5L, ss);
                                } else {
                                    digitsMap.replace(2L, ss);
                                }
                            });

                    return Arrays.stream(s[1].split(" "))
                            .map(ss -> getNominalValue(ss, digitsMap))
                            .collect(Collectors.joining(""));
                })
                .map(Long::valueOf)
                .mapToLong(l -> l)
                .sum();

        System.out.println(sum);
    }

    private static void partA(List<String[]> input) {
        long uniqueDigitsByLength = input.stream()
                .map(s -> s[1])
                .map(s -> s.split(" "))
                .flatMap(Arrays::stream)
                .filter(Puzzle8::isUniqueDigit)
                .count();
        System.out.println(uniqueDigitsByLength);
    }

    private static boolean isUniqueDigit(String digit) {
        return Stream.of(2, 4, 3, 7)
                .anyMatch(i -> digit.length() == i);

    }

    private static String sortedString(String s) {
        return Arrays.stream(s.split("")).sorted(Comparator.naturalOrder()).collect(Collectors.joining());
    }

    private static boolean hasSameSegments(String testString, String givenString) {
        return Arrays.stream(testString.split(""))
                .toList()
                .containsAll(Arrays.stream(givenString.split("")).toList());
    }

    private static String getNominalValue(String ss, Map<Long, String> values) {
        return values.entrySet()
                .stream()
                .filter(longStringEntry -> longStringEntry.getValue().equals(sortedString(ss)))
                .findFirst()
                .map(Map.Entry::getKey)
                .map(Object::toString)
                .orElse("");

    }
}
