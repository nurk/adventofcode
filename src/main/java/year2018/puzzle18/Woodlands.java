package year2018.puzzle18;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Woodlands {
    private String[][] woodlands;

    public Woodlands(List<String> input) {
        woodlands = new String[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> woodlands[row][column] = columns.get(column));
                });
    }

    public void advanceOneMinute() {
        String[][] newWoodlands = new String[woodlands.length][woodlands[0].length];

        for (int row = 0; row < woodlands.length; row++) {
            for (int column = 0; column < woodlands[row].length; column++) {
                String current = woodlands[row][column];

                switch (current) {
                    case "." ->
                            newWoodlands[row][column] = getNumberOfNeighborsOfType(row, column, "|") >= 3 ? "|" : ".";
                    case "|" ->
                            newWoodlands[row][column] = getNumberOfNeighborsOfType(row, column, "#") >= 3 ? "#" : "|";
                    case "#" -> newWoodlands[row][column] = (getNumberOfNeighborsOfType(row,
                            column,
                            "#") >= 1 && getNumberOfNeighborsOfType(row, column, "|") >= 1) ? "#" : ".";
                }
            }
        }

        woodlands = newWoodlands;
    }

    public int getTotalNumberOf(String type) {
        int sum = 0;
        for (String[] woodland : woodlands) {
            for (String s : woodland) {
                if (s.equals(type)) {
                    sum++;
                }
            }
        }

        return sum;
    }

    public String getState() {
        StringBuilder state = new StringBuilder();
        for (String[] row : woodlands) {
            for (String cell : row) {
                state.append(cell);
            }
        }
        return state.toString();
    }

    private int getNumberOfNeighborsOfType(int row, int column, String type) {
        int count = 0;
        count += isPositionOfType(row - 1, column - 1, type);
        count += isPositionOfType(row - 1, column, type);
        count += isPositionOfType(row - 1, column + 1, type);
        count += isPositionOfType(row, column - 1, type);
        count += isPositionOfType(row, column + 1, type);
        count += isPositionOfType(row + 1, column - 1, type);
        count += isPositionOfType(row + 1, column, type);
        count += isPositionOfType(row + 1, column + 1, type);
        return count;
    }

    private int isPositionOfType(int row, int column, String type) {
        try {
            return woodlands[row][column].equals(type) ? 1 : 0;
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }

    @Override
    public String toString() {
        return Arrays.stream(woodlands)
                .map(points -> String.join("", Arrays.stream(points).map(String::valueOf).toList()))
                .collect(joining("\n"));
    }
}
