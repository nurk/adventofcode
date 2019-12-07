package year2019.puzzle2;

public class IntCode {

    private int[] integers;

    public IntCode(int[] integers) {
        this.integers = integers;
        this.integers[1] = 12;
        this.integers[2] = 2;
    }

    public int run() {
        int currentPosition = 0;
        while (!runOperation(currentPosition)) {
            currentPosition += 4;
        }

        return integers[0];
    }

    private boolean runOperation(int currentPosition) {
        if (integers[currentPosition] == 1) {
            integers[integers[currentPosition + 3]] = integers[integers[currentPosition + 1]] + integers[integers[currentPosition + 2]];
            return false;
        } else if (integers[currentPosition] == 2) {
            integers[integers[currentPosition + 3]] = integers[integers[currentPosition + 1]] * integers[integers[currentPosition + 2]];
            return false;
        } else if (integers[currentPosition] == 99) {
            return true;
        }

        throw new IllegalArgumentException("uh oh");
    }
}
