package year2024.puzzle24;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Part A binary: 1101010110110010011100010010000000011011100110
 * Part A: 58740594706150
 */
public class Puzzle24 {

    public static void main(String[] args) {
        List<String> lines = new ArrayList<>(Utils.getInput("2024/input24.txt"));

        Map<String, Integer> pins = new HashMap<>();
        List<Gate> gates = new ArrayList<>();

        lines.forEach(line -> {
            if (Strings.CS.contains(line, "->")) {
                gates.add(GateFactory.createGate(line, pins));
            } else if (StringUtils.isNotBlank(line)) {
                String[] split = StringUtils.split(line, ": ");
                pins.put(split[0], Integer.valueOf(split[1]));
            }
        });

        while (gates.stream().anyMatch(gate -> !gate.isSatisfied(pins))) {
            gates.stream()
                    .filter(gate -> gate.canBeSatisfied(pins))
                    .forEach(gate -> gate.satisfy(pins));
        }

        String binary = pins.entrySet().stream()
                .filter(entry -> StringUtils.startsWith(entry.getKey(), "z"))
                .sorted((o1, o2) -> o2.getKey().compareTo(o1.getKey()))
                .map(entry -> String.valueOf(entry.getValue()))
                .collect(Collectors.joining());
        System.out.println("Part A binary: " + binary);
        System.out.println("Part A: " + Long.valueOf(binary, 2));
    }
}
