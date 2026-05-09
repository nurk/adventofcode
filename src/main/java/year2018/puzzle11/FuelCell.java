package year2018.puzzle11;

import lombok.Getter;

public class FuelCell {
    private final int x, y;
    @Getter
    private final int level;

    public FuelCell(int x, int y, int serialNumber) {
        this.x = x;
        this.y = y;

        long rackId = x + 10L;
        long tmpLevel = rackId * y;
        tmpLevel += serialNumber;
        tmpLevel *= rackId;
        tmpLevel = (tmpLevel / 100) % 10;
        this.level = (int) tmpLevel - 5;
    }

    @Override
    public String toString() {
        return "FuelCell{" +
                "x=" + x +
                ", y=" + y +
                ", level=" + level +
                '}';
    }
}
