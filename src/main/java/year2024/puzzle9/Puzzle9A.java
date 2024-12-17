package year2024.puzzle9;

import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Part A: 6200294120911
 */
public class Puzzle9A {

    public static final String EMPTY = ".";
    private static final List<String> blocks = new ArrayList<>();

    public static void main(String[] args) {
        List<String> input = Arrays.stream(Utils.getInput("2024/input9.txt", (s) -> s).getFirst().split(""))
                .toList();

        for (int i = 0; i < input.size(); i++) {
            int value = Integer.parseInt(input.get(i));

            if (i % 2 == 0) {
                int id = i / 2;
                for (int j = 0; j < value; j++) {
                    blocks.add(String.valueOf(id));
                }
            } else {
                for (int j = 0; j < value; j++) {
                    blocks.add(EMPTY);
                }
            }
        }
        printBlocks();

        int firstEmptyIndex = getFirstEmptyIndex();
        int lastNonEmptyIndex = getLastNonEmptyIndex();

        while (firstEmptyIndex < lastNonEmptyIndex) {
            String block = blocks.remove(lastNonEmptyIndex);
            blocks.add(EMPTY);
            blocks.remove(firstEmptyIndex);
            blocks.add(firstEmptyIndex, block);
            lastNonEmptyIndex = getLastNonEmptyIndex();
            firstEmptyIndex = getFirstEmptyIndex();
            printBlocks();
        }

        System.out.println("Part A: " + calculateChecksum());
    }

    private static long calculateChecksum() {
        long checksum = 0;
        for(int i=0; i < blocks.size(); i++) {
            if(!blocks.get(i).equals(EMPTY)) {
                checksum += Long.parseLong(blocks.get(i)) * i;
            }
        }

        return checksum;
    }

    private static int getFirstEmptyIndex() {
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).equals(EMPTY)) {
                return i;
            }
        }
        return -1;
    }

    private static int getLastNonEmptyIndex() {
        for (int i = blocks.size() - 1; i >= 0; i--) {
            if (!blocks.get(i).equals(EMPTY)) {
                return i;
            }
        }
        return -1;
    }

    private static void printBlocks() {
        //System.out.println(String.join("", blocks));
    }
}
