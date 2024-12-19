package year2024.puzzle15;

import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class BoardB {

    private final List<Direction> moves;
    private Pair<Integer, Integer> startPosition;
    private final List<Obstacle> obstacles = new ArrayList<>();
    private final int rows, cols;

    public BoardB(List<String> input) {
        StringBuilder symbols = new StringBuilder(input.removeLast());

        while (StringUtils.containsAny(input.getLast(), '^', 'v', '<', '>')) {
            symbols.insert(0, input.removeLast());
        }
        moves = Arrays.stream(symbols.toString().split(""))
                .map(Direction::fromSymbol)
                .filter(Objects::nonNull)
                .toList();

        input.removeLast();

        rows = input.size();
        cols = input.getFirst().length() * 2;
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    for (int i = 0; i < columns.size() * 2; i = i + 2) {
                        String val = columns.get(i / 2);
                        switch (val) {
                            case "#" -> obstacles.add(new Obstacle(true, Pair.with(row, i), Pair.with(row, i + 1)));
                            case "O" -> obstacles.add(new Obstacle(false, Pair.with(row, i), Pair.with(row, i + 1)));
                            case "@" -> this.startPosition = Pair.with(row, i);
                        }
                    }
                });
    }

    public void executeMoves() {
        Pair<Integer, Integer> currentPosition = startPosition;

        for (Direction move : moves) {
            //System.out.println(move);
            //System.out.println(this.toStringWithPosition(currentPosition));
            Pair<Integer, Integer> nextPosition = move.getPosition(currentPosition);

            List<Pair<Integer, Integer>> nextPositions = new ArrayList<>();
            Set<Obstacle> obstaclesToMove = new HashSet<>();

            nextPositions.add(nextPosition);

            while (true) {
                if (nextPositions.stream()
                        .allMatch(position -> obstacles.stream().noneMatch(obstacle -> obstacle.occupies(
                                position)))) {
                    // all next positions are free.  We can move
                    currentPosition = nextPosition;
                    obstaclesToMove.forEach(obstacle -> obstacle.move(move));
                    break;
                } else if (nextPositions.stream().anyMatch(pos -> obstacles.stream()
                        .filter(Obstacle::isWall)
                        .anyMatch(obstacle -> obstacle.occupies(pos)))) {
                    //found a wall.  Cannot move.
                    break;
                } else {
                    // not all position are free.
                    // did not find a wall in nextPositions.

                    //identify the boxes and make them the new next positions.
                    List<Obstacle> boxes = obstacles.stream()
                            .filter(obstacle -> !obstacle.isWall())
                            .filter(obstacle -> nextPositions.stream()
                                    .anyMatch(obstacle::occupies))
                            .toList();

                    nextPositions.clear();
                    nextPositions.addAll(boxes.stream()
                            .map(box -> {
                                if (move == Direction.LEFT) {
                                    return List.of(move.getPosition(box.getLeft()));
                                } else if (move == Direction.RIGHT) {
                                    return List.of(move.getPosition(box.getRight()));
                                } else {
                                    return List.of(move.getPosition(box.getLeft()), move.getPosition(box.getRight()));
                                }
                            })
                            .flatMap(Collection::stream)
                            .toList());
                    obstaclesToMove.addAll(boxes);
                }
            }
        }
    }

    public long getCoordinate() {
        return obstacles.stream()
                .filter(obstacle -> !obstacle.isWall())
                .map(obstacle -> 100L * obstacle.getLeft().getValue0() + obstacle.getLeft().getValue1())
                .mapToLong(Long::longValue)
                .sum();
    }

    @Override
    public String toString() {
        String[][] thisBoard = new String[rows][cols];
        for (String[] strings : thisBoard) {
            Arrays.fill(strings, ".");
        }

        obstacles.forEach(obstacle -> {
            thisBoard[obstacle.getLeft().getValue0()][obstacle.getLeft().getValue1()] = obstacle.getLeftSymbol();
            thisBoard[obstacle.getRight().getValue0()][obstacle.getRight().getValue1()] = obstacle.getRightSymbol();
        });

        return Arrays.stream(thisBoard)
                .map(points -> String.join("", points))
                .collect(joining("\n"));
    }

    public String toStringWithPosition(Pair<Integer, Integer> position) {
        String[][] thisBoard = new String[rows][cols];
        for (String[] strings : thisBoard) {
            Arrays.fill(strings, ".");
        }

        obstacles.forEach(obstacle -> {
            thisBoard[obstacle.getLeft().getValue0()][obstacle.getLeft().getValue1()] = obstacle.getLeftSymbol();
            thisBoard[obstacle.getRight().getValue0()][obstacle.getRight().getValue1()] = obstacle.getRightSymbol();
        });

        thisBoard[position.getValue0()][position.getValue1()] = "@";

        return Arrays.stream(thisBoard)
                .map(points -> String.join("", points))
                .collect(joining("\n"));
    }
}
