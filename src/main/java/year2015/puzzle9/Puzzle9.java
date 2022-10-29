package year2015.puzzle9;

import org.apache.commons.lang3.tuple.Pair;
import util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

// https://www.techiedelight.com/print-all-hamiltonian-path-present-in-a-graph/
public class Puzzle9 {

    private static final Map<Pair<Integer, Integer>, Integer> distancesByIndex = new HashMap<>();
    private static final Map<List<Integer>, Integer> solutions = new HashMap<>();

    public static void main(String[] args) {
        AtomicInteger cityCounter = new AtomicInteger(0);
        Map<String, Integer> cities = new HashMap<>();
        List<Edge> edges = new ArrayList<>();
        Utils.getInput("2015/input9.txt").forEach(
                line -> {
                    String[] split = line.split(" ");
                    cities.computeIfAbsent(split[0], k -> cityCounter.getAndIncrement());
                    cities.computeIfAbsent(split[2], k -> cityCounter.getAndIncrement());
                    distancesByIndex.put(Pair.of(cities.get(split[0]), cities.get(split[2])),
                            Integer.valueOf(split[4]));
                    edges.add(new Edge(cities.get(split[0]), cities.get(split[2])));
                }
        );

        int n = cities.size();
        Graph graph = new Graph(edges, n);
        findHamiltonianPaths(graph, n);

        System.out.println(solutions.values()
                .stream().min(Integer::compareTo).orElseThrow());
        System.out.println(solutions.values()
                .stream().max(Integer::compareTo).orElseThrow());
    }

    public static void findHamiltonianPaths(Graph graph, int n) {
        // start with every node
        for (int start = 0; start < n; start++) {
            // add starting node to the path
            List<Integer> path = new ArrayList<>();
            path.add(start);

            // mark the start node as visited
            boolean[] visited = new boolean[n];
            visited[start] = true;

            hamiltonianPaths(graph, start, visited, path, n);
        }
    }

    public static void hamiltonianPaths(Graph graph, int v, boolean[] visited,
                                        List<Integer> path, int n) {

        if (path.size() == n) {
            // solution
            int distance = 0;
            for (int i = 1; i < path.size(); i++) {
                distance += distancesByIndex.getOrDefault(Pair.of(path.get(i - 1), path.get(i)),
                        distancesByIndex.get(Pair.of(path.get(i), path.get(i - 1))));
            }
            solutions.put(path, distance);
            System.out.println(path + " " + distance);
            return;
        }

        // Check if every edge starting from vertex `v` leads
        // to a solution or not
        for (int w : graph.adjList.get(v)) {
            // process only unvisited vertices as the Hamiltonian
            // path visit each vertex exactly once
            if (!visited[w]) {
                visited[w] = true;
                path.add(w);

                // check if adding vertex `w` to the path leads
                // to the solution or not
                hamiltonianPaths(graph, w, visited, path, n);

                // backtrack
                visited[w] = false;
                path.remove(path.size() - 1);
            }
        }
    }

    static class Edge {
        int source, dest;

        public Edge(int source, int dest) {
            this.source = source;
            this.dest = dest;
        }
    }

    static class Graph {
        List<List<Integer>> adjList;

        Graph(List<Edge> edges, int n) {
            adjList = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                adjList.add(new ArrayList<>());
            }

            for (Edge edge : edges) {
                int src = edge.source;
                int dest = edge.dest;

                adjList.get(src).add(dest);
                adjList.get(dest).add(src);
            }
        }
    }
}
