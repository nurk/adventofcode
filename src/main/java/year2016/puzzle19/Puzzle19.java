package year2016.puzzle19;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 1815603
 * Part B: 1410630
 */
public class Puzzle19 {
    static void main() {
        partA();
        // claude for performance clue
        partBClaude();
        partB();
    }

    private static void partB() {
        int numElves = 3004953;

        List<Integer> elves = new ArrayList<>();
        for (int i = 0; i < numElves; i++) {
            elves.add(i+1);
        }

        int currentIndex = 0;
        while (elves.size() != 1) {
            int nextIndex = (currentIndex + elves.size() / 2) % elves.size();
            elves.remove(nextIndex);
            if (nextIndex > currentIndex) {
                currentIndex++;
            }
            currentIndex = currentIndex % elves.size();
        }

        System.out.println("Part B: " + elves.getFirst());
    }

    private static void partBClaude() {
        int numElves = 3004953;

        // Array-based circular doubly linked list for O(1) removal
        int[] next = new int[numElves];
        int[] prev = new int[numElves];
        for (int i = 0; i < numElves; i++) {
            next[i] = (i + 1) % numElves;
            prev[i] = (i - 1 + numElves) % numElves;
        }

        // Start with current=0, opposite at numElves/2
        int current = 0;
        int opposite = numElves / 2;
        int size = numElves;

        while (size > 1) {
            // Remove 'opposite' from the linked list
            next[prev[opposite]] = next[opposite];
            prev[next[opposite]] = prev[opposite];

            // Advance opposite: 1 step if size was even, 2 steps if odd
            opposite = (size % 2 == 0) ? next[opposite] : next[next[opposite]];

            current = next[current];
            size--;
        }

        System.out.println("Part B: " + (current + 1));
    }

    private static void partA() {
        int numElves = 3004953;

        List<Boolean> elves = new ArrayList<>();
        for (int i = 0; i < numElves; i++) {
            elves.add(true);
        }

        while (elves.stream().filter(elve -> elve == true).count() != 1) {
            for (int i = 0; i < elves.size(); i++) {
                if (elves.get(i)) {
                    int nextElve = (i + 1) % elves.size();
                    while (!elves.get(nextElve)) {
                        nextElve = (nextElve + 1) % elves.size();
                    }
                    elves.set(nextElve, false);
                }
            }
        }

        for (int i = 0; i < numElves; i++) {
            if (elves.get(i)) {
                System.out.println("Part A: " + (i + 1));
                break;
            }
        }
    }
}
