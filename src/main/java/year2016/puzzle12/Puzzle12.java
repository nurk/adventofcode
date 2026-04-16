package year2016.puzzle12;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.List;

/**
 * Part A: 317993
 * Part B: 9227647
 */
public class Puzzle12 {

    private static int a, b, c, d;
    private static List<String> instructions;
    private static int instructionIndex;

    static void main() {
        instructions = Utils.getInput("2016/input12.txt");

        partA();
        partB();
    }


    private static void partA() {
        a = 0;
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
        a = 0;
        b = 0;
        c = 1;
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
                if (StringUtils.isNumeric(split[1])) {
                    cpyValue = Integer.parseInt(split[1]);
                } else {
                    cpyValue = getRegisterValue(split[1]);
                }
                setRegisterValue(split[2], cpyValue);
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
                if (StringUtils.isNumeric(split[1])) {
                    jnzValue = Integer.parseInt(split[1]);
                } else {
                    jnzValue = getRegisterValue(split[1]);
                }
                if (jnzValue != 0) {
                    instructionIndex += Integer.parseInt(split[2]);
                } else {
                    instructionIndex++;
                }
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
}
