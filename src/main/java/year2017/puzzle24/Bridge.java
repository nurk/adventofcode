package year2017.puzzle24;

import java.util.ArrayList;
import java.util.List;

public class Bridge {
    private final List<Component> components = new ArrayList<>();
    private final List<Component> remainingComponents = new ArrayList<>();

    public Bridge(Component startingComponent, List<Component> allComponents) {
        components.add(startingComponent);
        remainingComponents.addAll(allComponents);
        remainingComponents.remove(startingComponent);
    }

    public Bridge(List<Component> components, List<Component> remainingComponents) {
        this.components.addAll(components);
        this.remainingComponents.addAll(remainingComponents);
    }

    public List<Bridge> buildBridges() {
        List<Bridge> bridges = new ArrayList<>();
        int openPort = getOpenPort();

        for (Component component : remainingComponents) {
            if (component.hasPort(openPort)) {
                List<Component> newComponents = new ArrayList<>(components);
                newComponents.add(component);

                List<Component> newRemainingComponents = new ArrayList<>(remainingComponents);
                newRemainingComponents.remove(component);

                Bridge newBridge = new Bridge(newComponents, newRemainingComponents);
                bridges.addAll(newBridge.buildBridges());
            }
        }

        bridges.add(this);

        return bridges;
    }

    private int getOpenPort() {
        if (components.isEmpty()) {
            return 0;
        }
        Component lastComponent = components.getLast();
        int portA = lastComponent.getPortA();
        int portB = lastComponent.getPortB();

        int connectedPort;

        if (components.size() > 1) {
            connectedPort = components.get(components.size() - 2)
                    .getPortA() == portA || components.get(components.size() - 2).getPortB() == portA ? portA : portB;
        } else {
            connectedPort = 0;
        }
        return (connectedPort == portA) ? portB : portA;
    }

    public Integer getStrength() {
        return components.stream().mapToInt(Component::getStrength).sum();
    }

    public int getLength() {
        return components.size();
    }

    public boolean hasLength(int length) {
        return getLength() == length;
    }
}
