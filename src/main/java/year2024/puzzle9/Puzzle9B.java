package year2024.puzzle9;

import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Part A: 6200294120911
 * Part B: 6227018762750
 */
public class Puzzle9B {

    public static final String EMPTY = ".";
    private static final List<FileBlock> blocks = new ArrayList<>();

    public static void main(String[] args) {
        List<String> input = Arrays.stream(Utils.getInput("2024/input9.txt", (s) -> s).getFirst().split(""))
                .toList();

        for (int i = 0; i < input.size(); i++) {
            int value = Integer.parseInt(input.get(i));

            if (i % 2 == 0) {
                if (value > 0) {
                    int id = i / 2;
                    blocks.add(new FileBlock(value, String.valueOf(id)));
                }
            } else {
                if (value > 0) {
                    blocks.add(new FileBlock(value, EMPTY));
                }
            }
        }
        printBlocks(blocks);
        List<FileBlock> workingBlocks = new ArrayList<>(blocks);

        for (int i = blocks.size() - 1; i >= 0; i--) {
            FileBlock nonEmptyBlock = blocks.get(i);
            if (!nonEmptyBlock.isEmpty()) {
                int indexOfNonEmptyBlock = workingBlocks.indexOf(nonEmptyBlock);
                for (int j = 0; j < indexOfNonEmptyBlock; j++) {
                    if (workingBlocks.get(j).isEmpty() && workingBlocks.get(j)
                            .getSize() >= nonEmptyBlock.getSize()) {
                        //move block;
                        workingBlocks.remove(indexOfNonEmptyBlock);
                        workingBlocks.add(indexOfNonEmptyBlock,
                                new FileBlock(nonEmptyBlock.getSize(), EMPTY));

                        FileBlock emptyBlock = workingBlocks.remove(j);
                        workingBlocks.add(j, nonEmptyBlock);
                        int newSize = emptyBlock.getSize() - nonEmptyBlock.getSize();
                        if (newSize > 0) {
                            workingBlocks.add(j + 1, new FileBlock(newSize, EMPTY));
                        }
                        break;
                    }
                }
            }
            printBlocks(workingBlocks);
        }

        System.out.println("Part B: " + calculateChecksum(workingBlocks));
    }

    private static long calculateChecksum(List<FileBlock> in) {
        long checksum = 0;

        List<String> list = new ArrayList<>();
        in.forEach(f -> {
            for (int i = 0; i < f.getSize(); i++) {
                list.add(f.getId());
            }
        });
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals(EMPTY)) {
                checksum += Long.parseLong(list.get(i)) * i;
            }
        }

        return checksum;
    }

    private static void printBlocks(List<FileBlock> in) {
        // System.out.println(in.stream().map(FileBlock::toString).collect(Collectors.joining("|")));
    }
}
