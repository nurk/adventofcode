package year2021.puzzle1;

import util.Utils;

import java.util.List;

public class Puzzle1 {
    public static void main(String[] args) throws Exception {
        List<Integer> numbers = Utils.getInput("2021/input1.txt", Integer::valueOf);

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
