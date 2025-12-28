package year2017.puzzle14;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KnotHash {

    public static String computeHashHex(String input) {
        List<Integer> lengths = new ArrayList<>(Arrays.stream(input.split(""))
                .map(c -> (int) c.charAt(0))
                .toList());
        lengths.addAll(Arrays.asList(17, 31, 73, 47, 23));

        List<Integer> sparseHash = solveKnotHash(lengths, 64, 256);

        List<List<Integer>> sparseHashSplit = ListUtils.partition(sparseHash, 16);

        return sparseHashSplit.stream()
                .map(KnotHash::xorList)
                .map(i -> Integer.toString(i, 16))
                .map(s -> StringUtils.leftPad(s, 2, "0"))
                .collect(Collectors.joining());
    }

    private static int xorList(List<Integer> list) {
        return list.stream().reduce(0, (a, b) -> a ^ b);
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
