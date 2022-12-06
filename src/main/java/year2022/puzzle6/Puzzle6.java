package year2022.puzzle6;

import util.Utils;

import java.util.HashSet;
import java.util.Set;

public class Puzzle6 {
    public static void main(String[] args) {
        Utils.getInput("2022/input6.txt").stream()
                .map(s -> {
                    for (int i = 0; i < s.length(); i++) {
                        Set<Character> uniqueLetters = new HashSet<>();
                        for (int j = i; j < i + 4; j++) {
                            uniqueLetters.add(s.charAt(j));
                        }
                        if (uniqueLetters.size() == 4) {
                            return i + 4;
                        }
                    }
                    return 0;
                })
                .forEach(System.out::println);

        Utils.getInput("2022/input6.txt").stream()
                .map(s -> {
                    for (int i = 0; i < s.length(); i++) {
                        Set<Character> uniqueLetters = new HashSet<>();
                        for (int j = i; j < i + 14; j++) {
                            uniqueLetters.add(s.charAt(j));
                        }
                        if (uniqueLetters.size() == 14) {
                            return i + 14;
                        }
                    }
                    return 0;
                })
                .forEach(System.out::println);
    }
}
