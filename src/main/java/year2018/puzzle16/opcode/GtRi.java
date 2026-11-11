package year2018.puzzle16.opcode;

import year2018.puzzle16.Registers;

public class GtRi extends OpCode {
    @Override
    public String getName() {
        return "gtri";
    }

    @Override
    public void execute(Registers registers, int a, int b, int c) {
        if (registers.getRegister(a) > b) {
            registers.setRegister(c, 1);
        } else {
            registers.setRegister(c, 0);
        }
    }
}
