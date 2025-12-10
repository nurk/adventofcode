package year2025.puzzle9;

import lombok.Getter;

public class Edge {
    @Getter
    private final long minX, minY, maxX, maxY;

    public Edge(RedTile from, RedTile to) {
        this.minX = Math.min(from.getX(), to.getX());
        this.minY = Math.min(from.getY(), to.getY());
        this.maxX = Math.max(from.getX(), to.getX());
        this.maxY = Math.max(from.getY(), to.getY());
    }
}
