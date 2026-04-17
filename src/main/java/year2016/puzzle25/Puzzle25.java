package year2016.puzzle25;

import org.apache.commons.lang3.Strings;
import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Trying a = 189
 */
public class Puzzle25 {

    private static int a, b, c, d;
    private static List<String> instructions;
    private static int instructionIndex;
    private static boolean wasOut = false;
    private static int outValue = 0;

    static void main() {
        instructions = new ArrayList<>(Utils.getInput("2016/input25.txt"));
        partA();
    }

    private static void partA() {
        int aRegisterValue = 0;
        while (true) {
            System.out.println("Trying a = " + aRegisterValue);

            a = aRegisterValue;
            b = 0;
            c = 0;
            d = 0;
            instructionIndex = 0;

            int nextExpectedReturnValue = 1;
            do {
                do {
                    doInstruction(instructions.get(instructionIndex));
                } while (!wasOut);
                nextExpectedReturnValue = (nextExpectedReturnValue + 1) % 2;
                wasOut = false;
                System.out.print(outValue);
            } while (outValue == nextExpectedReturnValue);

            System.out.println();
            System.out.println("Failed");
            aRegisterValue++;
        }
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
            case "out" -> {
                wasOut = true;
                if (isNumeric(split[1])) {
                    outValue = Integer.parseInt(split[1]);
                } else {
                    outValue = getRegisterValue(split[1]);
                }
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
