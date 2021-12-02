package year2021.puzzle1;

import util.Utils;

import java.util.List;
import java.util.stream.IntStream;

public class Puzzle1 {
    public static void main(String[] args) {
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

        Integer aAlternative = IntStream.range(0, numbers.size() - 1)
                .mapToObj(i -> List.of(numbers.get(i), numbers.get(i + 1)))
                .reduce(0, (total, elements) -> (elements.get(1) > elements.get(0)) ? total + 1 : total, Integer::sum);
        System.out.println(aAlternative);

        System.out.println(resultB);

        Integer bAlternative = IntStream.range(0, numbers.size() - 3)
                .mapToObj(i -> List.of(numbers.get(i) + numbers.get(i + 1) + numbers.get(i + 2),
                        numbers.get(i + 1) + numbers.get(i + 2) + numbers.get(i + 3)))
                .reduce(0, (total, elements) -> (elements.get(1) > elements.get(0)) ? total + 1 : total, Integer::sum);
        System.out.println(bAlternative);

    }
}
