package year2023.puzzle16;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Part A: 6361
 * Part B: 6701
 */
public class Puzzle16 {
    static void main() {
        List<String> input = new ArrayList<>(Utils.getInput("2023/input16.txt", (s) -> s));
        Cave cave = new Cave(input);

        partA(cave);
        partB(cave);
    }

    private static void partB(Cave cave) {
        Map<Triplet<Integer, Integer, Beam.Direction>, Integer> globalIntensity = new HashMap<>();

        for (int row = 0; row < cave.getRows(); row++) {
            doLoop(cave, Beam.Direction.RIGHT, row, 0, globalIntensity);
            doLoop(cave, Beam.Direction.LEFT, row, cave.getColumns() - 1, globalIntensity);
        }

        for (int column = 0; column < cave.getColumns(); column++) {
            doLoop(cave, Beam.Direction.DOWN, 0, column, globalIntensity);
            doLoop(cave, Beam.Direction.UP, cave.getRows() - 1, column, globalIntensity);
        }

        int maxIntensity = globalIntensity.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        System.out.println("Part B: " + maxIntensity);
    }

    private static void doLoop(Cave cave,
                               Beam.Direction direction,
                               int row,
                               int column,
                               Map<Triplet<Integer, Integer, Beam.Direction>, Integer> globalIntensity) {
        Beam.clearVisited();

        Map<Pair<Integer, Integer>, Integer> intensity = new HashMap<>();

        List<Beam> beams = new ArrayList<>();
        Beam startBeam = new Beam(new Pair<>(row, column), direction);
        beams.add(startBeam);

        intensity.merge(beams.getFirst().getPosition(), 1, Integer::sum);

        while (!beams.isEmpty()) {
            List<Beam> newBeams = new ArrayList<>();

            for (Beam beam : beams) {
                List<Beam> localBeams = beam.doAction(cave);
                newBeams.addAll(localBeams);
                localBeams.forEach(b -> intensity.merge(b.getPosition(), 1, Integer::sum));
            }

            beams = newBeams;
        }

        globalIntensity.put(new Triplet<>(row, column, direction), intensity.size());
    }

    private static void partA(Cave cave) {
        Map<Triplet<Integer, Integer, Beam.Direction>, Integer> globalIntensity = new HashMap<>();

        doLoop(cave, Beam.Direction.RIGHT, 0, 0, globalIntensity);

        System.out.println("Part A: " + globalIntensity.values().stream().mapToInt(Integer::intValue).max().orElse(0));
    }
}
