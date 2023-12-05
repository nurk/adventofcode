package year2023.puzzle2;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Game {

    @Getter
    private final int gameId;
    private final List<Map<Colors, Integer>> reveals = new ArrayList<>();

    public Game(String line) {
        String[] firstSplit = line.split(":");
        gameId = Integer.parseInt(StringUtils.removeStart(firstSplit[0], "Game "));

        for (String pull : firstSplit[1].split(";")) {
            Map<Colors, Integer> pullMap = new HashMap<>();
            for (String color : pull.split(",")) {
                String[] split = StringUtils.trim(color).split(" ");
                pullMap.put(Colors.valueOf(split[1]), Integer.parseInt(split[0]));
            }
            reveals.add(pullMap);
        }
    }

    public boolean isPossible(int red, int green, int blue) {
        return reveals.stream()
                .noneMatch(colorsIntegerMap ->
                        colorsIntegerMap.getOrDefault(Colors.red, 0) > red ||
                                colorsIntegerMap.getOrDefault(Colors.green, 0) > green ||
                                colorsIntegerMap.getOrDefault(Colors.blue, 0) > blue);
    }

    public Long getPower() {
        return Arrays.stream(Colors.values())
                .map(color -> reveals.stream()
                        .map(colorsIntegerMap -> colorsIntegerMap.getOrDefault(color, 1))
                        .map(Long::valueOf)
                        .max(Comparator.naturalOrder())
                        .orElse(1L))
                .reduce(1L, (a, b) -> a * b);
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", reveals=\n" + reveals.stream()
                .map(colorsIntegerMap -> colorsIntegerMap.entrySet()
                        .stream()
                        .map(colorsIntegerEntry -> colorsIntegerEntry.getKey() + " " + colorsIntegerEntry.getValue())
                        .collect(Collectors.joining(","))
                )
                .collect(Collectors.joining("\n")) +
                '}';
    }
}
