package year2022.puzzle12;

import util.Utils;

import java.util.*;
import java.util.stream.Stream;

public class Puzzle12 {
    private static Node[][] board;
    private static Node start, end;

    //PartA: 380
    //PartB: 375
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
                .filter(n -> n.height() == 0)
                .map(s -> shortestPath(s, end))
                .sorted()
                .findFirst()
                .orElse(-1L));
    }

    public static long shortestPath(Node start, Node end) {
        Queue<Node> pq = new ArrayDeque<>(board.length * board[0].length);
        pq.add(start);

        Map<Node, Integer> costSoFar = new HashMap<>();

        costSoFar.put(start, 0);

        while (!pq.isEmpty()) {
            Node current = pq.remove();

            if (current.equals(end)) {
                break;
            }

            int currentCost = costSoFar.get(current);

            for (Node neighbour : getNeighbours(current)) {
                int newCost = currentCost + neighbour.cost();

                if (!costSoFar.containsKey(neighbour) || newCost < costSoFar.get(neighbour)) {
                    costSoFar.put(neighbour, newCost);
                    pq.add(neighbour);
                }
            }
        }

        return costSoFar.getOrDefault(end, Integer.MAX_VALUE);
    }

    private static List<Node> getNeighbours(Node node) {
        return Stream.of(
                        getNodeAt(node.row() + 1, node.col()),
                        getNodeAt(node.row() - 1, node.col()),
                        getNodeAt(node.row(), node.col() + 1),
                        getNodeAt(node.row(), node.col() - 1))
                .flatMap(Optional::stream)
                .filter(n -> n.height() <= node.height() + 1)
                .toList();
    }

    public static Optional<Node> getNodeAt(int row, int col) {
        try {
            return Optional.ofNullable(board[row][col]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    record Node(int row, int col, int cost, int height) {
    }
}
