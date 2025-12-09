package year2025.puzzle8;

import lombok.Getter;

public class JunctionBoxPair {
    private static final JunctionBox ZERO_BOX = new JunctionBox("0,0,0");
    @Getter
    private final JunctionBox box1;
    @Getter
    private final JunctionBox box2;
    @Getter
    private final Double distance;

    public JunctionBoxPair(JunctionBox box1, JunctionBox box2, double distance) {
        if(ZERO_BOX.computeDistance(box1) < ZERO_BOX.computeDistance(box2)) {
            this.box1 = box1;
            this.box2 = box2;
        } else {
            this.box1 = box2;
            this.box2 = box1;
        }
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JunctionBoxPair that = (JunctionBoxPair) o;
        return box1.equals(that.box1) && box2.equals(that.box2) && distance.equals(that.distance);
    }

    @Override
    public int hashCode() {
        int result = box1.hashCode();
        result = 31 * result + box2.hashCode();
        result = 31 * result + distance.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "JunctionBoxPair{" +
                "box1=" + box1 +
                ", box2=" + box2 +
                ", distance=" + distance +
                '}';
    }
}
