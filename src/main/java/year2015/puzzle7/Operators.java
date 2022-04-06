package year2015.puzzle7;

import java.math.BigInteger;
import java.util.List;

public enum Operators {
    AND(" AND ") {
        @Override
        public BigInteger doOperation(List<BigInteger> inputs) {
            return inputs.get(0).and(inputs.get(1));
        }
    },
    OR(" OR ") {
        @Override
        public BigInteger doOperation(List<BigInteger> inputs) {
            return inputs.get(0).or(inputs.get(1));
        }
    },
    NOT("NOT ") {
        @Override
        public BigInteger doOperation(List<BigInteger> inputs) {
            BigInteger not = inputs.get(0).not();
            if (not.compareTo(BigInteger.ZERO) < 0) {
                not = not.add(TWO_COMPLEMENT_REF);
            }
            return not;
        }
    },
    LEFT_SHIFT(" LSHIFT ") {
        @Override
        public BigInteger doOperation(List<BigInteger> inputs) {
            return inputs.get(0).shiftLeft(inputs.get(1).intValue());
        }
    },
    RIGHT_SHIFT(" RSHIFT ") {
        @Override
        public BigInteger doOperation(List<BigInteger> inputs) {
            return inputs.get(0).shiftRight(inputs.get(1).intValue());
        }
    };

    private static final BigInteger TWO_COMPLEMENT_REF = BigInteger.ONE.shiftLeft(16);

    private final String name;

    Operators(String name) {
        this.name = name;
    }

    public abstract BigInteger doOperation(List<BigInteger> inputs);

    public String getName() {
        return name;
    }
}
