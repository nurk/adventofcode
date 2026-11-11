package year2023.puzzle21;

import org.javatuples.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Garden {

    private final String[][] board;

    public Garden(List<String> input) {
        board = new String[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> board[row][column] = columns.get(column));
                });
    }

    public int getRows() {
        return board.length;
    }

    public int getColumns() {
        return board[0].length;
    }

    public Pair<Integer, Integer> getStartPosition() {
        for (int row = 0; row < getRows(); row++) {
            for (int column = 0; column < getColumns(); column++) {
                if (board[row][column].equals("S")) {
                    return new Pair<>(row, column);
                }
            }
        }
        throw new IllegalStateException("Start position not found");
    }

    public boolean isGardenPlot(Pair<Integer, Integer> position) {
        int row = Math.floorMod(position.getValue0(), getRows());
        int column = Math.floorMod(position.getValue1(), getColumns());
        String s = board[row][column];
        return s.equals(".") || s.equals("S");
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", points))
                .collect(joining("\n"));
    }
}
