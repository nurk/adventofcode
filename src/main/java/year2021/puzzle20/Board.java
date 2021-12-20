package year2021.puzzle20;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;

class Board {
    private String[][] board;
    private final String decode;

    Board(String decode, List<String> input) {
        this.decode = decode;

        board = new String[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                board[i][j] = String.valueOf(input.get(i).charAt(j));
            }
        }
    }

    public void enhance(int times) {
        String[][] newBoard = new String[board.length + 4 * times][board[0].length + 4 * times];
        Arrays.stream(newBoard).forEach(strings -> Arrays.fill(strings, "."));

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                newBoard[i + times * 2][j + times * 2] = board[i][j];

            }
        }
        board = newBoard;


        for (int t = 0; t < times; t++) {
            String[][] newestBoard = new String[board.length][board[0].length];
            Arrays.stream(newestBoard).forEach(strings -> Arrays.fill(strings, "."));
            for (int i = 0; i < newestBoard.length; i++) {
                for (int j = 0; j < newestBoard[i].length; j++) {
                    newestBoard[i][j] = getDecodedValue(getBinaryCode(i, j));

                }
            }
            this.board = newestBoard;
        }

        System.out.println(countLit(times));
    }

    private long countLit(int times) {
        int count = 0;
        for (int i = times; i < board.length - times; i++) {
            for (int j = times; j < board[i].length - times; j++) {
                if (board[i][j].equals("#")) {
                    count++;
                }
            }
        }
        return count;
    }

    private String getDecodedValue(String binaryCode) {
        return String.valueOf(decode.charAt(Integer.valueOf(binaryCode, 2)));
    }

    private String getBinaryCode(int row, int col) {
        return getBinaryPoint(row - 1, col - 1) +
                getBinaryPoint(row - 1, col) +
                getBinaryPoint(row - 1, col + 1) +
                getBinaryPoint(row, col - 1) +
                getBinaryPoint(row, col) +
                getBinaryPoint(row, col + 1) +
                getBinaryPoint(row + 1, col - 1) +
                getBinaryPoint(row + 1, col) +
                getBinaryPoint(row + 1, col + 1);
    }

    private String getBinaryPoint(int row, int col) {
        try {
            return board[row][col].equals(".") ? "0" : "1";
        } catch (ArrayIndexOutOfBoundsException e) {
            return "0";
        }
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", points))
                .collect(joining("\n"));
    }
}
