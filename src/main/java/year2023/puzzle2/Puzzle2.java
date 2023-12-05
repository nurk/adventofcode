package year2023.puzzle2;

import util.Utils;

import java.util.concurrent.atomic.AtomicLong;

/*
Part A: 2913
Part B: 55593
 */
public class Puzzle2 {
    public static void main(String[] args) {
        partA();
    }

    private static void partA() {
        AtomicLong resultA = new AtomicLong(0);
        AtomicLong resultB = new AtomicLong(0);
        Utils.getInput("2023/input2.txt").forEach(line -> {
            Game game = new Game(line);
            if (game.isPossible(12, 13, 14)) {
                resultA.getAndAdd(game.getGameId());
            }
            resultB.getAndAdd(game.getPower());
        });
        System.out.println("Part A: " + resultA.get());
        System.out.println("Part B: " + resultB.get());
    }


}
