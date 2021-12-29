package year2021.puzzle25;

import java.util.Arrays;
import java.util.List;

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

    public boolean doEastStep() {
        String[][] newBoard = new String[board.length][board[0].length];
        boolean moved = false;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (">".equals(board[row][col])) {
                    int targetCol = (col + 1) % (board[row].length);
                    if (".".equals(board[row][targetCol])) {
                        moved = true;
                        newBoard[row][col] = ".";
                        newBoard[row][targetCol] = board[row][col];
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

    public boolean doSouthStep() {
        String[][] newBoard = new String[board.length][board[0].length];
        boolean moved = false;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if ("v".equals(board[row][col])) {
                    int targetRow = (row + 1) % (board.length);
                    if (".".equals(board[targetRow][col])) {
                        moved = true;
                        newBoard[row][col] = ".";
                        newBoard[targetRow][col] = board[row][col];
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
