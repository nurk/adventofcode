package year2017.puzzle24;

import util.Utils;

import java.util.Comparator;
import java.util.List;

/**
 * Part A: 1906
 * Part B: 1824
 */
public class Puzzle24 {
    public static void main(String[] args) {
        List<Component> components = Utils.getInput("2017/input24.txt", Component::new);

        List<Bridge> bridges = components.stream()
                .filter(component -> component.hasPort(0))
                .map(start -> new Bridge(start, components))
                .toList();

        List<Bridge> allBridges = bridges.stream()
                .flatMap(b -> b.buildBridges().stream())
                .toList();

        Bridge maxBridge = allBridges.stream()
                .max(Comparator.comparing(Bridge::getStrength))
                .orElseThrow();

        System.out.println("Part A: " + maxBridge.getStrength());

        Integer maxLength = allBridges.stream()
                .map(Bridge::getLength)
                .max(Integer::compareTo)
                .orElseThrow();

        Bridge strongestLongestBridge = allBridges.stream()
                .filter(b -> b.hasLength(maxLength))
                .toList().stream()
                .max(Comparator.comparing(Bridge::getStrength))
                .orElseThrow();

        System.out.println("Part B: " + strongestLongestBridge.getStrength());
    }
}
