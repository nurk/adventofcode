package year2023.puzzle14;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Platform {
    private final String[][] platform;

    public Platform(List<String> input) {
        platform = new String[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> platform[row][column] = columns.get(column));
                });
    }

    public long solvePartA() {
        String[][] rotatedPlatform = rotate90Clockwise(platform);
        moveBouldersToTheRight(rotatedPlatform);

        String[][] movedPlatform = rotate90CounterClockwise(rotatedPlatform);

        return countBoulderLoad(movedPlatform);
    }

    public long solvePartB() {
        String[][] rotatedPlatform = platform;

        Map<String, Pair<String[][], Integer>> seen = new HashMap<>();
        boolean fastForwarded = false;

        for (int i = 0; i < 1000000000; i++) {
            String key = platformToString(rotatedPlatform);

            if (!fastForwarded && seen.containsKey(key)) {
                int cycleLength = i - seen.get(key).getRight();
                int cyclesInLoop = (1000000000 - seen.get(key).getRight()) / cycleLength;
                int cycleLoopLength = cyclesInLoop * cycleLength;

                i = i + cycleLoopLength - cycleLength - 1;
                fastForwarded = true;

                continue;
            }

            rotatedPlatform = rotate90Clockwise(rotatedPlatform);
            moveBouldersToTheRight(rotatedPlatform);

            rotatedPlatform = rotate90Clockwise(rotatedPlatform);
            moveBouldersToTheRight(rotatedPlatform);

            rotatedPlatform = rotate90Clockwise(rotatedPlatform);
            moveBouldersToTheRight(rotatedPlatform);

            rotatedPlatform = rotate90Clockwise(rotatedPlatform);
            moveBouldersToTheRight(rotatedPlatform);

            seen.put(key, Pair.of(rotatedPlatform, i));
        }

        return countBoulderLoad(rotatedPlatform);
    }

    private long countBoulderLoad(String[][] platform) {
        long total = 0;
        for (int row = 0; row < platform.length; row++) {
            int finalRow = row;
            total += Arrays.stream(platform[row])
                    .map(s -> s.equals("O") ? 1 : 0)
                    .map(n -> n * (platform.length - finalRow))
                    .reduce(0, Integer::sum);
        }

        return total;
    }

    private void moveBouldersToTheRight(String[][] platform) {
        for (String[] row : platform) {
            boolean moved;
            do {
                moved = false;
                for (int column = row.length - 2; column >= 0; column--) {
                    if (row[column].equals("O") && row[column + 1].equals(".")) {
                        row[column] = ".";
                        row[column + 1] = "O";
                        moved = true;
                    }
                }
            } while (moved);
        }
    }

    private String[][] rotate90Clockwise(String[][] platform) {
        int rows = platform.length;
        int cols = platform[0].length;
        String[][] rotated = new String[cols][rows];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                rotated[c][rows - 1 - r] = platform[r][c];
            }
        }
        return rotated;
    }

    private String[][] rotate90CounterClockwise(String[][] platform) {
        int rows = platform.length;
        int cols = platform[0].length;
        String[][] rotated = new String[cols][rows];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                rotated[cols - 1 - c][r] = platform[r][c];
            }
        }
        return rotated;
    }

    @Override
    public String toString() {
        return platformToString(platform);
    }

    public String platformToString(String[][] platform) {
        return Arrays.stream(platform)
                .map(points -> String.join("", Arrays.stream(points).toList()))
                .collect(joining("\n"));
    }
}
