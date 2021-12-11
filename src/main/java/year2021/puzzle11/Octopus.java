package year2021.puzzle11;

class Octopus {
    private int level;
    private final int row;
    private final int col;

    Octopus(int level, int row, int col) {
        this.level = level;
        this.row = row;
        this.col = col;
    }

    public Octopus incrementLevel() {
        level++;
        return this;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean flashing() {
        return level > 9;
    }

    @Override
    public String toString() {
        return level + "";
    }

    public void resetLevel() {
        if (flashing()) {
            level = 0;
        }
    }
}
