package year2024.puzzle16;

import lombok.Getter;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class MazeB {

    private final String[][] board;
    @Getter
    private Pair<Integer, Integer> start;
    @Getter
    private Pair<Integer, Integer> end;

    private int numberOfFreePositions;

    public MazeB(List<String> input) {
        board = new String[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> {
                                switch (columns.get(column)) {
                                    case "S" -> {
                                        start = Pair.with(row, column);
                                        board[row][column] = ".";
                                        numberOfFreePositions++;
                                    }
                                    case "E" -> {
                                        end = Pair.with(row, column);
                                        board[row][column] = ".";
                                        numberOfFreePositions++;
                                    }
                                    case "." -> {
                                        numberOfFreePositions++;
                                        board[row][column] = columns.get(column);
                                    }
                                    default -> board[row][column] = columns.get(column);
                                }
                            });
                });
        System.out.println(numberOfFreePositions);
    }

    public long solve(long cost) {
        List<Pair<Long, List<Pair<Integer, Integer>>>> endPairs = new ArrayList<>();
        Deque<Position> pq = new ArrayDeque<>();
        Map<Position, Pair<Long, List<Pair<Integer, Integer>>>> costSoFar = new HashMap<>();

        Position startPosition = new Position(start, Direction.RIGHT);
        pq.add(startPosition);
        costSoFar.put(startPosition, Pair.with(0L, new ArrayList<>(List.of(start))));

        while (!pq.isEmpty()) {
            Position current = pq.remove();
            long currentCost = costSoFar.getOrDefault(current, Pair.with(cost + 100, List.of())).getValue0();

            if (pq.size() % 100000 == 0) {
                System.out.println(pq.size());
                System.out.println(costSoFar.size());
                System.out.println("--");
            }

            if (currentCost > cost) {
                System.out.println("remove based on cost");
                costSoFar.remove(current);
                continue;
            }

            Pair<Long, List<Pair<Integer, Integer>>> costSoFarFromMap = costSoFar.get(current);
            if (costSoFarFromMap.getValue1().size() > cost) {
                System.out.println("remove based on steps");
                costSoFar.remove(current);
                continue;
            }

            if (costSoFarFromMap.getValue1().size() >= numberOfFreePositions) {
                System.out.println("remove based on numberOfFreePositions");
                costSoFar.remove(current);
                continue;
            }

            if (current.position.equals(end)) {
                System.out.println("found end");
                endPairs.add(costSoFarFromMap);
                costSoFar.remove(current);
                continue;
            }

            Position forwardPosition = new Position(current.direction.getPosition(current.position), current.direction);

            if (isFree(forwardPosition.position) && (currentCost + 1) <= cost && !costSoFarFromMap
                    .getValue1()
                    .contains(forwardPosition.position)) {
                List<Pair<Integer, Integer>> newList = new ArrayList<>(costSoFarFromMap.getValue1());
                newList.add(forwardPosition.position);
                costSoFar.put(forwardPosition, Pair.with(currentCost + 1, newList));
                pq.add(forwardPosition);
            }

            Direction ccDirection = Direction.getCounterClockwise(current.direction);
            Position ccPosition = new Position(ccDirection.getPosition(current.position), ccDirection);

            if (isFree(ccPosition.position) && (currentCost + 1001) <= cost && !costSoFarFromMap
                    .getValue1()
                    .contains(ccPosition.position)) {
                List<Pair<Integer, Integer>> newList = new ArrayList<>(costSoFarFromMap.getValue1());
                newList.add(ccPosition.position);
                costSoFar.put(ccPosition, Pair.with(currentCost + 1001, newList));
                pq.add(ccPosition);
            }

            Direction cwDirection = Direction.getClockwise(current.direction);
            Position cwPosition = new Position(cwDirection.getPosition(current.position), cwDirection);

            if (isFree(cwPosition.position) && (currentCost + 1001) <= cost && !costSoFarFromMap
                    .getValue1()
                    .contains(cwPosition.position)) {
                List<Pair<Integer, Integer>> newList = new ArrayList<>(costSoFarFromMap.getValue1());
                newList.add(cwPosition.position);
                costSoFar.put(cwPosition, Pair.with(currentCost + 1001, newList));
                pq.add(cwPosition);
            }

            costSoFar.remove(current);
        }

        long minCost = endPairs.stream()
                .map(Pair::getValue0)
                .min(Long::compareTo)
                .orElseThrow();

        endPairs = endPairs.stream()
                .filter(entry -> entry.getValue0() == minCost)
                //.peek(l -> printResult(l.getValue().getValue1()))
                .toList();

        return endPairs.stream()
                .map(Pair::getValue1)
                .flatMap(List::stream)
                .collect(toSet()).size();
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

    public void printResult(List<Pair<Integer, Integer>> path) {
        String[][] cloneBoard = new String[board.length][board[0].length];

        for (int row = 0; row < board.length; row++) {
            System.arraycopy(board[row], 0, cloneBoard[row], 0, board[row].length);
        }

        path.forEach(pair -> cloneBoard[pair.getValue0()][pair.getValue1()] = "O");

        System.out.println();
        System.out.println(Arrays.stream(cloneBoard)
                .map(points -> String.join("", points))
                .collect(joining("\n")));
        System.out.println();
    }

    private static class Position {
        Pair<Integer, Integer> position;
        Direction direction;

        private Position(Pair<Integer, Integer> position, Direction direction) {
            this.position = position;
            this.direction = direction;
        }
    }
}
