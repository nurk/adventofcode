package year2021.puzzle1;

import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Puzzle1 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<Integer> numbers = new ArrayList<>();
        Files.readAllLines(Utils.getInputPath("2021/input1.txt")).forEach(
                line -> numbers.add(Integer.valueOf(line))
        );

        int resultA = 0;

        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) > numbers.get(i - 1)) {
                resultA++;
            }
        }

        int resultB = 0;

        for (int i = 3; i < numbers.size(); i++) {
            if ((numbers.get(i) + numbers.get(i - 1) + numbers.get(i - 2)) > (numbers.get(i - 1) + numbers.get(i - 2) + numbers.get(
                    i - 3))) {
                resultB++;
            }
        }

        System.out.println(resultA);
        System.out.println(resultB);
    }
}
