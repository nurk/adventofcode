package year2018.puzzle18;

import util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Part A: 589931
 * Part B: 222332
 */
public class Puzzle18 {
    static void main() {
        List<String> input = new ArrayList<>(Utils.getInput("2018/input18.txt", (s) -> s));
        partA(input);
        partB(input);
    }

    private static void partB(List<String> input) {
        Woodlands woodlands = new Woodlands(input);
        Map<String, Integer> seenStates = new HashMap<>();
        boolean didJump = false;

        for (int i = 0; i < 1000000000; i++) {
            woodlands.advanceOneMinute();
            String state = woodlands.getState();
            if (!didJump) {
                if (seenStates.containsKey(state)) {
                    int firstSeenMinute = seenStates.get(state);
                    int cycleLength = i - firstSeenMinute;
                    int remainingMinutes = 1000000000 - i;
                    int cyclesToJump = remainingMinutes / cycleLength;
                    int minutesToJump = cyclesToJump * cycleLength;
                    i += minutesToJump;

                    didJump = true;

                } else {
                    seenStates.put(state, i);
                }
            }
        }

        System.out.println("Part B: " + (woodlands.getTotalNumberOf("|") * woodlands.getTotalNumberOf("#")));
    }

    private static void partA(List<String> input) {
        Woodlands woodlands = new Woodlands(input);

        for (int i = 0; i < 10; i++) {
            woodlands.advanceOneMinute();
        }

        System.out.println("Part A: " + (woodlands.getTotalNumberOf("|") * woodlands.getTotalNumberOf("#")));
    }

}
