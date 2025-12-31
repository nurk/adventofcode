package year2017.puzzle24;

public class Component {
    private final int portA;
    private final int portB;

    public Component(String line) {
        String[] parts = line.split("/");
        this.portA = Integer.parseInt(parts[0]);
        this.portB = Integer.parseInt(parts[1]);
    }

    public int getPortA() {
        return portA;
    }

    public int getPortB() {
        return portB;
    }

    public int getStrength() {
        return portA + portB;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Component component = (Component) o;
        return portA == component.portA && portB == component.portB;
    }

    @Override
    public int hashCode() {
        int result = portA;
        result = 31 * result + portB;
        return result;
    }

    @Override
    public String toString() {
        return "Component{" +
                "portA=" + portA +
                ", portB=" + portB +
                '}';
    }

    public boolean hasPort(int i) {
        return portA == i || portB == i;
    }
}
