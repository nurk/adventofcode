package year2019.puzzle6;

import java.util.ArrayList;
import java.util.List;

public class Planet {
    private final String id;
    private Planet orbiting;
    private final List<Planet> orbiters = new ArrayList<>();

    public Planet(String id) {
        this.id = id;
    }

    public void setOrbiting(Planet orbiting) {
        this.orbiting = orbiting;
        orbiting.addOrbiter(this);
    }

    public void addOrbiter(Planet orbiter) {
        this.orbiters.add(orbiter);
    }

    public long countDirectAndIndirectOrbits() {
        if (orbiting == null) {
            return 0;
        }
        return 1 + orbiting.countDirectAndIndirectOrbits();
    }

    public List<Planet> getNeighborPlanets() {
        List<Planet> neighbors = new ArrayList<>();
        if (orbiting != null) {
            neighbors.add(orbiting);
        }
        neighbors.addAll(orbiters);
        return neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Planet planet = (Planet) o;
        return id.equals(planet.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Planet{" +
                "id='" + id + '\'' +
                ", orbiting=" + orbiting +
                '}';
    }
}
