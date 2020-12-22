package year2020.puzzle8;

import java.util.ArrayList;
import java.util.List;

public class Game {
    List<Integer> seenPositions = new ArrayList<>();
    private int position = 0;
    private int accumulator = 0;
    private final List<String> instructions;

    public Game(List<String> instructions) {
        this.instructions = instructions;
    }

    public int run() {
        while (position != instructions.size() - 1) {
            if (seenPositions.contains(position)) {
                System.out.println("Error: " + accumulator);
                return -1;
            }
            seenPositions.add(position);
            executeInstruction();
        }

        executeInstruction();
        System.out.println("Found the end " + accumulator);
        return accumulator;
    }

    private void executeInstruction() {
        String inst = instructions.get(position);
        String[] split = inst.split(" ");

        switch (split[0]) {
            case "acc":
                accumulator = accumulator + Integer.parseInt(split[1]);
                position++;
                break;
            case "jmp":
                position = position + Integer.parseInt(split[1]);
                break;
            default:
                position++;
                break;
        }
    }
}
