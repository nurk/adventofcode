package year2021.puzzle4;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    private final List<List<Point>> points = new ArrayList<>();

    public Board(List<String> input) {
        input.stream()
                .filter(StringUtils::isNotBlank)
                .forEach(s -> points.add(Arrays.stream(StringUtils.split(s, " "))
                        .map(Integer::valueOf)
                        .map(Point::new)
                        .collect(Collectors.toList())));
    }

    public void mark(int i) {
        points.forEach(l -> l.stream()
                .filter(p -> p.getNumber() == i)
                .forEach(Point::setMarked));
    }

    public boolean isBingo() {
        boolean hasRow = points.stream()
                .anyMatch(l -> l.stream().allMatch(Point::isMarked));
        if (!hasRow) {
            return IntStream.range(0, 5).anyMatch(i -> points.stream().allMatch(l -> l.get(i).isMarked()));
        }
        return true;
    }

    public int getUnmarkedSum() {
        return points.stream()
                .flatMap(Collection::stream)
                .filter(p -> !p.isMarked())
                .mapToInt(Point::getNumber)
                .sum();
    }

    @Override
    public String toString() {
        return points.stream()
                .map(l -> l.stream().map(Point::toString).collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));
    }
}
