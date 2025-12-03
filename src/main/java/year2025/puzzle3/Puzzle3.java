package year2025.puzzle3;

import util.Utils;

import java.util.List;

/**
 * Part A: 17412
 * Part B: 172681562473501
 */
public class Puzzle3 {
    public static void main(String[] args) {
        List<BatteryBank> batteryBanks = (Utils.getInput("2025/input3.txt", BatteryBank::new));

        long partA = batteryBanks.stream()
                .map(batteryBank -> batteryBank.getMaxJoltage(2))
                .mapToLong(Long::longValue)
                .sum();

        System.out.println("Part A: " + partA);

        long partB = batteryBanks.stream()
                .map(batteryBank -> batteryBank.getMaxJoltage(12))
                .mapToLong(Long::longValue)
                .sum();

        System.out.println("Part B: " + partB);
    }
}
