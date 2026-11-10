package year2023.puzzle20;

import java.util.List;
import java.util.Queue;

public class Broadcaster extends Module {
    public Broadcaster(String name, List<String> outputs) {
        super(name, outputs);
    }

    @Override
    public void processPulse(boolean pulse, Queue<Pulse> pulses, String source) {
        sendPulse(pulse, pulses);
    }
}
