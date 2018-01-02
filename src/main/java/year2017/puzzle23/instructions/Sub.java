package year2017.puzzle23.instructions;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;

public class Sub implements Instruction {

    private String x, y;

    public Sub(String x, String y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int performReturningInstuctionOffset(HashMap<String, Integer> registers) {
        int valueToSub;
        if (NumberUtils.isParsable(y)) {
            valueToSub = Integer.valueOf(y);
        } else {
            valueToSub = registers.get(y);
        }

        registers.put(x, registers.get(x) - valueToSub);
        return 1;
    }

    @Override
    public String toString() {
        return "Sub{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }
}
