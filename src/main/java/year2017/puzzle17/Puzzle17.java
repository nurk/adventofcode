package year2017.puzzle17;

/**
 * Part A: 417
 * Part B: 34334221
 */
public class Puzzle17 {
    public static void main(String[] args) {
        int stepSize = 348;
        CircularBuffer buffer = new CircularBuffer(stepSize);

        for (int i = 1; i <= 2017; i++) {
            buffer.insertIntoBuffer(i);
        }

        System.out.println("Part A: " + buffer.getValueAfter(2017));

        // https://observablehq.com/@jwolondon/advent-of-code-2017-day-17
        int position = 0;
        int valueAfterZero = -1;
        for (int i = 1; i <= 50_000_000; i++) {
            position = (position + stepSize) % i;
            if (position == 0) {
                valueAfterZero = i;
            }
            position = (position + 1) % (i + 1);
        }

        System.out.println("Part B: " + valueAfterZero);
    }

}
