package year2021.puzzle9;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

class Board {
    private final Point[][] board;

    public Board(List<String> input) {
        board = new Point[input.size()][input.get(0).length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> board[row][column] = new Point(Integer.parseInt(columns.get(column)),
                                    row,
                                    column));
                });
    }

    public int threeLargestBasinsMultiplied() {
        List<Integer> basinSizesSorted = Arrays.stream(board)
                .flatMap(Arrays::stream)
                .filter(Point::isLowPoint)
                .map(p -> {
                    Set<Point> basinPoints = new HashSet<>();
                    buildBasin(p, basinPoints);
                    return basinPoints.size();
                })
                .sorted(Comparator.reverseOrder())
                .toList();
        return basinSizesSorted.get(0) * basinSizesSorted.get(1) * basinSizesSorted.get(2);
    }

    private void buildBasin(Point point, Set<Point> basin) {
        basin.add(point);
        getAdjacentBasinPoints(point).forEach(p -> {
            if (!basin.contains(p)) {
                buildBasin(p, basin);
            }
        });
    }

    private List<Point> getAdjacentBasinPoints(Point point) {
        List<Point> points = new ArrayList<>();
        getPoint(point.getRow() + 1, point.getCol()).filter(p -> p.getHeight() != 9).ifPresent(points::add);
        getPoint(point.getRow() - 1, point.getCol()).filter(p -> p.getHeight() != 9).ifPresent(points::add);
        getPoint(point.getRow(), point.getCol() + 1).filter(p -> p.getHeight() != 9).ifPresent(points::add);
        getPoint(point.getRow(), point.getCol() - 1).filter(p -> p.getHeight() != 9).ifPresent(points::add);
        return points;
    }

    private Optional<Point> getPoint(int row,
                                     int col) {
        try {
            return Optional.ofNullable(board[row][col]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    public int getRiskLevel() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .filter(Point::isLowPoint)
                .map(p -> p.getHeight() + 1)
                .mapToInt(i -> i)
                .sum();
    }

    public void markLowPoints() {
        Arrays.stream(board)
                .flatMap(Arrays::stream)
                .filter(this::isLowPoint)
                .forEach(p -> p.setLowPoint(true));
    }

    private boolean isLowPoint(Point p) {
        return board[p.getRow()][p.getCol()].getHeight() < Stream.of(getPoint(p.getRow() - 1, p.getCol()),
                        getPoint(p.getRow() + 1, p.getCol()),
                        getPoint(p.getRow(), p.getCol() - 1),
                        getPoint(p.getRow(), p.getCol() + 1))
                .flatMap(Optional::stream)
                .map(Point::getHeight)
                .mapToInt(i -> i)
                .min()
                .orElseThrow();
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> Arrays.stream(points)
                        .map(Point::toString)
                        .collect(joining(" ")))
                .collect(joining("\n"));
    }
}
