package year2019.puzzle16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Part A: 82435530
 */
public class Puzzle16 {
    static void main() {

        String input = "59766832516471105169175836985633322599038555617788874561522148661927081324685821180654682056538815716097295567894852186929107230155154324411726945819817338647442140954601202408433492208282774032110720183977662097053534778395687521636381457489415906710702497357756337246719713103659349031567298436163261681422438462663511427616685223080744010014937551976673341714897682634253850270219462445161703240957568807600494579282412972591613629025720312652350445062631757413159623885481128914333982571503540357043736821931054029305931122179293220911720263006705242490442826574028623201238659548887822088996956559517179003476743001815465428992906356931239533104";

        partA(input);
        partB(input);
    }

    private static void partB(String input) {
        List<Integer> originalDigits = Arrays.stream(input.split(""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        List<Integer> digits = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            digits.addAll(originalDigits);
        }

        for (int i = 0; i < 100; i++) {
            applyPhase(digits);
        }

        System.out.println("Part B: " + getFirst8Digits(digits));
    }

    private static void partA(String input) {
        List<Integer> digits = Arrays.stream(input.split(""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        for (int i = 0; i < 100; i++) {
            applyPhase(digits);
        }

        System.out.println("Part A: " + getFirst8Digits(digits));
    }

    private static void applyPhase(List<Integer> digits) {
        for (int i = 0; i < digits.size(); i++) {
            SequenceGenerator s = new SequenceGenerator(i);
            int sum = 0;

            for (Integer digit : digits) {
                sum += digit * s.next();
            }
            digits.set(i, Math.abs(sum) % 10);
        }
    }

    private static String getFirst8Digits(List<Integer> digits) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            result.append(digits.get(i));
        }
        return result.toString();
    }
}
