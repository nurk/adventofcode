package year2021.puzzle21;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.ArrayList;
import java.util.List;

public class Puzzle21 {

    private static int diceValue = 0;

    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2021/input21.txt", (s) -> s));

        var p1 = new Object() {
            int position = Integer.parseInt(StringUtils.substringAfter(input.get(0), ": "));
            int score = 0;
        };

        var p2 = new Object() {
            int position = Integer.parseInt(StringUtils.substringAfter(input.get(1), ": "));
            int score = 0;
        };

        int diceRolls = 0;

        while (true) {
            diceRolls = diceRolls + 3;
            int rolled = getDiceRolls();

            p1.position = newPosition(p1.position, rolled);
            p1.score = p1.score + p1.position;

            if (p1.score >= 1000) {
                break;
            }


            diceRolls = diceRolls + 3;
            rolled = getDiceRolls();

            p2.position = newPosition(p2.position, rolled);
            p2.score = p2.score + p2.position;

            if (p2.score >= 1000) {
                break;
            }
        }

        System.out.println(Math.min(p1.score, p2.score) * diceRolls);
    }

    private static int getDiceRolls() {
        return getNextDiceRoll() + getNextDiceRoll() + getNextDiceRoll();
    }

    private static int getNextDiceRoll() {
        diceValue = (diceValue % 100) + 1;
        return diceValue;
    }

    private static int newPosition(int oldPosition, int rolled) {
        return (oldPosition + rolled - 1) % 10 + 1;
    }
}
