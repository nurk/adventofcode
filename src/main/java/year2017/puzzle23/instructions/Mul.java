package year2017.puzzle23.instructions;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;

public class Mul implements Instruction {
    public static int timesExecuted = 0;
    private String x, y;

    public Mul(String x, String y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int performReturningInstuctionOffset(HashMap<String, Integer> registers) {
        timesExecuted++;
        int valueToMul;
        if (NumberUtils.isParsable(y)) {
            valueToMul = Integer.valueOf(y);
        } else {
            valueToMul = registers.get(y);
        }


        registers.put(x, registers.get(x) * valueToMul);
        return 1;
    }

    @Override
    public String toString() {
        return "Mul{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }
}
