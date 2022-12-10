package year2022.puzzle10;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Puzzle10 {

    private static int x = 1;
    private static int cycle = 1;
    private static int score = 0;
    private static String sprite = "##" + Stream.generate(() -> ".").limit(37).collect(Collectors.joining());
    private static final List<List<String>> result = new ArrayList<>(Stream.generate(() -> new ArrayList<String>())
            .limit(6)
            .toList());

    public static void main(String[] args) {
        for (String s : Utils.getInput("2022/input10.txt")) {
            if (StringUtils.equals(s, "noop")) {
                doCycle();
            } else {
                int d = Integer.parseInt(s.split(" ")[1]);
                doCycle();
                x = x + d;
                doCycle();
            }
        }

        System.out.println(score);
        System.out.println(result.stream()
                .map(row -> String.join("", row))
                .collect(Collectors.joining("\n")));
    }

    private static void drawPixel() {
        int position = (cycle - 1) % 40;
        result.get((cycle - 1) / 40).add(StringUtils.substring(sprite, position, position + 1));
    }

    public static void doCycle() {
        drawPixel();
        cycle++;
        if (List.of(20, 60, 100, 140, 180, 220).contains(cycle)) {
            score = score + x * cycle;
        }
        resetSprite();
    }

    private static void resetSprite() {
        if (x == -1) {
            sprite = "##" + Stream.generate(() -> ".").limit(38).collect(Collectors.joining());
        } else {
            sprite = Stream.generate(() -> ".")
                    .limit(x - 1)
                    .collect(Collectors.joining()) + "###" + Stream.generate(() -> ".").limit(38 - x).collect(
                    Collectors.joining());
        }
    }
}
