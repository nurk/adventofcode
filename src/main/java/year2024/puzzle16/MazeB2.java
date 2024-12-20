package year2024.puzzle16;

import lombok.Getter;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class MazeB2 {

    private final String[][] board;
    @Getter
    private Pair<Integer, Integer> start;
    @Getter
    private Pair<Integer, Integer> end;

    private int numberOfFreePositions;

    public MazeB2(List<String> input) {
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
        Map<Pair<Integer, Integer>, List<Position>> mazePointsWithNeighbours = new HashMap<>();

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column].equals(".")) {
                    Pair<Integer, Integer> current = Pair.with(row, column);

                    List<Position> list = Arrays.stream(Direction.values())
                            .filter(direction -> isFree(direction.getPosition(current)))
                            .map(direction -> new Position(direction.getPosition(current), direction))
                            .toList();

                    if (!list.isEmpty()) {
                        mazePointsWithNeighbours.put(current, new ArrayList<>(list));
                    }
                }
            }
        }

        paths(mazePointsWithNeighbours);
        return 0L;
    }

    private void paths(Map<Pair<Integer, Integer>, List<Position>> mazePointsWithNeighbours) {
        List<List<Pair<Integer, Integer>>> paths = new ArrayList<>();
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        Map<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> parent = new HashMap<>();

        mazePointsWithNeighbours.keySet().forEach(pair -> parent.put(pair, new ArrayList<>()));

        bfs(mazePointsWithNeighbours, parent);

        findPaths(paths, path, parent, end);

        paths.forEach(l -> calculateCost(l));
        Set<Pair<Integer, Integer>> collect = paths.stream()
                .flatMap(List::stream)
                .collect(toSet());

        System.out.println(collect.size());
        //paths.forEach(l -> printResult(l));
    }

    private long calculateCost(List<Pair<Integer, Integer>> path) {
        path = new ArrayList<>(path.reversed());
        Pair<Integer, Integer> current = path.removeFirst();
        Direction direction = Direction.RIGHT;

        long cost = 0;

        do {
            Pair<Integer, Integer> next = path.removeFirst();
            Pair<Integer, Integer> finalCurrent = current;
            Direction newDirection = Arrays.stream(Direction.values())
                    .filter(dir -> dir.getPosition(finalCurrent).equals(next))
                    .findFirst()
                    .orElseThrow();

            cost += direction.getCost(newDirection);
            direction = newDirection;
            current = next;
        } while (!path.isEmpty());

        System.out.println(cost);
        return cost;
    }

    private void findPaths(List<List<Pair<Integer, Integer>>> paths,
                           List<Pair<Integer, Integer>> path,
                           Map<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> parent,
                           Pair<Integer, Integer> u) {
        if (u.equals(Pair.with(-1, -1))) {
            paths.add(new ArrayList<>(path));
            return;
        }

        for (Pair<Integer, Integer> par : parent.get(u)) {
            path.add(u);

            findPaths(paths, path, parent, par);

            path.removeLast();
        }
    }

    private void bfs(Map<Pair<Integer, Integer>, List<Position>> mazePointsWithNeighbours,
                     Map<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> parent) {

        Map<Pair<Integer, Integer>, Integer> dist = new HashMap<>();
        mazePointsWithNeighbours.keySet().forEach(pair -> dist.put(pair, Integer.MAX_VALUE));

        Queue<Position> q = new LinkedList<>();
        q.offer(new Position(start, Direction.RIGHT));

        parent.get(start).clear();
        parent.get(start).add(Pair.with(-1, -1));

        dist.put(start, 0);

        while (!q.isEmpty()) {
            Position u = q.poll();

            mazePointsWithNeighbours.get(u.position).forEach(v -> {

                int cost = u.direction.getCost(v.direction);

                if (cost != Integer.MAX_VALUE) {
                    if (dist.get(v.position) > dist.get(u.position) + cost) {
                        dist.put(v.position, dist.get(u.position) + cost);
                        q.offer(v);
                        parent.get(v.position).clear();
                        parent.get(v.position).add(u.position);
                    } else if (dist.get(v.position) == dist.get(u.position) + cost) {
                        parent.get(v.position).add(u.position);
                    }
                }
            });
        }
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

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Position position1 = (Position) o;
            return position.equals(position1.position) && direction == position1.direction;
        }

        @Override
        public int hashCode() {
            int result = position.hashCode();
            result = 31 * result + direction.hashCode();
            return result;
        }
    }
}
