package year2017.puzzle23.instructions;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;

public class Jnz implements Instruction {
    private String x, y;

    public Jnz(String x, String y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int performReturningInstuctionOffset(HashMap<String, Integer> registers) {
        int xInt;
        if (NumberUtils.isParsable(x)) {
            xInt = Integer.valueOf(x);
        } else {
            xInt = registers.get(x);
        }

        if (xInt == 0) {
            return 1;
        }

        if (NumberUtils.isParsable(y)) {
            return Integer.valueOf(y);
        } else {
            return registers.get(y);
        }
    }

    @Override
    public String toString() {
        return "Jnz{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }
}
