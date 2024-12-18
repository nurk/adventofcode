package year2024.puzzle13;

import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class Game {
    private final long aX, aY, bX, bY, x, y;

    public Game(List<String> gameLines) {
        /*
          Button A: X+94, Y+34
          Button B: X+22, Y+67
          Prize: X=8400, Y=5400
         */
        String buttonA = gameLines.get(0);
        String buttonB = gameLines.get(1);
        String result = gameLines.get(2);

        buttonA = StringUtils.remove(buttonA, "Button A: ");
        String[] buttonASplit = buttonA.split(", ");
        aX = Long.parseLong(StringUtils.remove(buttonASplit[0], "X+"));
        aY = Long.parseLong(StringUtils.remove(buttonASplit[1], "Y+"));

        buttonB = StringUtils.remove(buttonB, "Button B: ");
        String[] buttonBSplit = buttonB.split(", ");
        bX = Long.parseLong(StringUtils.remove(buttonBSplit[0], "X+"));
        bY = Long.parseLong(StringUtils.remove(buttonBSplit[1], "Y+"));

        result = StringUtils.remove(result, "Prize: ");
        String[] resultSplit = result.split(", ");
        x = Long.parseLong(StringUtils.remove(resultSplit[0], "X="));
        y = Long.parseLong(StringUtils.remove(resultSplit[1], "Y="));
    }

    public long solveA() {
        return solve(x, y);
    }

    public long solveB() {
        return solve(x + 10000000000000L, y + 10000000000000L);
    }

    public long solveCramersRuleA() {
        return cramersRule(x, y);
    }

    public long solveCramersRuleB() {
        return cramersRule(x + 10000000000000L, y + 10000000000000L);
    }

    //Brute force
    private long solve(long givenX, long givenY) {
        List<Pair<Long, Long>> abCombos = new ArrayList<>();
        LongStream.range(0, givenX / aX).parallel().forEach(i -> {
            long tmpX = givenX - i * aX;
            if (tmpX % bX == 0) {
                long aButton = i;
                long bButton = tmpX / bX;

                if (aButton * aY + bButton * bY == givenY) {
                    abCombos.add(new Pair<>(aButton, bButton));
                }
            }
        });

        if (abCombos.isEmpty()) {
            return 0;
        }
        return abCombos.stream()
                .map(p -> p.getValue0() * 3 + p.getValue1())
                .min(Long::compareTo)
                .orElseThrow();
    }

    //Cramer's rule  (needed the internet for this)
    //https://dev.to/grantdotdev/advent-of-code-24-day-13-claw-contraption-n2p
    private long cramersRule(long X, long Y) {
        long D = aX * bY - aY * bX;
        long Dx = X * bY - Y * bX;
        long Dy = Y * aX - X * aY;

        if (Dx % D == 0 && Dy % D == 0) {
            return 3 * (Dx / D) + Dy / D;
        }

        return 0;
    }

    @Override
    public String toString() {
        return "Game{" +
                "aX=" + aX +
                ", aY=" + aY +
                ", bX=" + bX +
                ", bY=" + bY +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
