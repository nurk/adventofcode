package year2025.puzzle11;

import util.Utils;

import java.util.*;

/**
 * Part B: ???
 */
public class Puzzle11BAI {

    private static long depth = 0;

    // Memoization cache: (currentInput, endpoint) -> number of paths
    private static final Map<String, Long> memoization = new HashMap<>();

    // Adjacency list: input label -> list of outputs
    private static final Map<String, List<String>> adjacency = new HashMap<>();

    public static void main(String[] args) {
        List<Device> devicesPartB = Utils.getInput("2025/input11.txt", Device::new);

        devicesPartB = reducePartBDevicesList(devicesPartB);

        // Build helper structures for memoized DFS
        buildGraph(devicesPartB);

        System.out.println("Searching SVR to DAC");
        depth = 0;
        memoization.clear();
        long svrToDac = findPathsPartB("svr", "dac", new HashSet<>());
        System.out.println(svrToDac);

        System.out.println("Searching SVR to FFT");
        depth = 0;
        memoization.clear();
        long svrToFft = findPathsPartB("svr", "fft", new HashSet<>());
        System.out.println(svrToFft);

        System.out.println("Searching DAC to FFT");
        depth = 0;
        memoization.clear();
        long dacToFft = findPathsPartB("dac", "fft", new HashSet<>());
        System.out.println(dacToFft);

        System.out.println("Searching FFT to DAC");
        depth = 0;
        memoization.clear();
        long fftToDac = findPathsPartB("fft", "dac", new HashSet<>());
        System.out.println(fftToDac);

        System.out.println("Searching FFT to OUT");
        depth = 0;
        memoization.clear();
        long fftToOut = findPathsPartB("fft", "out", new HashSet<>());
        System.out.println(fftToOut);

        System.out.println("Searching DAC to OUT");
        depth = 0;
        memoization.clear();
        long dacToOut = findPathsPartB("dac", "out", new HashSet<>());
        System.out.println(dacToOut);

        long partB = (svrToDac * dacToFft * fftToOut) + (svrToFft * fftToDac * dacToOut);
        System.out.println("Part B: " + partB);
    }

    // Build adjacency map from the list of devices
    private static void buildGraph(List<Device> devices) {
        adjacency.clear();
        for (Device device : devices) {
            adjacency.put(device.getInput(), device.getOutputs());
        }
    }

    /**
     * Memoized DFS to count all paths from currentInput to endpoint.
     *
     * This version assumes the reduced graph is effectively acyclic for the
     * reachable region, so the number of paths from a node to endpoint
     * depends only on (currentInput, endpoint). We add a lightweight
     * cycle-detection set as a safety net; if a cycle is encountered,
     * that branch contributes 0 paths.
     */
    private static long findPathsPartB(String currentInput, String endpoint, Set<String> onStack) {
        depth++;
        if (depth % 1_000_000 == 0) {
            System.out.println("Depth: " + depth + ", Current: " + currentInput);
        }

        if (currentInput.equals(endpoint)) {
            return 1L;
        }

        // cycle detection safety net
        if (!onStack.add(currentInput)) {
            // We hit a cycle; ignore this branch
            return 0L;
        }

        String memoKey = currentInput + "|" + endpoint;
        Long cached = memoization.get(memoKey);
        if (cached != null) {
            onStack.remove(currentInput);
            return cached;
        }

        List<String> outputs = adjacency.getOrDefault(currentInput, List.of());
        long total = 0L;
        for (String output : outputs) {
            if (output.equals(endpoint)) {
                total += 1L;
            } else {
                total += findPathsPartB(output, endpoint, onStack);
            }
        }

        memoization.put(memoKey, total);
        onStack.remove(currentInput);
        return total;
    }

    private static List<Device> reducePartBDevicesList(List<Device> devicesPartB) {
        List<Device> newDevices = new ArrayList<>();

        int mergeCount;

        do {
            mergeCount = 0;
            for (Device device : devicesPartB) {
                if (device.getOutputs().size() == 1 && !(device.getOutputs()
                        .getFirst()
                        .equals("out") || device.getOutputs().getFirst().equals("svr") || device.getOutputs()
                        .getFirst()
                        .equals("fft") || device.getOutputs().getFirst().equals("dac"))) {
                    Device nextDevice = findDeviceByInput(devicesPartB, device.getOutputs().getFirst());
                    if (nextDevice != null) {
                        mergeCount++;
                        Device mergedDevice = new Device(device.getInput(), nextDevice.getOutputs());
                        newDevices.add(mergedDevice);
                    } else {
                        newDevices.add(device);
                    }
                } else {
                    newDevices.add(device);
                }
            }
            devicesPartB = newDevices;
            newDevices = new ArrayList<>();
        } while (mergeCount > 0);

        return devicesPartB;
    }

    private static Device findDeviceByInput(List<Device> devices, String input) {
        return devices.stream().filter(device -> device.getInput().equals(input)).findFirst().orElse(null);
    }
}
