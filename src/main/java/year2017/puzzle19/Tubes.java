package year2017.puzzle19;

import org.apache.commons.lang3.tuple.Pair;
import util.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Tubes {
    private final String[][] tubes;
    private int startColumn;

    public Tubes(List<String> input) {
        int maxRows = input.size() + 1;
        int maxColumns = input.stream()
                .map(String::length)
                .max(Integer::compare)
                .orElseThrow() + 1;

        tubes = new String[maxRows][maxColumns];
        Arrays.stream(tubes).forEach(r -> Arrays.fill(r, " "));

        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> tubes[row][column] = columns.get(column));
                });

        for (int col = 0; col < maxColumns; col++) {
            if (tubes[0][col].equals("|")) {
                startColumn = col;
                break;
            }
        }
    }

    public final Pair<String, Integer> solve() {
        int stepCount = 0;
        StringBuilder letters = new StringBuilder();

        int currentRow = 0;
        int currentColumn = startColumn;
        int rowDelta = 1;
        int columnDelta = 0;

        do {
            stepCount++;
            currentRow += rowDelta;
            currentColumn += columnDelta;

            if (Utils.ALPHABET.toUpperCase().contains(tubes[currentRow][currentColumn])) {
                letters.append(tubes[currentRow][currentColumn]);
            } else if (tubes[currentRow][currentColumn].equals("+")) {
                if (rowDelta != 0) {
                    rowDelta = 0;
                    if (tubes[currentRow][currentColumn - 1].equals(" ")) {
                        columnDelta = 1;
                    } else {
                        columnDelta = -1;
                    }
                } else {
                    columnDelta = 0;
                    if (tubes[currentRow - 1][currentColumn].equals(" ")) {
                        rowDelta = 1;
                    } else {
                        rowDelta = -1;
                    }
                }
            }


        } while (!tubes[currentRow][currentColumn].equals(" "));

        return Pair.of(letters.toString(), stepCount);
    }

    @Override
    public String toString() {
        return Arrays.stream(tubes)
                .map(points -> String.join("", points))
                .collect(joining("\n"));
    }
}
