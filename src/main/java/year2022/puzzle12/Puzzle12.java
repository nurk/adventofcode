package year2022.puzzle12;

import util.Utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Puzzle12 {
    private static Node[][] board;
    private static Node start, end;

    public static void main(String[] args) {
        List<String> input = Utils.getInput("2022/input12.txt");
        board = new Node[input.size()][input.get(0).length()];

        for (int i = 0; i < input.size(); i++) {
            String[] split = input.get(i).split("");
            for (int j = 0; j < split.length; j++) {
                if (split[j].equals("S")) {
                    board[i][j] = new Node(i, j, 1, 0);
                    start = new Node(i, j, 1, 0);
                } else if (split[j].equals("E")) {
                    board[i][j] = new Node(i, j, 1, 25);
                    end = new Node(i, j, 1, 25);
                } else {
                    board[i][j] = new Node(i, j, 1, Utils.getAlphabetLetterIndex(split[j]));
                }
            }
        }

        System.out.println("PartA: " + shortestPath(start, end));

        System.out.println("PartB: " + Arrays.stream(board).flatMap(Arrays::stream)
                .filter(n -> n.getHeight() == 0)
                .map(s -> shortestPath(s, end))
                .sorted()
                .findFirst()
                .orElse(-1L));
    }

    public static long shortestPath(Node start, Node end) {
        Arrays.stream(board).flatMap(Arrays::stream).forEach(n -> n.setPathCost(0));
        PriorityQueue<Node> pq = new PriorityQueue<>(board.length * board[0].length);
        pq.add(start);

        Map<Node, Integer> costSoFar = new HashMap<>();

        costSoFar.put(start, 0);

        while (!pq.isEmpty()) {
            Node current = pq.remove();

            if (current.equals(end)) {
                break;
            }

            int currentCost = current.getPathCost();

            if (currentCost <= costSoFar.get(current)) {
                for (Node neighbour : getNeighbours(current)) {

                    int newCost = currentCost + neighbour.getCost();

                    if (!costSoFar.containsKey(neighbour) || newCost < costSoFar.get(neighbour)) {
                        neighbour.setPathCost(newCost);
                        costSoFar.put(neighbour, newCost);
                        pq.add(neighbour);
                    }
                }
            }
        }

        return costSoFar.getOrDefault(end, Integer.MAX_VALUE);
    }

    private static List<Node> getNeighbours(Node node) {
        return Stream.of(
                        getNodeAt(node.getRow() + 1, node.getCol()),
                        getNodeAt(node.getRow() - 1, node.getCol()),
                        getNodeAt(node.getRow(), node.getCol() + 1),
                        getNodeAt(node.getRow(), node.getCol() - 1))
                .flatMap(Optional::stream)
                .filter(n -> n.getHeight() <= node.getHeight() + 1)
                .toList();
    }

    public static Optional<Node> getNodeAt(int row, int col) {
        try {
            return Optional.ofNullable(board[row][col]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    static class Node implements Comparable<Node> {
        private final int row, col, cost, height;
        private int pathCost;

        public Node(int row, int col, int cost, int height) {
            this.row = row;
            this.col = col;
            this.cost = cost;
            this.pathCost = 0;
            this.height = height;
        }

        public int getPathCost() {
            return pathCost;
        }

        public void setPathCost(int pathCost) {
            this.pathCost = pathCost;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public int getCost() {
            return cost;
        }

        public int getHeight() {
            return height;
        }

        @Override
        public String toString() {
            return /*"(" + row + "," + col + ")*/"[" + height + "]";
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.pathCost, o.pathCost);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Node node = (Node) o;

            if (row != node.row) {
                return false;
            }
            return col == node.col;
        }

        @Override
        public int hashCode() {
            int result = row;
            result = 31 * result + col;
            return result;
        }
    }

    private static void printBoard() {
        System.out.println(Arrays.stream(board)
                .map(row -> Arrays.stream(row)
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")))
                .collect(Collectors.joining("\n")));
    }

}
