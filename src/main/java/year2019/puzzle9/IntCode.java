package year2019.puzzle9;

import org.apache.commons.lang3.StringUtils;

public class IntCode {

    private final int input;
    private long[] integers;
    private int currentPosition = 0;
    private long result;
    private long relativeBase;

    public IntCode(long[] integers, int input) {
        this.integers = integers;
        this.input = input;
    }

    public void run() {
        while (!runOperation()) {

        }

        System.out.println(result);
    }

    private boolean runOperation() {
        long curPos = integers[currentPosition];
        int opCode = getOpCode(curPos);

        if (opCode == 1) {
            int pos1 = getPosition(curPos, 1);
            int pos2 = getPosition(curPos, 2);
            int pos3 = getPosition(curPos, 3);
            integers[pos3] = integers[pos1] + integers[pos2];
            currentPosition += 4;
            return false;
        } else if (opCode == 2) {
            int pos1 = getPosition(curPos, 1);
            int pos2 = getPosition(curPos, 2);
            int pos3 = getPosition(curPos, 3);
            integers[pos3] = integers[pos1] * integers[pos2];
            currentPosition += 4;
            return false;
        } else if (opCode == 3) {
            int pos1 = getPosition(curPos, 1);
            integers[pos1] = input;
            currentPosition += 2;
            return false;
        } else if (opCode == 4) {
            int pos1 = getPosition(curPos, 1);
            this.result = integers[pos1];
            currentPosition += 2;
            return this.result != 0;
        } else if (opCode == 5) {
            int pos1 = getPosition(curPos, 1);
            if (integers[pos1] != 0) {
                int pos2 = getPosition(curPos, 2);
                currentPosition = (int) integers[pos2];
                return false;
            }
            currentPosition += 3;
            return false;
        } else if (opCode == 6) {
            int pos1 = getPosition(curPos, 1);
            if (integers[pos1] == 0) {
                int pos2 = getPosition(curPos, 2);
                currentPosition = (int) integers[pos2];
                return false;
            }
            currentPosition += 3;
            return false;
        } else if (opCode == 7) {
            int pos1 = getPosition(curPos, 1);
            int pos2 = getPosition(curPos, 2);
            int pos3 = getPosition(curPos, 3);
            if (integers[pos1] < integers[pos2]) {
                integers[pos3] = 1;
            } else {
                integers[pos3] = 0;
            }
            currentPosition += 4;
            return false;
        } else if (opCode == 8) {
            int pos1 = getPosition(curPos, 1);
            int pos2 = getPosition(curPos, 2);
            int pos3 = getPosition(curPos, 3);
            if (integers[pos1] == integers[pos2]) {
                integers[pos3] = 1;
            } else {
                integers[pos3] = 0;
            }
            currentPosition += 4;
            return false;
        } else if (opCode == 9) {
            int pos1 = getPosition(curPos, 1);
            relativeBase = relativeBase + integers[pos1];
            currentPosition += 2;
            return false;
        } else if (opCode == 99) {
            return true;
        }

        throw new IllegalArgumentException("uh oh");
    }

    private int getPosition(long curPos, int param) {
        if (isImmediate(curPos, param)) {
            return currentPosition + param;
        }
        if (isRelative(curPos, param)) {
            return (int) (relativeBase + integers[currentPosition + param]);
        }

        return (int) integers[currentPosition + param];
    }

    public int getOpCode(long curPos) {
        String op = StringUtils.leftPad(String.valueOf(curPos), 5, "0");
        if (StringUtils.endsWith(op, "99")) {
            return 99;
        }
        return Integer.parseInt(StringUtils.substring(op, 4, 5));
    }

    public boolean isImmediate(long curPos, int parameter) {
        return getMode(curPos, parameter).equals("1");
    }

    public boolean isRelative(long curPos, int parameter) {
        return getMode(curPos, parameter).equals("2");
    }

    private String getMode(long curPos, int parameter) {
        String op = StringUtils.leftPad(String.valueOf(curPos), 5, "0");
        String modes = StringUtils.substring(op, 0, 3);

        String reverse = StringUtils.reverse(modes);

        return StringUtils.substring(reverse, parameter - 1, parameter);
    }

    public long getResult() {
        return result;
    }
}
