package year2015.puzzle13;

import org.apache.commons.lang3.tuple.Pair;
import util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

// https://www.techiedelight.com/print-all-hamiltonian-path-present-in-a-graph/
public class Puzzle13 {

    private static final Map<Pair<Integer, Integer>, Integer> distancesByIndex = new HashMap<>();
    private static final Map<List<Integer>, Integer> solutions = new HashMap<>();

    public static void main(String[] args) {
        Map<String, Integer> nameIndex = new HashMap<>();
        AtomicInteger nameCounter = new AtomicInteger();
        Utils.getInput("2015/input13.txt").forEach(
                line -> {
                    //Alice would gain 54 happiness units by sitting next to Bob.
                    String[] split = line.split(" ");
                    Integer nameA = nameIndex.computeIfAbsent(split[0], k -> nameCounter.getAndIncrement());
                    Integer nameB = nameIndex.computeIfAbsent(split[10].replace(".", ""),
                            k -> nameCounter.getAndIncrement());

                    Integer happiness = Integer.valueOf(split[3]);
                    if (!"gain".equals(split[2])) {
                        happiness = happiness * -1;
                    }

                    if (distancesByIndex.containsKey(Pair.of(nameA, nameB))) {
                        distancesByIndex.put(Pair.of(nameA, nameB),
                                distancesByIndex.get(Pair.of(nameA, nameB)) + happiness);
                    } else if (distancesByIndex.containsKey(Pair.of(nameB, nameA))) {
                        distancesByIndex.put(Pair.of(nameB, nameA),
                                distancesByIndex.get(Pair.of(nameB, nameA)) + happiness);
                    } else {

                        distancesByIndex.put(Pair.of(nameA, nameB), happiness);
                    }
                });

        //for part 2
        int myself = nameCounter.getAndIncrement();
        nameIndex.values().forEach(
                otherName -> distancesByIndex.put(Pair.of(myself, otherName), 0)
        );
        nameIndex.put("myself", myself);
        //end for part 2


        List<Edge> edges = new ArrayList<>();
        distancesByIndex.keySet().forEach(
                key -> edges.add(new Edge(key.getLeft(), key.getRight()))
        );

        Graph graph = new Graph(edges, nameIndex.size());
        findHamiltonianPaths(graph, nameIndex.size());

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

            // add loop to start
            distance += distancesByIndex.getOrDefault(Pair.of(path.get(0), path.get(path.size() - 1)),
                    distancesByIndex.get(Pair.of(path.get(path.size() - 1), path.get(0))));

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
