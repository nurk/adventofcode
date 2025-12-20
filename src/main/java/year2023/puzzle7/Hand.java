package year2023.puzzle7;

import lombok.Getter;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public enum Hand {

    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(3),
    TWO_PAIR(2),
    ONE_PAIR(1),
    HIGH_CARD(0);

    @Getter
    private final Integer rank;

    Hand(Integer rank) {
        this.rank = rank;
    }

    public static Hand fromCards(List<Card> cards) {
        Map<Card, Long> cardCount = cards.stream()
                .collect(groupingBy(card -> card, counting()));

        if (cardCount.containsValue(5L)) {
            return FIVE_OF_A_KIND;
        } else if (cardCount.containsValue(4L)) {
            return FOUR_OF_A_KIND;
        } else if (cardCount.containsValue(3L) && cardCount.containsValue(2L)) {
            return FULL_HOUSE;
        } else if (cardCount.containsValue(3L)) {
            return THREE_OF_A_KIND;
        } else if (cardCount.values().stream().filter(count -> count == 2L).count() == 2) {
            return TWO_PAIR;
        } else if (cardCount.containsValue(2L)) {
            return ONE_PAIR;
        } else {
            return HIGH_CARD;
        }
    }
}
