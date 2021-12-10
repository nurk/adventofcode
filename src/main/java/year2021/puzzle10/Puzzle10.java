package year2021.puzzle10;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Puzzle10 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2021/input10.txt", (s) -> s));
        System.out.println(input.stream()
                .map(Puzzle10::removeAllValidPairs)
                .map(Puzzle10::getIllegalBracket)
                .flatMap(Optional::stream)
                .map(Bracket::getScoreA)
                .reduce(0, Integer::sum));

        List<Long> integers = input.stream()
                .map(Puzzle10::removeAllValidPairs)
                .filter(remainder -> Puzzle10.getIllegalBracket(remainder).isEmpty())
                .map(Puzzle10::remainderToScore)
                .sorted()
                .toList();
        System.out.println(integers.get(integers.size() / 2));
    }

    private static Long remainderToScore(String remainder) {
        return Arrays.stream(StringUtils.reverse(remainder).split(""))
                .map(Bracket::from)
                .map(Bracket::getScoreB)
                .map(Long::valueOf)
                .reduce(0L, (totalScore, score) -> totalScore * 5 + score);
    }

    private static String removeAllValidPairs(String line) {
        var ref = new Object() {
            String remainder = line;
            boolean found = true;
        };

        while (ref.found) {
            ref.found = false;
            Arrays.stream(Bracket.values())
                    .map(Bracket::getPair)
                    .forEach(pair -> {
                        if (StringUtils.contains(ref.remainder, pair)) {
                            ref.found = true;
                            ref.remainder = StringUtils.remove(ref.remainder, pair);
                        }
                    });
        }

        return ref.remainder;
    }

    private static Optional<Bracket> getIllegalBracket(String remainder) {
        return Arrays.stream(remainder.split(""))
                .filter(s -> Bracket.getClosingList().contains(s))
                .findFirst()
                .map(Bracket::from);
    }
}
