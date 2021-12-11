package year2021.puzzle11;

import util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Puzzle11 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2021/input11.txt", (s) -> s));

        partA(input);
        partB(input);
    }

    private static void partB(List<String> input) {
        int step = 1;
        Board board = new Board(input);

        while (board.doStep() != 100) {
            step++;
        }
        System.out.println(step);
    }

    private static void partA(List<String> input) {
        Board board = new Board(input);
        System.out.println(IntStream.rangeClosed(1, 100)
                .map(i -> board.doStep())
                .sum());
    }
}
