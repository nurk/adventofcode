package year2017.puzzle22;

import util.Utils;

import java.util.List;

/**
 * Part A: 5339
 * Part A: 2512380
 */
public class Puzzle22 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2017/input22.txt");
        Sporifica sporificaPartA = new Sporifica(input);
        Sporifica sporificaPartB = new Sporifica(input);

        for (int i = 0; i < 10000; i++) {
            sporificaPartA.tickPartA();
        }
        System.out.println("Part A: " + sporificaPartA.getNumberOfTimesInfected());

        for (int i = 0; i < 10000000; i++) {
            sporificaPartB.tickPartB();
        }
        System.out.println("Part A: " + sporificaPartB.getNumberOfTimesInfected());
    }
}
