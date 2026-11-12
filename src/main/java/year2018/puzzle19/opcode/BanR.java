package year2018.puzzle19.opcode;

import year2018.puzzle19.Registers;

public class BanR extends OpCode {
    @Override
    public String getName() {
        return "banr";
    }

    @Override
    public void execute(Registers registers, int a, int b, int c) {
        registers.setRegister(c, registers.getRegister(a) & registers.getRegister(b));
    }
}
