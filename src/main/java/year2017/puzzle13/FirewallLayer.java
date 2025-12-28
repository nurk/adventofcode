package year2017.puzzle13;

import lombok.Getter;

public class FirewallLayer {
    @Getter
    private final Integer depth;
    @Getter
    private final int range;
    @Getter
    private int scannerPosition = 1;
    private int direction = 1;

    public FirewallLayer(int depth, int range) {
        this.depth = depth;
        this.range = range;
    }

    public void tick() {
        if (range == 0) {
            return;
        }
        scannerPosition += direction;
        if (scannerPosition == range || scannerPosition == 1) {
            direction *= -1;
        }
    }

    public void initialTicks(int ticks) {
        if (ticks <= 0 || range <= 1) {
            // range==0 never moves; range==1 would be a degenerate scanner always at position 1
            return;
        }

        // Scanner repeats with period 2*(range-1): 1 -> ... -> range -> ... -> 1
        int cycleLength = 2 * (range - 1);

        // Offset into the repeating cycle: 0..cycleLength-1
        int cycleOffset = Math.floorMod(ticks, cycleLength);

        // Think of the motion as moving along 0..(range-1) and reflecting at the end.
        int zeroBasedPosition = cycleOffset <= (range - 1)
                ? cycleOffset
                : cycleLength - cycleOffset; // reflected position in 0..range-1

        scannerPosition = zeroBasedPosition + 1; // convert back to 1-based position

        // Direction after advancing 'ticks':
        // +1 while moving away from position 1, -1 while moving back toward position 1.
        if (cycleOffset == 0 || cycleOffset == cycleLength) {
            direction = 1;
        } else if (cycleOffset == (range - 1)) {
            direction = -1;
        } else {
            direction = (cycleOffset < (range - 1)) ? 1 : -1;
        }
    }

    public void reset() {
        scannerPosition = 1;
        direction = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FirewallLayer that = (FirewallLayer) o;
        return depth.equals(that.depth);
    }

    @Override
    public int hashCode() {
        return depth.hashCode();
    }
}
