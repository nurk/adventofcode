package year2021.puzzle3;

import util.Utils;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Puzzle3 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2021/input3.txt", (s) -> s);

        partA(input);
        System.out.println();
        partB(input);
    }

    private static void partA(List<String> input) {
        String gamma = IntStream.range(0, input.get(0).length())
                .mapToObj(i -> getMostCommonBit(input, i))
                .reduce((s, s2) -> s + s2)
                .orElseThrow();

        String epsilon = invertBits(gamma);

        System.out.println(gamma);
        System.out.println(epsilon);
        System.out.println(Long.valueOf(gamma, 2));
        System.out.println(Long.valueOf(epsilon, 2));

        System.out.println(Long.valueOf(gamma, 2) * Long.valueOf(epsilon, 2));
    }


    public static void partB(List<String> input) {
        String oxygen = getSelectedString(input, Puzzle3::getMostCommonBit);
        String co2 = getSelectedString(input, Puzzle3::getLeastCommonBit);

        System.out.println(oxygen);
        System.out.println(co2);
        System.out.println(Long.valueOf(oxygen, 2) * Long.valueOf(co2, 2));

    }

    private static String getSelectedString(List<String> input, BiFunction<List<String>, Integer, String> bitSelector) {
        List<String> subList = input;
        var ref = new Object() {
            int position = 0;
        };

        while (subList.size() > 1) {
            String selectedBit = bitSelector.apply(subList, ref.position);
            subList = subList.stream()
                    .filter(s -> selectedBit.equalsIgnoreCase(s.substring(ref.position, ref.position + 1)))
                    .collect(toList());
            ref.position++;
        }

        return subList.get(0);
    }

    public static String getMostCommonBit(List<String> input, int position) {
        int ones = input.stream()
                .map(s -> s.substring(position, position + 1))
                .map(s -> s.equalsIgnoreCase("1") ? 1 : 0)
                .mapToInt(Integer::intValue)
                .sum();
        return ones >= (input.size() / 2f) ? "1" : "0";
    }

    public static String getLeastCommonBit(List<String> input, int position) {
        return invertBits(getMostCommonBit(input, position));
    }

    private static String invertBits(String input) {
        return input.replaceAll("0", "x")
                .replaceAll("1", "0")
                .replaceAll("x", "1");
    }
}
