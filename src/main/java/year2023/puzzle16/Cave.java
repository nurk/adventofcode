package year2023.puzzle16;

import org.javatuples.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Cave {

    private final String[][] board;

    public Cave(List<String> input) {
        board = new String[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> board[row][column] = columns.get(column));
                });
    }

    public Optional<String> getAtPosition(Pair<Integer, Integer> position) {
        try {
            return Optional.of(board[position.getValue0()][position.getValue1()]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    public int getRows() {
        return board.length;
    }

    public int getColumns() {
        return board[0].length;
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", points))
                .collect(joining("\n"));
    }
}
