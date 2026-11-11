package year2018.puzzle16.opcode;

import year2018.puzzle16.Registers;

public class AddI extends OpCode {
    @Override
    public String getName() {
        return "addi";
    }

    @Override
    public void execute(Registers registers, int a, int b, int c) {
        registers.setRegister(c, registers.getRegister(a) + b);
    }
}
