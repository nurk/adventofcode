package year2019.puzzle10;

import util.Utils;

import java.util.List;

/**
 * Part A: 269
 * Part B: 612
 */
public class Puzzle10 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2019/input10.txt");

        AsteroidField asteroidField = new AsteroidField(input);

        System.out.println("Part A: " + asteroidField.countVisibleAsteroids());

        System.out.println("Part B: " + asteroidField.vaporizeAsteroids());
    }
}
