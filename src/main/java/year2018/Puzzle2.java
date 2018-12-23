package year2018;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Puzzle2 {
    private static Path getInputUri() throws URISyntaxException {
        return Paths.get(Puzzle2.class.getClassLoader().getResource("2018/input2a.txt").toURI());
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        puzzleA();
        puzzleB();
    }

    private static void puzzleA() throws IOException, URISyntaxException {
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

    private static void puzzleB() throws IOException, URISyntaxException {
        List<String> lines = new ArrayList<>(Files.readAllLines(getInputUri()));

        char[] lineA = new char[0];
        char[] lineB = new char[0];

        for (int i = 0; i < lines.size() - 1; i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                if (areSimilar(lines.get(i), lines.get(j))) {
                    lineA = lines.get(i).toCharArray();
                    lineB = lines.get(j).toCharArray();
                }
            }
        }

        String result = "";
        for (int i = 0; i < lineA.length; i++) {
            if (lineA[i] == lineB[i]) {
                result = result + lineA[i];
            }
        }

        System.out.println(result);

    }

    private static boolean areSimilar(String a, String b) {
        char[] aChars = a.toCharArray();
        char[] bChars = b.toCharArray();

        int nonSimilar = 0;
        for (int i = 0; i < aChars.length; i++) {
            if (aChars[i] != bChars[i]) {
                nonSimilar++;
            }
        }
        return nonSimilar == 1;
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
