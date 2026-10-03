package year2016.puzzle22;

import util.Utils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Part A: 952
 * Part B: 181 (AI generated)
 */
public class Puzzle22 {
    static void main() {
        List<Node> nodes = Utils.getInput("2016/input22.txt", Node::new);

        Set<ViablePair> viablePairs = new HashSet<>();

        for (Node nodeA : nodes) {
            for (Node nodeB : nodes) {
                if (nodeA.equals(nodeB)) {
                    continue;
                }
                if (nodeA.getUsed() == 0) {
                    continue;
                }
                if (nodeA.getUsed() <= nodeB.getAvailable()) {
                    viablePairs.add(new ViablePair(nodeA, nodeB));
                }
            }
        }

        System.out.println("Part A: " + viablePairs.size());
        System.out.println("Part B: " + partB(nodes));
    }

    private static int partB(List<Node> nodes) {
        int maxX = nodes.stream().mapToInt(Node::getX).max().orElseThrow();
        int maxY = nodes.stream().mapToInt(Node::getY).max().orElseThrow();

        Node[][] grid = new Node[maxX + 1][maxY + 1];
        for (Node node : nodes) {
            grid[node.getX()][node.getY()] = node;
        }

        Node hole = nodes.stream().filter(n -> n.getUsed() == 0).findFirst().orElseThrow();

        // Target data starts at the top-right corner (maxX, 0); it needs to end up at (0, 0).
        int targetX = maxX;
        int targetY = 0;

        // BFS the empty node's shortest path to the cell just left of the target data,
        // never stepping onto the target cell itself (that would move the data prematurely)
        // and never stepping onto a node whose data is too large to fit in the current hole.
        int distanceToTargetLeft = bfsHoleDistance(grid, hole.getX(), hole.getY(), targetX - 1, targetY, targetX, targetY);

        // Once the hole sits left of the target, shifting the target one step left costs
        // 5 moves (walk the hole around and swap), except the very last shift into (0, 0)
        // which costs a single move.
        return distanceToTargetLeft + 5 * (targetX - 1) + 1;
    }

    private static int bfsHoleDistance(Node[][] grid, int startX, int startY, int goalX, int goalY, int blockedX, int blockedY) {
        int width = grid.length;
        int height = grid[0].length;

        boolean[][] visited = new boolean[width][height];
        Deque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{startX, startY, 0});
        visited[startX][startY] = true;

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int steps = current[2];

            if (x == goalX && y == goalY) {
                return steps;
            }

            for (int[] dir : directions) {
                int nx = x + dir[0];
                int ny = y + dir[1];

                if (nx < 0 || nx >= width || ny < 0 || ny >= height) {
                    continue;
                }
                if (visited[nx][ny]) {
                    continue;
                }
                if (nx == blockedX && ny == blockedY) {
                    continue;
                }
                if (grid[nx][ny].getUsed() > grid[x][y].getSize()) {
                    continue;
                }

                visited[nx][ny] = true;
                queue.add(new int[]{nx, ny, steps + 1});
            }
        }

        throw new IllegalStateException("No path found for the empty node");
    }
}
