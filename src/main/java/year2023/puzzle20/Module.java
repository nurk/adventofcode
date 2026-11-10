package year2023.puzzle20;

import lombok.Getter;

import java.util.List;
import java.util.Queue;

public abstract class Module {

    @Getter
    private final String name;
    @Getter
    private final List<String> outputs;

    public Module(String name, List<String> outputs) {
        this.name = name;
        this.outputs = outputs;
    }

    public abstract void processPulse(boolean pulse, Queue<Pulse> pulses, String source);

    protected void sendPulse(boolean pulse, Queue<Pulse> pulses) {
        outputs.forEach(output -> pulses.add(new Pulse(name, output, pulse)));
    }
}
