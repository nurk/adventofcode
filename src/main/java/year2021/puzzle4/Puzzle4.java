package year2021.puzzle4;

import com.google.common.collect.Lists;
import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Puzzle4 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2021/input4.txt", (s) -> s));

        partA(new ArrayList<>(input));
        partB(new ArrayList<>(input));
    }

    private static void partA(List<String> input) {
        List<Integer> randomInput = new ArrayList<>(Arrays.stream(input.remove(0).split(","))
                .map(Integer::valueOf).toList());

        List<Board> boards = Lists.partition(input, 6).stream()
                .map(Board::new).toList();

        var ref = new Object() {
            int drawnNumber;
        };

        while (boards.stream().noneMatch(Board::isBingo)) {
            ref.drawnNumber = randomInput.remove(0);
            boards.forEach(b -> b.mark(ref.drawnNumber));
        }

        System.out.println(boards.stream()
                .filter(Board::isBingo)
                .findFirst()
                .orElseThrow()
                .getUnmarkedSum() * ref.drawnNumber);
    }

    private static void partB(List<String> input) {
        List<Integer> randomInput = new ArrayList<>(Arrays.stream(input.remove(0).split(","))
                .map(Integer::valueOf).toList());

        List<Board> boards = Lists.partition(input, 6).stream()
                .map(Board::new).toList();

        var ref = new Object() {
            Board board;
            int drawnNumber;
        };

        while (!randomInput.isEmpty() || !boards.isEmpty()) {
            int drawnNumber = randomInput.remove(0);
            boards.forEach(b -> b.mark(drawnNumber));
            boards = boards.stream()
                    .peek(b -> {
                        if (b.isBingo()) {
                            ref.board = b;
                            ref.drawnNumber = drawnNumber;
                        }
                    })
                    .filter(Predicate.not(Board::isBingo))
                    .toList();

        }

        System.out.println(ref.board.getUnmarkedSum() * ref.drawnNumber);
    }
}
