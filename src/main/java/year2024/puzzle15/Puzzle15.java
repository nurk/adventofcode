package year2024.puzzle15;

import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 1457740
 * Part B: 1467145
 */
public class Puzzle15 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2024/input15.txt", (s) -> s);

        BoardA boardA = new BoardA(new ArrayList<>(input));
        boardA.executeMoves();

        System.out.println("Part A: " + boardA.getCoordinate());

        BoardB boardB = new BoardB(new ArrayList<>(input));
        boardB.executeMoves();
        System.out.println("Part B: " + boardB.getCoordinate());
    }
}
