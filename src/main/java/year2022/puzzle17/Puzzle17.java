package year2022.puzzle17;

import util.Utils;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.IntStream;

public class Puzzle17 {
    static SortedSet<Point> occupied = new TreeSet<>();
    static CircularList<String> gusts = new CircularList<>();
    static CircularList<Shape> shapes;
    static int gustIndex = 0;
    static long shapeIndex = 0;
    static long lastCullingRow = 0;
    static NumberFormat formatter = NumberFormat.getInstance(Locale.forLanguageTag("nl-BE"));

    public static void main(String[] args) {
        System.out.println(formatter.format(1000000000000L));
        System.out.println(formatter.format(Long.MAX_VALUE));
        // partA 3149
        // partB 1554593222610 too high <-- weird experiment with modulos
        shapes = new CircularList<>(List.of(
                new Shape(List.of(new Point(2, 0), new Point(3, 0), new Point(4, 0), new Point(5, 0))),
                new Shape(List.of(new Point(3, 0), new Point(2, 1), new Point(3, 1), new Point(4, 1), new Point(3, 2))),
                new Shape(List.of(new Point(2, 0), new Point(3, 0), new Point(4, 0), new Point(4, 1), new Point(4, 2))),
                new Shape(List.of(new Point(2, 0), new Point(2, 1), new Point(2, 2), new Point(2, 3))),
                new Shape(List.of(new Point(2, 0), new Point(3, 0), new Point(2, 1), new Point(3, 1)))
        ));

        gusts = new CircularList<>(Utils.getInput("2022/input17.txt", s -> Arrays.stream(s.split("")).toList())
                .get(0));
        IntStream.range(0, 7)
                .forEach(i -> occupied.add(new Point(i, 0)));

        while (shapeIndex < 1000000000000L) {
            long maxY = occupied.last().y;
            Shape shape = shapes.getModulo(shapeIndex).clone(maxY + 4);
            shapeIndex++;
            boolean atEnd = false;
            while (!atEnd) {
                if (gusts.getModulo(gustIndex).equals("<")) {
                    if (shape.canMoveLeft()) {
                        shape.moveLeft();
                    }
                } else {
                    if (shape.canMoveRight()) {
                        shape.moveRight();
                    }
                }
                gustIndex++;
                if (shape.canMoveDown()) {
                    shape.moveDown();
                } else {
                    occupied.addAll(shape.points);
                    atEnd = true;
                }
            }
            doCulling();
            if (shapeIndex % 100000L == 0) {
                System.out.println("culled row " + formatter.format(lastCullingRow) + " shapeIndex: " + formatter.format(
                        shapeIndex) + " occupiedSize: " + formatter.format(occupied.size()));
            }

            if (shapeIndex == 2022L) {
                System.out.println("PartA: " + occupied.stream().max(Comparator.comparing(o -> o.y)).orElseThrow().y);
            }
        }
        System.out.println("PartB: " + occupied.stream().max(Comparator.comparing(o -> o.y)).orElseThrow().y);
    }

    private static void doCulling() {
        long maxY = occupied.stream().max(Comparator.comparing(o -> o.y)).orElseThrow().y;
        for (long i = maxY; i > lastCullingRow; i--) {
            if ((shapeIndex % shapes.size() == 0
                    && gustIndex % gusts.size() == 0
                    && occupied.contains(new Point(0, i)))
                    && (occupied.contains(new Point(1, i)))
                    && (occupied.contains(new Point(2, i)))
                    && (occupied.contains(new Point(3, i)))
                    && (occupied.contains(new Point(4, i)))
                    && (occupied.contains(new Point(5, i)))
                    && (occupied.contains(new Point(6, i)))) {
                // I do not seem to be hitting this.  Probably need to detect cycles
                System.out.println("SPECIAL CULLLING ROW");
                System.out.println("shapeIndex: " + shapeIndex);
                System.out.println("culling row: " + i);
                System.out.println("height at culling row " + occupied.stream()
                        .max(Comparator.comparing(o -> o.y))
                        .orElseThrow().y);
            }
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
            if (o == null || getClass() != o.getClass()) {
                return false;
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
        public int compareTo(Point o) {
            return this.y != o.y ? Long.compare(y, o.y) : Long.compare(x, o.x);
        }
    }

    static class Shape {
        List<Point> points;

        public Shape(List<Point> points) {
            this.points = points;
        }

        public Shape clone(long yIncrement) {
            return new Shape(points.stream().map(p -> new Point(p.x, p.y + yIncrement)).toList());
        }

        public boolean canMoveLeft() {
            Point minX = points.stream()
                    .min(Comparator.comparingLong(o -> o.x))
                    .orElseThrow();

            if (minX.x - 1 < 0) {
                return false;
            }
            return points.stream()
                    .map(p -> new Point(p.x - 1, p.y))
                    .noneMatch(p -> occupied.contains(p));
        }

        public boolean canMoveRight() {
            Point maxX = points.stream()
                    .max(Comparator.comparingLong(o -> o.x))
                    .orElseThrow();

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

    static class CircularList<T> extends ArrayList<T> {
        public CircularList() {
        }

        public CircularList(Collection<? extends T> c) {
            super(c);
        }

        public T getModulo(long index) {
            return super.get((int) (index % size()));
        }

        @Override
        public T set(int index, T element) {
            return super.set(index % size(), element);
        }
    }
}
