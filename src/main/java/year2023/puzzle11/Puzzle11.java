package year2023.puzzle11;

import util.Utils;

/**
 * Part A: 9623138
 * Part B: 726820169514
 */
public class Puzzle11 {
    public static void main(String[] args) {
        GalaxyMap galaxyMap = new GalaxyMap(Utils.getInput("2023/input11.txt"));

        System.out.println("Part A: " + galaxyMap.sumOfDistances(2 - 1));
        System.out.println("Part B: " + galaxyMap.sumOfDistances(1000000 - 1));
    }
}
