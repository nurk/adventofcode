package year2022.puzzle22;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Puzzle22 {

    //row, cols
    static String[][] board;
    static List<String> moves = new ArrayList<>();
    static List<Direction> directions = List.of(Direction.RIGHT, Direction.DOWN, Direction.LEFT, Direction.UP);
    static int currentDirection = 0;
    static int currentRow = 0;
    static int currentCol = 0;

    //part A: 162186

    public static void main(String[] args) {
        initBoard();
        initMoves();

        for (int j = 0; j < board[0].length; j++) {
            if (".".equals(board[0][j])) {
                currentCol = j;
                break;
            }

        }

        for (String move : moves) {
            if (StringUtils.isNumeric(move)) {
                for (int i = 0; i < Integer.parseInt(move); i++) {
                    Direction dir = directions.get(currentDirection);
                    int newRow = dir.row.apply(currentRow);
                    int newCol = dir.column.apply(currentCol);

                    while (" ".equals(board[newRow][newCol])) {
                        newRow = dir.row.apply(newRow);
                        newCol = dir.column.apply(newCol);
                    }

                    if (board[newRow][newCol].equals(".")) {
                        currentRow = newRow;
                        currentCol = newCol;
                    } else {
                        break;
                    }
                }
            } else {
                if ("L".equals(move)) {
                    currentDirection = Math.floorMod(currentDirection - 1, directions.size());
                } else {
                    currentDirection = Math.floorMod(currentDirection + 1, directions.size());
                }
            }
        }
        System.out.println((currentRow + 1) * 1000 + (currentCol + 1) * 4 + directions.get(currentDirection).value);
    }

    enum Direction {
        RIGHT(0, ">", i -> i, i -> Math.floorMod(i + 1, board[0].length)),
        DOWN(1, "v", i -> Math.floorMod(i + 1, board.length), i -> i),
        LEFT(2, "<", i -> i, i -> Math.floorMod(i - 1, board[0].length)),
        UP(3, "^", i -> Math.floorMod(i - 1, board.length), i -> i);

        final int value;
        final String symbol;
        final Function<Integer, Integer> row;
        final Function<Integer, Integer> column;

        Direction(int value, String symbol, Function<Integer, Integer> row, Function<Integer, Integer> column) {
            this.value = value;
            this.symbol = symbol;
            this.row = row;
            this.column = column;
        }
    }

    private static void initMoves() {
        String currentMove = "";
        for (String s : Utils.getInput("2022/input22moves.txt", s1 -> Arrays.stream(s1.split("")).toList())
                .get(0)) {
            if (StringUtils.isNumeric(s)) {
                currentMove = currentMove + s;
            } else {
                moves.add(currentMove);
                moves.add(s);
                currentMove = "";
            }
        }
        moves.add(currentMove);
    }

    private static void initBoard() {
        List<String> lines = Utils.getInput("2022/input22board.txt");
        int columns = lines.stream()
                .map(String::length)
                .max(Integer::compare)
                .orElseThrow();
        board = new String[lines.size()][columns];
        for (int i = 0; i < lines.size(); i++) {
            String line = StringUtils.rightPad(lines.get(i), columns, " ");
            for (int j = 0; j < line.length(); j++) {
                board[i][j] = String.valueOf(line.charAt(j));
            }
        }
    }

    private static void printBoard() {
        System.out.println(Arrays.stream(board)
                .map(row -> Arrays.stream(row)
                        .map(String::valueOf)
                        .collect(Collectors.joining("")))
                .collect(Collectors.joining("\n")));
    }
}
