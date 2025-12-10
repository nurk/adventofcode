package year2025.puzzle9;

import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 4748826374
 * Part B: 1554370486
 */
public class Puzzle9 {
    public static void main(String[] args) {
        List<RedTile> redTiles = Utils.getInput("2025/input9.txt", RedTile::new);
        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < redTiles.size() - 1; i++) {
            edges.add(new Edge(redTiles.get(i), redTiles.get(i + 1)));
        }
        edges.add(new Edge(redTiles.getLast(), redTiles.getFirst()));

        long maxAreaPartA = 0;
        long maxAreaPartB = 0;
        for (RedTile rt : redTiles) {
            for (RedTile rt2 : redTiles) {
                if (!rt.equals(rt2)) {
                    long area = rt.areaBetween(rt2);
                    if (area > maxAreaPartA) {
                        maxAreaPartA = area;
                    }
                    if (area > maxAreaPartB && isWithinEdges(rt, rt2, edges)) {
                        maxAreaPartB = area;
                    }
                }
            }
        }

        System.out.println("Part A: " + maxAreaPartA);
        System.out.println("Part B: " + maxAreaPartB);
    }

    private static boolean isWithinEdges(RedTile rt, RedTile rt2, List<Edge> edges) {
        long minX = Math.min(rt.getX(), rt2.getX());
        long minY = Math.min(rt.getY(), rt2.getY());
        long maxX = Math.max(rt.getX(), rt2.getX());
        long maxY = Math.max(rt.getY(), rt2.getY());

        for (Edge edge : edges) {
            if (minX < edge.getMaxX() &&
                    maxX > edge.getMinX() &&
                    minY < edge.getMaxY() &&
                    maxY > edge.getMinY()) {
                return false;
            }
        }

        return true;
    }
}
