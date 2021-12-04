package year2021.puzzle4;

import org.apache.commons.lang3.StringUtils;

public class Point {
    private final int number;
    private boolean marked = false;

    public Point(int number) {
        this.number = number;
    }

    public boolean isMarked() {
        return marked;
    }

    public int getNumber() {
        return number;
    }

    public void setMarked() {
        this.marked = true;
    }

    @Override
    public String toString() {
        return StringUtils.leftPad(String.valueOf(number), 2, " ");
    }
}

