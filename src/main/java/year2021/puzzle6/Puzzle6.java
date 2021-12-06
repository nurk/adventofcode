package year2021.puzzle6;

import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Puzzle6 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<Integer> input = Files.readAllLines(Utils.getInputPath("2021/input6.txt")).stream()
                .map(line -> line.split(","))
                .flatMap(Arrays::stream)
                .map(Integer::valueOf)
                .toList();

        Map<Integer, Long> fish = new HashMap<>();
        IntStream.rangeClosed(0, 8)
                .forEach(i -> fish.put(i, 0L));


        input.forEach(i -> fish.merge(i, 1L, Long::sum));
        int day = 0;

        while (day++ != 256) {
            long zeros = fish.get(0);

            fish.put(0, fish.get(1));
            fish.put(1, fish.get(2));
            fish.put(2, fish.get(3));
            fish.put(3, fish.get(4));
            fish.put(4, fish.get(5));
            fish.put(5, fish.get(6));
            fish.put(6, fish.get(7));
            fish.put(7, fish.get(8));
            fish.put(8, zeros);
            fish.put(6, fish.get(6) + zeros);
        }
        System.out.println(fish.values().stream().mapToLong(value -> value).sum());
    }
}
