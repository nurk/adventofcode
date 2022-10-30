package year2015.puzzle11;

import java.util.Arrays;

import static util.Utils.*;

public class Puzzle11 {
    public static void main(String[] args) {
        //String password = incrementPassword("vzbxkghb");
        String password = incrementPassword("vzbxxyzz");

        while (!isValidPassword(password)) {
            password = incrementPassword(password);
        }

        System.out.println(password);
    }

    private static String incrementPassword(String input) {
        String[] letters = input.split("");

        int currentLetter = 7;
        int newIndex = (getAlphabetLetterIndex(letters[currentLetter]) + 1) % 26;
        letters[currentLetter] = ALPHABET_ARRAY[newIndex];

        while (newIndex == 0) {
            currentLetter--;
            newIndex = (getAlphabetLetterIndex(letters[currentLetter]) + 1) % 26;
            letters[currentLetter] = ALPHABET_ARRAY[newIndex];
        }

        return String.join("", letters);
    }

    private static boolean isValidPassword(String input) {
        return !containsIllegalCharacter(input) && containsIncreasingStraight(input) && containsTwoUniquePairs(input);
    }

    private static boolean containsIncreasingStraight(String input) {
        for (int i = 0; i < input.length() - 2; i++) {
            String[] letters = input.substring(i, i + 3).split("");
            int firstLetterIndex = getAlphabetLetterIndex(letters[0]);
            if (firstLetterIndex <= 23) {
                if (letters[1].equals(ALPHABET_ARRAY[firstLetterIndex + 1]) && letters[2].equals(ALPHABET_ARRAY[firstLetterIndex + 2])) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean containsIllegalCharacter(String input) {
        return Arrays.stream(new String[]{"i", "o", "l"})
                .anyMatch(input::contains);

    }

    private static boolean containsTwoUniquePairs(String input) {
        String firstPair = null;
        for (int i = 0; i < input.length() - 1; i++) {
            String[] letters = input.substring(i, i + 2).split("");
            if (letters[0].equals(letters[1])) {
                firstPair = letters[0];
            }
        }

        if (firstPair != null) {
            for (int i = 0; i < input.length() - 1; i++) {
                String[] letters = input.substring(i, i + 2).split("");
                if (!letters[0].equals(firstPair) && letters[0].equals(letters[1])) {
                    return true;
                }
            }
        }
        return false;
    }
}
