package year2021.puzzle9;

import util.Utils;

import java.util.ArrayList;
import java.util.List;

public class Puzzle9 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2021/input9.txt", (s) -> s));

        Board b = new Board(input);
        b.markLowPoints();

        System.out.println(b.getRiskLevel());
        System.out.println(b.threeLargestBasinsMultiplied());
    }
}
