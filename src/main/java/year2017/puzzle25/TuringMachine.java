package year2017.puzzle25;

import java.util.stream.IntStream;

public class TuringMachine {
    private int[] states;
    private int currentPosition;

    public TuringMachine(int numberOfSteps) {
        states = new int[numberOfSteps * 2];
        currentPosition = numberOfSteps;
    }

    public int getChecksum() {
        return IntStream.of(states).sum();
    }

    public void toTheLeft() {
        currentPosition--;
    }

    public void toTheRight() {
        currentPosition++;
    }

    public void writeValue(int value) {
        states[currentPosition] = value;
    }

    public int getCurrentValue() {
        return states[currentPosition];
    }
}
