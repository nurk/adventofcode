package year2021.puzzle9;

class Point {
    private final int height;
    private final int row;
    private final int col;
    private boolean isLowPoint;

    Point(int height, int row, int col) {
        this.height = height;
        this.row = row;
        this.col = col;
    }

    public int getHeight() {
        return height;
    }

    public boolean isLowPoint() {
        return isLowPoint;
    }

    public void setLowPoint(boolean lowPoint) {
        isLowPoint = lowPoint;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return height + " (" + isLowPoint + ")";
    }
}
