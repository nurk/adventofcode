package year2025.puzzle8;

import lombok.Getter;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.commons.math3.util.MathArrays;

import java.util.HashSet;
import java.util.Set;

public class JunctionBox implements Comparable<JunctionBox> {
    @Getter
    private final double x, y, z;

    public JunctionBox(String line) {
        String[] split = line.split(",");
        this.x = Double.parseDouble(split[0]);
        this.y = Double.parseDouble(split[1]);
        this.z = Double.parseDouble(split[2]);
    }

    public double computeDistance(JunctionBox other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) +
                Math.pow(this.y - other.y, 2) +
                Math.pow(this.z - other.z, 2));
    }

    @Override
    public String toString() {
        return "JunctionBox{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JunctionBox that = (JunctionBox) o;
        return Double.compare(x, that.x) == 0 && Double.compare(y,
                that.y) == 0 && Double.compare(z, that.z) == 0;
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(x);
        result = 31 * result + Double.hashCode(y);
        result = 31 * result + Double.hashCode(z);
        return result;
    }

    @Override
    public int compareTo(JunctionBox o) {
        int compareX = Double.compare(this.x, o.x);
        if (compareX != 0) {
            return compareX;
        }
        int compareY = Double.compare(this.y, o.y);
        if (compareY != 0) {
            return compareY;
        }
        return Double.compare(this.z, o.z);
    }
}
