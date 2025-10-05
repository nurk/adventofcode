package year2024.puzzle24;

import java.util.Map;

public class AndGate extends Gate {
    protected AndGate(String input1, String input2, String output) {
        super(input1, input2, output);
    }

    @Override
    public void satisfy(Map<String, Integer> pins) {
        if (pins.get(input1) == 1 && pins.get(input2) == 1) {
            pins.put(output, 1);
        } else {
            pins.put(output, 0);
        }
    }
}
