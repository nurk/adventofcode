package year2017.puzzle11;

import org.apache.commons.lang3.tuple.Triple;
import util.Utils;

import java.util.Arrays;
import java.util.List;

/**
 * Part A: 720
 * Part B: 1485
 */
public class Puzzle11 {
    public static void main(String[] args) {
        List<String> moves = Utils.getInput("2017/input11.txt").stream()
                .flatMap(l -> Arrays.stream(l.split(",")))
                .toList();

        int maxDistance = 0;
        Triple<Integer, Integer, Integer> position = Triple.of(0, 0, 0);
        for (String move : moves) {
            position = move(move, position);
            int distance = getDistance(position);
            if (distance > maxDistance) {
                maxDistance = distance;
            }
        }

        System.out.println("Part A: " + getDistance(position));
        System.out.println("Part B: " + maxDistance);
    }

    private static int getDistance(Triple<Integer, Integer, Integer> position) {
        return (Math.abs(position.getLeft()) + Math.abs(position.getMiddle()) + Math.abs(position.getRight())) / 2;
    }

    /**
     * https://ferd.ca/advent-of-code-2017.html
     *                         x
     *
     *           (+y)         +--+        (-z)
     *              z        /    \       y
     *                   +--+  B   +--+
     *                  /    \    /    \
     *              +--+  I   +--+  C   +--+
     *             /    \    /    \    /    \
     *     (-x)   +  H   +--+  A   +--+  D   +   (+x)
     *             \    /    \    /    \    /
     *              +--+  G   +--+  E   +--+
     *                  \    /    \    /
     *                   +--+  F   +--+
     *                       \    /
     *              y         +--+        z
     *           (+z)                     (-y)
     *                         x
     */
    private static Triple<Integer, Integer, Integer> move(String direction,
                                                          Triple<Integer, Integer, Integer> position) {
        return switch (direction) {
            case "n" -> Triple.of(position.getLeft(), position.getMiddle() + 1, position.getRight() - 1);
            case "ne" -> Triple.of(position.getLeft() + 1, position.getMiddle(), position.getRight() - 1);
            case "nw" -> Triple.of(position.getLeft() - 1, position.getMiddle() + 1, position.getRight());
            case "s" -> Triple.of(position.getLeft(), position.getMiddle() - 1, position.getRight() + 1);
            case "se" -> Triple.of(position.getLeft() + 1, position.getMiddle() - 1, position.getRight());
            case "sw" -> Triple.of(position.getLeft() - 1, position.getMiddle(), position.getRight() + 1);
            default -> throw new IllegalArgumentException("Invalid direction: " + direction);
        };
    }
}
