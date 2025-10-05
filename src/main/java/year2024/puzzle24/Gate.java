package year2024.puzzle24;

import java.util.Map;

public abstract class Gate {

    protected final String input1;
    protected final String input2;
    protected final String output;

    protected Gate(String input1, String input2, String output) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
    }

    public boolean canBeSatisfied(Map<String, Integer> pins) {
        return pins.get(input1) != null && pins.get(input2) != null;
    }

    public boolean isSatisfied(Map<String, Integer> pins) {
        return pins.get(output) != null;
    }

    public abstract void satisfy(Map<String, Integer> pins);

    @Override
    public String toString() {
        return "Gate{" +
                "input1='" + input1 + '\'' +
                ", input2='" + input2 + '\'' +
                ", output='" + output + '\'' +
                '}';
    }
}
