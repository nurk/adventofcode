package year2017.puzzle17;

import java.util.ArrayList;
import java.util.List;

public class CircularBuffer {
    private final int stepSize;
    private final List<Integer> buffer = new ArrayList<>();
    private int currentPosition = 0;

    public CircularBuffer(int stepSize) {
        this.stepSize = stepSize;
        buffer.add(0);
    }

    public void insertIntoBuffer(int value) {
        currentPosition = (currentPosition + stepSize) % buffer.size();
        buffer.add(currentPosition+1, value);
        currentPosition = currentPosition+1;
    }

    public int getValueAfter(int target) {
        int index = buffer.indexOf(target);
        return buffer.get((index + 1) % buffer.size());
    }

    @Override
    public String toString() {
        return "CircularBuffer{" +
                "buffer=" + buffer +
                ", currentPosition=" + currentPosition +
                '}';
    }
}
