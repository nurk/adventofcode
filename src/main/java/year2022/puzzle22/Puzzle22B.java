package year2022.puzzle22;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Puzzle22B {

    //row, cols
    // to high 143415
    // to high 107527
    // to low  20572
    // not correct 56082
    static String[][] board;
    static List<String> moves = new ArrayList<>();
    static List<Direction> directions = List.of(Direction.RIGHT, Direction.DOWN, Direction.LEFT, Direction.UP);
    static int currentDirection = 0;
    static int currentRow = 0;
    static int currentCol = 0;
    static int squareSize = 50;


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
                    board[currentRow][currentCol] = directions.get(currentDirection).symbol;
                    //printBoard();
                    System.out.println("row " + currentRow + " col " + currentCol + " quadrant column " + currentCol / squareSize + " quadrant row " + currentRow / squareSize + " direction " + directions.get(
                            currentDirection));
                    Direction dir = directions.get(currentDirection);

                    int newRow = dir.row.apply(currentRow);
                    int newCol = dir.column.apply(currentCol);
                    int tempNewDirection = currentDirection;

                    //vertically we can wrap around which we should not do
                    if (" ".equals(board[newRow][newCol]) || (dir == Direction.DOWN && currentRow == board.length - 1) || (dir == Direction.UP && currentRow == 0)) {
                        Quadrant quadrant = new Quadrant(currentRow / squareSize, currentCol / squareSize);
                        Quadrant newQuadrant = quadrant.moveToQuadrant(dir);
                        tempNewDirection = directions.indexOf(quadrant.newQuadrantDirection(dir));

                        newRow = quadrant.newQuadrantRow(dir, quadrant.newQuadrantDirection(dir), newQuadrant);
                        newCol = quadrant.newQuadrantColumn(dir, quadrant.newQuadrantDirection(dir), newQuadrant);
                    }

                    if (!board[newRow][newCol].equals("#")) {
                        currentRow = newRow;
                        currentCol = newCol;
                        currentDirection = tempNewDirection;
                    } else {
                        System.out.println("brerak " + board[newRow][newCol]);
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
        System.out.println(currentRow + 1);
        System.out.println(currentCol + 1);
        System.out.println(directions.get(currentDirection));
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

    static class Quadrant {
        final int row, col;

        public Quadrant(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public Quadrant moveToQuadrant(Direction direction) {
            if (row == 0 && col == 2) {
                return switch (direction) {
                    case DOWN -> new Quadrant(1, 2);
                    case RIGHT -> new Quadrant(2, 3);
                    case LEFT -> new Quadrant(1, 1);
                    case UP -> new Quadrant(1, 0);
                };
            }
            if (row == 1 && col == 2) {
                return switch (direction) {
                    case DOWN -> new Quadrant(2, 2);
                    case RIGHT -> new Quadrant(2, 3);
                    case LEFT -> new Quadrant(1, 1);
                    case UP -> new Quadrant(0, 2);
                };
            }
            if (row == 2 && col == 2) {
                return switch (direction) {
                    case DOWN -> new Quadrant(1, 0);
                    case RIGHT -> new Quadrant(2, 3);
                    case LEFT -> new Quadrant(1, 1);
                    case UP -> new Quadrant(1, 2);
                };
            }
            if (row == 1 && col == 0) {
                return switch (direction) {
                    case DOWN -> new Quadrant(2, 2);
                    case RIGHT -> new Quadrant(1, 1);
                    case LEFT -> new Quadrant(2, 3);
                    case UP -> new Quadrant(0, 2);
                };
            }
            if (row == 2 && col == 3) {
                return switch (direction) {
                    case DOWN -> new Quadrant(1, 0);
                    case RIGHT -> new Quadrant(0, 2);
                    case LEFT -> new Quadrant(2, 2);
                    case UP -> new Quadrant(1, 2);
                };
            }
            if (row == 1 && col == 1) {
                return switch (direction) {
                    case DOWN -> new Quadrant(2, 2);
                    case RIGHT -> new Quadrant(1, 2);
                    case LEFT -> new Quadrant(1, 0);
                    case UP -> new Quadrant(0, 2);
                };
            }
            throw new IllegalArgumentException();
        }

        public Direction newQuadrantDirection(Direction direction) {
            if (row == 0 && col == 2) {
                return switch (direction) {
                    case DOWN -> Direction.DOWN;
                    case RIGHT -> Direction.LEFT;
                    case LEFT -> Direction.DOWN;
                    case UP -> Direction.DOWN;
                };
            }
            if (row == 1 && col == 2) {
                return switch (direction) {
                    case DOWN -> Direction.DOWN;
                    case RIGHT -> Direction.DOWN;
                    case LEFT -> Direction.LEFT;
                    case UP -> Direction.UP;
                };
            }
            if (row == 2 && col == 2) {
                return switch (direction) {
                    case DOWN -> Direction.UP;
                    case RIGHT -> Direction.RIGHT;
                    case LEFT -> Direction.UP;
                    case UP -> Direction.UP;
                };
            }
            if (row == 1 && col == 0) {
                return switch (direction) {
                    case DOWN -> Direction.UP;
                    case RIGHT -> Direction.RIGHT;
                    case LEFT -> Direction.UP;
                    case UP -> Direction.DOWN;
                };
            }
            if (row == 2 && col == 3) {
                return switch (direction) {
                    case DOWN -> Direction.RIGHT;
                    case RIGHT -> Direction.LEFT;
                    case LEFT -> Direction.LEFT;
                    case UP -> Direction.LEFT;
                };
            }
            if (row == 1 && col == 1) {
                return switch (direction) {
                    case DOWN -> Direction.RIGHT;
                    case RIGHT -> Direction.RIGHT;
                    case LEFT -> Direction.LEFT;
                    case UP -> Direction.RIGHT;
                };
            }
            throw new IllegalArgumentException();
        }

        public int newQuadrantRow(Direction oldDirection, Direction newDirection, Quadrant newQuadrant) {
            return switch (oldDirection) {
                case RIGHT -> switch (newDirection) {
                    case DOWN -> newQuadrant.row * squareSize;
                    case RIGHT -> currentRow;
                    case LEFT -> newQuadrant.row * squareSize + squareSize - 1 - currentRow % squareSize;
                    case UP -> throw new IllegalArgumentException();
                };
                case LEFT -> switch (newDirection) {
                    case DOWN -> newQuadrant.row * squareSize;
                    case RIGHT -> throw new IllegalArgumentException();
                    case LEFT -> currentRow;
                    case UP -> newQuadrant.row * squareSize + squareSize - 1;
                };
                case DOWN -> switch (newDirection) {
                    case DOWN -> currentRow + 1;
                    case RIGHT -> newQuadrant.row * squareSize + squareSize - 1 - currentCol % squareSize;
                    case LEFT -> throw new IllegalArgumentException();
                    case UP -> newQuadrant.row * squareSize + squareSize - 1;
                };
                case UP -> switch (newDirection) {
                    case DOWN -> newQuadrant.row * squareSize;
                    case RIGHT -> newQuadrant.row * squareSize + currentCol % squareSize;
                    case LEFT -> newQuadrant.row * squareSize + squareSize - 1 - currentCol % squareSize;
                    case UP -> currentRow - 1;
                };
            };
        }

        public int newQuadrantColumn(Direction oldDirection, Direction newDirection, Quadrant newQuadrant) {
            return switch (oldDirection) {
                case RIGHT -> switch (newDirection) {
                    case DOWN -> newQuadrant.col * squareSize + squareSize - 1 - currentRow % squareSize;
                    case RIGHT -> newQuadrant.col * squareSize;
                    case LEFT -> newQuadrant.col * squareSize + squareSize - 1;
                    case UP -> throw new IllegalArgumentException();
                };
                case LEFT -> switch (newDirection) {
                    case DOWN -> newQuadrant.col * squareSize + squareSize - 1;
                    case RIGHT -> throw new IllegalArgumentException();
                    case LEFT -> currentCol - 1;
                    case UP -> newQuadrant.col * squareSize + currentRow % squareSize;
                };
                case DOWN -> switch (newDirection) {
                    case DOWN -> currentCol;
                    case RIGHT -> newQuadrant.col * squareSize;
                    case LEFT -> throw new IllegalArgumentException();
                    case UP -> newQuadrant.col * squareSize + squareSize - 1 - currentCol % squareSize;
                };
                case UP -> switch (newDirection) {
                    case DOWN -> newQuadrant.col * squareSize + squareSize - 1 - currentCol % squareSize;
                    case RIGHT -> newQuadrant.col * squareSize;
                    case LEFT -> newQuadrant.col * squareSize + squareSize - 1;
                    case UP -> currentCol;
                };
            };
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Quadrant quadrant = (Quadrant) o;

            if (row != quadrant.row) {
                return false;
            }
            return col == quadrant.col;
        }

        @Override
        public int hashCode() {
            int result = row;
            result = 31 * result + col;
            return result;
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
        String fileName = "2022/input22board.txt";
        List<String> lines = Utils.getInput(fileName);
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
        //printBoard();
        if (!fileName.contains("test")) {
            //rearrange board
            String[][] newBoard = new String[150][200];
            Arrays.stream(newBoard).forEach(a -> Arrays.fill(a, " "));
            for (int j = 50; j < 100; j++) {
                for (int i = 0; i < 150; i++) {
                    newBoard[i][j + 50] = board[i][j];
                }
            }

            //3,1 -> 1,0
            String[][] temp = new String[50][50];
            for (int i = 150; i < 200; i++) {
                for (int j = 0; j < 50; j++) {
                    temp[i - 150][j] = board[i][j];

                }
            }
            temp = rotate(temp);
            for (int i = 0; i < 50; i++) {
                for (int j = 0; j < 50; j++) {
                    newBoard[i + 50][j] = temp[i][j];

                }
            }

            //0,2-> 2,3
            temp = new String[50][50];
            for (int i = 0; i < 50; i++) {
                for (int j = 100; j < 150; j++) {
                    temp[i][j - 100] = board[i][j];

                }
            }
            temp = rotate(temp);
            temp = rotate(temp);

            for (int i = 0; i < 50; i++) {
                for (int j = 0; j < 50; j++) {
                    newBoard[i + 100][j + 150] = temp[i][j];

                }
            }

            //2,0-> 1,1
            temp = new String[50][50];
            for (int i = 100; i < 150; i++) {
                for (int j = 0; j < 50; j++) {
                    temp[i - 100][j] = board[i][j];

                }
            }
            temp = rotate(temp);
            for (int i = 0; i < 50; i++) {
                for (int j = 0; j < 50; j++) {
                    newBoard[i + 50][j + 50] = temp[i][j];

                }
            }
            board = newBoard;
        }
        printBoard();
    }

    private static void printBoard() {
        System.out.println(Arrays.stream(board)
                .map(row -> Arrays.stream(row)
                        .map(String::valueOf)
                        .collect(Collectors.joining("")))
                .collect(Collectors.joining("\n")));
    }

    private static String[][] rotate(String[][] matrix) {
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < i; ++j) {
                String temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        return matrix;
    }
}
