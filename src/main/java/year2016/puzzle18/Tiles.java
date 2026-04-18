package year2016.puzzle18;

import java.util.Arrays;

public class Tiles {

    private String[][] tiles;

    public Tiles(String line, int rows) {
        tiles = new String[rows][line.length()];
        for (int i = 0; i < line.length(); i++) {
            tiles[0][i] = String.valueOf(line.charAt(i));
        }
    }

    public void fillRows() {
        for (int row = 1; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                boolean upperLeftTrap = isTrap(row - 1, col - 1);
                boolean upperCenterTrap = isTrap(row - 1, col);
                boolean upperRightTrap = isTrap(row - 1, col + 1);

                //Its left and center tiles are traps, but its right tile is not.
                //Its center and right tiles are traps, but its left tile is not.
                //Only its left tile is a trap.
                //Only its right tile is a trap.

                if ((upperLeftTrap && upperCenterTrap && !upperRightTrap) ||
                        (upperCenterTrap && upperRightTrap && !upperLeftTrap) ||
                        (upperLeftTrap && !upperCenterTrap && !upperRightTrap) ||
                        (upperRightTrap && !upperCenterTrap && !upperLeftTrap)) {
                    tiles[row][col] = "^";
                } else {
                    tiles[row][col] = ".";
                }
            }
        }
    }

    public long getSafeTileCount() {
        return Arrays.stream(tiles).flatMap(Arrays::stream)
                .filter(tile -> tile.equals("."))
                .count();
    }

    private boolean isTrap(int row, int col) {
        try {
            if (tiles[row][col].equals("^")) {
                return true;
            }
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return Arrays.stream(tiles)
                .map(row -> String.join("", row))
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
    }
}
