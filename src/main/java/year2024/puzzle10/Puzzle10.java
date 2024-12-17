package year2024.puzzle10;

import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 816
 * Part B: 1960
 */
public class Puzzle10 {

    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2024/input10.txt", (s) -> s));

        BoardA boardA = new BoardA(input);
        System.out.println("Part A: " + boardA.getTotalNumberOfTrails());

        BoardB boardB = new BoardB(input);
        System.out.println("Part B: " + boardB.getTotalRating());
    }
}

