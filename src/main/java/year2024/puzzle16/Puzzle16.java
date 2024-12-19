package year2024.puzzle16;

import util.Utils;

import java.util.List;

/**
 * Part A: 107512
 */
public class Puzzle16 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2024/input16.txt", (s) -> s);

        Maze maze = new Maze(input);
        MazeB mazeB = new MazeB(input);

        long cost = maze.solve();
        System.out.println("Part A: " + cost);
        System.out.println("Part B: " + mazeB.solve(cost));
    }
}
