package year2023.puzzle7;

import lombok.Getter;

public enum CardB {
    ACE("A", 100),
    KING("K", 95),
    QUEEN("Q", 90),
    TEN("T", 80),
    NINE("9", 75),
    EIGHT("8", 70),
    SEVEN("7", 65),
    SIX("6", 60),
    FIVE("5", 55),
    FOUR("4", 50),
    THREE("3", 45),
    TWO("2", 40),
    JOKER("J", 35);

    private final String name;
    @Getter
    private final Integer strength;

    CardB(String name, Integer strength) {
        this.name = name;
        this.strength = strength;
    }

    public static CardB fromName(String name) {
        for (CardB card : values()) {
            if (card.name.equals(name)) {
                return card;
            }
        }
        throw new IllegalArgumentException("No card with name: " + name);
    }
}
