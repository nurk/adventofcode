package year2019.puzzle6;

import org.apache.commons.lang3.tuple.Pair;
import util.Utils;

import java.util.*;

/**
 * Part A: 358244
 * Part B: 517
 */
public class Puzzle6 {

    public static void main(String[] args) {
        Map<String, Planet> planets = new HashMap<>();

        Utils.getInput("2019/input6.txt").forEach(s -> {
            String[] split = s.split("\\)");
            planets.putIfAbsent(split[0], new Planet(split[0]));
            planets.putIfAbsent(split[1], new Planet(split[1]));
            planets.get(split[1]).setOrbiting(planets.get(split[0]));
        });

        long totalCount = planets.values().stream().mapToLong(Planet::countDirectAndIndirectOrbits).sum();
        System.out.println("Part A: " + totalCount);

        long shortestPath = shortestPath(planets.get("YOU"), planets.get("SAN")) - 2;
        System.out.println("Part B: " + shortestPath);
    }

    private static Long shortestPath(Planet start, Planet end) {
        // FIFO
        Deque<Planet> queue = new ArrayDeque<>();
        queue.addLast(start);

        Map<Planet, Pair<Long, List<Planet>>> costSoFar = new HashMap<>();

        costSoFar.put(start, Pair.of(0L, List.of(start)));

        while (!queue.isEmpty()) {
            Planet current = queue.pop();

            if (current.equals(end)) {
                return costSoFar.get(current).getLeft();
            }
            long currentCost = costSoFar.get(current).getLeft();

            long newCost = currentCost + 1;

            List<Planet> neighbours = current.getNeighborPlanets();

            for (Planet neighbour : neighbours) {
                if (!costSoFar.containsKey(neighbour) || newCost < costSoFar.get(neighbour).getLeft()) {
                    List<Planet> newList = new ArrayList<>(costSoFar.get(current).getRight());
                    newList.add(neighbour);
                    costSoFar.put(neighbour, Pair.of(newCost, newList));
                    queue.addLast(neighbour);
                }
            }
        }

        return costSoFar.getOrDefault(end, Pair.of(Long.MAX_VALUE, List.of())).getLeft();
    }
}
