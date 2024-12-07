package year2024.puzzle4;

import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 2447
 * Part B: 1868
 */
public class Puzzle4 {

    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2024/input4.txt", (s) -> s));

        Board b = new Board(input);

        System.out.println("Part A: " + b.findXmas());
        System.out.println("Part B: " + b.findMas());
    }
}
