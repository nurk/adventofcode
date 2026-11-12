package year2020.puzzle24;

import org.javatuples.Pair;
import util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Puzzle24 {

    static Map<String, Pair<Double, Double>> directions = Map.of(
            "e", Pair.with(1.0, 0.0),
            "w", Pair.with(-1.0, 0.0),
            "ne", Pair.with(0.5, 1.0),
            "nw", Pair.with(-0.5, 1.0),
            "se", Pair.with(0.5, -1.0),
            "sw", Pair.with(-0.5, -1.0)
    );

    static void main() {
        Set<Pair<Double, Double>> blackTiles = new HashSet<>();
        List<String> inputLines = Utils.getInput("2020/input24.txt");

        for (String line : inputLines) {
            List<String> parsedDirections = parseDirections(line);
            Pair<Double, Double> position = Pair.with(0.0, 0.0);
            for (String direction : parsedDirections) {
                Pair<Double, Double> move = directions.get(direction);
                position = Pair.with(position.getValue0() + move.getValue0(), position.getValue1() + move.getValue1());
            }
            if (!blackTiles.add(position)) {
                blackTiles.remove(position);
            }
        }

        System.out.println("Part A: " + blackTiles.size());

        for (int day = 1; day <= 100; day++) {
            blackTiles = simulateDay(blackTiles);
        }
        System.out.println("Part B: " + blackTiles.size());
    }

    private static Set<Pair<Double, Double>> simulateDay(Set<Pair<Double, Double>> blackTiles) {
        Map<Pair<Double, Double>, Integer> blackNeighborCounts = new HashMap<>();
        for (Pair<Double, Double> tile : blackTiles) {
            blackNeighborCounts.putIfAbsent(tile, 0);
            for (Pair<Double, Double> direction : directions.values()) {
                Pair<Double, Double> neighbor = Pair.with(
                        tile.getValue0() + direction.getValue0(),
                        tile.getValue1() + direction.getValue1()
                );
                blackNeighborCounts.merge(neighbor, 1, Integer::sum);
            }
        }

        Set<Pair<Double, Double>> nextBlackTiles = new HashSet<>();
        blackNeighborCounts.forEach((tile, neighborCount) -> {
            if (neighborCount == 2 || (neighborCount == 1 && blackTiles.contains(tile))) {
                nextBlackTiles.add(tile);
            }
        });
        return nextBlackTiles;
    }

    static List<String> parseDirections(String line) {
        List<String> parsedDirections = new ArrayList<>();
        for (int i = 0; i < line.length(); ) {
            char first = line.charAt(i);
            String direction = "";
            if (first == 'e' || first == 'w') {
                direction = String.valueOf(first);
                i++;
            } else if (first == 'n' || first == 's') {
                direction = line.substring(i, i + 2);
                i += 2;
            }
            parsedDirections.add(direction);
        }
        return parsedDirections;
    }
}
