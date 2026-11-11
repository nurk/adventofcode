package year2018.puzzle16.opcode;

import year2018.puzzle16.Registers;

public class GtIr extends OpCode {
    @Override
    public String getName() {
        return "gtir";
    }

    @Override
    public void execute(Registers registers, int a, int b, int c) {
        if (a > registers.getRegister(b)) {
            registers.setRegister(c, 1);
        } else {
            registers.setRegister(c, 0);
        }
    }
}
