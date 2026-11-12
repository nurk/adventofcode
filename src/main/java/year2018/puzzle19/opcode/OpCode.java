package year2018.puzzle19.opcode;

import lombok.Getter;
import lombok.Setter;
import year2018.puzzle19.Registers;

public abstract class OpCode {

    @Setter
    @Getter
    private int opCode;

    public abstract String getName();

    public abstract void execute(Registers registers, int a, int b, int c);
}
