package year2018.puzzle23;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public class Nanobot {
    @Getter
    long x;
    @Getter
    long y;
    @Getter
    long z;
    long radius;

    public Nanobot(String line) {
        //pos=<0,0,0>, r=4
        String[] positions = StringUtils.substringBetween(line, "<", ">").split(",");
        x = Long.parseLong(positions[0]);
        y = Long.parseLong(positions[1]);
        z = Long.parseLong(positions[2]);
        radius = Long.parseLong(StringUtils.substringAfter(line, "r="));
    }

    public long manhattanDistance(Nanobot other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z);
    }

    public long manhattanDistance(long x, long y, long z) {
        return Math.abs(this.x - x) + Math.abs(this.y - y) + Math.abs(this.z - z);
    }

    public boolean inRange(Nanobot other) {
        return manhattanDistance(other) <= other.radius;
    }

    public boolean inRangePartB(Nanobot other) {
        return manhattanDistance(other) <= radius + other.radius;
    }

    public boolean inRange(long x, long y, long z) {
        return manhattanDistance(x, y, z) <= radius;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Nanobot nanobot = (Nanobot) o;
        return x == nanobot.x && y == nanobot.y && z == nanobot.z && radius == nanobot.radius;
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(x);
        result = 31 * result + Long.hashCode(y);
        result = 31 * result + Long.hashCode(z);
        result = 31 * result + Long.hashCode(radius);
        return result;
    }

    @Override
    public String toString() {
        return "Nanobot{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", radius=" + radius +
                '}';
    }
}
