package year2021.puzzle14;

import util.Utils;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Puzzle14 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2021/input14.txt", (s) -> s);
        String start = input.get(0);
        List<String> polymer = input.subList(input.indexOf("") + 1, input.size());

        Map<String, String> polymers = new HashMap<>();
        polymer.stream()
                .map(s -> s.split(" -> "))
                .forEach(split -> polymers.put(split[0], split[1]));

        calculate(start, polymers, 10);
        calculate(start, polymers, 40);
    }

    private static void calculate(String start, Map<String, String> polymers, int steps) {
        int i = 0;
        Map<String, BigInteger> pairs = toPairs(start);
        while (i < steps) {
            i++;
            pairs = doStep(pairs, polymers);
        }

        Map<String, BigInteger> counters = pairsToCounters(start, pairs);

        System.out.println(counters.values()
                .stream()
                .max(Comparator.naturalOrder())
                .orElseThrow()
                .subtract(counters.values().stream().min(Comparator.naturalOrder()).orElseThrow()));
    }

    private static Map<String, BigInteger> pairsToCounters(String start, Map<String, BigInteger> pairs) {
        Map<String, BigInteger> counters = new HashMap<>();
        pairs.forEach((key, value) -> counters.merge(String.valueOf(key.charAt(0)), value, BigInteger::add));
        counters.merge(String.valueOf(start.charAt(start.length() - 1)), BigInteger.ONE, BigInteger::add);
        return counters;
    }

    private static Map<String, BigInteger> toPairs(String start) {
        Map<String, BigInteger> pairs = new HashMap<>();
        IntStream.range(0, start.length() - 1)
                .forEach(i -> pairs.merge(start.charAt(i) + String.valueOf(start.charAt(i + 1)),
                        BigInteger.ONE,
                        BigInteger::add));
        return pairs;
    }

    private static Map<String, BigInteger> doStep(Map<String, BigInteger> pairs, Map<String, String> polymers) {
        Map<String, BigInteger> newPairs = new HashMap<>();
        pairs.forEach((key, value) -> {
            if (polymers.containsKey(key)) {
                String leftPair = key.charAt(0) + polymers.get(key);
                String rightPair = polymers.get(key) + key.charAt(1);
                newPairs.merge(leftPair, value, BigInteger::add);
                newPairs.merge(rightPair, value, BigInteger::add);
            } else {
                newPairs.merge(key, value, BigInteger::add);
            }
        });

        return newPairs;
    }
}
