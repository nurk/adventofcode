package year2024.puzzle15;

import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class BoardA {

    private final String[][] board;
    private final List<Direction> moves;
    private Pair<Integer, Integer> startPosition;

    public BoardA(List<String> input) {
        StringBuilder symbols = new StringBuilder(input.removeLast());

        while (StringUtils.containsAny(input.getLast(), '^', 'v', '<', '>')) {
            symbols.insert(0, input.removeLast());
        }
        moves = Arrays.stream(symbols.toString().split(""))
                .map(Direction::fromSymbol)
                .filter(Objects::nonNull)
                .toList();

        input.removeLast();

        board = new String[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> board[row][column] = columns.get(column));
                });

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column].equals("@")) {
                    startPosition = Pair.with(row, column);
                    board[row][column] = ".";
                    break;
                }
            }
        }
    }

    public void executeMoves() {
        Pair<Integer, Integer> currentPosition = startPosition;

        for (Direction move : moves) {
            Pair<Integer, Integer> nextPosition = move.getPosition(currentPosition);
            if (board[nextPosition.getValue0()][nextPosition.getValue1()].equals(".")) {
                currentPosition = nextPosition;
            } else {
                Pair<Integer, Integer> loopPosition = nextPosition;
                while (board[loopPosition.getValue0()][loopPosition.getValue1()].equals("O")) {
                    loopPosition = move.getPosition(loopPosition);
                }

                if (board[loopPosition.getValue0()][loopPosition.getValue1()].equals(".")) {
                    board[loopPosition.getValue0()][loopPosition.getValue1()] = "O";
                    board[nextPosition.getValue0()][nextPosition.getValue1()] = ".";
                    currentPosition = nextPosition;
                }
            }
        }
    }

    public long getCoordinate() {
        long sum = 0;
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column].equals("O")) {
                    sum += 100L * row + column;
                }
            }
        }
        return sum;
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", points))
                .collect(joining("\n"));
    }
}
