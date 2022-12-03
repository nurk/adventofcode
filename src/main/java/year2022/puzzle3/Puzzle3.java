package year2022.puzzle3;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import util.Utils;

import java.util.Arrays;
import java.util.List;

public class Puzzle3 {
    public static void main(String[] args) {
        System.out.println(Utils.getInput("2022/input3.txt",
                        s -> Pair.of(s.substring(0, s.length() / 2), s.substring(s.length() / 2)))
                .stream()
                .map(Puzzle3::getPriority)
                .reduce(0, Integer::sum));

        System.out.println(ListUtils.partition(Utils.getInput("2022/input3.txt"), 3)
                .stream()
                .map(Puzzle3::getPriority)
                .reduce(0, Integer::sum));
    }

    private static Integer getPriority(Pair<String, String> rucksack) {
        return Arrays.stream(rucksack.getLeft().split(""))
                .filter(c -> rucksack.getRight().contains(c))
                .findFirst()
                .map(Puzzle3::calculatePriority)
                .orElse(0);
    }

    private static Integer getPriority(List<String> rucksacks) {
        return Arrays.stream(rucksacks.get(0).split(""))
                .filter(c -> rucksacks.get(1).contains(c) && rucksacks.get(2).contains(c))
                .findFirst()
                .map(Puzzle3::calculatePriority)
                .orElse(0);
    }

    private static Integer calculatePriority(String c) {
        return Utils.getAlphabetLetterIndex(c.toLowerCase()) + 1 + (StringUtils.isAllUpperCase(c) ? 26 : 0);
    }
}
