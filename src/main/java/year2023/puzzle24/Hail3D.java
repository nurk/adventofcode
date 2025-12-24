package year2023.puzzle24;

import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Hail3D {

    private final double x, y, z, vx, vy, vz;
    private final Line line;

    public Hail3D(String input) {
        String[] parts = input.split(" @ ");
        String[] posParts = parts[0].split(", ");
        String[] velParts = parts[1].split(", ");
        x = Double.parseDouble(posParts[0]);
        y = Double.parseDouble(posParts[1]);
        z = Double.parseDouble(posParts[2]);
        vx = Double.parseDouble(velParts[0]);
        vy = Double.parseDouble(velParts[1]);
        vz = Double.parseDouble(velParts[2]);

        line = new Line(new Vector3D(x, y, z), new Vector3D(x + vx, y + vy, z + vz), 1.0e-10);

    }

    public boolean crossesInFuture(Hail3D other) {
        Vector3D intersection = this.line.intersection(other.line);
        System.out.println(intersection);

        if (intersection == null) {
            System.out.println("No intersection");
            return false;
        }

        if (intersection.isInfinite()) {
            System.out.println("Infinite intersection");
            return false;
        }

        if (intersection.isNaN()) {
            System.out.println("NaN intersection");
            return false;
        }

        if ((intersection.getX() - x) / vx < 0) {
            System.out.println("Intersection in past for this");
            return false;
        }

        if ((intersection.getX() - other.x) / other.vx < 0) {
            System.out.println("Intersection in past for other");
            return false;
        }

        return true;
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
