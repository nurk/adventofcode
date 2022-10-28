package year2016.puzzle6;

import util.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Puzzle6 {
    public static void main(String[] args) {
        Map<Integer, Map<String, Integer>> map = new HashMap<>();

        Utils.getInput("2016/input6.txt")
                .forEach(line -> {
                    String[] letters = line.split("");
                    for (int i = 0; i < letters.length; i++) {
                        String letter = letters[i];
                        map.putIfAbsent(i, new HashMap<>());
                        map.get(i).merge(letter, 1, Integer::sum);
                    }
                });

        System.out.println(map.values().stream()
                .map(letterMap -> letterMap.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .orElseThrow()
                        .getKey()
                )
                .collect(Collectors.joining()));
        System.out.println(map.values().stream()
                .map(letterMap -> letterMap.entrySet().stream()
                        .min(Map.Entry.comparingByValue())
                        .orElseThrow()
                        .getKey()
                )
                .collect(Collectors.joining()));
    }
}
