package year2023.puzzle20;

import java.util.List;
import java.util.Queue;

public class FlipFlop extends Module {

    private boolean state = false;

    public FlipFlop(String name, List<String> outputs) {
        super(name, outputs);
    }

    @Override
    public void processPulse(boolean pulse, Queue<Pulse> pulses, String source) {
        if (pulse) {
            return;
        }

        state = !state;
        sendPulse(state, pulses);
    }
}
