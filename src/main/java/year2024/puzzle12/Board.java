package year2024.puzzle12;

import org.javatuples.Pair;

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

    public List<Region> getRegions() {
        List<Region> regions = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                Pair<Integer, Integer> point = Pair.with(row, column);
                regions.stream()
                        .filter(region -> region.getCrop().equals(board[point.getValue0()][point.getValue1()]))
                        .filter(region -> region.touches(point))
                        .findFirst()
                        .ifPresentOrElse(
                                region -> region.add(point),
                                () -> regions.add(new Region(board[point.getValue0()][point.getValue1()], point))
                        );
            }
        }

        List<Region> mergedRegions = new ArrayList<>(regions);
        int size;
        do {
            size = mergedRegions.size();
            mergedRegions = mergeRegions(mergedRegions);
        } while (mergedRegions.size() != size);

        return mergedRegions;
    }

    private List<Region> mergeRegions(List<Region> regions) {
        List<Region> mergedRegions = new ArrayList<>();
        for (int i = regions.size() - 1; i >= 0; i--) {
            Region region = regions.remove(i);

            mergedRegions.stream()
                    .filter(region::touches)
                    .findFirst()
                    .ifPresentOrElse(
                            mRegion -> mRegion.add(region),
                            () -> mergedRegions.add(region)
                    );
        }
        return mergedRegions;
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", Arrays.stream(points).map(String::valueOf).toList()))
                .collect(joining("\n"));
    }
}
