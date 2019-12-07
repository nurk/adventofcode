package year2019.puzzle2;

public class IntCode2 {

    private int[] integers;

    public IntCode2(int[] integers, int noun, int verb) {
        this.integers = integers.clone();
        this.integers[1] = noun;
        this.integers[2] = verb;
    }

    public int run() {
        try {
            int currentPosition = 0;
            while (!runOperation(currentPosition)) {
                currentPosition += 4;
            }

            return integers[0];
        } catch (IllegalArgumentException e) {
            return 0;
        }
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
