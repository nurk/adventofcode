package year2022.puzzle2;

import org.apache.commons.lang3.tuple.Pair;
import util.Utils;

import java.util.Arrays;

public class Puzzle2 {
    public static void main(String[] args) {
        System.out.println(Utils.getInput("2022/input2.txt", s -> Pair.of(s.split(" ")[0], s.split(" ")[1]))
                .stream()
                .map(pair -> {
                    Choice other = Choice.fromValue(pair.getLeft());
                    Choice me = Choice.fromValue(pair.getRight());
                    if (me == other) {
                        return me.score + 3;
                    } else if (me.winsFrom.equals(other.other)) {
                        return me.score + 6;
                    } else {
                        return me.score;
                    }
                })
                .reduce(0, Integer::sum));

        System.out.println(Utils.getInput("2022/input2.txt", s -> Pair.of(s.split(" ")[0], s.split(" ")[1]))
                .stream()
                .map(pair -> {
                    Choice other = Choice.fromValue(pair.getLeft());
                    Choice me;
                    if (pair.getRight().equals("X")) {
                        //need to lose
                        me = Choice.fromValue(other.winsFrom);
                    } else if (pair.getRight().equals("Y")) {
                        //draw
                        me = other;
                    } else {
                        //need to win
                        me = Choice.fromValue(other.loosesFrom);
                    }
                    if (me == other) {
                        return me.score + 3;
                    } else if (me.winsFrom.equals(other.other)) {
                        return me.score + 6;
                    } else {
                        return me.score;
                    }
                })
                .reduce(0, Integer::sum));
    }

    public enum Choice {
        ROCK("A", "X", 1, "C", "B"),
        PAPER("B", "Y", 2, "A", "C"),
        SCISSOR("C", "Z", 3, "B", "A");

        private final String other, me;
        private final int score;
        private final String winsFrom;
        private final String loosesFrom;

        Choice(String other, String me, int score, String winsFrom, String loosesFrom) {
            this.other = other;
            this.me = me;
            this.score = score;
            this.winsFrom = winsFrom;
            this.loosesFrom = loosesFrom;
        }

        public static Choice fromValue(String value) {
            return Arrays.stream(Choice.values())
                    .filter(g -> g.me.equals(value) || g.other.equals(value))
                    .findAny()
                    .orElseThrow();
        }
    }
}
