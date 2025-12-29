package year2017.puzzle16;

import util.Utils;

import java.util.*;

/**
 * Part A: cknmidebghlajpfo
 * Part B: cbolhmkgfpenidaj
 */
public class Puzzle16 {
    public static void main(String[] args) {
        List<String> moves = Arrays.stream(Utils.getInput("2017/input16.txt").getFirst()
                        .split(","))
                .toList();

        char endChar = moves.size() > 10 ? 'p' : 'e';

        List<String> dancers = new ArrayList<>();
        for (char c = 'a'; c <= endChar; c++) {
            dancers.add(String.valueOf(c));
        }

        Map<Integer, String> seenStates = new HashMap<>();
        seenStates.put(0, String.join("", dancers));

        boolean fastForwarded = false;
        for (int i = 1; i <= 1_000_000_000; i++) {
            for (String move : moves) {
                char moveType = move.charAt(0);
                switch (moveType) {
                    case 's' -> {
                        int spinSize = Integer.parseInt(move.substring(1));
                        List<String> end = new ArrayList<>(dancers.subList(dancers.size() - spinSize, dancers.size()));
                        end.addAll(dancers.subList(0, dancers.size() - spinSize));
                        dancers = end;
                    }
                    case 'x' -> {
                        String[] positions = move.substring(1).split("/");
                        int posA = Integer.parseInt(positions[0]);
                        int posB = Integer.parseInt(positions[1]);
                        String temp = dancers.get(posA);
                        dancers.set(posA, dancers.get(posB));
                        dancers.set(posB, temp);
                    }
                    case 'p' -> {
                        String[] names = move.substring(1).split("/");
                        int posA = dancers.indexOf(names[0]);
                        int posB = dancers.indexOf(names[1]);
                        String temp = dancers.get(posA);
                        dancers.set(posA, dancers.get(posB));
                        dancers.set(posB, temp);
                    }
                }
            }
            String currentState = String.join("", dancers);
            if (!fastForwarded && seenStates.containsValue(currentState)) {
                int firstSeen = seenStates.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(currentState))
                        .findFirst()
                        .orElseThrow().getKey();

                int cycleLength = i - firstSeen;
                int cyclesInLoop = (1_000_000_000 - firstSeen) / cycleLength;
                int cycleLoopLength = cyclesInLoop * cycleLength;

                i = i + cycleLoopLength - cycleLength;
                fastForwarded = true;
                continue;
            }
            seenStates.put(i, currentState);
        }

        System.out.println("Part A: " + seenStates.get(1));
        System.out.println("Part B: " + String.join("", dancers));
    }
}
