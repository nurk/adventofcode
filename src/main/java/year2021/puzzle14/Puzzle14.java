package year2021.puzzle14;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Puzzle14 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2021/input14test.txt", (s) -> s);
        String start = input.get(0);
        List<String> polymer = input.subList(input.indexOf("") + 1, input.size());

        Map<String, String> polymers = new HashMap<>();
        polymer.stream()
                .map(s -> s.split(" -> "))
                .forEach(split -> polymers.put(split[0], split[0].charAt(0) + split[1]));

        partA(start, polymers);
        //partB(start, polymers);
    }

    private static void partA(String start, Map<String, String> polymers) {
        int i = 0;
        while (i < 10) {
            i++;
            start = doStep(start, polymers);
        }
        final String s = start;

        int min = Arrays.stream(start.split("")).map(c -> StringUtils.countMatches(s, c))
                .mapToInt(count -> count)
                .min()
                .orElseThrow();
        int max = Arrays.stream(start.split("")).map(c -> StringUtils.countMatches(s, c))
                .mapToInt(count -> count)
                .max()
                .orElseThrow();
        System.out.println(max - min);
    }

    private static void partB(String start, Map<String, String> polymers) {
        int i = 0;
        while (i < 40) {
            i++;
            System.out.println(i);
            start = doStep(start, polymers);
        }
        final String s = start;

        int min = Arrays.stream(start.split("")).map(c -> StringUtils.countMatches(s, c))
                .mapToInt(count -> count)
                .min()
                .orElseThrow();
        int max = Arrays.stream(start.split("")).map(c -> StringUtils.countMatches(s, c))
                .mapToInt(count -> count)
                .max()
                .orElseThrow();
        System.out.println(max - min);
    }

    private static String doStep(String start, Map<String, String> polymers) {
        return IntStream.range(0, start.length() - 1)
                .boxed()
                .map(i -> getPair(String.valueOf(start.charAt(i)), String.valueOf(start.charAt(i + 1)), polymers))
                .collect(Collectors.joining()) + start.charAt(start.length() - 1);
    }

    private static String getPair(String s, String s1, Map<String, String> polymers) {
        return polymers.getOrDefault(s + s1, s);
    }
}
