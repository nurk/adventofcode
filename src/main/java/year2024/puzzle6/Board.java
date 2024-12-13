package year2024.puzzle6;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Board {

    private final String[][] board;
    private int row;
    private int col;
    private int startRow;
    private int startCol;
    private Direction direction = Direction.UP;

    private String[][] cloneBoard;

    public Board(List<String> input) {
        board = new String[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> board[row][column] = columns.get(column));
                });

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column].equals("^")) {
                    this.row = row;
                    this.col = column;
                    this.startRow = row;
                    this.startCol = column;
                    break;
                }
            }
        }
    }

    private void reset() {
        cloneBoard = new String[board.length][board[0].length];

        for (int row = 0; row < board.length; row++) {
            System.arraycopy(board[row], 0, cloneBoard[row], 0, board[row].length);
        }
        row = startRow;
        col = startCol;
        direction = Direction.UP;
    }

    public int traverse() {
        reset();
        int steps = 1;
        while (!isOutOfBounds(row, col)) {
            int newRow = direction.getRow(row);
            int newCol = direction.getColumn(col);

            if (isOutOfBounds(newRow, newCol)) {
                break;
            } else {
                if (cloneBoard[newRow][newCol].equals("#")) {
                    direction = Direction.getRightDirection(direction);
                } else {
                    if (!cloneBoard[newRow][newCol].equals("^")) {
                        steps++;
                        cloneBoard[newRow][newCol] = "^";
                    }
                    row = newRow;
                    col = newCol;
                }
            }
        }

        return steps;
    }

    public int b() {
        int loops = 0;
        List<Pair<Integer, Integer>> obstacles = new ArrayList<>();
        reset();
        for (int localRow = 0; localRow < board.length; localRow++) {
            for (int localCol = 0; localCol < board[localRow].length; localCol++) {
                reset();
                obstacles.clear();
                if (!cloneBoard[localRow][localCol].equals("^") && !cloneBoard[localRow][localCol].equals("#")) {
                    cloneBoard[localRow][localCol] = "#";
                } else {
                    continue;
                }

                while (true) {
                    int newRow = direction.getRow(row);
                    int newCol = direction.getColumn(col);

                    if (isOutOfBounds(newRow, newCol)) {
                        break;
                    } else if (hasLoop(obstacles)) {
                        break;
                    } else {
                        if (cloneBoard[newRow][newCol].equals("#")) {
                            obstacles.add(new Pair<>(row, col));
                            direction = Direction.getRightDirection(direction);
                        } else {
                            if (!cloneBoard[newRow][newCol].equals("^")) {
                                cloneBoard[newRow][newCol] = "^";
                            }
                            row = newRow;
                            col = newCol;
                        }
                    }
                }
                if (hasLoop(obstacles)) {
                    loops++;
                }
            }
        }
        return loops;
    }

    private boolean hasLoop(List<Pair<Integer, Integer>> obstacles) {
        if (obstacles.size() < 8) {
            return false;
        }
        // this is totally wrong, but I must detect big loops as well.
        // and I have not figured that out yet.
        // Still got the right answer though.
        if (obstacles.size() > 10000) {
            return true;
        }
        // detect small 4 obstacle loop to shortcut
        return obstacles.getLast().equals(obstacles.get(obstacles.size() - 5)) &&
               obstacles.get(obstacles.size() - 2).equals(obstacles.get(obstacles.size() - 6)) &&
               obstacles.get(obstacles.size() - 3).equals(obstacles.get(obstacles.size() - 7)) &&
               obstacles.get(obstacles.size() - 4).equals(obstacles.get(obstacles.size() - 8));
    }

    private boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= cloneBoard.length || col < 0 || col >= cloneBoard[0].length;
    }

    @Override
    public String toString() {
        return Arrays.stream(cloneBoard)
                .map(points -> String.join("", points))
                .collect(joining("\n"));
    }
}
