package year2024.puzzle25;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class LockPart {
    private final String[][] board;
    private final int[] positions;

    public LockPart(List<String> input) {
        board = new String[7][5];
        IntStream.range(0, 7)
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, 5)
                            .forEach(column -> board[row][column] = columns.get(column));
                });
        positions = new int[5];

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i][j].equals("#")) {
                    positions[j] = positions[j] + 1;
                }
            }
        }

    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", points))
                .collect(joining("\n")) +
                "\n" +
                Arrays.toString(positions) +
                "\n";
    }

    public boolean fits(LockPart other) {
        for (int i = 0; i < 5; i++) {
            if (positions[i] + other.positions[i] > 7) {
                return false;
            }
        }
        return true;
    }
}
