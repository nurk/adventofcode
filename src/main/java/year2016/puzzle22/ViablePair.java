package year2016.puzzle22;

public class ViablePair {
    private final Node nodeA;
    private final Node nodeB;

    public ViablePair(Node nodeA, Node nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ViablePair that = (ViablePair) o;
        return nodeA.equals(that.nodeA) && nodeB.equals(that.nodeB);
    }

    @Override
    public int hashCode() {
        int result = nodeA.hashCode();
        result = 31 * result + nodeB.hashCode();
        return result;
    }
}
