package year2025.puzzle7;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class BeamGrid {

    private final String[][] board;

    public BeamGrid(List<String> input) {
        board = new String[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> board[row][column] = columns.get(column));
                });

        startBeam();
    }

    private void startBeam() {
        for (int column = 0; column < board[0].length; column++) {
            if (board[0][column].equals("S")) {
                board[1][column] = "|";
            }
        }
    }

    public int splitBeams() {
        int splitCount = 0;
        for (int row = 2; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row - 1][column].equals("|")) {
                    if (board[row][column].equals(".")) {
                        board[row][column] = "|";
                    } else if (board[row][column].equals("^")) {
                        splitCount++;
                        board[row][column - 1] = "|";
                        board[row][column + 1] = "|";
                    }
                }
            }
        }
        return splitCount;
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", points))
                .collect(joining("\n"));
    }
}
