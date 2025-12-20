package year2023.puzzle8;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.util.ArithmeticUtils;
import util.Utils;

import java.util.*;
import java.util.function.Function;

/**
 * Part A: 15517
 * Part B: 14935034899483
 */
public class Puzzle8 {

    private static final Map<String, Pair<String, String>> MAP = new HashMap<>();
    private static final List<String> ROUTE = new ArrayList<>();

    public static void main(String[] args) {
        Utils.getInput("2023/input8.txt", line -> {
            if (line.isBlank()) {
                return null;
            }
            if (!line.contains("=")) {
                ROUTE.addAll(Arrays.stream(line.split("")).toList());
                return null;
            }
            String[] split = line.split(" = ");
            String source = split[0];
            String left = split[1].split(", ")[0].replace("(", "");
            String right = split[1].split(", ")[1].replace(")", "");
            MAP.put(source, Pair.of(left, right));
            return null;
        });

        partA();
        partB();
    }

    private static void partB() {
        List<String> startNodes = MAP.keySet().stream()
                .filter(key -> key.endsWith("A"))
                .toList();

        List<Long> stepsPerStartPoint = startNodes.stream()
                .map(startNode -> countSteps(startNode, node -> !node.endsWith("Z")))
                .toList();

        long lcm = ArithmeticUtils.lcm(stepsPerStartPoint.get(0), stepsPerStartPoint.get(1));
        for (int i = 2; i < stepsPerStartPoint.size(); i++) {
            lcm = ArithmeticUtils.lcm(lcm, stepsPerStartPoint.get(i));
        }

        System.out.println("Part B: " + lcm);
    }

    private static void partA() {
        System.out.println("Part A: " + countSteps("AAA", node -> !node.equals("ZZZ")));
    }

    private static long countSteps(String start, Function<String, Boolean> loopCondition) {
        String current = start;
        long steps = 0L;
        List<String> currentRoute = new ArrayList<>(ROUTE);

        while (loopCondition.apply(current)) {
            steps++;
            String nextMove = currentRoute.removeFirst();
            if (currentRoute.isEmpty()) {
                currentRoute = new ArrayList<>(ROUTE);
            }

            Pair<String, String> nextSteps = MAP.get(current);
            if (nextMove.equals("L")) {
                current = nextSteps.getLeft();
            } else {
                current = nextSteps.getRight();
            }
        }

        return steps;
    }
}
