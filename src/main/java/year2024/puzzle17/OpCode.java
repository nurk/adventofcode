package year2024.puzzle17;

import java.util.Arrays;

public enum OpCode {
    adv("0") {
        @Override
        public void execute(InstructionPointer instructionPointer, Register register, Operand operand) {
            long numerator = register.getA();
            long denominator = (long) Math.pow(2, operand.getCombo(register));

            register.setA(numerator / denominator);
            instructionPointer.advanceTwo();
        }
    },
    bxl("1") {
        @Override
        public void execute(InstructionPointer instructionPointer, Register register, Operand operand) {
            register.setB(register.getB() ^ operand.getLiteral());
            instructionPointer.advanceTwo();
        }
    },
    bst("2") {
        @Override
        public void execute(InstructionPointer instructionPointer, Register register, Operand operand) {
            register.setB(operand.getCombo(register) % 8);
            instructionPointer.advanceTwo();
        }
    },
    jnz("3") {
        @Override
        public void execute(InstructionPointer instructionPointer, Register register, Operand operand) {
            if (register.getA() == 0L) {
                instructionPointer.advanceTwo();
            } else {
                instructionPointer.set(operand.getLiteral());
            }
        }
    },
    bxc("4") {
        @Override
        public void execute(InstructionPointer instructionPointer, Register register, Operand operand) {
            register.setB(register.getB() ^ register.getC());
            instructionPointer.advanceTwo();
        }
    },
    out("5") {
        @Override
        public void execute(InstructionPointer instructionPointer, Register register, Operand operand) {
            register.addOutput(operand.getCombo(register) % 8);
            instructionPointer.advanceTwo();
        }
    },
    bdv("6") {
        @Override
        public void execute(InstructionPointer instructionPointer, Register register, Operand operand) {
            long numerator = register.getA();
            long denominator = (long) Math.pow(2, operand.getCombo(register));

            register.setB(numerator / denominator);
            instructionPointer.advanceTwo();
        }
    },
    cdv("7") {
        @Override
        public void execute(InstructionPointer instructionPointer, Register register, Operand operand) {
            long numerator = register.getA();
            long denominator = (long) Math.pow(2, operand.getCombo(register));

            register.setC(numerator / denominator);
            instructionPointer.advanceTwo();
        }
    };
    private final String value;

    OpCode(String value) {
        this.value = value;
    }

    public abstract void execute(InstructionPointer instructionPointer, Register register, Operand operand);

    public static OpCode fromValue(String value) {
        return Arrays.stream(OpCode.values())
                .filter(opCode -> opCode.value.equals(value))
                .findFirst()
                .orElseThrow();
    }
}
