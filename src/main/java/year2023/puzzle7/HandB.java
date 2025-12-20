package year2023.puzzle7;

import lombok.Getter;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public enum HandB {

    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(3),
    TWO_PAIR(2),
    ONE_PAIR(1),
    HIGH_CARD(0);

    @Getter
    private final Integer rank;

    HandB(Integer rank) {
        this.rank = rank;
    }

    public static HandB fromCards(List<CardB> cards) {
        Map<CardB, Long> cardCount = cards.stream()
                .collect(groupingBy(card -> card, counting()));

        int numberOfJokers = cardCount.getOrDefault(CardB.JOKER, 0L).intValue();

        if (cardCount.containsValue(5L)) {
            return FIVE_OF_A_KIND;
        } else if (cardCount.containsValue(4L)) {
            if (numberOfJokers > 0) {
                return FIVE_OF_A_KIND;
            }
            return FOUR_OF_A_KIND;
        } else if (cardCount.containsValue(3L) && cardCount.containsValue(2L)) {
            if (numberOfJokers == 3 || numberOfJokers == 2) {
                return FIVE_OF_A_KIND;
            }
            return FULL_HOUSE;
        } else if (cardCount.containsValue(3L)) {
            if (numberOfJokers == 3) {
                return FOUR_OF_A_KIND;
            } else if (numberOfJokers == 2) {
                return FIVE_OF_A_KIND;
            } else if (numberOfJokers == 1) {
                return FOUR_OF_A_KIND;
            }
            return THREE_OF_A_KIND;
        } else if (cardCount.values().stream().filter(count -> count == 2L).count() == 2) {
            if (numberOfJokers == 2) {
                return FOUR_OF_A_KIND;
            } else if (numberOfJokers == 1) {
                return FULL_HOUSE;
            }
            return TWO_PAIR;
        } else if (cardCount.containsValue(2L)) {
            if (numberOfJokers != 0) {
                return THREE_OF_A_KIND;
            }
            return ONE_PAIR;
        } else {
            if (numberOfJokers != 0) {
                return ONE_PAIR;
            }
            return HIGH_CARD;
        }
    }
}
