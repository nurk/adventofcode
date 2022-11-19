package year2015.puzzle18;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Puzzle18 {

    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2015/input18B.txt"));
        Board board = new Board(input);
        for (int i = 0; i < 100; i++) {
            board.doStep(true);
        }
        //System.out.println(board);
        System.out.println(board.countOnLights());
    }

    static class Board {
        private String[][] board;

        public Board(List<String> input) {
            board = new String[input.size()][input.get(0).length()];
            IntStream.range(0, input.size())
                    .forEach(row -> {
                        List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                        IntStream.range(0, columns.size())
                                .forEach(column -> board[row][column] = columns.get(column));
                    });
        }

        public void doStep(boolean isPartB) {
            String[][] newBoard = new String[board.length][board[0].length];

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    long onNeighbours = countOnNeighbours(i, j);

                    if (isPartB && isCorner(i, j)) {
                        newBoard[i][j] = "#";
                    } else if (isPositionOn(i, j)) {
                        if (onNeighbours == 2 || onNeighbours == 3) {
                            newBoard[i][j] = "#";
                        } else {
                            newBoard[i][j] = ".";
                        }
                    } else {
                        if (onNeighbours == 3) {
                            newBoard[i][j] = "#";
                        } else {
                            newBoard[i][j] = ".";
                        }
                    }

                }
            }

            this.board = newBoard;
        }

        private boolean isCorner(int i, int j) {
            return (i == 0 || i == board.length - 1) && (j == 0 || j == board[i].length - 1);
        }

        public int countOnLights() {
            int sum = 0;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (isPositionOn(i, j)) {
                        sum++;
                    }
                }
            }

            return sum;
        }

        private long countOnNeighbours(int i, int j) {
            return Stream.of(
                            isPositionOn(i - 1, j - 1),
                            isPositionOn(i - 1, j),
                            isPositionOn(i - 1, j + 1),
                            isPositionOn(i, j - 1),
                            isPositionOn(i, j + 1),
                            isPositionOn(i + 1, j - 1),
                            isPositionOn(i + 1, j),
                            isPositionOn(i + 1, j + 1)
                    ).filter(b -> b)
                    .count();
        }

        private boolean isPositionOn(int i, int j) {
            try {
                return StringUtils.equals(board[i][j], "#");
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        public String toString() {
            return Arrays.stream(board)
                    .map(points -> String.join("", points))
                    .collect(joining("\n"));
        }
    }
}
