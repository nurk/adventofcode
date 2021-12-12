package year2021.puzzle12;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Puzzle12 {
    public static void main(String[] args) {
        Map<String, List<String>> caves = new HashMap<>();
        Utils.getInput("2021/input12.txt", (s) -> s).stream()
                .map(s -> s.split("-"))
                .forEach(split -> {
                    if (!caves.containsKey(split[0])) {
                        caves.put(split[0], new ArrayList<>());
                    }
                    if (!caves.containsKey(split[1])) {
                        caves.put(split[1], new ArrayList<>());
                    }
                    caves.get(split[0]).add(split[1]);
                    caves.get(split[1]).add(split[0]);
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
