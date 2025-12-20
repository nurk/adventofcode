package year2023.puzzle7;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class Bet implements Comparable<Bet> {
    private final List<Card> cards;
    @Getter
    private final long bid;
    private final Hand hand;

    public Bet(String line) {
        String[] split = line.split(" ");
        this.bid = Long.parseLong(split[1]);
        this.cards = Arrays.stream(split[0].split(""))
                .map(Card::fromName)
                .toList();
        this.hand = Hand.fromCards(cards);
    }

    @Override
    public int compareTo(Bet o) {
        if (this.hand.equals(o.hand)) {
            for (int i = 0; i < this.cards.size(); i++) {
                int compare = Integer.compare(this.cards.get(i).getStrength(), o.cards.get(i).getStrength());
                if (compare != 0) {
                    return compare;
                }
            }
            return 0;
        } else {
            return Integer.compare(this.hand.getRank(), o.hand.getRank());
        }
    }

    @Override
    public String toString() {
        return "Bet{" +
                "cards=" + cards +
                ", bid=" + bid +
                ", hand=" + hand +
                '}';
    }
}
