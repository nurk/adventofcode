package year2023.puzzle18;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class DigSite2 {

    private long row = 0;
    private long col = 0;

    private long perimeter = 0;

    private final List<Pair<Long, Long>> vertices = new ArrayList<>();

    public DigSite2() {
    }

    public void moveA(String move) {
        String[] parts = move.split(" ");
        String direction = parts[0];
        int distance = Integer.parseInt(parts[1]);

        perimeter += distance;

        switch (direction) {
            case "U" -> row -= distance;
            case "D" -> row += distance;
            case "L" -> col -= distance;
            case "R" -> col += distance;
        }


        vertices.add(Pair.of(row, col));
    }

    public void moveB(String move) {
        String[] parts = move.split(" ");
        String color = parts[2].substring(2, parts[2].length() - 1);

        long distance = Long.parseLong(color.substring(0, color.length() - 1), 16);
        String direction = color.substring(color.length() - 1);

        perimeter += distance;

        switch (direction) {
            case "3" -> row -= distance;
            case "1" -> row += distance;
            case "2" -> col -= distance;
            case "0" -> col += distance;
        }


        vertices.add(Pair.of(row, col));
    }

    /**
     * Shoelace formula to calculate the area of a polygon
     */
    public long getArea() {
        long area = 0L;
        for (int i = 0; i < vertices.size(); i++) {
            Pair<Long, Long> p1 = vertices.get(i);
            Pair<Long, Long> p2 = vertices.get((i + 1) % vertices.size()); // Next point (wraps around)

            // Shoelace formula component: (x1*y2 - x2*y1)
            var area1 = (p1.getLeft() * p2.getRight()) - (p2.getLeft() * p1.getRight());
            area += area1;
        }
        return Math.abs(area / 2); // Absolute value and divide by 2
    }

    /**
     * Returns the number of grid cells covered by the lagoon including the boundary trench.
     * <p>
     * Uses Pick's theorem rearranged:
     * filled = I + B = A + B/2 + 1
     * where:
     * A = polygon area from shoelace
     * B = boundary length in unit steps (sum of move distances)
     * <p>
     * Needed AI for this part
     */
    public long getAreaIncludingBorder() {
        long area = getArea();
        return area + (perimeter / 2) + 1;
    }

}
