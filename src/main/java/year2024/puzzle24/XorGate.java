package year2024.puzzle24;

import java.util.Map;

public class XorGate extends Gate {

    protected XorGate(String input1, String input2, String output) {
        super(input1, input2, output);
    }

    @Override
    public void satisfy(Map<String, Integer> pins) {
        if (!pins.get(input1).equals(pins.get(input2))) {
            pins.put(output, 1);
        } else {
            pins.put(output, 0);
        }
    }
}
