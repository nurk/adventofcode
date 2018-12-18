package year2018;

import year2017.Puzzle4b;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Puzzle2 {
    private static Path getInputUri() throws URISyntaxException {
        return Paths.get(Puzzle4b.class.getClassLoader().getResource("2018/input2a.txt").toURI());
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        AtomicInteger twoOfAKind = new AtomicInteger(0);
        AtomicInteger threeOfAKind = new AtomicInteger(0);
        Files.readAllLines(getInputUri()).forEach(
                line -> {
                    if (hasNOfAKind(line, 2)) {
                        twoOfAKind.getAndIncrement();
                    }
                    if (hasNOfAKind(line, 3)) {
                        threeOfAKind.getAndIncrement();
                    }
                }
        );
        System.out.println(twoOfAKind.get() * threeOfAKind.get());
    }

    private static boolean hasNOfAKind(String line, int n) {
        Map<String, Integer> letters = new HashMap<>();
        Stream.of(line.split("")).forEach(
                letter -> {
                    letters.computeIfPresent(letter, (k, v) -> ++v);
                    letters.putIfAbsent(letter, 1);
                }
        );
        return letters.containsValue(n);
    }
}
