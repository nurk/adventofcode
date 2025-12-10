package year2025.puzzle9;

import lombok.Getter;

public class RedTile {
    @Getter
    private final long x, y;

    public RedTile(String line) {
        String[] split = line.split(",");
        this.x = Long.parseLong(split[0]);
        this.y = Long.parseLong(split[1]);
    }

    public long areaBetween(RedTile other) {
        return (Math.abs(this.x - other.x) + 1) * (Math.abs(this.y - other.y) + 1);
    }

    @Override
    public String toString() {
        return "RedTile{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RedTile redTile = (RedTile) o;
        return x == redTile.x && y == redTile.y;
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(x);
        result = 31 * result + Long.hashCode(y);
        return result;
    }
}
