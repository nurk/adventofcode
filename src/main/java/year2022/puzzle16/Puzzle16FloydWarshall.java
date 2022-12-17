package year2022.puzzle16;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Puzzle16FloydWarshall {
    static List<Valve> valves;
    static final int INF = 9999;
    static int[][] graph;

    public static void main(String[] args) {
        valves = Utils.getInput("2022/input16test.txt", Valve::new).stream()
                .sorted(Comparator.comparing(o -> o.name))
                .toList();

        graph = new int[valves.size()][valves.size()];
        for (int i = 0; i < valves.size(); i++) {
            for (int j = 0; j < valves.size(); j++) {
                Valve from = valves.get(i);
                Valve to = valves.get(j);
                int weight = 0;
                if (from.equals(to)) {
                    weight = 0;
                } else {
                    if (from.leadsTo.contains(to.name)) {
                        weight = to.invertedFlowRate;
                    } else {
                        weight = INF;
                    }
                }
                graph[i][j] = weight;
            }
        }

        printMatrix(graph);
        System.out.println();
        System.out.println();
        floydWarshall(graph);
    }

    static void floydWarshall(int[][] graph) {
        int nV = graph.length;
        int[][] matrix = new int[graph.length][graph[0].length];
        int i, j, k;

        for (i = 0; i < nV; i++) {
            for (j = 0; j < nV; j++) {
                matrix[i][j] = graph[i][j];
            }
        }

        // Adding vertices individually
        for (k = 0; k < nV; k++) {
            for (i = 0; i < nV; i++) {
                for (j = 0; j < nV; j++) {
                    if (matrix[i][k] + matrix[k][j] < matrix[i][j]) {
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                    }
                }
            }
        }
        printMatrix(matrix);
    }

    public static void printMatrix(int[][] matrix) {
        System.out.println(IntStream.range(0, matrix.length)
                .boxed()
                .map(i -> IntStream.range(0, matrix[i].length)
                        .boxed()
                        .map(j -> i + " => " + j + ": " + StringUtils.leftPad(String.valueOf(matrix[i][j]), 4, "0"))
                        .collect(Collectors.joining(" | "))).
                collect(Collectors.joining("\n")));
    }


    static Valve getValve(String n) {
        return valves.stream()
                .filter(v -> v.name.equals(n))
                .findFirst()
                .orElseThrow();
    }

    static class Valve {
        private final Integer flowRate;
        private final Integer invertedFlowRate;
        private final String name;
        private final List<String> leadsTo;

        public Valve(String s) {
            name = StringUtils.substringBetween(s, "Valve ", " has flow rate");
            flowRate = Integer.parseInt(StringUtils.substringBetween(s, "flow rate=", ";"));
            invertedFlowRate = 50 - flowRate;
            if (s.contains("valves")) {
                leadsTo = Arrays.stream(StringUtils.substringAfter(s, "to valves ").split(", ")).toList();
            } else {
                leadsTo = Arrays.stream(StringUtils.substringAfter(s, "to valve ").split(", ")).toList();
            }
        }

        @Override
        public String toString() {
            return "Valve{" +
                    "flowRate=" + flowRate +
                    ", name='" + name + '\'' +
                    ", leadsTo=" + String.join(",", leadsTo) +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Valve valve = (Valve) o;

            return name.equals(valve.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
}
