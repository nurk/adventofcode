package year2022.puzzle17;

import util.Utils;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Puzzle17 {
    static List<Point> occupied = new ArrayList<>();
    static CircularList<String> gusts = new CircularList<>();
    static int gustIndex = 0;
    static long shapeIndex = 0;
    static long lastCullingRow = 0;
    static NumberFormat formatter = NumberFormat.getInstance(Locale.forLanguageTag("nl-BE"));

    public static void main(String[] args) {
        System.out.println(formatter.format(1000000000000L));
        System.out.println(formatter.format(Long.MAX_VALUE));
        // partA 3149
        CircularList<Shape> shapes = new CircularList<>(List.of(
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

        // 1.000.000.000.000
        while (shapeIndex < 1000000000000L) {
            //while (shapeIndex < 2022L) {
            long maxY = occupied.stream().max(Comparator.comparingLong(o -> o.y)).orElseThrow().y;
            Shape shape = shapes.getModulod(shapeIndex).clone(maxY + 4);
            shapeIndex++;
            boolean atEnd = false;
            while (!atEnd) {
                if (gusts.getModulod(gustIndex).equals("<")) {
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
                        shapeIndex));
            }
        }
        System.out.println(occupied.stream().max(Comparator.comparing(o -> o.y)).orElseThrow().y);
    }

    private static void doCulling() {
        long maxY = occupied.stream().max(Comparator.comparing(o -> o.y)).orElseThrow().y;
        for (long i = maxY; i > lastCullingRow; i--) {
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

    static class Point {
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
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
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

        public void moveLeft() {
            points.forEach(Point::moveLeft);
        }

        public void moveRight() {
            points.forEach(Point::moveRight);
        }

        public void moveDown() {
            points.forEach(Point::moveDown);
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

        @Override
        public String toString() {
            return "Shape{" +
                    "points=" + points.stream().map(Point::toString).collect(Collectors.joining("\n")) +
                    '}';
        }
    }

/*    static void printOccupied() {
        System.out.println(Utils.reverseRange(0,
                        occupied.stream().max(Comparator.comparing(o -> o.y)).orElseThrow().y + 1)
                .boxed()
                .map(y -> IntStream.range(0, 7)
                        .boxed()
                        .map(x -> occupied.contains(new Point(x, y)) ? "#" : ".")
                        .collect(Collectors.joining())
                )
                .collect(Collectors.joining("\n")));
    }*/

    private static class CircularList<T> extends ArrayList<T> {
        public CircularList() {
        }

        public CircularList(Collection<? extends T> c) {
            super(c);
        }

        public T getModulod(long index) {
            return super.get((int) (index % size()));
        }

        @Override
        public T set(int index, T element) {
            return super.set(index % size(), element);
        }
    }
}
