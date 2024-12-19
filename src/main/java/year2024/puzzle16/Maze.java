package year2024.puzzle16;

import lombok.Getter;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Maze {

    private final String[][] board;
    @Getter
    private Pair<Integer, Integer> start;
    @Getter
    private Pair<Integer, Integer> end;

    public Maze(List<String> input) {
        board = new String[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> {
                                if (columns.get(column).equals("S")) {
                                    start = Pair.with(row, column);
                                    board[row][column] = ".";
                                } else if (columns.get(column).equals("E")) {
                                    end = Pair.with(row, column);
                                    board[row][column] = ".";
                                } else {
                                    board[row][column] = columns.get(column);
                                }
                            });
                });
    }

    public long solve() {
        Deque<Position> pq = new ArrayDeque<>();
        pq.addLast(new Position(start, Direction.RIGHT));

        Map<Position, Long> costSoFar = new HashMap<>();

        costSoFar.put(new Position(start, Direction.RIGHT), 0L);

        while (!pq.isEmpty()) {
            Position current = pq.removeFirst();
            long currentCost = costSoFar.get(current);

            if (current.position.equals(end)) {
                continue;
            }

            Position forwardPosition = new Position(current.direction.getPosition(current.position), current.direction);

            if (isFree(forwardPosition.position) && (!costSoFar.containsKey(forwardPosition) || (currentCost + 1) < costSoFar.get(
                    forwardPosition))) {
                costSoFar.put(forwardPosition, currentCost + 1);
                pq.add(forwardPosition);
            }

            Direction ccDirection = Direction.getCounterClockwise(current.direction);
            Position ccPosition = new Position(ccDirection.getPosition(current.position), ccDirection);

            if (isFree(ccPosition.position) && (!costSoFar.containsKey(ccPosition) || (currentCost + 1001) < costSoFar.get(
                    ccPosition))) {
                costSoFar.put(ccPosition, currentCost + 1001);
                pq.add(ccPosition);
            }

            Direction cwDirection = Direction.getClockwise(current.direction);
            Position cwPosition = new Position(cwDirection.getPosition(current.position), cwDirection);

            if (isFree(cwPosition.position) && (!costSoFar.containsKey(cwPosition) || (currentCost + 1001) < costSoFar.get(
                    cwPosition))) {
                costSoFar.put(cwPosition, currentCost + 1001);
                pq.add(cwPosition);
            }
        }

        return costSoFar.entrySet().stream()
                .filter(entry -> entry.getKey().position.equals(end))
                .map(Map.Entry::getValue)
                .min(Long::compareTo)
                .orElseThrow();
    }

    private boolean isFree(Pair<Integer, Integer> position) {
        return board[position.getValue0()][position.getValue1()].equals(".");
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", points))
                .collect(joining("\n"));
    }

    private record Position(Pair<Integer, Integer> position, Direction direction) {
    }
}
