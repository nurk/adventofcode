package year2022.puzzle15;

import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;
import util.Utils;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class Puzzle15 {

    public static void main(String[] args) {
        //6425133 -> correct partA
        //10996191429555 -> correct partB
        List<Sensor> sensors = Utils.getInput("2022/input15.txt").stream()
                .map(Sensor::new)
                .toList();

        //Part A
        int minX = sensors.stream()
                .map(s -> Math.min(s.sensorX, s.beaconX))
                .min(Integer::compareTo)
                .orElseThrow() - sensors.stream().map(s -> s.manhattanDistance).max(Integer::compare).orElseThrow();

        int maxX = sensors.stream()
                .map(s -> Math.max(s.sensorX, s.beaconX))
                .max(Integer::compareTo)
                .orElseThrow() + sensors.stream().map(s -> s.manhattanDistance).max(Integer::compare).orElseThrow();

        System.out.println(IntStream.rangeClosed(minX, maxX)
                .filter(x -> sensors.stream().anyMatch(s -> !s.canHaveBeacon(x, 2000000)))
                .count());

        //Part B
        int maxCoordinateValue = 4000000;
        boolean found = false;
        var pos = new Object() {
            int x, y;
        };
        for (pos.y = 0; pos.y <= maxCoordinateValue; pos.y++) {
            for (pos.x = 0; pos.x <= maxCoordinateValue; pos.x++) {
                pos.x = pos.x + sensors.stream()
                        .map(s -> s.isCoveredBySensorAndXMoveAmount(pos.x, pos.y))
                        .filter(Pair::getValue0)
                        .max(Comparator.comparing(Pair::getValue1))
                        .map(pair -> pair.getValue1() - 1) // subtract 1 because the loop will add 1
                        .orElse(0);

                if (sensors.stream()
                        .noneMatch(s -> s.isCoveredBySensorAndXMoveAmount(pos.x, pos.y).getValue0())) {
                    System.out.println(BigInteger.valueOf(pos.x)
                            .multiply(BigInteger.valueOf(4000000))
                            .add(BigInteger.valueOf(pos.y)));
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }
    }

    static class Sensor {
        private final int sensorX, sensorY, beaconX, beaconY, manhattanDistance;

        public Sensor(String line) {
            String[] sensorPos = StringUtils.substringBetween(line, "Sensor at ", ":").split(", ");
            sensorX = Integer.parseInt(StringUtils.substringAfter(sensorPos[0], "="));
            sensorY = Integer.parseInt(StringUtils.substringAfter(sensorPos[1], "="));

            String[] beaconPos = StringUtils.substringAfter(line, "closest beacon is at ").split(", ");
            beaconX = Integer.parseInt(StringUtils.substringAfter(beaconPos[0], "="));
            beaconY = Integer.parseInt(StringUtils.substringAfter(beaconPos[1], "="));

            manhattanDistance = calculateManhattanDistanceTo(beaconX, beaconY);
        }

        private int calculateManhattanDistanceTo(int x, int y) {
            return Math.abs(sensorX - x) + Math.abs(sensorY - y);
        }

        public boolean canHaveBeacon(int x, int y) {
            if (beaconX == x && beaconY == y) {
                return true;
            }
            return calculateManhattanDistanceTo(x, y) > manhattanDistance;
        }

        public Pair<Boolean, Integer> isCoveredBySensorAndXMoveAmount(int x, int y) {
            if (calculateManhattanDistanceTo(x, y) > manhattanDistance) {
                return Pair.with(false, 1);
            }

            int moveXAmount = 1;
            if (x < sensorX) {
                moveXAmount = (sensorX - x) * 2 + 1;
            } else if (x > sensorX) {
                moveXAmount = manhattanDistance - calculateManhattanDistanceTo(x, y) + 1;
            } else if (y == sensorY) {
                moveXAmount = manhattanDistance / 2 + 1;
            }

            return Pair.with(true, moveXAmount);
        }
    }
}
