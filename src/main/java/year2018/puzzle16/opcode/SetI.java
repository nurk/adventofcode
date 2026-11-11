package year2018.puzzle16.opcode;

import year2018.puzzle16.Registers;

public class SetI extends OpCode {
    @Override
    public String getName() {
        return "seti";
    }

    @Override
    public void execute(Registers registers, int a, int b, int c) {
        registers.setRegister(c, a);
    }
}
