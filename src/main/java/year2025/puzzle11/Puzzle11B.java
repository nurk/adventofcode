package year2025.puzzle11;

import util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Part B: ???
 */
public class Puzzle11B {

    private static long depth = 0;

    // need to do something smarter with memoization
    // too much depth.
    // just cannot figure it out for the moment
    private static Map<List<String>, Long> memoization = new HashMap<>();

    public static void main(String[] args) {
        List<Device> devicesPartB = Utils.getInput("2025/input11.txt", Device::new);

        devicesPartB = reducePartBDevicesList(devicesPartB);

        depth = 0;
        memoization = new HashMap<>();
        System.out.println("Searching SVR to DAC");
        long svrToDac = findPathsPartB(devicesPartB.stream()
                .filter(device -> device.getInput().equals("svr"))
                .findFirst()
                .orElseThrow(), devicesPartB, new ArrayList<>(), "dac");

        System.out.println(svrToDac);

        depth = 0;
        memoization = new HashMap<>();
        System.out.println("Searching SVR to FFT");
        long svrToFft = findPathsPartB(devicesPartB.stream()
                .filter(device -> device.getInput().equals("svr"))
                .findFirst()
                .orElseThrow(), devicesPartB, new ArrayList<>(), "fft");

        System.out.println(svrToFft);

        depth = 0;
        memoization = new HashMap<>();
        System.out.println("Searching DAC to FFT");
        long dacToFft = findPathsPartB(devicesPartB.stream()
                .filter(device -> device.getInput().equals("dac"))
                .findFirst()
                .orElseThrow(), devicesPartB, new ArrayList<>(), "fft");

        System.out.println(dacToFft);

        depth = 0;
        memoization = new HashMap<>();
        System.out.println("Searching FFT to DAC");
        long fftToDac = findPathsPartB(devicesPartB.stream()
                .filter(device -> device.getInput().equals("fft"))
                .findFirst()
                .orElseThrow(), devicesPartB, new ArrayList<>(), "dac");

        System.out.println(fftToDac);

        depth = 0;
        memoization = new HashMap<>();
        System.out.println("Searching FFT to OUT");
        long fftToOut = findPathsPartB(devicesPartB.stream()
                .filter(device -> device.getInput().equals("fft"))
                .findFirst()
                .orElseThrow(), devicesPartB, new ArrayList<>(), "out");

        System.out.println(fftToOut);

        depth = 0;
        memoization = new HashMap<>();
        System.out.println("Searching DAC to OUT");
        long dacToOut = findPathsPartB(devicesPartB.stream()
                .filter(device -> device.getInput().equals("dac"))
                .findFirst()
                .orElseThrow(), devicesPartB, new ArrayList<>(), "out");

        System.out.println(dacToOut);

        long partB = (svrToDac * dacToFft * fftToOut) + (svrToFft * fftToDac * dacToOut);
        System.out.println("Part B: " + partB);
    }


    private static long findPathsPartB(Device current, List<Device> devices, List<String> seenInputs, String endpoint) {
        depth++;
        if (depth % 1000000 == 0) {
            System.out.println("Depth: " + depth + ", Current: " + current.getInput() + ", Seen: " + seenInputs);
        }

        List<String> newSeenInputs = new ArrayList<>(seenInputs);
        newSeenInputs.add(current.getInput());

        return current.getOutputs()
                .stream()
                .filter(output -> !newSeenInputs.contains(output))
                .map(output -> validateAndRecursePartB(devices, output, newSeenInputs, endpoint))
                .reduce(Long::sum)
                .orElse(0L);
    }

    private static long validateAndRecursePartB(List<Device> devices,
                                                String output,
                                                List<String> newSeenInputs,
                                                String endpoint) {
        if (output.equals(endpoint)) {
            return 1L;
        }

        return devices.stream()
                .filter(device -> device.getInput().equals(output))
                .map(device -> findPathsPartB(device, devices, newSeenInputs, endpoint))
                .reduce(Long::sum)
                .orElse(0L);
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
