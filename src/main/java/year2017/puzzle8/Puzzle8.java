package year2017.puzzle8;

import util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Part A: 5221
 * Part B: 7491
 */
public class Puzzle8 {
    public static void main(String[] args) {
        List<Instruction> instructions = Utils.getInput("2017/input8.txt", Instruction::new);

        Map<String, Long> registers = new HashMap<>();
        AtomicLong highestValueEver = new AtomicLong(0L);
        instructions.forEach(instruction -> {
            instruction.performInstruction(registers);
            long currentMax = registers.values().stream().mapToLong(v -> v).max().orElse(0L);
            highestValueEver.updateAndGet(x -> Math.max(x, currentMax));
        });

        System.out.println("Part A: " + registers.values().stream().mapToLong(v -> v).max().orElse(0L));
        System.out.println("Part B: " + highestValueEver.get());
    }
}
