package year2018.puzzle16.opcode;

import year2018.puzzle16.Registers;

public class MulI extends OpCode {
    @Override
    public String getName() {
        return "muli";
    }

    @Override
    public void execute(Registers registers, int a, int b, int c) {
        registers.setRegister(c, registers.getRegister(a) * b);
    }
}
