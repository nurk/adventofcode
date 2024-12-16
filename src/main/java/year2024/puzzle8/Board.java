package year2024.puzzle8;

import org.javatuples.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Board {

    private final Pair<String, Boolean>[][] board;

    public Board(List<String> input) {
        board = new Pair[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> board[row][column] = Pair.with(columns.get(column), Boolean.FALSE));
                });
    }

    public int countAntinodes() {
        int sum = 0;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col].getValue1()) {
                    sum++;
                }
            }
        }
        return sum;
    }

    public int countAntinodesB() {
        int sum = 0;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col].getValue1() || !board[row][col].getValue0().equals(".")) {
                    sum++;
                }
            }
        }
        return sum;
    }

    public void markAntinodes(boolean partB) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (!board[row][col].getValue0().equals(".")) {
                    markAntinodes(board[row][col].getValue0(), row, col, partB);
                }
            }
        }
    }

    private void markAntinodes(String node, int row, int col, boolean partB) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getValue0().equals(node) && i != row && j != col) {
                    markAntinodes(row, col, i, j, partB);
                }
            }
        }
    }

    private void markAntinodes(int rowA, int colA, int rowB, int colB, boolean partB) {
        if (rowB < rowA) {
            return;
        }
        int rowDelta = Math.abs(rowA - rowB);
        int colDelta = Math.abs(colA - colB);

        if (colA >= colB) {
            if (partB) {
                int r = rowA - rowDelta;
                int c = colA + colDelta;
                while (r >= 0 && c < board[r].length) {
                    markAntinode(r, c);
                    r = r - rowDelta;
                    c = c + colDelta;
                }

                r = rowB + rowDelta;
                c = colB - colDelta;
                while (r < board.length && c >= 0) {
                    markAntinode(r, c);
                    r = r + rowDelta;
                    c = c - colDelta;
                }
            } else {
                markAntinode(rowA - rowDelta, colA + colDelta);
                markAntinode(rowB + rowDelta, colB - colDelta);
            }
        } else {
            if (partB) {
                int r = rowA - rowDelta;
                int c = colA - colDelta;
                while (r >= 0 && c >= 0) {
                    markAntinode(r, c);
                    r = r - rowDelta;
                    c = c - colDelta;
                }

                r = rowB + rowDelta;
                c = colB + colDelta;
                while (r < board.length && c < board[r].length) {
                    markAntinode(r, c);
                    r = r + rowDelta;
                    c = c + colDelta;
                }
            } else {
                markAntinode(rowA - rowDelta, colA - colDelta);
                markAntinode(rowB + rowDelta, colB + colDelta);
            }
        }
    }

    private void markAntinode(int row, int col) {
        try {
            board[row][col] = board[row][col].setAt1(true);
        } catch (ArrayIndexOutOfBoundsException _) {
        }
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> Arrays.stream(points).map(this::mapPair).collect(joining("")))
                .collect(joining("\n"));
    }

    private String mapPair(Pair<String, Boolean> p) {
        if (p.getValue0().equals(".")) {
            if (p.getValue1()) {
                return "#";
            }
            return ".";
        }
        return p.getValue0();
    }
}
