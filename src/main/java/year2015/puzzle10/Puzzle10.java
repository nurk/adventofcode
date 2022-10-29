package year2015.puzzle10;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzle10 {
    private static final Pattern pattern = Pattern.compile("(.)\\1*");

    public static void main(String[] args) {
        String input = "1113122113";
        for (int i = 0; i < 50; i++) {
            input = convert(input);
        }

        System.out.println(input.length());
    }

    private static String convert(String input) {
        StringBuilder result = new StringBuilder();
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String group = matcher.group();
            result.append(group.length());
            result.append(group.charAt(0));
        }

        return result.toString();
    }
}
