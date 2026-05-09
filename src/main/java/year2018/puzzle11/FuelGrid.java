package year2018.puzzle11;

import org.javatuples.Pair;

public class FuelGrid {
    private final FuelCell[][] grid = new FuelCell[300][300];

    public FuelGrid(int serialNumber) {
        for (int x = 1; x <= 300; x++) {
            for (int y = 1; y <= 300; y++) {
                grid[x - 1][y - 1] = new FuelCell(x, y, serialNumber);
            }
        }
    }

    private FuelCell getCell(int x, int y) {
        return grid[x][y];
    }

    public FuelCell getTopLeftCellOfBiggestCluster() {
        int maxLevel = 0;
        FuelCell maxCell = null;
        for (int x = 0; x <= 297; x++) {
            for (int y = 0; y <= 297; y++) {
                int clusterLevel = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        clusterLevel += getCell(x + i, y + j).getLevel();
                    }
                }
                if (clusterLevel > maxLevel) {
                    maxLevel = clusterLevel;
                    maxCell = getCell(x, y);
                }
            }
        }
        System.out.println("maxLevel = " + maxLevel);
        return maxCell;
    }

    public Pair<FuelCell, Integer> getMaxFuel() {
        long maxLevel = 0;
        FuelCell maxCell = null;
        int maxStep = 0;

        for (int step = 1; step < 300; step++) {
            for (int x = 0; x <= 300 - step; x++) {
                for (int y = 0; y <= 300 - step; y++) {
                    int clusterLevel = 0;
                    for (int i = 0; i < step; i++) {
                        for (int j = 0; j < step; j++) {
                            clusterLevel += getCell(x + i, y + j).getLevel();
                        }
                    }
                    if (clusterLevel > maxLevel) {
                        maxStep = step;
                        maxLevel = clusterLevel;
                        maxCell = getCell(x, y);
                    }
                }
            }
        }

        System.out.println("maxLevel = " + maxLevel);
        return Pair.with(maxCell, maxStep);
    }
}
