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
    // not correct 97207
    // 22512 to low
    // 134041 wrong
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
                    //board[currentRow][currentCol] = directions.get(currentDirection).symbol;
                    //printBoard();
                    System.out.println("row " + currentRow + " col " + currentCol + " quadrant column " + currentCol / squareSize + " quadrant row " + currentRow / squareSize + " direction " + directions.get(
                            currentDirection));
                    Direction dir = directions.get(currentDirection);

                    int newRow = dir.row.apply(currentRow);
                    int newCol = dir.column.apply(currentCol);
                    int tempNewDirection = currentDirection;

                    //vertically we can wrap around which we should not do
                    if ((dir == Direction.LEFT && currentCol == 0) || (dir == Direction.RIGHT && currentCol == board[0].length - 1) || (dir == Direction.DOWN && currentRow == board.length - 1) || (dir == Direction.UP && currentRow == 0) || " ".equals(
                            board[newRow][newCol])) {
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
                        System.out.println("break " + board[newRow][newCol]);
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
                throw new IllegalArgumentException();
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
                throw new IllegalArgumentException();
            }
        }

        public Direction newQuadrantDirection(Direction direction) {
            if (squareSize == 4) {
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
            } else {
                if (row == 0 && col == 1) {
                    return switch (direction) {
                        case DOWN -> Direction.DOWN;
                        case RIGHT -> Direction.RIGHT;
                        case LEFT -> Direction.RIGHT;
                        case UP -> Direction.RIGHT;
                    };
                }
                if (row == 1 && col == 1) {
                    return switch (direction) {
                        case DOWN -> Direction.DOWN;
                        case RIGHT -> Direction.UP;
                        case LEFT -> Direction.DOWN;
                        case UP -> Direction.UP;
                    };
                }
                if (row == 2 && col == 1) {
                    return switch (direction) {
                        case DOWN -> Direction.LEFT;
                        case RIGHT -> Direction.LEFT;
                        case LEFT -> Direction.LEFT;
                        case UP -> Direction.UP;
                    };
                }
                if (row == 3 && col == 0) {
                    return switch (direction) {
                        case DOWN -> Direction.DOWN;
                        case RIGHT -> Direction.UP;
                        case LEFT -> Direction.DOWN;
                        case UP -> Direction.UP;
                    };
                }
                if (row == 0 && col == 2) {
                    return switch (direction) {
                        case DOWN -> Direction.LEFT;
                        case RIGHT -> Direction.LEFT;
                        case LEFT -> Direction.LEFT;
                        case UP -> Direction.UP;
                    };
                }
                if (row == 2 && col == 0) {
                    return switch (direction) {
                        case DOWN -> Direction.DOWN;
                        case RIGHT -> Direction.RIGHT;
                        case LEFT -> Direction.RIGHT;
                        case UP -> Direction.RIGHT;
                    };
                }
                throw new IllegalArgumentException();
            }
        }

        public int newQuadrantRow(Direction oldDirection, Direction newDirection, Quadrant newQuadrant) {
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
                    case LEFT -> newQuadrant.row * squareSize - currentCol % squareSize;
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

        public int newQuadrantColumn(Direction oldDirection, Direction newDirection, Quadrant newQuadrant) {
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
