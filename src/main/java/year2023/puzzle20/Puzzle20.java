package year2023.puzzle20;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.jspecify.annotations.NonNull;
import util.Utils;

import java.util.*;

public class Puzzle20 {
    static void main() {
        System.out.println("Part A: " + partA(Utils.getInput("2023/input20.txt"), 1000));
        System.out.println("Part B: " + partB(Utils.getInput("2023/input20.txt")));
    }

    static long partA(List<String> input, int buttonPresses) {
        Map<String, Module> modules = getInitiatedModules(input);

        Queue<Pulse> pulses = new ArrayDeque<>();
        long totalLowPulses = 0;
        long totalHighPulses = 0;
        for (int i = 0; i < buttonPresses; i++) {
            pulses.add(new Pulse("button", "broadcaster", false));
            while (!pulses.isEmpty()) {
                Pulse pulse = pulses.remove();
                if (pulse.pulse()) {
                    totalHighPulses++;
                } else {
                    totalLowPulses++;
                }

                Module module = modules.get(pulse.destination());
                if (module != null) {
                    module.processPulse(pulse.pulse(), pulses, pulse.source());
                }
            }
        }
        return totalHighPulses * totalLowPulses;
    }

    static long partB(List<String> input) {
        Map<String, Module> modules = getInitiatedModules(input);

        Queue<Pulse> pulses = new ArrayDeque<>();
        long totalButtonPresses = 0;
        while (true) {
            totalButtonPresses++;
            pulses.add(new Pulse("button", "broadcaster", false));
            while (!pulses.isEmpty()) {
                Pulse pulse = pulses.remove();

                if (pulse.destination().equals("rx") && !pulse.pulse()) {
                    return totalButtonPresses;
                }

                Module module = modules.get(pulse.destination());
                if (module != null) {
                    module.processPulse(pulse.pulse(), pulses, pulse.source());
                }
            }
        }
    }

    static @NonNull Map<String, Module> getInitiatedModules(List<String> input) {
        Map<String, Module> modules = new HashMap<>();
        input.forEach(line -> {
            String[] split = line.split(" -> ");
            if (Strings.CS.equals("broadcaster", split[0])) {
                String[] outputs = split[1].split(", ");
                modules.put(split[0], new Broadcaster(split[0], List.of(outputs)));
            } else {
                String type = StringUtils.substring(split[0], 0, 1);
                String name = StringUtils.substring(split[0], 1);
                String[] outputs = split[1].split(", ");
                switch (type) {
                    case "%" -> modules.put(name, new FlipFlop(name, List.of(outputs)));
                    case "&" -> modules.put(name, new Conjunction(name, List.of(outputs)));
                }
            }
        });

        modules.values().forEach(module -> {
            if (module instanceof Conjunction conjunction) {
                conjunction.init(modules);
            }
        });
        return modules;
    }
}
