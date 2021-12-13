package year2018.puzzle5;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.Arrays;

public class Puzzle5 {
    private final static String[] alphabet = "abcdefghijklmnopqrstuvwxyz".split("");

    public static void main(String[] args) {
        String input = Utils.getInput("2018/input5.txt", (s) -> s).get(0);

        System.out.println("Part A: " + doReactions(input).length());

        System.out.println("Part B: " + Arrays.stream(alphabet)
                .map(letter -> RegExUtils.removeAll(input, "(?i)" + letter))
                .map(Puzzle5::doReactions)
                .map(String::length)
                .mapToInt(i -> i)
                .min()
                .orElseThrow());
    }

    private static String doReactions(String input) {
        var ref = new Object() {
            boolean found = true;
            String text = input;
        };

        while (ref.found) {
            ref.found = false;
            Arrays.stream(alphabet)
                    .forEach(letter -> {
                        if (ref.text.contains(letter + letter.toUpperCase())) {
                            ref.found = true;
                            ref.text = StringUtils.remove(ref.text, letter + letter.toUpperCase());
                        }
                        if (ref.text.contains(letter.toUpperCase() + letter)) {
                            ref.found = true;
                            ref.text = StringUtils.remove(ref.text, letter.toUpperCase() + letter);
                        }

                    });
        }
        return ref.text;
    }

}
