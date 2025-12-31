package year2017.puzzle21;

import lombok.Getter;

import java.util.List;

public class Fractal {
    private final List<Rule> rules;
    @Getter
    private String[][] grid;

    public Fractal(List<Rule> rules) {
        this.rules = rules;
        this.grid = new String[][]{
                {".", "#", "."},
                {".", ".", "#"},
                {"#", "#", "#"}
        };
    }

    private long numberOfOnPixels() {
        long count = 0;
        for (String[] row : grid) {
            for (String cell : row) {
                if (cell.equals("#")) {
                    count++;
                }
            }
        }
        return count;
    }

    public long evaluate() {
        int iterations = rules.size() > 2 ? 5 : 2;
        return evaluate(iterations);
    }

    public long evaluate(int iterations) {
        for (int i = 0; i < iterations; i++) {
            iterate();
        }
        return numberOfOnPixels();
    }

    private void iterate() {
        int gridSize = grid.length;
        int blockSize = (gridSize % 2 == 0) ? 2 : 3;
        int blocksPerRow = gridSize / blockSize;

        int newBlockSize = blockSize + 1;
        int newGridSize = blocksPerRow * newBlockSize;

        String[][] newGrid = new String[newGridSize][newGridSize];

        for (int blockRow = 0; blockRow < blocksPerRow; blockRow++) {
            for (int blockCol = 0; blockCol < blocksPerRow; blockCol++) {
                String[][] block = extractBlock(blockRow, blockCol, blockSize);
                String[][] blockToInsert = applyRule(block);
                insertBlock(newGrid, blockToInsert, blockRow, blockCol);
            }
        }

        this.grid = newGrid;
    }

    private void insertBlock(String[][] newGrid,
                             String[][] blockToInsert,
                             int blockRow,
                             int blockCol) {
        for (int i = 0; i < blockToInsert.length; i++) {
//            for (int j = 0; j < blockToInsert.length; j++) {
//                newGrid[blockRow * blockToInsert.length + i][blockCol * blockToInsert.length + j] = blockToInsert[i][j];
//            }
            System.arraycopy(blockToInsert[i],
                    0,
                    newGrid[blockRow * blockToInsert.length + i],
                    blockCol * blockToInsert.length,
                    blockToInsert.length);
        }
    }

    private String[][] applyRule(String[][] block) {
        for (Rule rule : rules) {
            if (rule.matches(block)) {
                return rule.getOutputGrid();
            }
        }
        throw new IllegalStateException("No matching rule found for block");
    }

    private String[][] extractBlock(int blockRow, int blockCol, int blockSize) {
        String[][] block = new String[blockSize][blockSize];
        for (int i = 0; i < blockSize; i++) {
//            for (int j = 0; j < blockSize; j++) {
//                block[i][j] = grid[blockRow * blockSize + i][blockCol * blockSize + j];
//            }
            System.arraycopy(grid[blockRow * blockSize + i], blockCol * blockSize, block[i], 0, blockSize);
        }
        return block;
    }
}
