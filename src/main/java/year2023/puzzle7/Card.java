package year2023.puzzle7;

import lombok.Getter;

public enum Card {
    ACE("A", 100),
    KING("K", 95),
    QUEEN("Q", 90),
    JACK("J", 85),
    TEN("T", 80),
    NINE("9", 75),
    EIGHT("8", 70),
    SEVEN("7", 65),
    SIX("6", 60),
    FIVE("5", 55),
    FOUR("4", 50),
    THREE("3", 45),
    TWO("2", 40);

    private final String name;
    @Getter
    private final Integer strength;

    Card(String name, Integer strength) {
        this.name = name;
        this.strength = strength;
    }

    public static Card fromName(String name) {
        for (Card card : values()) {
            if (card.name.equals(name)) {
                return card;
            }
        }
        throw new IllegalArgumentException("No card with name: " + name);
    }
}
