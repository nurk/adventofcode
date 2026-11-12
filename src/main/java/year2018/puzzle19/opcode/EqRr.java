package year2018.puzzle19.opcode;

import year2018.puzzle19.Registers;

public class EqRr extends OpCode {
    @Override
    public String getName() {
        return "eqrr";
    }

    @Override
    public void execute(Registers registers, int a, int b, int c) {
        registers.setRegister(c, registers.getRegister(a) == registers.getRegister(b) ? 1 : 0);
    }
}
