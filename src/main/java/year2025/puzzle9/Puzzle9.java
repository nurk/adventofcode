package year2025.puzzle9;

import util.Utils;

import java.util.List;

/**
 * Part A: 4748826374
 */
public class Puzzle9 {
    public static void main(String[] args) {
        List<RedTile> redTiles = Utils.getInput("2025/input9.txt", RedTile::new);

        long maxArea = 0;

        for (RedTile rt : redTiles) {
            for (RedTile rt2 : redTiles) {
                if (!rt.equals(rt2)) {
                    long area = rt.areaBetween(rt2);
                    if (area > maxArea) {
                        maxArea = area;
                    }
                }
            }
        }

        System.out.println("Part A: " + maxArea);

    }
}
