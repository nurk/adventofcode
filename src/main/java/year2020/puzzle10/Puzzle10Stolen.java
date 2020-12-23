package year2020.puzzle10;

import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

// https://github.com/benfff85/advent-of-code-2020/blob/master/src/main/java/com/adventofcode/day10/Controller.java
public class Puzzle10Stolen {

    private static final Map<Integer, Integer> differenceOccurrenceMap = new HashMap<>();
    private static final Map<Integer, Map<List<Integer>, Long>> cache = new HashMap<>();

    public static void main(String[] args) throws URISyntaxException, IOException {

        List<Integer> rawInput = new ArrayList<>();
        Files.readAllLines(Utils.getInputPath("2020/input10.txt")).forEach(
                line -> rawInput.add(Integer.parseInt(line))
        );
        List<Integer> input = rawInput.stream().sorted().collect(Collectors.toList());

        int difference;
        int previousNum = 0;
        for (Integer i : input) {
            difference = i - previousNum;
            trackOccurrencesOfDifferences(difference);
            previousNum = i;
        }
        trackOccurrencesOfDifferences(3);

        System.out.println("Product of adapters with diff 1 and adapters with diff 3: " + differenceOccurrenceMap.get(1) * differenceOccurrenceMap
                .get(3));
        System.out.println("Count of possible adapter configurations: " + getPossibleArrangements(0, new LinkedList<>(input)));

    }

    private static void trackOccurrencesOfDifferences(int difference) {
        differenceOccurrenceMap.put(difference, defaultIfNull(differenceOccurrenceMap.get(difference), 0) + 1);
    }

    private static Long getPossibleArrangements(int previous, List<Integer> remainingAdapters) {

        if (isEmpty(remainingAdapters)) {
            return 1L;
        }

        Long count = getFromCache(previous, remainingAdapters);
        if (nonNull(count)) {
            return count;
        }

        Integer nextAdapter = remainingAdapters.remove(0);
        count = getPossibleArrangements(nextAdapter, remainingAdapters);

        if (!remainingAdapters.isEmpty() && remainingAdapters.get(0) - previous <= 3) {
            count += getPossibleArrangements(previous, remainingAdapters);
        }
        remainingAdapters.add(0, nextAdapter);

        addToCache(previous, remainingAdapters, count);
        return count;
    }

    private static void addToCache(int previous, List<Integer> remainingAdapters, Long count) {
        Map<List<Integer>, Long> map = new HashMap<>();
        map.put(remainingAdapters, count);
        cache.put(previous, map);
    }

    private static Long getFromCache(int previous, List<Integer> remainingAdapters) {
        Map<List<Integer>, Long> map = cache.get(previous);
        return nonNull(map) ? map.get(remainingAdapters) : null;
    }
}
