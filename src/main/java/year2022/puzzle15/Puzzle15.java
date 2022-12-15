package year2022.puzzle15;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.List;
import java.util.Random;

public class Puzzle15 {
    public static void main(String[] args) {
        //6425133 -> correct partA
        List<Sensor> sensors = Utils.getInput("2022/input15.txt").stream()
                .map(Sensor::new)
                .toList();

/*        int minX = sensors.stream()
                .map(s -> Math.min(s.sensorX, s.beaconX))
                .min(Integer::compareTo)
                .orElseThrow() - 100000000;

        int maxX = sensors.stream()
                .map(s -> Math.max(s.sensorX, s.beaconX))
                .max(Integer::compareTo)
                .orElseThrow() + 100000000;

        System.out.println(IntStream.rangeClosed(minX, maxX).sequential()
                .filter(x -> sensors.stream()
                        .anyMatch(s -> s.isCoveredPartA(x, 2000000)))
                .count());*/


        int maxCoordinateValue = 4000000;
        boolean found = false;

        while(!found) {
            Random random = new Random();
            int x = random.nextInt(maxCoordinateValue + 1);
            int y = random.nextInt(maxCoordinateValue + 1);
            //System.out.println(x + " " + y);
            if (sensors.stream()
                    .noneMatch(s -> s.isCoveredPartB(x, y))) {
                System.out.println(x + " " + y);
                System.out.println(x * 4000000 + y);
                found = true;
            }
        }

/*        for (int x = 0; x <= maxCoordinateValue; x++) {
            for (int y = 0; y <= maxCoordinateValue; y++) {
                int finalX = x;
                int finalY = y;
                if (sensors.stream()
                        .noneMatch(s -> s.isCoveredPartB(finalX, finalY))) {
                    System.out.println(x * 4000000 + y);
                    found = true;
                    break;
                }
            }
            System.out.println(x);
            if (found) {
                break;
            }
        }*/

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

        public boolean isCoveredPartB(int x, int y) {
            if ((x == beaconX && y == beaconY) || (x == sensorX && y == sensorY)) {
                return true;
            }
            int myDistance = Math.abs(sensorX - x) + Math.abs(sensorY - y);
            return myDistance <= distance;
        }
    }
}
