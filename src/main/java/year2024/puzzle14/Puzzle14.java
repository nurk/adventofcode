package year2024.puzzle14;

import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

/**
 * Part A: 230172768
 * Part B: 8087
 */
public class Puzzle14 {
    private static final List<Robot> robots = new ArrayList<>();

    public static void main(String[] args) {
        robots.addAll(Utils.getInput("2024/input14.txt", (s) -> s).stream()
                .map(Robot::new)
                .toList());

        IntStream.range(0, 100).forEach(_ -> robots.forEach(Robot::tick));

        Map<Integer, List<Robot>> quadrants = robots.stream()
                .filter(robot -> robot.getQuadrant() != -1)
                .collect(Collectors.groupingBy(Robot::getQuadrant));

        System.out.println("Part A: " + quadrants.values()
                .stream()
                .mapToInt(List::size)
                .reduce(1, (left, right) -> left * right));

        robots.forEach(Robot::reset);

        int i = 0;
        while (true) {
            boolean b = robots.stream().collect(groupingBy(Robot::getPY)).values().stream()
                    .mapToInt(List::size)
                    .anyMatch(size -> size > 30) &&
                    robots.stream().collect(groupingBy(Robot::getPX)).values().stream()
                            .mapToInt(List::size)
                            .anyMatch(size -> size > 30);
            if (b) {
                System.out.println("Part B: " + i);
                print();
                break;
            }
            robots.forEach(Robot::tick);
            i++;
        }
    }

    private static void print() {
        String[][] debug = new String[103][101];
        for (String[] strings : debug) {
            Arrays.fill(strings, " ");
        }

        robots.forEach(robot -> debug[robot.getPY()][robot.getPX()] = "#");

        System.out.println(Arrays.stream(debug)
                .map(points -> String.join("", points))
                .collect(joining("\n")));
    }
}
