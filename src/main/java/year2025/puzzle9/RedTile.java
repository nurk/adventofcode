package year2025.puzzle9;

import lombok.Getter;

public class RedTile {
    @Getter
    private final long column, row;

    public RedTile(String line) {
        String[] split = line.split(",");
        this.column = Long.parseLong(split[0]);
        this.row = Long.parseLong(split[1]);
    }

    public long areaBetween(RedTile other) {
        return (Math.abs(this.column - other.column) + 1) * (Math.abs(this.row - other.row) + 1);
    }

    @Override
    public String toString() {
        return "RedTile{" +
                "x=" + column +
                ", y=" + row +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RedTile redTile = (RedTile) o;
        return column == redTile.column && row == redTile.row;
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(column);
        result = 31 * result + Long.hashCode(row);
        return result;
    }
}
