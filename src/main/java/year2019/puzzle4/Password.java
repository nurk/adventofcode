package year2019.puzzle4;

import java.util.HashMap;
import java.util.Map;

public class Password {
    private int password;

    public Password(int password) {
        this.password = password;
    }

    public boolean isValidA() {
        return isIncreasing() && hasSequence();
    }

    public boolean isValidB() {
        return isIncreasing() && hasASequenceOf2();
    }

    public boolean isIncreasing() {
        char[] split = String.valueOf(password).toCharArray();

        String previousChar = "0";
        for (char s : split) {
            if (Integer.parseInt(String.valueOf(s)) < Integer.parseInt(previousChar)) {
                return false;
            }
            previousChar = String.valueOf(s);
        }
        return true;
    }

    public boolean hasSequence() {
        char[] split = String.valueOf(password).toCharArray();

        String previousChar = null;
        for (int i = 0; i < split.length; i++) {
            String s = String.valueOf(split[i]);
            if (previousChar != null && previousChar.equals(s)) {
                return true;
            }
            previousChar = s;
        }
        return false;
    }

    public boolean hasASequenceOf2() {
        Map<String, Integer> counter = new HashMap<>();

        String.valueOf(password).chars()
                .forEach(c -> counter.merge(String.valueOf(c), 1, Integer::sum));

        return counter.containsValue(2);

    }
}
