package year2017.puzzle21;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rule {
    private final String inputPattern;
    private final String outputPattern;

    private final String[][] inputGrid;
    @Getter
    private final String[][] outputGrid;

    private final List<String[][]> transformations = new ArrayList<>();

    public Rule(String line) {
        String[] parts = line.split(" => ");
        this.inputPattern = parts[0];
        this.outputPattern = parts[1];

        this.inputGrid = parsePattern(inputPattern);
        this.outputGrid = parsePattern(outputPattern);

        String[][] currentTransformation = inputGrid;
        for (int i = 0; i < 4; i++) {
            transformations.add(currentTransformation);
            currentTransformation = rotate90Clockwise(currentTransformation);
        }

        currentTransformation = flipHorizontally(inputGrid);
        for (int i = 0; i < 4; i++) {
            transformations.add(currentTransformation);
            currentTransformation = rotate90Clockwise(currentTransformation);
        }
    }

    public boolean matches(String[][] grid) {
        if (grid.length != inputGrid.length) {
            return false;
        }

        for (String[][] transformation : transformations) {
            boolean equal = true;
            for (int i = 0; i < grid.length; i++) {
                if (!Arrays.equals(grid[i], transformation[i])) {
                    equal = false;
                    break;
                }
            }
            if (equal) {
                return true;
            }
        }
        return false;
    }

    private String[][] parsePattern(String pattern) {
        String[] rows = pattern.split("/");
        String[][] grid = new String[rows.length][rows.length];
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows.length; j++) {
                grid[i][j] = String.valueOf(rows[i].charAt(j));
            }
        }
        return grid;
    }

    private String[][] rotate90Clockwise(String[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        String[][] rotated = new String[cols][rows];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                rotated[c][rows - 1 - r] = grid[r][c];
            }
        }
        return rotated;
    }

    private String[][] flipHorizontally(String[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        String[][] flipped = new String[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                flipped[r][cols - 1 - c] = grid[r][c];
            }
        }
        return flipped;
    }

    @Override
    public String toString() {
        return inputPattern + " => " + outputPattern
                + "\nInput Grid: \n" + Puzzle21.gridToString(inputGrid)
                + "\nOutput Grid: \n" + Puzzle21.gridToString(outputGrid);
    }


}
