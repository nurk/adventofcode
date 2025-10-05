package year2022.puzzle17;

import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.time.StopWatch;
import util.Utils;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Puzzle17 {
    static SortedSet<Point> occupied = new TreeSet<>(yComparator());

    static List<String> gusts;
    static List<Shape> shapes;
    static int gustIndex = 0;
    static int shapeIndex = 0;
    static long lastCullingRow = 0;
    static NumberFormat formatter = NumberFormat.getInstance(Locale.forLanguageTag("nl-BE"));

    public static void main(String[] args) {
        // partA 3149
        // partB 1554593222610 too high <-- weird experiment with modulos
        shapes = List.of(
                new Shape(new TreeSet<>(Set.of(new Point(2, 0), new Point(3, 0), new Point(4, 0), new Point(5, 0)))),
                new Shape(new TreeSet<>(Set.of(new Point(3, 0),
                        new Point(2, 1),
                        new Point(3, 1),
                        new Point(4, 1),
                        new Point(3, 2)))),
                new Shape(new TreeSet<>(Set.of(new Point(2, 0),
                        new Point(3, 0),
                        new Point(4, 0),
                        new Point(4, 1),
                        new Point(4, 2)))),
                new Shape(new TreeSet<>(Set.of(new Point(2, 0), new Point(2, 1), new Point(2, 2), new Point(2, 3)))),
                new Shape(new TreeSet<>(Set.of(new Point(2, 0), new Point(3, 0), new Point(2, 1), new Point(3, 1))))
        );

        gusts = Utils.getInput("2022/input17.txt", s -> Arrays.stream(s.split("")).toList())
                .get(0);
        IntStream.range(0, 7)
                .forEach(i -> occupied.add(new Point(i, 0)));

        StopWatch stopWatch = StopWatch.createStarted();
        long counter = 0;
        //1.000.000.000.000L
        while (counter < 1000000000000L) {
            counter++;
            long maxY = occupied.last().y;
            Shape shape = shapes.get(shapeIndex).clone(maxY + 4);
            shapeIndex = (shapeIndex + 1) % shapes.size();
            boolean atEnd = false;
            while (!atEnd) {
                if (gusts.get(gustIndex).equals("<")) {
                    if (shape.canMoveLeft()) {
                        shape.moveLeft();
                    }
                } else {
                    if (shape.canMoveRight()) {
                        shape.moveRight();
                    }
                }
                gustIndex = (gustIndex + 1) % gusts.size();
                if (shape.canMoveDown()) {
                    shape.moveDown();
                } else {
                    occupied.addAll(shape.points);
                    atEnd = true;
                }
            }
            doCulling();
            if (counter % 1000000L == 0) {
                stopWatch.split();
                System.out.println(stopWatch.formatSplitTime() + " culled row " + formatter.format(lastCullingRow) + " counter: " + formatter.format(
                        counter) + " occupiedSize: " + formatter.format(occupied.size()));
            }

            if (counter == 2022L) {
                System.out.println("PartA: " + occupied.last().y);
            }
        }
        System.out.println("PartB: " + occupied.last().y);
    }

    private static void doCulling() {
        long maxY = occupied.last().y;
        for (long i = maxY; i > lastCullingRow + 2; i--) {
            if ((occupied.contains(new Point(0, i)) || occupied.contains(new Point(0, i - 1)))
                    && (occupied.contains(new Point(1, i)) || occupied.contains(new Point(1, i - 1)))
                    && (occupied.contains(new Point(2, i)) || occupied.contains(new Point(2, i - 1)))
                    && (occupied.contains(new Point(3, i)) || occupied.contains(new Point(3, i - 1)))
                    && (occupied.contains(new Point(4, i)) || occupied.contains(new Point(4, i - 1)))
                    && (occupied.contains(new Point(5, i)) || occupied.contains(new Point(5, i - 1)))
                    && (occupied.contains(new Point(6, i)) || occupied.contains(new Point(6, i - 1)))) {
                lastCullingRow = i - 1;
                occupied.removeIf(p -> p.y < lastCullingRow);
                break;
            }
        }
    }

    static class Point implements Comparable<Point> {
        long x, y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        void moveLeft() {
            x--;
        }

        void moveRight() {
            x++;
        }

        void moveDown() {
            y--;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            Point point = (Point) o;

            if (x != point.x) {
                return false;
            }
            return y == point.y;
        }

        @Override
        public int hashCode() {
            int result = (int) (x ^ (x >>> 32));
            result = 31 * result + (int) (y ^ (y >>> 32));
            return result;
        }

        @Override
        public int compareTo(@Nonnull Point o) {
            return xComparator().compare(this, o);
        }
    }

    static class Shape {
        SortedSet<Point> points;

        public Shape(SortedSet<Point> points) {
            this.points = points;
        }

        public Shape clone(long yIncrement) {
            return new Shape(points.stream()
                    .map(p -> new Point(p.x, p.y + yIncrement))
                    .collect(Collectors.toCollection(TreeSet::new)));
        }

        public boolean canMoveLeft() {
            Point minX = points.first();

            if (minX.x - 1 < 0) {
                return false;
            }
            return points.stream()
                    .map(p -> new Point(p.x - 1, p.y))
                    .noneMatch(p -> occupied.contains(p));
        }

        public boolean canMoveRight() {
            Point maxX = points.last();

            if (maxX.x + 1 > 6) {
                return false;
            }
            return points.stream()
                    .map(p -> new Point(p.x + 1, p.y))
                    .noneMatch(p -> occupied.contains(p));
        }

        public boolean canMoveDown() {
            return points.stream()
                    .map(p -> new Point(p.x, p.y - 1))
                    .noneMatch(p -> occupied.contains(p));
        }

        public void moveLeft() {
            points.forEach(Point::moveLeft);
        }

        public void moveRight() {
            points.forEach(Point::moveRight);
        }

        public void moveDown() {
            points.forEach(Point::moveDown);
        }

    }

    private static Comparator<Point> xComparator() {
        return (o1, o2) -> o1.x != o2.x ? Long.compare(o1.x,
                o2.x) : Long.compare(o1.y, o2.y);
    }

    private static Comparator<Point> yComparator() {
        return (o1, o2) -> o1.y != o2.y ? Long.compare(o1.y, o2.y) : Long.compare(o1.x, o2.x);
    }
}
