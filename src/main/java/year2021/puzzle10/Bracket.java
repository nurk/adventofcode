package year2021.puzzle10;

import java.util.Arrays;
import java.util.List;

public enum Bracket {
    PARENTHESES(new String[]{"(", ")"}, 3, 1),
    BRACKET(new String[]{"[", "]"}, 57, 2),
    ACCOLADE(new String[]{"{", "}"}, 1197, 3),
    CHEVRON(new String[]{"<", ">"}, 25137, 4);

    private final String[] strings;
    private final int score;
    private final int scoreB;

    Bracket(String[] strings, int score, int scoreB) {
        this.strings = strings;
        this.score = score;
        this.scoreB = scoreB;
    }

    public int getScore() {
        return score;
    }

    public int getScoreB() {
        return scoreB;
    }

    public static Bracket fromClosing(String s) {
        return Arrays.stream(Bracket.values())
                .filter(b -> b.getClosing().equals(s))
                .findFirst()
                .orElseThrow();
    }

    public static Bracket fromOpening(String s) {
        return Arrays.stream(Bracket.values())
                .filter(b -> b.getOpening().equals(s))
                .findFirst()
                .orElseThrow();
    }

    public String getPair() {
        return strings[0] + strings[1];
    }

    private String getClosing() {
        return strings[1];
    }

    private String getOpening() {
        return strings[0];
    }

    public static List<String> getClosingList() {
        return Arrays.stream(Bracket.values())
                .map(Bracket::getClosing)
                .toList();
    }
}
