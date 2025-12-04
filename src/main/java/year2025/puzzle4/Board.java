package year2025.puzzle4;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Board {

    private final String[][] board;

    public Board(List<String> input) {
        board = new String[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> board[row][column] = columns.get(column));
                });
    }

    public int numberOfAccessiblePaperRolls() {
        int count = 0;
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column].equals("@") && numberOfPaperRollsSurrounding(row, column) < 4) {
                    count++;
                }
            }
        }
        return count;
    }

    public int numberOfPaperRollsThatCanBeRemoved() {
        int totalCount = 0;
        int count;
        do {
            count = 0;
            for (int row = 0; row < board.length; row++) {
                for (int column = 0; column < board[row].length; column++) {
                    if (board[row][column].equals("@") && numberOfPaperRollsSurrounding(row, column) < 4) {
                        board[row][column] = ".";
                        count++;
                    }
                }
            }
            totalCount += count;
        } while (count > 0);

        return totalCount;
    }

    private int numberOfPaperRollsSurrounding(int i, int j) {
        int paperRolls = 0;
        paperRolls += getNumberOfPaperRolesAtPosition(i - 1, j - 1);
        paperRolls += getNumberOfPaperRolesAtPosition(i - 1, j);
        paperRolls += getNumberOfPaperRolesAtPosition(i - 1, j + 1);
        paperRolls += getNumberOfPaperRolesAtPosition(i, j - 1);
        paperRolls += getNumberOfPaperRolesAtPosition(i, j + 1);
        paperRolls += getNumberOfPaperRolesAtPosition(i + 1, j - 1);
        paperRolls += getNumberOfPaperRolesAtPosition(i + 1, j);
        paperRolls += getNumberOfPaperRolesAtPosition(i + 1, j + 1);

        return paperRolls;
    }

    private int getNumberOfPaperRolesAtPosition(int i, int j) {
        try {
            if (board[i][j].equals("@")) {
                return 1;
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", points))
                .collect(joining("\n"));
    }
}
