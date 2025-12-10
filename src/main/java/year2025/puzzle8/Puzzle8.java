package year2025.puzzle8;

import org.apache.commons.lang3.tuple.Pair;
import util.Utils;

import java.util.*;

/**
 * Part A: 57564
 * Part B: 133296744
 */
public class Puzzle8 {

    public static void main(String[] args) {
        List<JunctionBox> junctionBoxes = Utils.getInput("2025/input8.txt", JunctionBox::new);
        int connectionsToMake = junctionBoxes.size() < 1000 ? 10 : 1000;

        Map<Double, Pair<JunctionBox, JunctionBox>> allDistances = new TreeMap<>();

        for (JunctionBox junctionBox : junctionBoxes) {
            for (JunctionBox junctionBoxOther : junctionBoxes) {
                if (!junctionBox.equals(junctionBoxOther)) {
                    double distance = junctionBox.computeDistance(junctionBoxOther);
                    allDistances.put(distance, Pair.of(junctionBox, junctionBoxOther));
                }
            }
        }

        partA(allDistances, connectionsToMake);
        partB(allDistances, junctionBoxes.size());
    }

    private static void partB(Map<Double, Pair<JunctionBox, JunctionBox>> allDistances, int junctionBoxesCount) {
        Set<Set<JunctionBox>> loops = new HashSet<>();
        List<Map.Entry<Double, Pair<JunctionBox, JunctionBox>>> sortedDistances = new ArrayList<>(allDistances.entrySet()
                .stream()
                .toList());

        Pair<JunctionBox, JunctionBox> pair;
        do {
            pair = sortedDistances.removeFirst().getValue();

            Pair<JunctionBox, JunctionBox> localPair = pair;

            List<Set<JunctionBox>> list = loops.stream()
                    .filter(set -> set.contains(localPair.getLeft()) || set.contains(localPair.getRight()))
                    .toList();

            if (list.isEmpty()) {
                Set<JunctionBox> newSet = new HashSet<>();
                newSet.add(pair.getLeft());
                newSet.add(pair.getRight());
                loops.add(newSet);
            } else {
                list.forEach(l -> {
                    l.add(localPair.getLeft());
                    l.add(localPair.getRight());
                });
            }


            Set<Set<JunctionBox>> mergedLoops = new HashSet<>();
            for (Set<JunctionBox> loop : loops) {
                List<Set<JunctionBox>> loopsToBeMerged = mergedLoops.stream()
                        .filter(ml -> !Collections.disjoint(ml, loop))
                        .toList();

                if (!loopsToBeMerged.isEmpty()) {
                    loopsToBeMerged.forEach(ml -> ml.addAll(loop));
                } else {
                    mergedLoops.add(loop);
                }
            }
            loops = mergedLoops;
        } while (!(loops.size() == 1 && loops.stream().findFirst().orElseThrow().size() == junctionBoxesCount));

        System.out.println("Part B: " + Double.valueOf(pair.getLeft().getX())
                .longValue() * Double.valueOf(pair.getRight().getX()).longValue());
    }

    private static void partA(Map<Double, Pair<JunctionBox, JunctionBox>> allDistances, int connectionsToMake) {
        Set<Set<JunctionBox>> loops = new HashSet<>();
        List<Map.Entry<Double, Pair<JunctionBox, JunctionBox>>> sortedDistances = allDistances.entrySet()
                .stream()
                .toList();

        for (int i = 0; i < connectionsToMake; i++) {
            Pair<JunctionBox, JunctionBox> pair = sortedDistances.get(i).getValue();

            List<Set<JunctionBox>> list = loops.stream()
                    .filter(set -> set.contains(pair.getLeft()) || set.contains(pair.getRight()))
                    .toList();

            if (list.isEmpty()) {
                Set<JunctionBox> newSet = new HashSet<>();
                newSet.add(pair.getLeft());
                newSet.add(pair.getRight());
                loops.add(newSet);
            } else {
                list.forEach(l -> {
                    l.add(pair.getLeft());
                    l.add(pair.getRight());
                });
            }


            Set<Set<JunctionBox>> mergedLoops = new HashSet<>();
            for (Set<JunctionBox> loop : loops) {
                List<Set<JunctionBox>> loopsToBeMerged = mergedLoops.stream()
                        .filter(ml -> !Collections.disjoint(ml, loop))
                        .toList();

                if (!loopsToBeMerged.isEmpty()) {
                    loopsToBeMerged.forEach(ml -> ml.addAll(loop));
                } else {
                    mergedLoops.add(loop);
                }
            }
            loops = mergedLoops;
        }

        long partA = loops.stream()
                .map(Set::size)
                .sorted(Comparator.reverseOrder())
                //.peek(System.out::println)
                .limit(3)
                .mapToLong(Long::valueOf)
                .reduce(1, (a, b) -> a * b);

        System.out.println("Part A: " + partA);
    }
}
