package year2017.puzzle23.instructions;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;

public class Set implements Instruction {
    private String x, y;

    public Set(String x, String y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int performReturningInstuctionOffset(HashMap<String, Integer> registers) {
        int valueToSet;
        if (NumberUtils.isParsable(y)) {
            valueToSet = Integer.valueOf(y);
        } else {
            valueToSet = registers.get(y);
        }

        registers.put(x, valueToSet);
        return 1;
    }

    @Override
    public String toString() {
        return "Set{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }
}
