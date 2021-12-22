package year2021.puzzle22;

import util.Utils;

import java.util.ArrayList;
import java.util.List;

public class Puzzle22 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2021/input22.txt", (s) -> s));
        partA(input);
        partB(input);
    }

    private static void partA(List<String> input) {
        BoardA b = new BoardA();
        input.forEach(b::doStep);
        System.out.println(b.countOn());
    }

    private static void partB(List<String> input) {
        BoardB b = new BoardB();
        //b.doStep("on x=10..12,y=10..12,z=10..12");
        //b.doStep("on x=11..13,y=11..13,z=11..13");
        input.forEach(b::doStep);
        System.out.println(b.countOn());
        //1254011191104293
    }
}
