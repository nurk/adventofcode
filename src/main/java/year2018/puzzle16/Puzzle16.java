package year2018.puzzle16;

import org.apache.commons.lang3.Strings;
import util.Utils;
import year2018.puzzle16.opcode.*;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Part A: 542
 * Part B: 575
 */
public class Puzzle16 {
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

    static Map<Integer, Set<String>> opCodeMap = new HashMap<>();

    static {
        IntStream.range(0, opCodes.size())
                .forEach(i -> opCodeMap.put(i, new HashSet<>(opCodes.stream().map(OpCode::getName).toList())));
    }

    static void main() {
        List<String> input = new ArrayList<>(Utils.getInput("2018/input16-partA.txt"));
        List<List<String>> sets = Utils.splitOnBlankLine(input);

        System.out.println("Part A: " + sets.stream()
                .filter(Puzzle16::isValid)
                .count());

        distillOpCodes();

        List<String> programLines = new ArrayList<>(Utils.getInput("2018/input16-partB.txt"));
        Registers partBRegisters = new Registers(0, 0, 0, 0);
        for (String line : programLines) {
            String[] instructionParts = line.split(" ");
            int opCode = Integer.parseInt(instructionParts[0]);
            int a = Integer.parseInt(instructionParts[1]);
            int b = Integer.parseInt(instructionParts[2]);
            int c = Integer.parseInt(instructionParts[3]);

            opCodes.stream()
                    .filter(opcode -> opcode.getOpCode() == opCode)
                    .findFirst()
                    .ifPresentOrElse(opcode -> opcode.execute(partBRegisters, a, b, c),
                            () -> {
                                throw new RuntimeException("No opcode found for " + opCode);
                            });
        }

        System.out.println("Part B: " + partBRegisters.getRegister(0));
    }

    private static boolean isValid(List<String> set) {
        Set<String> matches = new HashSet<>();

        Registers targetRegister = new Registers(Strings.CI.remove(set.get(2), "After:  "));
        String[] instructionParts = set.get(1).split(" ");
        int opCode = Integer.parseInt(instructionParts[0]);
        int a = Integer.parseInt(instructionParts[1]);
        int b = Integer.parseInt(instructionParts[2]);
        int c = Integer.parseInt(instructionParts[3]);

        for (OpCode opcode : opCodes) {
            Registers registers = new Registers(Strings.CI.remove(set.getFirst(), "Before: "));

            opcode.execute(registers, a, b, c);

            if (registers.equals(targetRegister)) {
                matches.add(opcode.getName());
            }
        }

        opCodeMap.get(opCode).retainAll(matches);
        return matches.size() >= 3;
    }

    static void distillOpCodes() {
        while (!opCodeMap.isEmpty()) {
            Map.Entry<Integer, Set<String>> uniqueMatch = opCodeMap.entrySet().stream()
                    .filter(entry -> entry.getValue().size() == 1)
                    .findFirst()
                    .orElseThrow();

            int opCodeNumber = uniqueMatch.getKey();
            String opCodeName = uniqueMatch.getValue().iterator().next();

            opCodes.stream()
                    .filter(opcode -> opcode.getName().equals(opCodeName))
                    .findFirst()
                    .ifPresentOrElse(opcode -> opcode.setOpCode(opCodeNumber),
                            () -> {
                                throw new RuntimeException("No opcode found for " + opCodeName);
                            });

            opCodeMap.remove(opCodeNumber);
            opCodeMap.values().forEach(names -> names.remove(opCodeName));
        }
    }
}
