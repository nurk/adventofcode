package year2021.puzzle5;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

class Board {
    private final int[][] places = new int[1000][1000];

    public void drawPartA(Pair<IntegerPair, IntegerPair> pair) {
        if (pair.getLeft().x() == pair.getRight().x()) {
            toRange(pair.getLeft().y(), pair.getRight().y())
                    .forEach(i -> places[pair.getLeft().x()][i]++);
        } else if (pair.getLeft().y() == pair.getRight().y()) {
            toRange(pair.getLeft().x(), pair.getRight().x())
                    .forEach(i -> places[i][pair.getLeft().y()]++);
        }
    }

    public void drawPartB(Pair<IntegerPair, IntegerPair> pair) {
        if (pair.getLeft().x() == pair.getRight().x() || pair.getLeft().y() == pair.getRight().y()) {
            drawPartA(pair);
        } else {
            List<Integer> xRange = toRange(pair.getLeft().x(), pair.getRight().x());
            List<Integer> yRange = toRange(pair.getLeft().y(), pair.getRight().y());
            IntStream.range(0, xRange.size())
                    .forEach(i -> places[xRange.get(i)][yRange.get(i)]++);
        }
    }

    public int largerThan2() {
        return Arrays.stream(places)
                .flatMapToInt(Arrays::stream)
                .map(i -> i >= 2 ? 1 : 0)
                .sum();
    }

    private List<Integer> toRange(int start, int end) {
        if (start > end) {
            return IntStream.rangeClosed(end, start)
                    .boxed()
                    .sorted(Collections.reverseOrder())
                    .toList();
        }
        return IntStream.rangeClosed(start, end).boxed().toList();
    }
}
