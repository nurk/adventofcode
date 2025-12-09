package year2025.puzzle8;

import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.math3.util.Pair;
import util.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class Puzzle8 {

    public Puzzle8() {
    }

    public static void main(String[] args) {
        List<JunctionBox> junctionBoxes = Utils.getInput("2025/input8.txt", JunctionBox::new);
        int connectionsToMake = 1000;


        // connect 10 closest junction boxes
        // find 1 not connected junction box that has the closest other junction box

        // closest first iteration
        // 162,817,812 and 425,690,689
        // 162,817,812 and 431,825,988
        // 906,360,560 and 805,96,715
        // 431,825,988 and 425,690,689

        Set<JunctionBoxPair> shortestPairs = new HashSet<>();

        for (JunctionBox junctionBox : junctionBoxes) {
            JunctionBoxPair closestPair;

            List<JunctionBox> possiblePairs = new ArrayList<>(junctionBoxes);

            do {
                JunctionBox closestJunctionBox = possiblePairs.stream()
                        .filter(j -> !j.equals(junctionBox))
                        .min(Comparator.comparingDouble(junctionBox::computeDistance))
                        .orElseThrow();

                possiblePairs.remove(closestJunctionBox);

                closestPair = new JunctionBoxPair(junctionBox,
                        closestJunctionBox,
                        junctionBox.computeDistance(closestJunctionBox));
            } while (shortestPairs.contains(closestPair));
            shortestPairs.add(closestPair);
        }

        List<JunctionBoxPair> sortedList = new ArrayList<>(shortestPairs.stream()
                .sorted(Comparator.comparing(JunctionBoxPair::getDistance))
                .toList());

        Set<Set<JunctionBox>> loops = new HashSet<>();
        int pairParsed = 0;
        do {
            if (sortedList.isEmpty()) {
                break;
            }
            JunctionBoxPair pair = sortedList.removeFirst();
            pairParsed++;

            List<Set<JunctionBox>> list = loops.stream()
                    .filter(set -> set.contains(pair.getBox1()) || set.contains(pair.getBox2()))
                    .toList();

            if(list.isEmpty()){
                Set<JunctionBox> newSet = new HashSet<>();
                newSet.add(pair.getBox1());
                newSet.add(pair.getBox2());
                loops.add(newSet);
            }else {
                list.forEach(l -> {
                    l.add(pair.getBox1());
                    l.add(pair.getBox2());
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
        } while (pairParsed < connectionsToMake);

        long partA = loops.stream()
                .map(Set::size)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToLong(Long::valueOf)
                .reduce(1, (a, b) -> a * b);

        System.out.println("Part A: " + partA);

        // 1100 too low
        // 7038 too low
        // 7866 too low
        // 12012 not correct
        // 34481 not correct
    }
}
