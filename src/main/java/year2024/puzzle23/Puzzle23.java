package year2024.puzzle23;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.*;

/**
 * Part A: 1400
 */
public class Puzzle23 {

    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2024/input23.txt"));

        partA(input);

        // this is not working for the real input.
        // I'm building 1 big set with all possible connections
        //partB(input);
    }

    private static void partB(List<String> input) {
        Set<Set<String>> output = new HashSet<>();

        for (String s : input) {
            String nodeA = StringUtils.substringBefore(s, "-");
            String nodeB = StringUtils.substringAfter(s, "-");

            Set<Set<String>> setsToAdd = new HashSet<>();
            for (Set<String> strings : output) {
                if (strings.stream()
                        .anyMatch(ss -> ss.contains(nodeA) || ss.contains(nodeB))) {
                    Set<String> newSet = new HashSet<>(strings);
                    newSet.add(s);
                        setsToAdd.add(newSet);
                }
            }

            Set<String> c = new HashSet<>();
            c.add(s);
            output.add(c);
            output.addAll(setsToAdd);

            System.out.println(input.indexOf(s) + " - " + output.size() + " - " + setsToAdd.size());
        }

        System.out.println(output.size());

        List<List<String>> filteredOutput = new ArrayList<>();

        output.forEach(ou -> {
            Set<String> nodes = new HashSet<>();
            for (String o : ou) {
                nodes.add(StringUtils.substringBefore(o, "-"));
                nodes.add(StringUtils.substringAfter(o, "-"));
            }

            boolean missedConnection = false;
            List<String> nodeList = new ArrayList<>(nodes);
            for (int i = 0; i < nodeList.size() - 1; i++) {
                for (int j = i + 1; j < nodeList.size(); j++) {
                    String nA = nodeList.get(i);
                    String nB = nodeList.get(j);
                    if (ou.stream()
                            .noneMatch(ss -> ss.contains(nA) && ss.contains(nB))) {
                        missedConnection = true;
                    }
                }
            }

            if (!missedConnection) {
                filteredOutput.add(nodeList);
            }
        });

        List<List<String>> list = filteredOutput.stream()
                .sorted(Comparator.comparing(List::size))
                .toList();

        List<String> partB = list.getLast();
        Collections.sort(partB);
        System.out.println("Part B: " + partB);
    }

    private static void partA(List<String> input) {
        Set<Set<String>> triplets = new HashSet<>();
        for (int i = 0; i < input.size() - 2; i++) {
            String current = input.get(i);
            String nodeA = StringUtils.substringBefore(current, "-");
            String nodeB = StringUtils.substringAfter(current, "-");

            for (int j = i + 1; j < input.size() - 1; j++) {
                if (input.get(j).contains(nodeA) || input.get(j).contains(nodeB)) {

                    String nodeAA = StringUtils.substringBefore(input.get(j), "-");
                    String nodeBB = StringUtils.substringAfter(input.get(j), "-");

                    for (int k = j + 1; k < input.size(); k++) {
                        Set<String> match = new HashSet<>();
                        match.add(nodeA);
                        match.add(nodeB);
                        match.add(nodeAA);
                        match.add(nodeBB);
                        String nodeAAA = StringUtils.substringBefore(input.get(k), "-");
                        String nodeBBB = StringUtils.substringAfter(input.get(k), "-");
                        if (match.contains(nodeAAA) && match.contains(nodeBBB)) {
                            match.add(nodeAAA);
                            match.add(nodeBBB);
                            triplets.add(match);
                        }
                    }
                }
            }
        }
        System.out.println("Part A: " + triplets.stream()
                .filter(triplet -> triplet.stream()
                        .anyMatch(ss -> StringUtils.startsWith(ss, "t")))
                .count());
    }
}


