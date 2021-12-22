package year2021.puzzle22;

import org.apache.commons.lang3.tuple.Pair;

class Volume {
    private final Pair<Integer, Integer> x, y, z;
    private final boolean on;

    Volume(Pair<Integer, Integer> x,
           Pair<Integer, Integer> y,
           Pair<Integer, Integer> z,
           boolean on) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.on = on;
    }

    public boolean intersects(Volume otherVolume) {
        return overlappingPair(x, otherVolume.x) && overlappingPair(y, otherVolume.y) && overlappingPair(z,
                otherVolume.z);
    }

    public Volume getIntersection(Volume otherVolume, boolean intersectionState) {
        return new Volume(
                Pair.of(Math.max(x.getLeft(), otherVolume.x.getLeft()),
                        Math.min(x.getRight(), otherVolume.x.getRight())),
                Pair.of(Math.max(y.getLeft(), otherVolume.y.getLeft()),
                        Math.min(y.getRight(), otherVolume.y.getRight())),
                Pair.of(Math.max(z.getLeft(), otherVolume.z.getLeft()),
                        Math.min(z.getRight(), otherVolume.z.getRight())),
                intersectionState);
    }

    public long getVolume() {
        return (Math.abs(x.getRight() - x.getLeft()) + 1L) * (Math.abs(y.getRight() - y.getLeft()) + 1L) * (Math.abs((z.getRight() - z.getLeft())) + 1L) * (on ? 1 : -1);
    }

    private static boolean overlappingPair(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
        if (b.getLeft() > a.getRight()) {
            return false;
        }
        if (b.getRight() < a.getLeft()) {
            return false;
        }
        return true;
    }

    public boolean isOn() {
        return on;
    }
}
