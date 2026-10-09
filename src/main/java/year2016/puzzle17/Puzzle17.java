package year2016.puzzle17;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * Part A: DRDRULRDRD
 * Part B: 384
 */
public class Puzzle17 {

    private static final Pair<Integer, Integer> START = Pair.of(0, 0);
    private static final Pair<Integer, Integer> END = Pair.of(3, 3);

    static void main() {
        String passCode = "vwbaicqe";
        System.out.println("Part A: " + partA(passCode));
        System.out.println("Part B: " + partB(passCode));

    }

    private static String partA(String passCode) {
        Deque<State> queue = new ArrayDeque<>();
        queue.push(new State(START, ""));

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (current.isGoal()) {
                return current.path;
            }

            for (Direction direction : getOpenDirections(passCode, current.path)) {
                Pair<Integer, Integer> newPosition = direction.move(current.position);
                if (isValidPosition(newPosition)) {
                    String newPath = current.path + direction.getChar();
                    queue.add(new State(newPosition, newPath));
                }
            }
        }
        return null;
    }
    private static String partB(String passCode) {
        Deque<State> queue = new ArrayDeque<>();
        queue.push(new State(START, ""));
        int longest = 0;

        while (!queue.isEmpty()) {
            State current = queue.pop();

            if (current.isGoal()) {
                longest = Math.max(longest, current.path.length());
                continue;
            }

            for (Direction direction : getOpenDirections(passCode, current.path)) {
                Pair<Integer, Integer> newPosition = direction.move(current.position);
                if (isValidPosition(newPosition)) {
                    String newPath = current.path + direction.getChar();
                    queue.add(new State(newPosition, newPath));
                }
            }
        }
        return longest + "";
    }


    record State(Pair<Integer, Integer> position, String path) {
        boolean isGoal() {
            return position.equals(END);
        }
    }

    private static List<Direction> getOpenDirections(String passCode, String path) {
        String hash = DigestUtils.md5Hex(passCode + path).toLowerCase();
        List<Direction> openDirections = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            char c = hash.charAt(i);
            if (c >= 'b' && c <= 'f') {
                openDirections.add(Direction.values()[i]);
            }
        }
        return openDirections;
    }

    private static boolean isValidPosition(Pair<Integer, Integer> position) {
        int row = position.getLeft();
        int col = position.getRight();
        return row >= 0 && row <= 3 && col >= 0 && col <= 3;
    }

    private enum Direction {
        UP('U', -1, 0),
        DOWN('D', 1, 0),
        LEFT('L', 0, -1),
        RIGHT('R', 0, 1);

        private final char directionChar;
        private final int rowChange;
        private final int colChange;

        Direction(char directionChar, int rowChange, int colChange) {
            this.directionChar = directionChar;
            this.rowChange = rowChange;
            this.colChange = colChange;
        }

        public char getChar() {
            return directionChar;
        }

        public Pair<Integer, Integer> move(Pair<Integer, Integer> position) {
            return Pair.of(position.getLeft() + rowChange, position.getRight() + colChange);
        }
    }
}
