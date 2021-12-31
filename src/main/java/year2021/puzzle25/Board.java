package year2021.puzzle25;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;

import static java.util.stream.Collectors.*;

class Board {
    private String[][] board;

    Board(List<String> input) {
        board = new String[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                board[i][j] = String.valueOf(input.get(i).charAt(j));
            }
        }
    }

    public int getRows() {
        return board.length;
    }

    public int getCols() {
        return board[0].length;
    }

    public boolean doStep(String symbol,
                          IntFunction<Integer> targetRowFunction,
                          IntFunction<Integer> targetColFunction) {
        String[][] newBoard = new String[board.length][board[0].length];
        boolean moved = false;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (symbol.equals(board[row][col])) {
                    int targetCol = targetColFunction.apply(col);
                    int targetRow = targetRowFunction.apply(row);
                    if (".".equals(board[targetRow][targetCol])) {
                        moved = true;
                        newBoard[row][col] = ".";
                        newBoard[targetRow][targetCol] = board[row][col];
                    } else {
                        newBoard[row][col] = board[row][col];
                    }
                } else if (newBoard[row][col] == null) {
                    newBoard[row][col] = board[row][col];
                }
            }
        }
        this.board = newBoard;
        return moved;
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", points))
                .collect(joining("\n")) + "\n";
    }
}
