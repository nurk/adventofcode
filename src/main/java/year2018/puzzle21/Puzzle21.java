package year2018.puzzle21;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * Part A: 8681
 */
public class Puzzle21 {
    static void main() {
//        int depth = 510;
//        int endX = 10;
//        int endY = 10;
        int depth = 5616;
        int endX = 10;
        int endY = 785;
        Map<Pair<Integer, Integer>, Region> regions = new HashMap<>();

        Region startRegion = new Region(0, 0, 0);
        Region endRegion = new Region(endX, endY, 0);

        regions.put(startRegion.getCoordinates(), startRegion);
        regions.put(endRegion.getCoordinates(), endRegion);

        for (int y = 0; y <= endY; y++) {
            for (int x = 0; x <= endX; x++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                if (x == endX && y == endY) {
                    continue;
                }

                int geologicalIndex;
                if (y == 0) {
                    geologicalIndex = x * 16807;
                } else if (x == 0) {
                    geologicalIndex = y * 48271;
                } else {
                    Region leftRegion = regions.get(Pair.of(x - 1, y));
                    Region aboveRegion = regions.get(Pair.of(x, y - 1));
                    geologicalIndex = leftRegion.getErosionLevel() * aboveRegion.getErosionLevel();
                }

                int erosionLevel = (geologicalIndex + depth) % 20183;
                Region region = new Region(x, y, erosionLevel);
                regions.put(region.getCoordinates(), region);
            }
        }

        long totalRiskLevel = 0;
        for (int y = 0; y <= endY; y++) {
            for (int x = 0; x <= endX; x++) {
                totalRiskLevel += regions.get(Pair.of(x, y)).getRiskLevel();
            }
        }

        System.out.println("Part A: " + totalRiskLevel);
    }
}
