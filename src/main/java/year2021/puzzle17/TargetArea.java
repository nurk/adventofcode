package year2021.puzzle17;

import lombok.Getter;

@Getter
public class TargetArea {
    private final int xMin;
    private final int xMax;
    private final int yMin;
    private final int yMax;

    public TargetArea(int xMin, int xMax, int yMin, int yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    public boolean isWithin(int x, int y) {
        return x >= xMin && x <= xMax && y >= yMin && y <= yMax;
    }

    public boolean hasOvershot(int x, int y) {
        return x > xMax || y < yMin;
    }
}
