package year2023.puzzle4;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Card {

    private final Integer cardId;
    private final List<Integer> winningNumbers;
    private final List<Integer> scratchedNumbers;

    public Card(String line) {
        line = StringUtils.replace(line, "  ", " ");
        cardId = Integer.parseInt(StringUtils.substringAfter(StringUtils.substringBefore(line, ":"), "Card ").trim());
        line = StringUtils.substringAfter(line, ": ");
        String[] split = line.split(" \\| ");

        winningNumbers = Stream.of(split[0].split(" "))
                .map(Integer::parseInt)
                .toList();

        scratchedNumbers = Stream.of(split[1].split(" "))
                .map(Integer::parseInt)
                .toList();
    }

    public int getScore() {
        int score = 0;
        for (Integer scratchedNumber : scratchedNumbers) {
            if (winningNumbers.contains(scratchedNumber)) {
                if (score == 0) {
                    score = 1;
                } else {
                    score = score * 2;
                }
            }
        }
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Card card = (Card) o;

        return cardId.equals(card.cardId);
    }

    @Override
    public int hashCode() {
        return cardId.hashCode();
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardId=" + cardId + ", " +
                "winningNumbers=" + winningNumbers.stream().map(Object::toString).collect(Collectors.joining(",")) +
                ", scratchedNumbers=" + scratchedNumbers.stream()
                .map(Object::toString)
                .collect(Collectors.joining(",")) +
                '}';
    }
}
