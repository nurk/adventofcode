package year2018.puzzle16.opcode;

import year2018.puzzle16.Registers;

public class BorI extends OpCode {
    @Override
    public String getName() {
        return "bori";
    }

    @Override
    public void execute(Registers registers, int a, int b, int c) {
        registers.setRegister(c, registers.getRegister(a) | b);
    }
}
