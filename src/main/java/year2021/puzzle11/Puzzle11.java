package year2021.puzzle11;

import util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class Puzzle11 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2021/input11.txt", (s) -> s));

        partA(input);
        partB(input);
    }

    private static void partB(List<String> input) {
        Board board = new Board(input);

        /*int step = 0;
        while (!board.isSynchronized()) {
            board.doStep();
            step++;
        }
        System.out.println(step);*/

        System.out.println(LongStream.iterate(1, step -> !board.isSynchronized(), step -> {
                    board.doStep();
                    return ++step;
                })
                .max().orElseThrow());
    }

    private static void partA(List<String> input) {
        Board board = new Board(input);

        System.out.println(LongStream.rangeClosed(1, 100)
                .peek(i -> board.doStep())
                .map(i -> board.countOctopiThatFlashed())
                .sum());
    }
}
