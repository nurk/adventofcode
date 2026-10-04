package year2018.puzzle20;

import org.apache.commons.lang3.tuple.Pair;
import util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Part A: 4778
 * Part B: 8459
 */
public class Puzzle20 {
    static void main() {
        String regex = new ArrayList<>(Utils.getInput("2018/input20.txt", (s) -> s)).getFirst();

        Stack<Pair<Integer, Integer>> positionStack = new Stack<>();
        Stack<Integer> distanceStack = new Stack<>();
        Map<Pair<Integer, Integer>, Integer> distances = new HashMap<>();

        Pair<Integer, Integer> currentPosition = Pair.of(0, 0);
        distances.put(currentPosition, 0);
        int currentDistance = 0;

        for (char c:regex.toCharArray()) {
            switch (c) {
                case 'N':
                    currentPosition = Pair.of(currentPosition.getLeft(), currentPosition.getRight() - 1);
                    currentDistance++;
                    if (!distances.containsKey(currentPosition) || distances.get(currentPosition) > currentDistance) {
                        distances.put(currentPosition, currentDistance);
                    }
                    break;
                case 'S':
                    currentPosition = Pair.of(currentPosition.getLeft(), currentPosition.getRight() + 1);
                    currentDistance++;
                    if (!distances.containsKey(currentPosition) || distances.get(currentPosition) > currentDistance) {
                        distances.put(currentPosition, currentDistance);
                    }
                    break;
                case 'E':
                    currentPosition = Pair.of(currentPosition.getLeft() + 1, currentPosition.getRight());
                    currentDistance++;
                    if (!distances.containsKey(currentPosition) || distances.get(currentPosition) > currentDistance) {
                        distances.put(currentPosition, currentDistance);
                    }
                    break;
                case 'W':
                    currentPosition = Pair.of(currentPosition.getLeft() - 1, currentPosition.getRight());
                    currentDistance++;
                    if (!distances.containsKey(currentPosition) || distances.get(currentPosition) > currentDistance) {
                        distances.put(currentPosition, currentDistance);
                    }
                    break;
                case '(':
                    positionStack.push(currentPosition);
                    distanceStack.push(currentDistance);
                    break;
                case '|':
                    currentPosition = positionStack.peek();
                    currentDistance = distanceStack.peek();
                    break;
                case ')':
                    currentPosition = positionStack.pop();
                    currentDistance = distanceStack.pop();
                    break;
            }
        }
        System.out.println("Part A: " + distances.values().stream().mapToInt(Integer::intValue).max().orElse(0));
        System.out.println("Part B: " + distances.values().stream().mapToInt(Integer::intValue).filter(d -> d >= 1000).count());
    }
}
