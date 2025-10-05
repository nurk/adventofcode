package year2024.puzzle25;

import com.google.common.collect.Lists;
import util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Part A: 2824
 */
public class Puzzle25 {

    public static void main(String[] args) {
        List<String> lines = new ArrayList<>(Utils.getInput("2024/input25.txt"));

        List<LockPart> keys = new ArrayList<>();
        List<LockPart> tumblers = new ArrayList<>();

        Lists.partition(lines, 8)
                .forEach(strings -> {
                    if (Objects.equals(strings.getFirst(), ".....")) {
                        keys.add(new Key(strings));
                    } else {
                        tumblers.add(new Tumblers(strings));
                    }
                });

        //keys.forEach(System.out::println);
        //tumblers.forEach(System.out::println);

        System.out.println("Part A: " + keys.stream()
                .map(key -> tumblers.stream()
                        .filter(tumbler -> tumbler.fits(key))
                        .count())
                .mapToInt(Long::intValue)
                .sum());
    }
}
