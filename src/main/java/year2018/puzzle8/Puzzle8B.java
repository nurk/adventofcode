package year2018.puzzle8;

import util.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Puzzle8B {
    static void main() throws IOException {
        List<Integer> input = Utils.getInput("2018/input8.txt", (s) -> s.split(" "))
                .stream()
                .flatMap(Arrays::stream)
                .map(Integer::parseInt)
                .toList();

        AtomicInteger idx = new AtomicInteger(0);
        long value = parseNodeValue(input, idx);
        System.out.println("root node value = " + value);
    }

    private static long parseNodeValue(List<Integer> input, AtomicInteger idx) {
        int childCount = input.get(idx.getAndIncrement());
        int metaCount = input.get(idx.getAndIncrement());

        long[] childValues = new long[childCount];
        for (int i = 0; i < childCount; i++) {
            childValues[i] = parseNodeValue(input, idx);
        }

        long value = 0;
        for (int i = 0; i < metaCount; i++) {
            int m = input.get(idx.getAndIncrement());
            if (childCount == 0) {
                value += m;
            } else {
                if (m >= 1 && m <= childCount) {
                    value += childValues[m - 1];
                }
            }
        }

        return value;
    }
}

