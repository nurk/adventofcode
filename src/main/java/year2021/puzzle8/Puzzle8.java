package year2021.puzzle8;

import util.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class Puzzle8 {
    public static void main(String[] args) {
        List<String[]> input = new ArrayList<>(Utils.getInput("2021/input8.txt", (s) -> s.split(" \\| ")));

        partA(input);
        partB(input);
    }

    private static void partB(List<String[]> input) {
        long sum = input.stream()
                .map(line -> {
                    Map<Integer, String> digitsMap = convertSignalsToDigits(line[0]);
                    return Arrays.stream(line[1].split(" "))
                            .map(ss -> getDigitFromSegmentString(ss, digitsMap))
                            .collect(Collectors.joining(""));
                })
                .mapToLong(Long::valueOf)
                .sum();

        System.out.println(sum);
    }

    private static Map<Integer, String> convertSignalsToDigits(String signals) {
        Map<Integer, List<String>> signalLengthMap = Arrays.stream(signals.split(" "))
                .map(Puzzle8::sortedString)
                .collect(Collectors.groupingBy(String::length));

        Map<Integer, String> digitsMap = new HashMap<>();

        digitsMap.put(1, signalLengthMap.get(2).get(0));
        digitsMap.put(4, signalLengthMap.get(4).get(0));
        digitsMap.put(7, signalLengthMap.get(3).get(0));
        digitsMap.put(8, signalLengthMap.get(7).get(0));
        signalLengthMap.get(6).forEach(ss -> {
            if (!hasSameSegments(ss, digitsMap.get(1))) {
                digitsMap.put(6, ss);
            } else if (hasSameSegments(ss, digitsMap.get(4))) {
                digitsMap.put(9, ss);
            } else {
                digitsMap.put(0, ss);
            }
        });
        signalLengthMap.get(5).forEach(ss -> {
            if (hasSameSegments(ss, digitsMap.get(7))) {
                digitsMap.put(3, ss);
            } else if (hasSameSegments(digitsMap.get(6), ss)) {
                digitsMap.put(5, ss);
            } else {
                digitsMap.put(2, ss);
            }
        });
        return digitsMap;
    }

    private static void partA(List<String[]> input) {
        long uniqueDigitsByLength = input.stream()
                .map(s -> s[1])
                .map(s -> s.split(" "))
                .flatMap(Arrays::stream)
                .map(String::length)
                .filter(length -> List.of(2, 4, 3, 7).contains(length))
                .count();
        System.out.println(uniqueDigitsByLength);
    }

    private static String sortedString(String s) {
        return Arrays.stream(s.split("")).sorted(Comparator.naturalOrder()).collect(Collectors.joining());
    }

    private static boolean hasSameSegments(String testString, String givenString) {
        return Arrays.stream(testString.split(""))
                .toList()
                .containsAll(Arrays.stream(givenString.split("")).toList());
    }

    private static String getDigitFromSegmentString(String segmentString, Map<Integer, String> digitsMap) {
        return digitsMap.entrySet()
                .stream()
                .filter(integerStringEntry -> integerStringEntry.getValue().equals(sortedString(segmentString)))
                .findFirst()
                .map(Map.Entry::getKey)
                .map(Object::toString)
                .orElse("");

    }
}
