package year2024.puzzle8;

import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 214
 * Part B: 809
 */
public class Puzzle8 {

    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2024/input8.txt", (s) -> s));

        Board b = new Board(input);
        b.markAntinodes(false);
        System.out.println("Part A: " + b.countAntinodes());

        b = new Board(input);
        b.markAntinodes(true);
        System.out.println("Part B: " + b.countAntinodesB());
    }

}
