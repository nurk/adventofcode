package year2023.puzzle3;

import util.Utils;

import java.util.List;

/*
Part A: 529618
Part B: 77509019
 */
public class Puzzle3 {
    public static void main(String[] args) {
        List<String> lines = Utils.getInput("2023/input3.txt");
        Engine engine = new Engine(lines);
        System.out.println("Part A: " + engine.sumOfPartNumbers());
        System.out.println("Part B: " + engine.gearRatio());
    }
}
