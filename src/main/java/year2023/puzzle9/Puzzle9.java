package year2023.puzzle9;

import util.Utils;

import java.util.List;

/**
 * Part A: 1584748274
 */
public class Puzzle9 {

    public static void main(String[] args) {
        List<OasisSensor> sensors = Utils.getInput("2023/input9.txt", OasisSensor::new);

        System.out.println("Part A: " + sensors.stream()
                .map(OasisSensor::solveForward)
                .reduce(0L, Long::sum));

        System.out.println("Part B: " + sensors.stream()
                .map(OasisSensor::solveBackward)
                .reduce(0L, Long::sum));
    }
}
