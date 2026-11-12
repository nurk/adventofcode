package year2018.puzzle19;

import util.Utils;
import year2018.puzzle19.opcode.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Part A: 1806
 */
public class Puzzle19 {
    static List<OpCode> opCodes = new ArrayList<>();

    static {
        opCodes.add(new AddR());
        opCodes.add(new AddI());
        opCodes.add(new MulR());
        opCodes.add(new MulI());
        opCodes.add(new SetR());
        opCodes.add(new SetI());
        opCodes.add(new BanR());
        opCodes.add(new BanI());
        opCodes.add(new BorR());
        opCodes.add(new BorI());
        opCodes.add(new EqIr());
        opCodes.add(new EqRi());
        opCodes.add(new EqRr());
        opCodes.add(new GtIr());
        opCodes.add(new GtRi());
        opCodes.add(new GtRr());
    }

    static Map<String, OpCode> opCodeNameMap = new HashMap<>();

    static {
        for (OpCode opcode : opCodes) {
            opCodeNameMap.put(opcode.getName(), opcode);
        }
    }

    static void main() {
        List<String> input = new ArrayList<>(Utils.getInput("2018/input19.txt"));

        String ip = input.removeFirst();
        int ipRegister = Integer.parseInt(ip.split(" ")[1]);

        Registers registers = new Registers(0, 0, 0, 0, 0, 0, ipRegister);

        while (registers.getInstructionPointer() < input.size()) {
            String instruction = input.get(registers.getInstructionPointer());
            String[] parts = instruction.split(" ");
            String opCodeName = parts[0];
            int a = Integer.parseInt(parts[1]);
            int b = Integer.parseInt(parts[2]);
            int c = Integer.parseInt(parts[3]);

            OpCode opCode = opCodeNameMap.get(opCodeName);
            opCode.execute(registers, a, b, c);

            registers.incrementInstructionPointer();
        }

        System.out.println("Part A: " + registers.getRegister(0));
    }
}
