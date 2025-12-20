package year2023.puzzle10;

import util.Utils;

/**
 * Part A: 6806
 * Part B: 449
 */
public class Puzzle10 {

    public static void main(String[] args) {
        HotSpring hotSpring = new HotSpring(Utils.getInput("2023/input10.txt"));

        System.out.println("Part A: " + hotSpring.solve());

        System.out.println(hotSpring.toLoopString());

        System.out.println("Part B: " + hotSpring.countTilesWithinLoop());
    }
}
