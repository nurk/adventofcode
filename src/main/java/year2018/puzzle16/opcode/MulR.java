package year2018.puzzle16.opcode;

import year2018.puzzle16.Registers;

public class MulR extends OpCode {
    @Override
    public String getName() {
        return "mulr";
    }

    @Override
    public void execute(Registers registers, int a, int b, int c) {
        registers.setRegister(c, registers.getRegister(a) * registers.getRegister(b));
    }
}
