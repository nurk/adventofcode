package year2025.puzzle10.internetz3;

import util.Utils;

import java.util.List;

/**
 * Part B: 14677
 */
public class Puzzle10BInternetZ3 {
    public static void main(String[] args) {
        List<MachineInternetZ3> machines = Utils.getInput("2025/input10.txt", MachineInternetZ3::new);

        Integer partB = machines.stream()
                .map(MachineInternetZ3::solvePart2WithZ3)
                .reduce(0, Integer::sum);

        System.out.println("Part B: " + partB);
    }
}
