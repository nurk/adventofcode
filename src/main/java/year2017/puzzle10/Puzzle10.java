package year2017.puzzle10;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Part A: 20056
 * Part B: d9a7de4a809c56bf3a9465cb84392c8e
 */
public class Puzzle10 {
    public static void main(String[] args) {
        partA();
        partB();
    }

    private static void partB() {
        List<Integer> lengths = new ArrayList<>(Utils.getInput("2017/input10.txt").stream()
                .flatMap(l -> Arrays.stream(l.split("")))
                .map(c -> (int) c.charAt(0))
                .toList());
        lengths.addAll(Arrays.asList(17, 31, 73, 47, 23));

        List<Integer> sparseHash = solveKnotHash(lengths, 64, 256);

        List<List<Integer>> sparseHashSplit = ListUtils.partition(sparseHash, 16);
        String hash = sparseHashSplit.stream()
                .map(Puzzle10::xorList)
                .map(i -> Integer.toString(i, 16))
                .map(s -> StringUtils.leftPad(s, 2, "0"))
                .collect(Collectors.joining());

        System.out.println("Part B: " + hash);
    }

    private static int xorList(List<Integer> list) {
        return list.stream().reduce(0, (a, b) -> a ^ b);
    }

    private static void partA() {
        List<Integer> lengths = Utils.getInput("2017/input10.txt").stream()
                .flatMap(l -> Arrays.stream(l.split(",")))
                .map(Integer::parseInt)
                .toList();

        List<Integer> knotHash = solveKnotHash(lengths, 1, lengths.size() > 10 ? 256 : 5);

        System.out.println("Part A: " + (knotHash.get(0) * knotHash.get(1)));
    }

    private static List<Integer> solveKnotHash(List<Integer> lengths, int rounds, int maxElements) {
        List<Integer> knotHash = new ArrayList<>();
        for (int i = 0; i < maxElements; i++) {
            knotHash.add(i);
        }
        int currentPosition = 0;
        int skipSize = 0;

        for (int round = 0; round < rounds; round++) {
            for (int length : lengths) {
                List<Integer> subList = new ArrayList<>();
                for (int i = 0; i < length; i++) {
                    subList.add(knotHash.get((currentPosition + i) % knotHash.size()));
                }
                for (int i = 0; i < length; i++) {
                    knotHash.set((currentPosition + i) % knotHash.size(), subList.get(length - 1 - i));
                }
                currentPosition = (currentPosition + length + skipSize) % knotHash.size();
                skipSize++;
            }
        }

        return knotHash;
    }
}
