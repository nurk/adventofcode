package year2022.puzzle17;

import util.Utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Puzzle17 {
    static List<Point> occupied = new ArrayList<>();
    static List<String> gusts = new CircularList<>();
    static int gustIndex = 0;
    static int shapeIndex = 0;

    public static void main(String[] args) {
        List<Shape> shapes = new CircularList<>(List.of(
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

        while (shapeIndex < 2022) {
            int maxY = occupied.stream().max(Comparator.comparing(o -> o.y)).orElseThrow().y;
            Shape shape = shapes.get(shapeIndex).clone(maxY + 4);
            shapeIndex++;
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
                gustIndex++;
                if (shape.canMoveDown()) {
                    shape.moveDown();
                } else {
                    occupied.addAll(shape.points);
                    atEnd = true;
                }
            }
        }

        System.out.println(occupied.stream().max(Comparator.comparing(o -> o.y)).orElseThrow().y);
    }

    static class Point {
        int x, y;

        public Point(int x, int y) {
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

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

    static class Shape {
        List<Point> points;

        public Shape(List<Point> points) {
            this.points = points;
        }

        public Shape clone(int yIncrement) {
            return new Shape(points.stream().map(p -> new Point(p.x, p.y + yIncrement)).toList());
        }

        public boolean canMoveLeft() {
            Point minX = points.stream()
                    .min(Comparator.comparingInt(o -> o.x))
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
                    .max(Comparator.comparingInt(o -> o.x))
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

    static void printOccupied() {
        System.out.println(Utils.reverseRange(0,
                        occupied.stream().max(Comparator.comparing(o -> o.y)).orElseThrow().y + 1)
                .boxed()
                .map(y -> IntStream.range(0, 7)
                        .boxed()
                        .map(x -> occupied.contains(new Point(x, y)) ? "#" : ".")
                        .collect(Collectors.joining())
                )
                .collect(Collectors.joining("\n")));
    }

    private static class CircularList<T> extends ArrayList<T> {

        public CircularList(int initialCapacity) {
            super(initialCapacity);
        }

        public CircularList() {
        }

        public CircularList(Collection<? extends T> c) {
            super(c);
        }

        @Override
        public T get(int index) {
            return super.get(index % size());
        }

        @Override
        public T set(int index, T element) {
            return super.set(index % size(), element);
        }
    }
}
