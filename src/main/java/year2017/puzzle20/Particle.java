package year2017.puzzle20;

import lombok.Getter;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Particle {
    @Getter
    private final int id;
    private final Vector3D position;
    private final Vector3D velocity;
    private final Vector3D acceleration;

    private Vector3D currentPosition;
    private Vector3D currentVelocity;

    public Particle(int id, String line) {
        this.id = id;
        String[] parts = line.split(", ");
        this.position = parseVector(parts[0]);
        this.velocity = parseVector(parts[1]);
        this.acceleration = parseVector(parts[2]);

        this.currentPosition = position;
        this.currentVelocity = velocity;
    }

    private Vector3D parseVector(String part) {
        String vectorString = part.substring(3, part.length() - 1);
        String[] coords = vectorString.split(",");
        return new Vector3D(Double.parseDouble(coords[0]),
                Double.parseDouble(coords[1]),
                Double.parseDouble(coords[2]));
    }

    public void tick() {
        currentVelocity = currentVelocity.add(acceleration);
        currentPosition = currentPosition.add(currentVelocity);
    }

    public long getDistance(Vector3D point) {
        return (int) currentPosition.distance(point);
    }

    public boolean isColliding(Particle other) {
        return this.currentPosition.equals(other.currentPosition);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Particle particle = (Particle) o;
        return id == particle.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Particle{" +
                "id=" + id +
                ", position=" + position +
                ", velocity=" + velocity +
                ", acceleration=" + acceleration +
                '}';
    }
}
