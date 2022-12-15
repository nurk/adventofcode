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

        int minX = sensors.stream()
                .map(s -> Math.min(s.sensorX, s.beaconX))
                .min(Integer::compareTo)
                .orElseThrow() - 5000000;

        int maxX = sensors.stream()
                .map(s -> Math.max(s.sensorX, s.beaconX))
                .max(Integer::compareTo)
                .orElseThrow() + 5000000;

        System.out.println(IntStream.rangeClosed(minX, maxX).sequential()
                .filter(x -> sensors.stream()
                        .anyMatch(s -> s.isCoveredPartA(x, 2000000)))
                .count());


        int maxCoordinateValue = 4000000;
        boolean found = false;
        for (int y = 0; y <= maxCoordinateValue; y++) {
            for (int x = 0; x <= maxCoordinateValue; x++) {
                int finalX = x;
                int finalY = y;

                Pair<Boolean, Integer> covered = sensors.stream()
                        .map(s -> s.isCoveredPartB(finalX, finalY))
                        .filter(Pair::getValue0)
                        .max(Comparator.comparing(Pair::getValue1))
                        .orElse(null);
                if (covered != null && covered.getValue0() && covered.getValue1() != 0) {
                    x = x + covered.getValue1() - 1;
                    continue;
                }

                if (sensors.stream()
                        .noneMatch(s -> s.isCoveredPartB(finalX, finalY).getValue0())) {
                    System.out.println(BigInteger.valueOf(x)
                            .multiply(BigInteger.valueOf(4000000))
                            .add(BigInteger.valueOf(y)));
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
        int sensorX, sensorY, beaconX, beaconY, distance;

        public Sensor(String line) {
            String[] sensorPos = StringUtils.substringBetween(line, "Sensor at ", ":").split(", ");
            sensorX = Integer.parseInt(StringUtils.substringAfter(sensorPos[0], "="));
            sensorY = Integer.parseInt(StringUtils.substringAfter(sensorPos[1], "="));

            String[] beaconPos = StringUtils.substringAfter(line, "closest beacon is at ").split(", ");
            beaconX = Integer.parseInt(StringUtils.substringAfter(beaconPos[0], "="));
            beaconY = Integer.parseInt(StringUtils.substringAfter(beaconPos[1], "="));

            distance = Math.abs(sensorX - beaconX) + Math.abs(sensorY - beaconY);
        }

        public boolean isCoveredPartA(int x, int y) {
            if ((x == beaconX && y == beaconY)) {
                return false;
            }
            int myDistance = Math.abs(sensorX - x) + Math.abs(sensorY - y);
            return myDistance <= distance;
        }

        public Pair<Boolean, Integer> isCoveredPartB(int x, int y) {
            int myDistance = Math.abs(sensorX - x) + Math.abs(sensorY - y);
            if (myDistance > distance) {
                return Pair.with(false, 0);
            }

            int moveXAmount = 0;
            if (x < sensorX) {
                moveXAmount = (sensorX - x) * 2 + 1;
            } else if (x > sensorX) {
                moveXAmount = distance - myDistance + 1;
            } else if (y == sensorY) {
                moveXAmount = distance + 1;
            }

            return Pair.with(true, moveXAmount);
        }
    }
}
