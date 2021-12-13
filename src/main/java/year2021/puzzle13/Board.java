package year2021.puzzle13;


import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;

class Board {
    private String[][] board;

    public Board(List<String> coordinates) {
        int maxCols = coordinates.stream()
                .map(s -> s.split(","))
                .map(split -> split[0])
                .mapToInt(Integer::valueOf)
                .max()
                .orElseThrow() + 1;
        int maxRow = coordinates.stream()
                .map(s -> s.split(","))
                .map(split -> split[1])
                .mapToInt(Integer::valueOf)
                .max()
                .orElseThrow() + 1;

        board = createBoard(maxRow, maxCols);

        coordinates.stream()
                .map(s -> s.split(","))
                .forEach(s -> board[Integer.parseInt(s[1])][Integer.parseInt(s[0])] = "#");
    }

    private String[][] createBoard(int maxRow, int maxCols) {
        final String[][] board;
        board = new String[maxRow][maxCols];
        Arrays.stream(board).forEach(a -> Arrays.fill(a, "."));
        return board;
    }

    public long countHashes() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .filter("#"::equals)
                .count();
    }

    public void foldRow(int row) {
        int toRow = row - 1;
        for (int i = row + 1; i < board.length; i++) {
            String[] strings = board[i];

            for (int j = 0; j < strings.length; j++) {
                if (board[toRow][j].equals(".")) {
                    board[toRow][j] = strings[j];
                }
            }
            toRow = toRow - 1;
        }

        removeRows(row);
    }

    public void foldCol(int col) {
        for (int i = 0; i < board.length; i++) {
            String[] strings = board[i];
            int toCol = col - 1;

            for (int j = col + 1; j < strings.length; j++) {
                if (board[i][toCol].equals(".")) {
                    board[i][toCol] = strings[j];
                }
                toCol = toCol - 1;
            }
        }

        removeCols(col);
    }

    private void removeCols(int col) {
        String[][] b = createBoard(board.length, col);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < col; j++) {
                b[i][j] = board[i][j];
            }
        }

        board = b;
    }

    private void removeRows(int row) {
        String[][] b = createBoard(row, board[0].length);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < board[0].length; j++) {
                b[i][j] = board[i][j];
            }
        }

        board = b;
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", points))
                .collect(joining("\n"));
    }
}
