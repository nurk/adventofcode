package year2025.puzzle7;

import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class TimeSplitBeamGrid {

    private final Pair<String, Long>[][] board;

    public TimeSplitBeamGrid(List<String> input) {
        //noinspection unchecked
        board = new Pair[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> board[row][column] = new Pair<>(columns.get(column), 0L));
                });

        startBeam();
    }

    private void startBeam() {
        for (int column = 0; column < board[0].length; column++) {
            if (board[0][column].getValue0().equals("S")) {
                board[1][column] = new Pair<>("|", 1L);
            }
        }
    }

    public Long splitBeams() {
        for (int row = 1; row < board.length - 1; row++) {
            for (int column = 0; column < board[0].length; column++) {
                Pair<String, Long> currentPair = board[row][column];
                if (currentPair.getValue0().equals("^")) {
                    board[row + 1][column - 1] = new Pair<>(board[row + 1][column - 1].getValue0(),
                            currentPair.getValue1() + board[row + 1][column - 1].getValue1());
                    board[row + 1][column + 1] = new Pair<>(board[row + 1][column + 1].getValue0(),
                            currentPair.getValue1() + board[row + 1][column + 1].getValue1());

                    if (board[row + 1][column - 1].getValue0().equals(".")) {
                        board[row + 1][column - 1] = new Pair<>("|", board[row + 1][column - 1].getValue1());
                    }
                    if (board[row + 1][column + 1].getValue0().equals(".")) {
                        board[row + 1][column + 1] = new Pair<>("|", board[row + 1][column + 1].getValue1());
                    }
                } else {
                    board[row + 1][column] = new Pair<>(board[row + 1][column].getValue0(),
                            currentPair.getValue1() + board[row + 1][column].getValue1());

                    if (board[row + 1][column].getValue0().equals(".") && currentPair.getValue0().equals("|")) {
                        board[row + 1][column] = new Pair<>("|", board[row + 1][column].getValue1());
                    }
                }
            }
        }

        return Arrays.stream(board[board.length - 1])
                .mapToLong(Pair::getValue1)
                .sum();
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(row -> Arrays.stream(row)
                        .map(pair -> pair.getValue0()
                                .equals("|") ? pair.getValue0() + pair.getValue1() : pair.getValue0())
                        .map(s -> StringUtils.leftPad(s, 5, " "))
                        .toArray(String[]::new))
                .map(points -> String.join("", Arrays.toString(points)))
                .collect(joining("\n"));
    }

    public String altToString() {
        return Arrays.stream(board)
                .map(row -> Arrays.stream(row)
                        .map(pair -> pair.getValue0() + pair.getValue1())
                        .map(s -> StringUtils.leftPad(s, 5, " "))
                        .toArray(String[]::new))
                .map(points -> String.join("", Arrays.toString(points)))
                .collect(joining("\n"));
    }
}
