package year2024.puzzle17;

import org.apache.commons.lang3.NotImplementedException;

import java.util.Arrays;

public enum Operand {

    ZERO("0") {
        @Override
        public Long getCombo(Register register) {
            return 0L;
        }

        @Override
        public Long getLiteral() {
            return 0L;
        }
    },
    ONE("1") {
        @Override
        public Long getCombo(Register register) {
            return 1L;
        }

        @Override
        public Long getLiteral() {
            return 1L;
        }
    },
    TWO("2") {
        @Override
        public Long getCombo(Register register) {
            return 2L;
        }

        @Override
        public Long getLiteral() {
            return 2L;
        }
    },
    THREE("3") {
        @Override
        public Long getCombo(Register register) {
            return 3L;
        }

        @Override
        public Long getLiteral() {
            return 3L;
        }
    },
    FOUR("4") {
        @Override
        public Long getCombo(Register register) {
            return register.getA();
        }

        @Override
        public Long getLiteral() {
            return 4L;
        }
    },
    FIVE("5") {
        @Override
        public Long getCombo(Register register) {
            return register.getB();
        }

        @Override
        public Long getLiteral() {
            return 5L;
        }
    },
    SIX("6") {
        @Override
        public Long getCombo(Register register) {
            return register.getC();
        }

        @Override
        public Long getLiteral() {
            return 7L;
        }
    },
    SEVEN("7") {
        @Override
        public Long getCombo(Register register) {
            throw new NotImplementedException("Not implemented yet");
        }

        @Override
        public Long getLiteral() {
            throw new NotImplementedException("Not implemented yet");
        }
    };

    private final String value;

    Operand(String value) {
        this.value = value;
    }

    public abstract Long getCombo(Register register);
    public abstract Long getLiteral();

    public static Operand fromValue(String value) {
        return Arrays.stream(Operand.values())
                .filter(operand -> operand.value.equals(value))
                .findFirst()
                .orElseThrow();
    }
}
