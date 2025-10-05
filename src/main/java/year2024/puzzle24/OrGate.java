package year2024.puzzle24;

import java.util.Map;

public class OrGate extends Gate {

    protected OrGate(String input1, String input2, String output) {
        super(input1, input2, output);
    }

    @Override
    public void satisfy(Map<String, Integer> pins) {
        if (pins.get(input1) == 0 && pins.get(input2) == 0) {
            pins.put(output, 0);
        } else {
            pins.put(output, 1);
        }
    }
}
