package year2024.puzzle4;

import org.apache.commons.lang3.StringUtils;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Board {

    private final String[][] board;

    public Board(List<String> input) {
        board = new String[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> board[row][column] = columns.get(column));
                });
    }

    public long findXmas() {
        long found = 0;
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                int finalRow = row;
                int finalColumn = column;
                found += Arrays.stream(Direction.values())
                        .filter(direction -> findWord(finalRow,
                                finalColumn,
                                "XMAS",
                                direction))
                        .count();
            }
        }
        return found;
    }

    private boolean findWord(int row, int column, String value, Direction direction) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        if (row < 0 || row >= board.length || column < 0 || column >= board[row].length) {
            return false;
        }
        boolean found = false;
        if (board[row][column].equals(StringUtils.substring(value, 0, 1))) {
            found = findWord(direction.getRow(row),
                    direction.getColumn(column),
                    StringUtils.substring(value, 1),
                    direction);
        }
        return found;
    }

    public long findMas() {
        List<Triplet<Integer, Integer, Direction>> found = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (findWord(row, column, "MAS", Direction.DOWN_LEFT)) {
                    found.add(Triplet.with(row, column, Direction.DOWN_LEFT));
                }
                if (findWord(row, column, "MAS", Direction.DOWN_RIGHT)) {
                    found.add(Triplet.with(row, column, Direction.DOWN_RIGHT));
                }

                if (findWord(row, column, "SAM", Direction.DOWN_LEFT)) {
                    found.add(Triplet.with(row, column, Direction.DOWN_LEFT));
                }
                if (findWord(row, column, "SAM", Direction.DOWN_RIGHT)) {
                    found.add(Triplet.with(row, column, Direction.DOWN_RIGHT));
                }

            }
        }

        return found.stream()
                .filter(triplet -> triplet.getValue2().equals(Direction.DOWN_RIGHT))
                .filter(triplet -> found.contains(Triplet.with(triplet.getValue0(),
                        triplet.getValue1() + 2,
                        Direction.DOWN_LEFT)))
                .count();
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", points))
                .collect(joining("\n"));
    }
}
