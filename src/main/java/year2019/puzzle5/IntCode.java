package year2019.puzzle5;

import org.apache.commons.lang3.StringUtils;

public class IntCode {

    private final int input;
    private int[] integers;
    private int currentPosition = 0;

    public IntCode(int[] integers, int input) {
        this.integers = integers;
        this.input = input;
    }

    public void run() {
        while (true) {
            runOperation();
        }
    }

    private void runOperation() {
        int curPos = integers[currentPosition];
        int opCode = getOpCode(curPos);

        if (opCode == 1) {
            int pos1 = isImmediate(curPos, 1) ? currentPosition + 1 : integers[currentPosition + 1];
            int pos2 = isImmediate(curPos, 2) ? currentPosition + 2 : integers[currentPosition + 2];
            int pos3 = integers[currentPosition + 3];
            integers[pos3] = integers[pos1] + integers[pos2];
            currentPosition += 4;
            return;
        } else if (opCode == 2) {
            int pos1 = isImmediate(curPos, 1) ? currentPosition + 1 : integers[currentPosition + 1];
            int pos2 = isImmediate(curPos, 2) ? currentPosition + 2 : integers[currentPosition + 2];
            int pos3 = integers[currentPosition + 3];
            integers[pos3] = integers[pos1] * integers[pos2];
            currentPosition += 4;
            return;
        } else if (opCode == 3) {
            int pos1 = isImmediate(curPos, 1) ? currentPosition + 1 : integers[currentPosition + 1];
            integers[pos1] = input;
            currentPosition += 2;
            return;
        } else if (opCode == 4) {
            int pos1 = isImmediate(curPos, 1) ? currentPosition + 1 : integers[currentPosition + 1];
            System.out.println(integers[pos1]);
            currentPosition += 2;
            return;
        } else if (opCode == 5) {
            int pos1 = isImmediate(curPos, 1) ? currentPosition + 1 : integers[currentPosition + 1];
            if (integers[pos1] != 0) {
                int pos2 = isImmediate(curPos, 2) ? currentPosition + 2 : integers[currentPosition + 2];
                currentPosition = integers[pos2];
                return;
            }
            currentPosition += 3;
            return;
        } else if (opCode == 6) {
            int pos1 = isImmediate(curPos, 1) ? currentPosition + 1 : integers[currentPosition + 1];
            if (integers[pos1] == 0) {
                int pos2 = isImmediate(curPos, 2) ? currentPosition + 2 : integers[currentPosition + 2];
                currentPosition = integers[pos2];
                return;
            }
            currentPosition += 3;
            return;
        } else if (opCode == 7) {
            int pos1 = isImmediate(curPos, 1) ? currentPosition + 1 : integers[currentPosition + 1];
            int pos2 = isImmediate(curPos, 2) ? currentPosition + 2 : integers[currentPosition + 2];
            int pos3 = integers[currentPosition + 3];
            if (integers[pos1] < integers[pos2]) {
                integers[pos3] = 1;
            } else {
                integers[pos3] = 0;
            }
            currentPosition += 4;
            return;
        } else if (opCode == 8) {
            int pos1 = isImmediate(curPos, 1) ? currentPosition + 1 : integers[currentPosition + 1];
            int pos2 = isImmediate(curPos, 2) ? currentPosition + 2 : integers[currentPosition + 2];
            int pos3 = integers[currentPosition + 3];
            if (integers[pos1] == integers[pos2]) {
                integers[pos3] = 1;
            } else {
                integers[pos3] = 0;
            }
            currentPosition += 4;
            return;
        } else if (opCode == 99) {
            System.exit(0);
        }

        throw new IllegalArgumentException("uh oh");
    }

    public int getOpCode(int curPos) {
        String op = StringUtils.leftPad(String.valueOf(curPos), 5, "0");
        return Integer.parseInt(StringUtils.substring(op, 4, 5));
    }

    public boolean isImmediate(int curPos, int parameter) {
        String op = StringUtils.leftPad(String.valueOf(curPos), 5, "0");
        String modes = StringUtils.substring(op, 0, 3);

        String reverse = StringUtils.reverse(modes);

        String mode = StringUtils.substring(reverse, parameter - 1, parameter);

        return mode.equals("1");

    }

}
