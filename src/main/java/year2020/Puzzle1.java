package year2020;

import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Puzzle1 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<Integer> numbers = new ArrayList<>();
        Files.readAllLines(Utils.getInputPath("2020/input1a.txt")).forEach(
                line -> {
                    numbers.add(Integer.valueOf(line));
                }
        );

        puzzleA(numbers);
        puzzleB(numbers);
    }

    private static void puzzleA(List<Integer> numbers) {
        for (int i = 0; i < numbers.size() - 1; i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                if (numbers.get(i) + numbers.get(j) == 2020) {
                    System.out.println("Puzzle A: " + numbers.get(i) * numbers.get(j));
                    return;
                }
            }
        }
    }

    private static void puzzleB(List<Integer> numbers) {
        for (int i = 0; i < numbers.size() - 2; i++) {
            for (int j = i + 1; j < numbers.size() - 1; j++) {
                for (int k = i + 1; k < numbers.size(); k++) {
                    if (numbers.get(i) + numbers.get(j) + numbers.get(k) == 2020) {
                        System.out.println("Puzzle B: " + numbers.get(i) * numbers.get(j) * numbers.get(k));
                        return;
                    }
                }
            }
        }
    }
}
