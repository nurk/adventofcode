package year2021.puzzle5;

import org.apache.commons.lang3.tuple.Pair;
import util.Utils;

import java.util.ArrayList;
import java.util.List;

public class Puzzle5 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2021/input5.txt", (s) -> s));

        List<Pair<IntegerPair, IntegerPair>> parsedInput =
                input.stream()
                        .map(s -> s.split(" -> "))
                        .map(split -> Pair.of(toIntegerPair(split[0]), toIntegerPair(split[1])))
                        .toList();

        Board boardA = new Board();
        parsedInput.forEach(boardA::drawPartA);
        System.out.println(boardA.largerThan2());

        Board boardB = new Board();
        parsedInput.forEach(boardB::drawPartB);
        System.out.println(boardB.largerThan2());
    }

    public static IntegerPair toIntegerPair(String s) {
        String[] split = s.split(",");
        return new IntegerPair(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }
}
