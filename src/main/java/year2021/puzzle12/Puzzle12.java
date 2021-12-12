package year2021.puzzle12;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Puzzle12 {
    public static void main(String[] args) {
        Map<String, List<String>> caves = new HashMap<>();
        Utils.getInput("2021/input12.txt", (s) -> s).stream()
                .map(s -> s.split("-"))
                .forEach(split -> {
                    caves.merge(split[0],
                            List.of(split[1]),
                            (oldList, newList) -> Stream.of(oldList, newList).flatMap(Collection::stream).toList());
                    caves.merge(split[1],
                            List.of(split[0]),
                            (oldList, newList) -> Stream.of(oldList, newList).flatMap(Collection::stream).toList());
                });

        System.out.println(countPaths(new LinkedList<>(List.of("start")), caves, false));
        System.out.println(countPaths(new LinkedList<>(List.of("start")), caves, true));
    }

    private static int countPaths(Deque<String> path, Map<String, List<String>> caves, boolean allowTwice) {
        if ("end".equals(path.peek())) {
            return 1;
        }
        AtomicInteger count = new AtomicInteger();
        caves.get(path.peek()).forEach(neighbour -> {
            if (StringUtils.isAllUpperCase(neighbour)) {
                path.push(neighbour);
                count.getAndAdd(countPaths(path, caves, allowTwice));
                path.pop();
            } else if (!path.contains(neighbour)) {
                path.push(neighbour);
                count.getAndAdd(countPaths(path, caves, allowTwice));
                path.remove();
            } else if (!"start".equals(neighbour) && !"end".equals(neighbour) && allowTwice) {
                path.push(neighbour);
                count.getAndAdd(countPaths(path, caves, false));
                path.remove();
            }
        });

        return count.get();
    }
}
