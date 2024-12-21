package year2024.puzzle17;

import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;
import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Part A: 6,4,6,0,4,5,7,2,7
 */
public class Puzzle17 {
    public static void main(String[] args) {
        Register register = new Register();

        List<String> input = new ArrayList<>(Utils.getInput("2024/input17.txt", (s) -> s));

        String s = input.removeFirst();
        register.setA(Long.parseLong(StringUtils.substringAfter(s, ": ")));

        s = input.removeFirst();
        register.setB(Long.parseLong(StringUtils.substringAfter(s, ": ")));

        s = input.removeFirst();
        register.setC(Long.parseLong(StringUtils.substringAfter(s, ": ")));

        input.removeFirst();

        String originalProgram = StringUtils.substringAfter(input.removeFirst(), ": ");
        List<String> list = Arrays.stream(originalProgram.split(",")).toList();

        List<Pair<OpCode, Operand>> codes = new ArrayList<>();

        long originalA = register.getA();
        long originalB = register.getB();
        long originalC = register.getC();

        for (int i = 0; i < list.size(); i += 2) {
            codes.add(new Pair<>(OpCode.fromValue(list.get(i)), Operand.fromValue(list.get(i + 1))));
        }

        InstructionPointer instructionPointer = new InstructionPointer(list.size() - 1);

        while (!instructionPointer.isOutOfBounds()) {
            Pair<OpCode, Operand> code = codes.get(instructionPointer.getPosition() / 2);
            code.getValue0().execute(instructionPointer, register, code.getValue1());
        }

        System.out.println("Part A: " + register.getValue());

        String newProgram = "";
        long newA = -1L;

        while (!newProgram.equals(originalProgram)) {
            newA += 1;
            if (newA == originalA) {
                newA += 1;
            }

            if (newA % 1000000 == 0) {
                System.out.println(newA);
            }

            Register bRegister = new Register();
            bRegister.setA(newA);
            bRegister.setB(originalB);
            bRegister.setC(originalC);
            InstructionPointer bInstructionPointer = new InstructionPointer(list.size() - 1);

            int loops = 0;
            while (!bInstructionPointer.isOutOfBounds()) {
                try {
                    if (loops > 1000000) {
                        System.out.println("Loops exceeded: " + newA);
                        break;
                    }
                    Pair<OpCode, Operand> code = codes.get(bInstructionPointer.getPosition() / 2);

                    code.getValue0().execute(bInstructionPointer, bRegister, code.getValue1());

                    if (!StringUtils.startsWith(originalProgram, bRegister.getValue())) {
                        break;
                    }
                } catch (Exception e) {
                    break;
                }
                loops++;
            }
            newProgram = bRegister.getValue();
        }

        System.out.println("Part B: " + newA);
    }
}
