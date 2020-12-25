package year2020.puzzle14;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Puzzle14 {
    private static char[] mask;
    private static char[] maskB;
    private static final Map<Integer, Long> memory = new HashMap<>();
    private static final Map<Long, Long> memoryB = new HashMap<>();

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> input = new ArrayList<>(Files.readAllLines(Utils.getInputPath("2020/input14.txt")));

        for (String s : input) {
            if (s.startsWith("mask = ")) {
                mask = StringUtils.removeStart(s, "mask = ").toCharArray();
            } else {
                int location = Integer.parseInt(StringUtils.substringBetween(s, "[", "]"));
                long number = Long.parseLong(StringUtils.substringAfter(s, "= "));
                maskAndStore(location, number);
            }
        }

        System.out.println(memory.values().stream().reduce(0L, Long::sum));

        for (String s : input) {
            if (s.startsWith("mask = ")) {
                maskB = StringUtils.removeStart(s, "mask = ").toCharArray();
            } else {
                int location = Integer.parseInt(StringUtils.substringBetween(s, "[", "]"));
                long number = Long.parseLong(StringUtils.substringAfter(s, "= "));
                maskAndStoreB(location, number);
            }
        }

        System.out.println(memoryB.values().stream().reduce(0L, Long::sum));

    }

    private static void maskAndStore(int location, Long number) {
        char[] binaryChars = StringUtils.leftPad(Long.toBinaryString(number), 36, "0").toCharArray();
        for (int i = 0; i < mask.length; i++) {
            if (mask[i] != 'X') {
                binaryChars[i] = mask[i];
            }
        }
        memory.put(location, Long.valueOf(String.valueOf(binaryChars), 2));
    }

    private static void maskAndStoreB(int location, Long number) {
        char[] binaryLocation = StringUtils.leftPad(Integer.toBinaryString(location), 36, "0").toCharArray();
        for (int i = 0; i < maskB.length; i++) {
            if (maskB[i] != '0') {
                binaryLocation[i] = maskB[i];
            }
        }

        for (char[] permutation : getAllPermutations(binaryLocation)) {
            String binaryString = String.valueOf(binaryLocation);
            for (char c : permutation) {
                binaryString = StringUtils.replaceOnce(binaryString, "X", String.valueOf(c));
            }
            memoryB.put(Long.valueOf(binaryString, 2), number);

        }
    }

    private static List<char[]> getAllPermutations(char[] binaryLocation) {
        int numberOfXes = 0;
        for (char c : binaryLocation) {
            if (c == 'X') {
                numberOfXes++;
            }
        }

        double perms = Math.pow(2, numberOfXes);

        List<char[]> permutations = new ArrayList<>();

        for (int i = 0; i < perms; i++) {
            permutations.add(StringUtils.leftPad(Integer.toBinaryString(i), numberOfXes, "0").toCharArray());
        }

        return permutations;
    }
}
