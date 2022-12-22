package year2022.puzzle22;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Puzzle22B {

    static String[][] board;
    final static List<String> moves = new ArrayList<>();
    final static List<Direction> directions = List.of(Direction.RIGHT, Direction.DOWN, Direction.LEFT, Direction.UP);
    static int currentDirectionIndex = 0;
    static int currentRow = 0;
    static int currentCol = 0;
    static int squareSize = 50;

    // Part B: 55267
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
                moveMe(move);
            } else {
                changeDirection(move);
            }
        }
        System.out.println(currentRow + 1);
        System.out.println(currentCol + 1);
        System.out.println(directions.get(currentDirectionIndex));
        System.out.println((currentRow + 1) * 1000 + (currentCol + 1) * 4 + directions.get(currentDirectionIndex).value);
    }

    private static void changeDirection(String move) {
        if ("L".equals(move)) {
            currentDirectionIndex = Math.floorMod(currentDirectionIndex - 1, directions.size());
        } else {
            currentDirectionIndex = Math.floorMod(currentDirectionIndex + 1, directions.size());
        }
    }

    private static void moveMe(String move) {
        for (int i = 0; i < Integer.parseInt(move); i++) {
            Direction currentDirection = directions.get(currentDirectionIndex);

            int newRow = currentDirection.row.apply(currentRow);
            int newCol = currentDirection.column.apply(currentCol);
            int tempNewDirectionIndex = currentDirectionIndex;

            //we can wrap around which we should not do, we should move quadrant instead
            if (newRow < 0 || newCol < 0 || newRow >= board.length || newCol >= board[0].length || " ".equals(board[newRow][newCol])) {
                Quadrant currentQuadrant = new Quadrant(currentRow / squareSize, currentCol / squareSize);
                Quadrant newQuadrant = currentQuadrant.moveToQuadrant(currentDirection);
                Direction directionInNewQuadrant = currentQuadrant.newQuadrantDirection(currentDirection);
                tempNewDirectionIndex = directions.indexOf(directionInNewQuadrant);

                newRow = newQuadrantRow(currentDirection, directionInNewQuadrant, newQuadrant);
                newCol = newQuadrantColumn(currentDirection, directionInNewQuadrant, newQuadrant);
            }

            if (board[newRow][newCol].equals(".")) {
                currentRow = newRow;
                currentCol = newCol;
                currentDirectionIndex = tempNewDirectionIndex;
            } else {
                break;
            }
        }
    }

    enum Direction {
        RIGHT(0, ">", i -> i, i -> i + 1),
        DOWN(1, "v", i -> i + 1, i -> i),
        LEFT(2, "<", i -> i, i -> i - 1),
        UP(3, "^", i -> i - 1, i -> i);

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

    record Quadrant(int row, int col) {

        public Quadrant moveToQuadrant(Direction direction) {
                if (squareSize == 4) {
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
                } else {
                    if (row == 0 && col == 1) {
                        return switch (direction) {
                            case DOWN -> new Quadrant(1, 1);
                            case RIGHT -> new Quadrant(0, 2);
                            case LEFT -> new Quadrant(2, 0);
                            case UP -> new Quadrant(3, 0);
                        };
                    }
                    if (row == 1 && col == 1) {
                        return switch (direction) {
                            case DOWN -> new Quadrant(2, 1);
                            case RIGHT -> new Quadrant(0, 2);
                            case LEFT -> new Quadrant(2, 0);
                            case UP -> new Quadrant(0, 1);
                        };
                    }
                    if (row == 2 && col == 1) {
                        return switch (direction) {
                            case DOWN -> new Quadrant(3, 0);
                            case RIGHT -> new Quadrant(0, 2);
                            case LEFT -> new Quadrant(2, 0);
                            case UP -> new Quadrant(1, 1);
                        };
                    }
                    if (row == 3 && col == 0) {
                        return switch (direction) {
                            case DOWN -> new Quadrant(0, 2);
                            case RIGHT -> new Quadrant(2, 1);
                            case LEFT -> new Quadrant(0, 1);
                            case UP -> new Quadrant(2, 0);
                        };
                    }
                    if (row == 0 && col == 2) {
                        return switch (direction) {
                            case DOWN -> new Quadrant(1, 1);
                            case RIGHT -> new Quadrant(2, 1);
                            case LEFT -> new Quadrant(0, 1);
                            case UP -> new Quadrant(3, 0);
                        };
                    }
                    if (row == 2 && col == 0) {
                        return switch (direction) {
                            case DOWN -> new Quadrant(3, 0);
                            case RIGHT -> new Quadrant(2, 1);
                            case LEFT -> new Quadrant(0, 1);
                            case UP -> new Quadrant(1, 1);
                        };
                    }
                }
                throw new IllegalArgumentException();
            }

            public Direction newQuadrantDirection(Direction direction) {
                if (squareSize == 4) {
                    if (row == 0 && col == 2) {
                        return switch (direction) {
                            case DOWN, LEFT, UP -> Direction.DOWN;
                            case RIGHT -> Direction.LEFT;
                        };
                    }
                    if (row == 1 && col == 2) {
                        return switch (direction) {
                            case DOWN, RIGHT -> Direction.DOWN;
                            case LEFT -> Direction.LEFT;
                            case UP -> Direction.UP;
                        };
                    }
                    if (row == 2 && col == 2) {
                        return switch (direction) {
                            case DOWN, UP, LEFT -> Direction.UP;
                            case RIGHT -> Direction.RIGHT;
                        };
                    }
                    if (row == 1 && col == 0) {
                        return switch (direction) {
                            case DOWN, LEFT -> Direction.UP;
                            case RIGHT -> Direction.RIGHT;
                            case UP -> Direction.DOWN;
                        };
                    }
                    if (row == 2 && col == 3) {
                        return switch (direction) {
                            case DOWN -> Direction.RIGHT;
                            case RIGHT, LEFT, UP -> Direction.LEFT;
                        };
                    }
                    if (row == 1 && col == 1) {
                        return switch (direction) {
                            case DOWN, UP, RIGHT -> Direction.RIGHT;
                            case LEFT -> Direction.LEFT;
                        };
                    }
                } else {
                    if (row == 0 && col == 1) {
                        return switch (direction) {
                            case DOWN -> Direction.DOWN;
                            case RIGHT, LEFT, UP -> Direction.RIGHT;
                        };
                    }
                    if (row == 1 && col == 1) {
                        return switch (direction) {
                            case DOWN, LEFT -> Direction.DOWN;
                            case RIGHT, UP -> Direction.UP;
                        };
                    }
                    if (row == 2 && col == 1) {
                        return switch (direction) {
                            case DOWN, RIGHT, LEFT -> Direction.LEFT;
                            case UP -> Direction.UP;
                        };
                    }
                    if (row == 3 && col == 0) {
                        return switch (direction) {
                            case DOWN, LEFT -> Direction.DOWN;
                            case RIGHT, UP -> Direction.UP;
                        };
                    }
                    if (row == 0 && col == 2) {
                        return switch (direction) {
                            case DOWN, LEFT, RIGHT -> Direction.LEFT;
                            case UP -> Direction.UP;
                        };
                    }
                    if (row == 2 && col == 0) {
                        return switch (direction) {
                            case DOWN -> Direction.DOWN;
                            case RIGHT, LEFT, UP -> Direction.RIGHT;
                        };
                    }
                }
                throw new IllegalArgumentException();
            }
        }

    private static int newQuadrantRow(Direction oldDirection, Direction newDirection, Quadrant newQuadrant) {
        return switch (oldDirection) {
            case RIGHT -> switch (newDirection) {
                case DOWN -> newQuadrant.row * squareSize;
                case RIGHT -> newQuadrant.row * squareSize + currentRow % squareSize;
                case LEFT -> newQuadrant.row * squareSize + squareSize - 1 - currentRow % squareSize;
                case UP -> newQuadrant.row * squareSize + squareSize - 1;
            };
            case LEFT -> switch (newDirection) {
                case DOWN -> newQuadrant.row * squareSize;
                case RIGHT -> newQuadrant.row * squareSize + squareSize - 1 - currentRow % squareSize;
                case LEFT -> newQuadrant.row * squareSize + currentRow % squareSize;
                case UP -> newQuadrant.row * squareSize + squareSize - 1;
            };
            case DOWN -> switch (newDirection) {
                case DOWN -> newQuadrant.row * squareSize;
                case RIGHT -> newQuadrant.row * squareSize + squareSize - 1 - currentCol % squareSize;
                case LEFT -> newQuadrant.row * squareSize + currentCol % squareSize;//////////
                case UP -> newQuadrant.row * squareSize + squareSize - 1;
            };
            case UP -> switch (newDirection) {
                case DOWN -> newQuadrant.row * squareSize;
                case RIGHT -> newQuadrant.row * squareSize + currentCol % squareSize;
                case LEFT -> newQuadrant.row * squareSize + squareSize - 1 - currentCol % squareSize;
                case UP -> newQuadrant.row * squareSize + squareSize - 1;
            };
        };
    }

    private static int newQuadrantColumn(Direction oldDirection, Direction newDirection, Quadrant newQuadrant) {
        return switch (oldDirection) {
            case RIGHT -> switch (newDirection) {
                case DOWN -> newQuadrant.col * squareSize + squareSize - 1 - currentRow % squareSize;
                case RIGHT -> newQuadrant.col * squareSize;
                case LEFT -> newQuadrant.col * squareSize + squareSize - 1;
                case UP -> newQuadrant.col * squareSize + currentRow % squareSize;
            };
            case LEFT -> switch (newDirection) {
                case DOWN -> newQuadrant.col * squareSize + currentRow % squareSize;
                case RIGHT -> newQuadrant.col * squareSize;
                case LEFT -> newQuadrant.col * squareSize + squareSize - 1;
                case UP -> newQuadrant.col * squareSize + squareSize - 1 - currentRow % squareSize;
            };
            case DOWN -> switch (newDirection) {
                case DOWN -> newQuadrant.col * squareSize + currentCol % squareSize;
                case RIGHT -> newQuadrant.col * squareSize;
                case LEFT -> newQuadrant.col * squareSize + squareSize - 1;
                case UP -> newQuadrant.col * squareSize + squareSize - 1 - currentCol % squareSize;
            };
            case UP -> switch (newDirection) {
                case DOWN -> newQuadrant.col * squareSize + squareSize - 1 - currentCol % squareSize;
                case RIGHT -> newQuadrant.col * squareSize;
                case LEFT -> newQuadrant.col * squareSize + squareSize - 1;
                case UP -> newQuadrant.col * squareSize + currentCol % squareSize;
            };
        };
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
        if (fileName.contains("test")) {
            squareSize = 4;
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
