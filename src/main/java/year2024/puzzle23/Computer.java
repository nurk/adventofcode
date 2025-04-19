package year2024.puzzle23;

import java.util.ArrayList;
import java.util.List;

public class Computer implements Comparable<Computer> {

    private final String id;
    private final List<Computer> connectedComputers = new ArrayList<>();

    public Computer(String id) {
        this.id = id;
    }

    public void addConnectedComputer(Computer computer) {
        connectedComputers.add(computer);
    }

    @Override
    public int compareTo(Computer o) {
        return this.id.compareTo(o.id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Computer computer = (Computer) o;
        return id.equals(computer.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
