package year2021.puzzle15;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

class Board {
    private final Node[][] board;

    public Board(List<String> input, int numberOfTiles) {
        int rows = input.size();
        int cols = input.get(0).length();
        board = new Node[rows * numberOfTiles][cols * numberOfTiles];
        IntStream.range(0, numberOfTiles).forEach(rowTile ->
                IntStream.range(rows * rowTile, rows * (rowTile + 1)).forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row % rows).split("")).toList();
                    IntStream.range(0, numberOfTiles).forEach(colTile ->
                            IntStream.range(cols * colTile, cols * (colTile + 1)).forEach(col -> {
                                int cost = (Integer.parseInt(columns.get(col % cols)) + rowTile +
                                        colTile - 1) % 9 + 1;
                                board[row][col] = new Node(row,
                                        col,
                                        cost);
                            }));
                }));
    }

    public long shortestPath(Node start, Node end) {
        //Interpretation of https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-in-java-using-priorityqueue/
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

        return costSoFar.get(end);
    }

    private List<Node> getNeighbours(Node node) {
        return Stream.of(
                        getNodeAt(node.getRow() + 1, node.getCol()),
                        getNodeAt(node.getRow() - 1, node.getCol()),
                        getNodeAt(node.getRow(), node.getCol() + 1),
                        getNodeAt(node.getRow(), node.getCol() - 1))
                .flatMap(Optional::stream)
                .toList();
    }

    public Optional<Node> getNodeAt(int row, int col) {
        try {
            return Optional.ofNullable(board[row][col]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    public int getRows() {
        return board.length;
    }

    public int getCols() {
        return board[0].length;
    }


    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> Arrays.stream(points)
                        .map(Node::toString)
                        .collect(joining(" ")))
                .collect(joining("\n"));
    }
}
