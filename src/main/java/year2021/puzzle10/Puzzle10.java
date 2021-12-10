package year2021.puzzle10;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class Puzzle10 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2021/input10.txt", (s) -> s));
        System.out.println(input.stream()
                .map(Puzzle10::parseLine)
                .map(Pair::getLeft)
                .filter(Optional::isPresent)
                .map(Optional::orElseThrow)
                .map(Bracket::getScore)
                .mapToInt(i -> i)
                .sum());

        List<Long> integers = input.stream()
                .map(Puzzle10::parseLine)
                .filter(pair -> pair.getLeft().isEmpty())
                .map(Pair::getRight)
                .map(Puzzle10::remainderToScore)
                .sorted()
                .toList();
        System.out.println(integers.get(integers.size() / 2));
    }

    private static Long remainderToScore(String s) {
        AtomicLong totalScore = new AtomicLong(0);
        s = StringUtils.reverse(s);
        Arrays.stream(s.split(""))
                .map(Bracket::fromOpening)
                .map(Bracket::getScoreB)
                .forEach(score -> totalScore.set(totalScore.get() * 5 + score));
        return totalScore.get();
    }

    private static Pair<Optional<Bracket>, String> parseLine(String s) {
        var ref = new Object() {
            String ss = s;
            boolean found = true;
        };
        while (ref.found) {
            ref.found = false;
            Arrays.stream(Bracket.values())
                    .map(Bracket::getPair)
                    .forEach(pair -> {
                        if (StringUtils.contains(ref.ss, pair)) {
                            ref.found = true;
                            ref.ss = StringUtils.remove(ref.ss, pair);
                        }
                    });
        }

        return Pair.of(Arrays.stream(ref.ss.split(""))
                .filter(sss -> Bracket.getClosingList().contains(sss))
                .findFirst()
                .map(Bracket::fromClosing), ref.ss);
    }
}
