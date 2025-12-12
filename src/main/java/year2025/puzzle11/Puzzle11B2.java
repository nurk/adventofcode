package year2025.puzzle11;

import util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Part B: 290219757077250
 */
public class Puzzle11B2 {

    private final static Map<String, Long> MEMOIZATION = new HashMap<>();

    public static void main(String[] args) {
        List<Device> devicesPartB = Utils.getInput("2025/input11.txt", Device::new);

        MEMOIZATION.clear();
        System.out.println("Searching SVR to DAC");
        long svrToDac = findPathsPartB(findDeviceByInput(devicesPartB, "svr"), devicesPartB, "dac");

        System.out.println(svrToDac);

        MEMOIZATION.clear();
        System.out.println("Searching SVR to FFT");
        long svrToFft = findPathsPartB(findDeviceByInput(devicesPartB, "svr"), devicesPartB, "fft");

        System.out.println(svrToFft);

        MEMOIZATION.clear();
        System.out.println("Searching DAC to FFT");
        long dacToFft = findPathsPartB(findDeviceByInput(devicesPartB, "dac"), devicesPartB, "fft");

        System.out.println(dacToFft);

        MEMOIZATION.clear();
        System.out.println("Searching FFT to DAC");
        long fftToDac = findPathsPartB(findDeviceByInput(devicesPartB, "fft"), devicesPartB, "dac");

        System.out.println(fftToDac);

        MEMOIZATION.clear();
        System.out.println("Searching FFT to OUT");
        long fftToOut = findPathsPartB(findDeviceByInput(devicesPartB, "fft"), devicesPartB, "out");

        System.out.println(fftToOut);

        MEMOIZATION.clear();
        System.out.println("Searching DAC to OUT");
        long dacToOut = findPathsPartB(findDeviceByInput(devicesPartB, "dac"), devicesPartB, "out");

        System.out.println(dacToOut);

        long partB = (svrToDac * dacToFft * fftToOut) + (svrToFft * fftToDac * dacToOut);
        System.out.println("Part B: " + partB);
    }


    private static long findPathsPartB(Device current, List<Device> devices, String endpoint) {
        if (MEMOIZATION.containsKey(current.getInput())) {
            return MEMOIZATION.get(current.getInput());
        }

        Long sum = current.getOutputs().stream()
                .map(output -> output.equals(endpoint) ? 1 : devices.stream()
                        .filter(device -> device.getInput().equals(output))
                        .map(device -> findPathsPartB(device, devices, endpoint))
                        .reduce(Long::sum)
                        .orElse(0L))
                .reduce(Long::sum)
                .orElse(0L);

        MEMOIZATION.put(current.getInput(), sum);
        return sum;
    }

    private static Device findDeviceByInput(List<Device> devices, String input) {
        return devices.stream().filter(device -> device.getInput().equals(input)).findFirst().orElse(null);
    }
}
