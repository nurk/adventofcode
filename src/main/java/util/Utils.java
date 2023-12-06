package util;

import org.apache.commons.lang3.StringUtils;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Utils {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

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

    public static List<List<String>> splitOnBlankLine(List<String> input) {
        List<List<String>> output = new ArrayList<>();
        List<String> subList = new ArrayList<>();

        for (String line : input) {
            if (StringUtils.isBlank(line)) {
                output.add(subList);
                subList = new ArrayList<>();
            } else {
                subList.add(line);
            }
        }

        output.add(subList);

        return output;
    }
}
