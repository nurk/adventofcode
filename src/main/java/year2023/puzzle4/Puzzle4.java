package year2023.puzzle4;

import util.Utils;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Part A: 32609
 * Part B: 14624680
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


        SortedMap<Card, Integer> sortedCards = new TreeMap<>();
        cards.forEach(card -> sortedCards.put(card, 0));

        sortedCards.keySet().forEach((card) -> {
            int amountOfWinningNumbers = card.getAmountOfWinningNumbers();
            sortedCards.computeIfPresent(card, (card1, integer1) -> integer1 + 1);

            if (amountOfWinningNumbers > 0) {
                for (int i = card.getCardId() + 1; i <= card.getCardId() + amountOfWinningNumbers; i++) {
                    int finalI = i;
                    cards.stream()
                            .filter(card1 -> card1.getCardId().equals(finalI))
                            .findFirst()
                            .ifPresent(copy -> sortedCards.computeIfPresent(copy,
                                    (card1, integer1) -> integer1 + sortedCards.get(card)));
                }
            }
        });

        System.out.println("Part B: " + sortedCards.values().stream().mapToInt(value -> value).sum());
    }
}
