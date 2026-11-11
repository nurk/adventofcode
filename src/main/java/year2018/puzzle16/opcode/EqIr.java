package year2018.puzzle16.opcode;

import year2018.puzzle16.Registers;

public class EqIr extends OpCode {
    @Override
    public String getName() {
        return "eqir";
    }

    @Override
    public void execute(Registers registers, int a, int b, int c) {
        registers.setRegister(c, a == registers.getRegister(b) ? 1 : 0);
    }
}
