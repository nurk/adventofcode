package year2023.puzzle13;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class LavaIsland {

    private final String[][] originalIslands;

    public LavaIsland(List<String> input) {
        originalIslands = new String[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> originalIslands[row][column] = columns.get(column));
                });
    }

    public long solvePartA() {
        long result = solveRows(originalIslands, 100, Pair.of(-1, -1));
        if (result == -1) {
            String[][] rotated90 = rotate90Clockwise(originalIslands);
            result = solveRows(rotated90, 1, Pair.of(-1, -1));
        }

        return result;
    }

    public long solvePartB() {
        String[][] originalClone = new String[originalIslands.length][];
        for (int i = 0; i < originalIslands.length; i++) {
            originalClone[i] = originalIslands[i].clone();
        }
        String[][] rotated90 = rotate90Clockwise(originalIslands);

        Pair<Integer, Integer> mirrorRowsOriginal = getMirrorRows(originalClone);
        Pair<Integer, Integer> mirrorRowsRotated = getMirrorRows(rotated90);

        long result = solveWithSmudges(originalClone, 100, mirrorRowsOriginal);
        if (result != -1) {
            return result;
        }

        return solveWithSmudges(rotated90, 1, mirrorRowsRotated);
    }

    private long solveWithSmudges(String[][] islands, long multiplier, Pair<Integer, Integer> originalMirrorRows) {
        for (int row = 0; row < islands.length; row++) {
            for (int col = 0; col < islands[row].length; col++) {
                if (islands[row][col].equals("#")) {
                    islands[row][col] = ".";
                } else if (islands[row][col].equals(".")) {
                    islands[row][col] = "#";
                }
                long result = solveRows(islands, multiplier, originalMirrorRows);
                if (islands[row][col].equals("#")) {
                    islands[row][col] = ".";
                } else if (islands[row][col].equals(".")) {
                    islands[row][col] = "#";
                }
                if (result != -1) {
                    return result;
                }
            }
        }
        return -1;
    }


    private long solveRows(String[][] islands, long multiplier, Pair<Integer, Integer> originalMirrorRows) {
        int mirrorRow1 = -1;
        int mirrorRow2 = -1;
        for (int i = 0; i < islands.length - 1; i++) {
            if (Pair.of(i, i + 1).equals(originalMirrorRows)) {
                continue;
            }
            if (Arrays.equals(islands[i], islands[i + 1]) && isRowReflectionValid(i, i + 1, islands)) {
                mirrorRow1 = i;
                mirrorRow2 = i + 1;
            }
        }
        if (mirrorRow2 == -1) {
            return -1;
        }

        return (mirrorRow1 + 1) * multiplier;
    }

    private Pair<Integer, Integer> getMirrorRows(String[][] islands) {
        int mirrorRow1 = -1;
        int mirrorRow2 = -1;
        for (int i = 0; i < islands.length - 1; i++) {
            if (Arrays.equals(islands[i], islands[i + 1]) && isRowReflectionValid(i, i + 1, islands)) {
                mirrorRow1 = i;
                mirrorRow2 = i + 1;
            }
        }
        return Pair.of(mirrorRow1, mirrorRow2);
    }

    private boolean isRowReflectionValid(int mirrorRow1, int mirrorRow2, String[][] islands) {
        while (mirrorRow1 >= 0 && mirrorRow2 < islands.length) {
            if (!Arrays.equals(islands[mirrorRow1], islands[mirrorRow2])) {
                return false;
            }
            mirrorRow1--;
            mirrorRow2++;
        }
        return true;
    }

    private String[][] rotate90Clockwise(String[][] islands) {
        int rows = islands.length;
        int cols = islands[0].length;
        String[][] rotated = new String[cols][rows];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                rotated[c][rows - 1 - r] = islands[r][c];
            }
        }
        return rotated;
    }

    @Override
    public String toString() {
        return islandsToString(originalIslands);
    }

    public String islandsToString(String[][] islands) {
        return Arrays.stream(islands)
                .map(points -> String.join("", Arrays.stream(points).toList()))
                .collect(joining("\n"));
    }
}
