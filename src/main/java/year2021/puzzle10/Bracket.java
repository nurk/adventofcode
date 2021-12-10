package year2021.puzzle10;

import java.util.Arrays;
import java.util.List;

public enum Bracket {
    PARENTHESES("()", 3, 1),
    BRACKET("[]", 57, 2),
    ACCOLADE("{}", 1197, 3),
    CHEVRON("<>", 25137, 4);

    private final String value;
    private final int scoreA;
    private final int scoreB;

    Bracket(String value, int scoreA, int scoreB) {
        this.value = value;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
    }

    public int getScoreA() {
        return scoreA;
    }

    public int getScoreB() {
        return scoreB;
    }

    public String getPair() {
        return value;
    }

    public static List<String> getClosingList() {
        return Arrays.stream(Bracket.values())
                .map(Bracket::getPair)
                .map(pair -> pair.split(""))
                .map(pair -> pair[1])
                .toList();
    }

    public static Bracket from(String s) {
        return Arrays.stream(Bracket.values())
                .filter(b -> b.value.contains(s))
                .findFirst()
                .orElseThrow();
    }
}
