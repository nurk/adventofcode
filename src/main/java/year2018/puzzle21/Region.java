package year2018.puzzle21;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

public class Region {
    private final int x;
    private final int y;
    @Getter
    private final int riskLevel;
    @Getter
    private final int erosionLevel;

    public Region(int x, int y, int erosionLevel) {
        this.x = x;
        this.y = y;
        this.erosionLevel = erosionLevel;
        this.riskLevel = erosionLevel % 3;
    }

    public Pair<Integer, Integer> getCoordinates() {
        return Pair.of(x, y);
    }
}
