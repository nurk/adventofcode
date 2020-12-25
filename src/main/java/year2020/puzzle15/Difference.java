package year2020.puzzle15;

public class Difference {
    private int lastSeen;
    private int secondToLastSeen = -1;

    public Difference(int turn) {
        this.lastSeen = turn;
    }

    public Difference seenAgainAt(int turn) {
        secondToLastSeen = lastSeen;
        lastSeen = turn;
        return this;
    }

    public int getDifference() {
        return lastSeen - secondToLastSeen;
    }

    public int getSecondToLastSeen() {
        return secondToLastSeen;
    }
}
