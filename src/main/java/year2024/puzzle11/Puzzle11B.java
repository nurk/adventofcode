package year2024.puzzle11;

import util.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Part A: 207683
 * Part B: 244782991106220
 */
public class Puzzle11B {

    private static final Map<Long, Long> stones = new HashMap<>();

    public static void main(String[] args) {
        stones.putAll(Arrays.stream(Utils.getInput("2024/input11.txt", (s) -> s).getFirst().split(" "))
                .map(Long::parseLong)
                .collect(Collectors.toMap(o -> o, _ -> 1L)));

        IntStream.range(0, 25).forEach(_ -> blink());
        System.out.println("Part A: " + getTotalStones());

        IntStream.range(0, 50).forEach(_ -> blink());
        System.out.println("Part B: " + getTotalStones());
    }

    public static void blink() {
        Map<Long, Long> stonesCopy = new HashMap<>();

        for (Map.Entry<Long, Long> stone : stones.entrySet()) {
            if (stone.getKey() == 0) {
                stonesCopy.merge(1L, stone.getValue(), Long::sum);

            } else if (stone.getKey().toString().length() % 2 == 0) {
                int length = stone.getKey().toString().length() / 2;

                stonesCopy.merge(Long.parseLong(stone.getKey().toString().substring(0, length)),
                        stone.getValue(),
                        Long::sum);
                stonesCopy.merge(Long.parseLong(stone.getKey().toString().substring(length)),
                        stone.getValue(),
                        Long::sum);
            } else {
                stonesCopy.merge(stone.getKey() * 2024, stone.getValue(), Long::sum);
            }
        }

        stones.clear();
        stones.putAll(stonesCopy);
    }

    public static long getTotalStones() {
        return stones.values().stream().mapToLong(Long::longValue).sum();
    }
}
