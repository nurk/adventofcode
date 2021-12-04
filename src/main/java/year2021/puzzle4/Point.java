package year2021.puzzle4;

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
        return String.valueOf(number) + " " + marked;
    }
}

