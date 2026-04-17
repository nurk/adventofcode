package year2016.puzzle23;

import org.apache.commons.lang3.Strings;
import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 14346
 * Part B: 479010906
 */
public class Puzzle23 {

    private static int a, b, c, d;
    private static List<String> instructions;
    private static int instructionIndex;

    static void main() {
        partA();
        partB();
    }

    private static void partA() {
        instructions = new ArrayList<>(Utils.getInput("2016/input23.txt"));

        a = 7;
        b = 0;
        c = 0;
        d = 0;
        instructionIndex = 0;

        do {
            doInstruction(instructions.get(instructionIndex));
        } while (instructionIndex < instructions.size());

        System.out.println("Part A: " + a);
    }


    private static void partB() {
        instructions = new ArrayList<>(Utils.getInput("2016/input23mul.txt"));

        a = 12;
        b = 0;
        c = 0;
        d = 0;
        instructionIndex = 0;

        do {
            doInstruction(instructions.get(instructionIndex));
        } while (instructionIndex < instructions.size());

        System.out.println("Part B: " + a);
    }

    private static void doInstruction(String s) {
        String[] split = s.split(" ");
        switch (split[0]) {
            case "cpy" -> {
                int cpyValue;
                if (isNumeric(split[1])) {
                    cpyValue = Integer.parseInt(split[1]);
                } else {
                    cpyValue = getRegisterValue(split[1]);
                }
                if (!isNumeric(split[2])) {
                    setRegisterValue(split[2], cpyValue);
                }
                instructionIndex++;
            }
            case "inc" -> {
                addToRegister(split[1], 1);
                instructionIndex++;
            }
            case "dec" -> {
                addToRegister(split[1], -1);
                instructionIndex++;
            }
            case "jnz" -> {
                int jnzValue;
                if (isNumeric(split[1])) {
                    jnzValue = Integer.parseInt(split[1]);
                } else {
                    jnzValue = getRegisterValue(split[1]);
                }
                if (jnzValue != 0) {
                    int jnzJump;
                    if (isNumeric(split[2])) {
                        jnzJump = Integer.parseInt(split[2]);
                    } else {
                        jnzJump = getRegisterValue(split[2]);
                    }
                    instructionIndex += jnzJump;
                } else {
                    instructionIndex++;
                }
            }
            case "tgl" -> {
                int tglValue;
                if (isNumeric(split[1])) {
                    tglValue = Integer.parseInt(split[1]);
                } else {
                    tglValue = getRegisterValue(split[1]);
                }
                int tglPointer = instructionIndex + tglValue;

                try {
                    String inst = instructions.get(tglPointer);
                    if (Strings.CS.startsWith(inst, "inc")) {
                        instructions.set(tglPointer, inst.replaceFirst("inc", "dec"));
                    } else if (Strings.CS.startsWith(inst, "dec") || Strings.CS.startsWith(inst, "tgl")) {
                        instructions.set(tglPointer, inst.replaceFirst("dec|tgl", "inc"));
                    } else if (Strings.CS.startsWith(inst, "jnz")) {
                        instructions.set(tglPointer, inst.replaceFirst("jnz", "cpy"));
                    } else if (Strings.CS.startsWith(inst, "cpy")) {
                        instructions.set(tglPointer, inst.replaceFirst("cpy", "jnz"));
                    }
                } catch (IndexOutOfBoundsException e) {
                    //ignored
                } finally {
                    instructionIndex++;
                }
            }
            case "mul" -> {
                int operandOne = getRegisterValue(split[1]);
                int operandTwo = getRegisterValue(split[2]);
                int mulValue = operandOne * operandTwo;

                setRegisterValue(split[3], mulValue);
                instructionIndex++;
            }
        }
    }

    private static void setRegisterValue(String s, int value) {
        switch (s) {
            case "a" -> a = value;
            case "b" -> b = value;
            case "c" -> c = value;
            case "d" -> d = value;
        }
    }

    private static int getRegisterValue(String s) {
        return switch (s) {
            case "a" -> a;
            case "b" -> b;
            case "c" -> c;
            case "d" -> d;
            default -> throw new IllegalStateException("Unexpected value: " + s);
        };
    }

    private static void addToRegister(String s, int value) {
        switch (s) {
            case "a" -> a += value;
            case "b" -> b += value;
            case "c" -> c += value;
            case "d" -> d += value;
        }
    }

    private static boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
