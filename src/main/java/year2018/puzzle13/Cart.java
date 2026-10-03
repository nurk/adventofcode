package year2018.puzzle13;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Cart implements Comparable<Cart> {

    private int row;
    private int column;
    private Heading heading;
    private NextIntersectionHeading nextIntersectionHeading = NextIntersectionHeading.LEFT;

    public Cart(int row, int column, Heading heading) {
        this.row = row;
        this.column = column;
        this.heading = heading;
    }

    @Override
    public int compareTo(Cart o) {
        return this.row != o.row ? Integer.compare(this.row, o.row) : Integer.compare(this.column,
                o.column);
    }

    public void takeIntersection() {
        switch (nextIntersectionHeading) {
            case LEFT -> heading = heading.getLeft();
            case STRAIGHT -> heading = heading.getStraight();
            case RIGHT -> heading = heading.getRight();
        }
        nextIntersectionHeading = nextIntersectionHeading.getNext();
    }

    private enum NextIntersectionHeading {
        LEFT,
        STRAIGHT,
        RIGHT;

        public NextIntersectionHeading getNext() {
            return switch (this) {
                case LEFT -> STRAIGHT;
                case STRAIGHT -> RIGHT;
                case RIGHT -> LEFT;
            };
        }
    }

    public void move() {
        heading.move(this);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "row=" + row +
                ", column=" + column +
                ", heading=" + heading +
                ", nextIntersectionHeading=" + nextIntersectionHeading +
                '}';
    }
}
