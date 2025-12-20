package year2023.puzzle10;

import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class HotSpring {
    private final Pair<String, Boolean>[][] board;
    private Pair<Integer, Integer> startPosition;
    private final Polygon polygon = new Polygon();

    public HotSpring(List<String> input) {
        //noinspection unchecked
        board = new Pair[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> {
                                board[row][column] = Pair.of(columns.get(column), false);
                                if (board[row][column].getLeft().equals("S")) {
                                    board[row][column] = Pair.of(board[row][column].getLeft(), true);
                                    startPosition = Pair.of(row, column);
                                }
                            });
                });
    }

    public long solve() {
        Pair<Integer, Integer> previousPosition = startPosition;
        Pair<Integer, Integer> currentPosition = getConnectingNeighbor(startPosition, startPosition);
        polygon.addPoint(startPosition.getRight(), startPosition.getLeft());
        polygon.addPoint(currentPosition.getRight(), currentPosition.getLeft());

        while (!currentPosition.equals(startPosition)) {
            board[currentPosition.getLeft()][currentPosition.getRight()] =
                    Pair.of(board[currentPosition.getLeft()][currentPosition.getRight()].getLeft(), true);

            Pair<Integer, Integer> followPosition = followCurrent(currentPosition, previousPosition);

            polygon.addPoint(followPosition.getRight(), followPosition.getLeft());
            previousPosition = currentPosition;
            currentPosition = followPosition;
        }

        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .filter(Pair::getRight)
                .count() / 2;
    }

    public long countTilesWithinLoop() {
        long count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!board[i][j].getRight()) {
                    if (polygon.contains(j, i)) {
                        System.out.print("#");
                        count++;
                    } else {
                        System.out.print(" ");
                    }
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }

        return count;
    }

    private Pair<Integer, Integer> followCurrent(Pair<Integer, Integer> currentPosition,
                                                 Pair<Integer, Integer> previousPosition) {
        Pair<Integer, Integer> firstPosition;
        Pair<Integer, Integer> secondPosition;

        switch (board[currentPosition.getLeft()][currentPosition.getRight()].getLeft()) {
            case "|": {
                firstPosition = Pair.of(currentPosition.getLeft() - 1, currentPosition.getRight());
                secondPosition = Pair.of(currentPosition.getLeft() + 1, currentPosition.getRight());
                break;
            }
            case "-": {
                firstPosition = Pair.of(currentPosition.getLeft(), currentPosition.getRight() - 1);
                secondPosition = Pair.of(currentPosition.getLeft(), currentPosition.getRight() + 1);
                break;
            }
            case "J": {
                firstPosition = Pair.of(currentPosition.getLeft() - 1, currentPosition.getRight());
                secondPosition = Pair.of(currentPosition.getLeft(), currentPosition.getRight() - 1);
                break;
            }
            case "L": {
                firstPosition = Pair.of(currentPosition.getLeft() - 1, currentPosition.getRight());
                secondPosition = Pair.of(currentPosition.getLeft(), currentPosition.getRight() + 1);
                break;
            }
            case "7": {
                firstPosition = Pair.of(currentPosition.getLeft(), currentPosition.getRight() - 1);
                secondPosition = Pair.of(currentPosition.getLeft() + 1, currentPosition.getRight());
                break;
            }
            case "F": {
                firstPosition = Pair.of(currentPosition.getLeft() + 1, currentPosition.getRight());
                secondPosition = Pair.of(currentPosition.getLeft(), currentPosition.getRight() + 1);
                break;
            }
            default:
                throw new IllegalArgumentException("No connection found for position " + currentPosition);
        }

        if (firstPosition.equals(previousPosition)) {
            return secondPosition;
        }
        return firstPosition;
    }

    private Pair<Integer, Integer> getConnectingNeighbor(Pair<Integer, Integer> currentPosition,
                                                         Pair<Integer, Integer> previousPosition) {
        Pair<Integer, Integer> upperPosition = Pair.of(currentPosition.getLeft() - 1, currentPosition.getRight());
        Pair<Integer, Integer> lowerPosition = Pair.of(currentPosition.getLeft() + 1, currentPosition.getRight());
        Pair<Integer, Integer> leftPosition = Pair.of(currentPosition.getLeft(), currentPosition.getRight() - 1);
        Pair<Integer, Integer> rightPosition = Pair.of(currentPosition.getLeft(), currentPosition.getRight() + 1);
        if (!upperPosition.equals(previousPosition) && hasCorrectSymbols(upperPosition,
                List.of("|", "7", "F", "S"))) {
            return upperPosition;
        } else if (!lowerPosition.equals(previousPosition) && hasCorrectSymbols(lowerPosition,
                List.of("|", "L", "J", "S"))) {
            return lowerPosition;
        } else if (!leftPosition.equals(previousPosition) && hasCorrectSymbols(leftPosition,
                List.of("-", "L", "F", "S"))) {
            return leftPosition;
        } else if (!rightPosition.equals(previousPosition) && hasCorrectSymbols(rightPosition,
                List.of("-", "J", "7", "S"))) {
            return rightPosition;
        }
        throw new IllegalArgumentException("No connection found for position " + currentPosition);
    }


    private boolean hasCorrectSymbols(Pair<Integer, Integer> position, List<String> symbols) {
        try {
            return symbols.contains(board[position.getLeft()][position.getRight()].getLeft());
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", Arrays.stream(points).map(Pair::getLeft).toList()))
                .collect(joining("\n"));
    }

    public String toLoopString() {
        return Arrays.stream(board)
                .map(points -> String.join("",
                        Arrays.stream(points).map(b -> b.getRight() ? b.getLeft() : ".").toList()))
                .collect(joining("\n"));
    }
}
