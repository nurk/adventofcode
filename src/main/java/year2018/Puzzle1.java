package year2018;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Puzzle1 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        puzzleA();
        puzzleB();
    }

    private static void puzzleA() throws IOException, URISyntaxException {
        AtomicInteger total = new AtomicInteger(0);
        Files.readAllLines(getInputUri()).forEach(
                line -> {
                    total.getAndAdd(Integer.valueOf(line));
                }
        );
        System.out.println("Puzzle a: " + total.get());
    }

    private static void puzzleB() throws IOException, URISyntaxException {
        Set<Integer> frequentiesSeen = new HashSet<>();
        frequentiesSeen.add(0);
        AtomicInteger currentFrequentie = new AtomicInteger(0);
        AtomicBoolean seenBefore = new AtomicBoolean(false);

        while (!seenBefore.get()) {
            Files.readAllLines(getInputUri()).forEach(
                    line -> {
                        if (seenBefore.get()) {
                            return;
                        }
                        currentFrequentie.getAndAdd(Integer.valueOf(line));
                        seenBefore.set(!frequentiesSeen.add(currentFrequentie.get()));
                    }
            );
        }
        System.out.println("Puzzle b: " + currentFrequentie.get());
    }

    private static Path getInputUri() throws URISyntaxException {
        return Paths.get(Puzzle1.class.getClassLoader().getResource("2018/input1a.txt").toURI());
    }
}
