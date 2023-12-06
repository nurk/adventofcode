package year2023.puzzle3;

import util.Utils;

import java.util.List;

public class Puzzle3 {
    public static void main(String[] args) {
        List<String> lines = Utils.getInput("2023/input3test.txt");
        Engine engine = new Engine(lines);
        System.out.println(engine);
    }
}
