package year2025.puzzle5;

public class IngredientRange implements Comparable<IngredientRange> {
    private final long min;
    private final long max;

    public IngredientRange(String line) {
        String[] parts = line.split("-");
        this.min = Long.parseLong(parts[0]);
        this.max = Long.parseLong(parts[1]);
    }

    public IngredientRange(long min, long max) {
        this.min = min;
        this.max = max;
    }

    public boolean isInRange(long value) {
        return value >= min && value <= max;
    }

    @Override
    public String toString() {
        return "IngredientRange{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }

    @Override
    public int compareTo(IngredientRange o) {
        return Long.compare(this.min, o.min);
    }

    public boolean overlapsWith(IngredientRange other) {
        return this.min >= other.min && this.min <= other.max ||
                other.min >= this.min && other.min <= this.max;
    }

    public IngredientRange mergeWith(IngredientRange other) {
        long newMin = Math.min(this.min, other.min);
        long newMax = Math.max(this.max, other.max);
        return new IngredientRange(newMin, newMax);
    }

    public long numberOfIngredients() {
        return max - min + 1;
    }
}
