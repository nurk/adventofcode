package year2024.puzzle18;

import org.javatuples.Pair;

import java.util.*;

import static java.util.stream.Collectors.*;

public class Board {

    private final String[][] board;

    public Board(int size, int bytes, List<String> input) {
        board = new String[size][size];

        for (String[] strings : board) {
            Arrays.fill(strings, ".");
        }

        for (int i = 0; i < bytes; i++) {
            String[] pos = input.get(i).split(",");
            board[Integer.parseInt(pos[1])][Integer.parseInt(pos[0])] = "#";
        }
    }

    public int solve() {
        Deque<Pair<Integer, Integer>> pq = new ArrayDeque<>();
        pq.addLast(Pair.with(0, 0));

        Map<Pair<Integer, Integer>, Integer> costSoFar = new HashMap<>();

        costSoFar.put(Pair.with(0, 0), 0);

        while (!pq.isEmpty()) {
            Pair<Integer, Integer> currentPosition = pq.removeFirst();
            int currentCost = costSoFar.get(currentPosition);

            if (currentPosition.equals(Pair.with(board.length - 1, board.length - 1))) {
                continue;
            }

            for (Direction direction : Direction.values()) {
                Pair<Integer, Integer> newPosition = direction.getPosition(currentPosition);

                if (isFree(newPosition) && (!costSoFar.containsKey(newPosition) || (currentCost + 1) < costSoFar.get(
                        newPosition))) {
                    costSoFar.put(newPosition, currentCost + 1);
                    pq.add(newPosition);
                }
            }
        }

        return costSoFar.entrySet().stream()
                .filter(entry -> entry.getKey().equals(Pair.with(board.length - 1, board.length - 1)))
                .map(Map.Entry::getValue)
                .min(Integer::compareTo)
                .orElse(Integer.MAX_VALUE);
    }

    private boolean isFree(Pair<Integer, Integer> position) {
        try {
            return board[position.getValue0()][position.getValue1()].equals(".");
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", points))
                .collect(joining("\n"));
    }
}
