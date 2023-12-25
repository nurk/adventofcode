package year2023.puzzle4;

import util.Utils;

import java.util.List;

/**
 * Part A: 32609
 */
public class Puzzle4 {

    public static void main(String[] args) {
        List<Card> cards = Utils.getInput("2023/input4.txt").stream()
                .map(Card::new)
                .toList();

        System.out.println("Part A: " + cards.stream()
                .map(Card::getScore)
                .mapToInt(value -> value)
                .sum());
    }
}
