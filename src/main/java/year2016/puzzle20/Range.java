package year2016.puzzle20;

import lombok.Getter;

public class Range {
    @Getter
    private final long start;
    @Getter
    private final long end;

    public Range(String line) {
        String[] parts = line.split("-");
        this.start = Long.parseLong(parts[0]);
        this.end = Long.parseLong(parts[1]);
    }

    public Range(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public boolean canBeMerged(Range other) {
        if (this.end + 1 == other.start || other.end + 1 == this.start) {
            return true;
        }
        return !(this.end < other.start || this.start > other.end);
    }

    public Range merge(Range other) {
        long newStart = Math.min(this.start, other.start);
        long newEnd = Math.max(this.end, other.end);

        return new Range(newStart, newEnd);
    }

    @Override
    public String toString() {
        return "Range{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
