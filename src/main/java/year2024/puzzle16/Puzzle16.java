package year2024.puzzle16;

import util.Utils;

import java.util.List;

/**
 * Part A: 107512
 */
public class Puzzle16 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2024/input16-test.txt", (s) -> s);

        Maze maze = new Maze(input);
        MazeB2 mazeB2 = new MazeB2(input);

        long cost = maze.solve();
        System.out.println("Part A: " + cost);
        System.out.println("Part B: " + mazeB2.solve(cost));
    }
}
