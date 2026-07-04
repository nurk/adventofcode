package year2019.puzzle24;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Bugs {

    @Getter
    private String[][] board;

    public Bugs(List<String> input) {
        board = new String[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> board[row][column] = columns.get(column));
                });
    }

    public void advance() {
        String[][] newBoard = new String[board.length][board[0].length];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                int count = countNeighbourBugs(row, col);
                if (board[row][col].equals("#")) {
                    if (count == 1) {
                        newBoard[row][col] = "#";
                    } else {
                        newBoard[row][col] = ".";
                    }
                } else {
                    if (count == 1 || count == 2) {
                        newBoard[row][col] = "#";
                    } else {
                        newBoard[row][col] = ".";
                    }
                }
            }
        }
        board = newBoard;
    }

    private int countNeighbourBugs(int row, int col) {
        int bugs = 0;
        bugs += isBug(row - 1, col) ? 1 : 0;
        bugs += isBug(row + 1, col) ? 1 : 0;
        bugs += isBug(row, col - 1) ? 1 : 0;
        bugs += isBug(row, col + 1) ? 1 : 0;
        return bugs;
    }

    private boolean isBug(int row, int col) {
        try {
            return board[row][col].equals("#");
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", points))
                .collect(joining("\n"));
    }

    public long getBioDiversity() {
        long bioDiversity = 0;
        int tile = 0;
        for (String[] strings : board) {
            for (int col = 0; col < board[0].length; col++) {
                if (strings[col].equals("#")) {
                    bioDiversity += (long) Math.pow(2, tile);
                }
                tile++;
            }
        }
        return bioDiversity;
    }
}
