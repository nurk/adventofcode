package year2025.puzzle10;

import util.Utils;

import java.util.List;

/**
 * Part A: 390
 */
public class Puzzle10 {
    public static void main(String[] args) {
        List<MachineA> machineA = Utils.getInput("2025/input10.txt", MachineA::new);

        int partA = machineA.stream()
                .map(MachineA::solve)
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Part A: " + partA);

        List<MachineB> machineB = Utils.getInput("2025/input10-test.txt", MachineB::new);

        int partB = machineB.stream()
                .map(MachineB::solve)
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Part B: " + partB);
    }
}
