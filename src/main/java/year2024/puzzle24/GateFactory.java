package year2024.puzzle24;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class GateFactory {

    public static Gate createGate(String line, Map<String, Integer> pins) {
        //x00 AND y00 -> z00
        String[] split = StringUtils.split(line, " ");
        pins.putIfAbsent(split[0], null);
        pins.putIfAbsent(split[2], null);
        pins.putIfAbsent(split[4], null);

        switch (split[1]) {
            case "AND" -> {
                return new AndGate(split[0], split[2], split[4]);
            }
            case "OR" -> {
                return new OrGate(split[0], split[2], split[4]);
            }
            case "XOR" -> {
                return new XorGate(split[0], split[2], split[4]);
            }
            default -> throw new IllegalArgumentException();
        }
    }
}
