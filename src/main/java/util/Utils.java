package util;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Utils {

    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    public static final String[] ALPHABET_ARRAY = ALPHABET.split("");

    public static int getAlphabetLetterIndex(String letter) {
        for (int i = 0; i < ALPHABET_ARRAY.length; i++) {
            if (letter.equals(ALPHABET_ARRAY[i])) {
                return i;
            }
        }

        return -1;
    }

    public static IntStream reverseRange(int from, int to) {
        return IntStream.range(from, to)
                .map(i -> to - i + from - 1);
    }

    public static Path getInputPath(String fileName) throws URISyntaxException {
        return Paths.get(Utils.class.getClassLoader().getResource(fileName).toURI());
    }

    public static <T> List<T> getInput(String fileName, Function<? super String, T> parser) {
        try {
            return getInputAsStream(fileName, parser).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Stream<T> getInputAsStream(String fileName, Function<? super String, T> parser) {
        try {
            return Files.readAllLines(Utils.getInputPath(fileName)).stream().map(parser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getInput(String fileName) {
        try {
            return Files.readAllLines(Utils.getInputPath(fileName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int[] getSplitIndexes(List<String> input) {
        return Stream.of(IntStream.of(-1),
                        IntStream.range(0, input.size()).filter(i -> input.get(i).isBlank()),
                        IntStream.of(input.size()))
                .flatMapToInt(s -> s).toArray();
    }
}
