package year2023.puzzle11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class GalaxyMap {

    private final String[][] board;
    private final List<Galaxy> galaxies = new ArrayList<>();
    private final List<Integer> emptyRows = new ArrayList<>();
    private final List<Integer> emptyColumns = new ArrayList<>();

    public GalaxyMap(List<String> input) {
        board = new String[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> {
                                board[row][column] = columns.get(column);
                                if ("#".equals(board[row][column])) {
                                    galaxies.add(new Galaxy(row, column));
                                }
                            });
                });

        for (int i = 0; i < board.length; i++) {
            boolean isEmpty = true;
            for (int j = 0; j < board[i].length; j++) {
                if ("#".equals(board[i][j])) {
                    isEmpty = false;
                    break;
                }
            }
            if (isEmpty) {
                emptyRows.add(i);
            }
        }

        for (int j = 0; j < board[0].length; j++) {
            boolean isEmpty = true;
            for (String[] strings : board) {
                if ("#".equals(strings[j])) {
                    isEmpty = false;
                    break;
                }
            }
            if (isEmpty) {
                emptyColumns.add(j);
            }
        }
    }

    public long sumOfDistances(int multiplier) {
        long sum = 0;
        for (int i = 0; i < galaxies.size() - 1; i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                Galaxy galaxyOne = galaxies.get(i);
                Galaxy galaxyTwo = galaxies.get(j);
                long distance = galaxyOne.getDistance(galaxyTwo, emptyRows, emptyColumns, multiplier);

                sum += distance;
            }
        }

        return sum;
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", Arrays.stream(points).toList()))
                .collect(joining("\n"));
    }

    private record Galaxy(int row, int column) {

        private long getManhattanDistance(Galaxy other) {
            return Math.abs(this.row - other.row) + Math.abs(this.column - other.column);
        }

        public long getDistance(Galaxy other, List<Integer> emptyRows, List<Integer> emptyColumns, int multiplier) {
            int minRow = Math.min(this.row, other.row);
            int maxRow = Math.max(this.row, other.row);
            int minColumn = Math.min(this.column, other.column);
            int maxColumn = Math.max(this.column, other.column);

            long emptyRowCount = emptyRows.stream()
                    .filter(r -> r > minRow && r < maxRow)
                    .count();
            long emptyColumnCount = emptyColumns.stream()
                    .filter(c -> c > minColumn && c < maxColumn)
                    .count();

            return this.getManhattanDistance(other) + (emptyRowCount + emptyColumnCount) * multiplier;
        }
    }
}
