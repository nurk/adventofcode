package year2018.puzzle9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Part A: 409832
 * Part A: 3469562780
 */
public class Puzzle9 {

    static final int numberOfPlayers = 428;
    static final int numberOfMarbles = 7206100;
    static final Map<Integer, Long> scores = new HashMap<>();

    static void main() {
        List<Integer> marbles = new ArrayList<>();
        marbles.add(0);
        int currentIndex = 0;

        for (int i = 1; i <= numberOfMarbles; i++) {
            if (i % 100000 == 0) {
                System.out.println("Marble " + i);
            }
            if (marbles.size() < 3) {
                currentIndex = 1;
                marbles.add(currentIndex, i);
            } else if (i % 23 == 0) {
                scores.merge(getPlayer(i), (long) i, Long::sum);
                currentIndex = currentIndex - 7;
                if (currentIndex < 0) {
                    currentIndex = marbles.size() + currentIndex;
                }
                scores.merge(getPlayer(i), (long) marbles.get(currentIndex), Long::sum);
                marbles.remove(currentIndex);
            } else {
                currentIndex = currentIndex + 2;
                if (currentIndex > marbles.size()) {
                    currentIndex = 1;
                }
                marbles.add(currentIndex, i);
            }
        }

//        marbles.forEach(i -> System.out.print(i + " "));
        System.out.println();

        scores.values().stream().max(Long::compareTo).ifPresent(x -> System.out.println("Part A: " + x));

    }

    private static int getPlayer(int i) {
        if (i % numberOfPlayers == 0) {
            return numberOfPlayers;
        } else {
            return i % numberOfPlayers;
        }
    }
}
