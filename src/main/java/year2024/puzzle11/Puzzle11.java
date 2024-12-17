package year2024.puzzle11;

import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Part A: 207683
 */
public class Puzzle11 {

    private static List<Long> stones = new ArrayList<>();

    public static void main(String[] args) {
        stones.addAll(Arrays.stream(Utils.getInput("2024/input11.txt", (s) -> s).getFirst().split(" "))
                .map(Long::parseLong)
                .toList());

        IntStream.range(0, 25).forEach(i -> blink());
        System.out.println("Part A: " + stones.size());
    }

    public static void blink() {
        List<Long> stonesCopy = new ArrayList<>();

        for (Long stone : stones) {
            if (stone == 0) {
                stonesCopy.add(1L);
            } else if (stone.toString().length() % 2 == 0) {
                int length = stone.toString().length() / 2;
                stonesCopy.add(Long.parseLong(stone.toString().substring(0, length)));
                stonesCopy.add(Long.parseLong(stone.toString().substring(length)));
            } else {
                stonesCopy.add(stone * 2024);
            }
        }

        stones.clear();
        stones.addAll(stonesCopy);
    }
}
