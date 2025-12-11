package year2025.puzzle11;

import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 733
 */
public class Puzzle11A {
    public static void main(String[] args) {
        List<Device> devicesPartA = Utils.getInput("2025/input11.txt", Device::new);

        Device you = devicesPartA.stream()
                .filter(device -> device.getInput().equals("you"))
                .findFirst()
                .orElseThrow();

        System.out.println("Part A: " + findPathsPartA(you, devicesPartA, new ArrayList<>()));
    }

    private static int findPathsPartA(Device current, List<Device> devices, List<String> seenInputs) {
        List<String> newSeenInputs = new ArrayList<>(seenInputs);
        newSeenInputs.add(current.getInput());

        return current.getOutputs().stream()
                .filter(output -> !newSeenInputs.contains(output))
                .map(output -> output.equals("out") ? 1 : devices.stream()
                        .filter(device -> device.getInput().equals(output))
                        .map(device -> findPathsPartA(device, devices, newSeenInputs))
                        .reduce(Integer::sum)
                        .orElse(0))
                .reduce(Integer::sum)
                .orElse(0);
    }
}
