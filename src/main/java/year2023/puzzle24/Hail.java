package year2023.puzzle24;

import org.apache.commons.math3.geometry.euclidean.twod.Line;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Hail {

    private final double x, y, z, vx, vy, vz;
    private final Line line;

    public Hail(String input) {
        String[] parts = input.split(" @ ");
        String[] posParts = parts[0].split(", ");
        String[] velParts = parts[1].split(", ");
        x = Double.parseDouble(posParts[0]);
        y = Double.parseDouble(posParts[1]);
        z = Double.parseDouble(posParts[2]);
        vx = Double.parseDouble(velParts[0]);
        vy = Double.parseDouble(velParts[1]);
        vz = Double.parseDouble(velParts[2]);

        line = new Line(new Vector2D(x, y), new Vector2D(x + vx, y + vy), 1.0e-10);

    }

    public boolean crossesInBoundsAndFuture(Hail other, double min, double max) {
        Vector2D intersection = this.line.intersection(other.line);

        if (intersection == null) {
            return false;
        }

        if (intersection.isInfinite()) {
            return false;
        }

        if (intersection.isNaN()) {
            return false;
        }

        if ((intersection.getX() - x) / vx < 0) {
            return false;
        }

        if ((intersection.getX() - other.x) / other.vx < 0) {
            return false;
        }

        return intersection.getX() >= min && intersection.getX() <= max && intersection.getY() >= min && intersection.getY() <= max;
    }

    @Override
    public String toString() {
        return "Hail{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", vx=" + vx +
                ", vy=" + vy +
                ", vz=" + vz +
                ", line=" + line +
                '}';
    }
}
