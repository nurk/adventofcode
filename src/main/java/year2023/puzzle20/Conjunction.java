package year2023.puzzle20;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Conjunction extends Module {

    private final Map<String, Boolean> inputStates = new HashMap<>();

    public Conjunction(String name, List<String> outputs) {
        super(name, outputs);
    }

    public void init(Map<String, Module> modules) {
        modules.values().forEach(module -> {
            if (module.getOutputs().contains(getName())) {
                inputStates.put(module.getName(), false);
            }
        });
    }

    @Override
    public void processPulse(boolean pulse, Queue<Pulse> pulses, String source) {
        inputStates.put(source, pulse);

        boolean allHigh = inputStates.values().stream().allMatch(b -> b == true);

        boolean outputPulse = !allHigh;

        sendPulse(outputPulse, pulses);
    }
}
