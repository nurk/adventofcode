package year2021.puzzle2;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.List;

public class Puzzle2 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2021/input2.txt", (s) -> s);

        partA(input);
        partB(input);
    }

    private static void partA(List<String> input) {
        long position = 0;
        long depth = 0;

        for (String s : input) {
            String[] split = StringUtils.split(s, " ");

            long value = Long.parseLong(split[1]);
            switch (split[0]) {
                case "forward" -> position += value;
                case "up" -> depth -= value;
                case "down" -> depth += value;
            }
        }

        System.out.println(position * depth);
        System.out.println(
                (input.stream()
                        .map(s -> StringUtils.split(s, " "))
                        .filter(l -> l[0].equalsIgnoreCase("forward"))
                        .mapToLong(l -> Long.parseLong(l[1]))
                        .sum()) *
                        (input.stream()
                                .map(s -> StringUtils.split(s, " "))
                                .filter(l -> !l[0].equalsIgnoreCase("forward"))
                                .mapToLong(l -> Long.parseLong(l[1]) * (l[0].equalsIgnoreCase("up") ? -1 : 1))
                                .sum()));
    }

    private static void partB(List<String> input) {
        long position = 0;
        long depth = 0;
        long aim = 0;

        for (String s : input) {
            String[] split = StringUtils.split(s, " ");

            long value = Long.parseLong(split[1]);
            switch (split[0]) {
                case "forward" -> {
                    position += value;
                    depth += aim * value;
                }
                case "up" -> aim -= value;
                case "down" -> aim += value;
            }
        }

        System.out.println(position * depth);
    }
}
