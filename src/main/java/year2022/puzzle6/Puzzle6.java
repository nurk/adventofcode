package year2022.puzzle6;

import util.Utils;

import java.util.HashSet;
import java.util.Set;

public class Puzzle6 {
    public static void main(String[] args) {
        Utils.getInput("2022/input6.txt").stream()
                .map(s -> getUniqueLetterGroupIndex(s, 4))
                .forEach(System.out::println);

        Utils.getInput("2022/input6.txt").stream()
                .map(s -> getUniqueLetterGroupIndex(s, 14))
                .forEach(System.out::println);
    }

    private static int getUniqueLetterGroupIndex(String s, int groupSize) {
        for (int i = 0; i < s.length(); i++) {
            Set<Character> uniqueLetters = new HashSet<>();
            for (int j = i; j < i + groupSize; j++) {
                uniqueLetters.add(s.charAt(j));
            }
            if (uniqueLetters.size() == groupSize) {
                return i + groupSize;
            }
        }
        return 0;
    }
}
