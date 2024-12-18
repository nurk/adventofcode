package year2024.puzzle12;

import lombok.Getter;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Getter
public class Region {
    private final List<Pair<Integer, Integer>> points = new ArrayList<>();
    private final String crop;

    public Region(String crop, Pair<Integer, Integer> point) {
        this.crop = crop;
        points.add(point);
    }

    public boolean touches(Pair<Integer, Integer> point) {
        return Arrays.stream(Direction.values())
                .anyMatch(direction -> points.contains(direction.getPosition(point)));
    }

    public boolean touches(Region region) {
        if (!region.getCrop().equals(crop)) {
            return false;
        }
        return region.getPoints().stream()
                .anyMatch(this::touches);
    }

    public void add(Pair<Integer, Integer> point) {
        points.add(point);
    }

    public void add(Region region) {
        this.points.addAll(region.points);
    }

    private long perimeter() {
        AtomicLong perimeter = new AtomicLong(0L);
        points.forEach(point ->
                perimeter.getAndAdd(4 - Arrays.stream(Direction.values())
                        .filter(direction -> points.contains(direction.getPosition(point)))
                        .count()));

        return perimeter.get();
    }

    private long sides() {
        AtomicLong sideCount = new AtomicLong(0L);

        List<Side> sides = new ArrayList<>();

        points.forEach(point -> {
            Pair<Integer, Integer> up = Direction.UP.getPosition(point);
            Pair<Integer, Integer> down = Direction.DOWN.getPosition(point);
            Pair<Integer, Integer> left = Direction.LEFT.getPosition(point);
            Pair<Integer, Integer> right = Direction.RIGHT.getPosition(point);

            if (!points.contains(up)) {
                sides.add(new Side(Direction.UP, point.getValue0(), point));
            }
            if (!points.contains(down)) {
                sides.add(new Side(Direction.DOWN, point.getValue0(), point));
            }
            if (!points.contains(left)) {
                sides.add(new Side(Direction.LEFT, point.getValue1(), point));
            }
            if (!points.contains(right)) {
                sides.add(new Side(Direction.RIGHT, point.getValue1(), point));
            }
        });

        sides.stream()
                .collect(Collectors.groupingBy(side -> side.direction))
                .forEach((direction, sidesPerDirection) -> sidesPerDirection
                        .stream()
                        .collect(Collectors.groupingBy(side -> side.level))
                        .forEach((_, sidesPerDirectionAndLevel) -> sideCount.addAndGet(getDistinctSides(direction,
                                sidesPerDirectionAndLevel))));

        return sideCount.get();
    }

    private long getDistinctSides(Direction direction, List<Side> sides) {
        List<Integer> s;
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            s = sides.stream()
                    .map(side -> side.point.getValue0())
                    .sorted()
                    .toList();
        } else {
            s = sides.stream()
                    .map(side -> side.point.getValue1())
                    .sorted()
                    .toList();
        }

        if (s.size() == 1) {
            return 1;
        }

        int numberOfSides = 1;
        int currentValue = s.getFirst();
        for (int i = 1; i < s.size(); i++) {
            if (s.get(i) != currentValue + 1) {
                numberOfSides++;
            }
            currentValue = s.get(i);
        }

        return numberOfSides;
    }

    public long priceA() {
        return perimeter() * points.size();
    }

    public long priceB() {
        return sides() * points.size();
    }

    @Override
    public String toString() {
        return "Region{" +
                "points=" + points.size() +
                ", crop='" + crop + '\'' +
                '}';
    }

    private record Side(Direction direction, int level, Pair<Integer, Integer> point) {
    }
}
