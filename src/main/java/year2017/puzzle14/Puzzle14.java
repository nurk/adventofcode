package year2017.puzzle14;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Part A: 8074
 * Part B: 1212
 */
public class Puzzle14 {
    public static void main(String[] args) {
        //String input = "flqrgnkx";
        String input = "jzgqcdpd";

        Pair<String, String>[][] grid = new Pair[128][128];

        long usedSquares = 0;
        for (int i = 0; i < 128; i++) {
            String rowInput = input + "-" + i;
            String hash = KnotHash.computeHashHex(rowInput);
            String binaryString = toBinaryString(hash);
            usedSquares += binaryString.chars().filter(c -> c == '1').count();
            for (int j = 0; j < 128; j++) {
                grid[i][j] = Pair.of(binaryString.substring(j, j + 1), "ungrouped");
            }
        }

        System.out.println("Part A: " + usedSquares);

        int currentGroup = 0;
        for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 128; j++) {
                if (grid[i][j].getLeft().equals("1") && grid[i][j].getRight().equals("ungrouped")) {
                    markGroup(grid, i, j, currentGroup);
                    currentGroup++;
                }

            }
        }

        System.out.println("Part B: " + (currentGroup));
    }

    private static void markGroup(Pair<String, String>[][] grid, int i, int j, int currentGroup) {
        if (i < 0 || i >= 128 || j < 0 || j >= 128) {
            return;
        }
        if (grid[i][j].getLeft().equals("1") && grid[i][j].getRight().equals("ungrouped")) {
            grid[i][j] = Pair.of("1", String.valueOf(currentGroup));
            markGroup(grid, i - 1, j, currentGroup);
            markGroup(grid, i + 1, j, currentGroup);
            markGroup(grid, i, j - 1, currentGroup);
            markGroup(grid, i, j + 1, currentGroup);
        }
    }

    private static String toBinaryString(String hexString) {
        StringBuilder binaryString = new StringBuilder();
        for (char c : hexString.toCharArray()) {
            int decimal = Integer.parseInt(String.valueOf(c), 16);
            String binarySegment = String.format("%4s", Integer.toBinaryString(decimal)).replace(' ', '0');
            binaryString.append(binarySegment);
        }
        return binaryString.toString();
    }
}
