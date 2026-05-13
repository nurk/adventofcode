package year2018.puzzle23;

import org.javatuples.Triplet;
import util.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Part A: 691
 * Part B: 126529978
 */
public class Puzzle23 {
    static void main() {
        List<Nanobot> nanobots = Utils.getInput("2018/input23.txt", Nanobot::new);

        partA(nanobots);
        partB(nanobots);
    }

    private static void partB(List<Nanobot> nanobots) {
        long bestNumberInRange = 0;
        List<Triplet<Long, Long, Long>> bestLocations = new ArrayList<>();

        long minX = nanobots.stream()
                .mapToLong(Nanobot::getX)
                .min()
                .orElseThrow();
        long maxX = nanobots.stream()
                .mapToLong(Nanobot::getX)
                .max()
                .orElseThrow();
        long minY = nanobots.stream()
                .mapToLong(Nanobot::getY)
                .min()
                .orElseThrow();
        long maxY = nanobots.stream()
                .mapToLong(Nanobot::getY)
                .max()
                .orElseThrow();
        long minZ = nanobots.stream()
                .mapToLong(Nanobot::getZ)
                .min()
                .orElseThrow();
        long maxZ = nanobots.stream()
                .mapToLong(Nanobot::getZ)
                .max()
                .orElseThrow();

        // hone in on the coordinate.
        // checking each takes too much time.
        for (long div = 100000000; true; div = div / 10) {
            for (long x = minX; x <= maxX; x += div) {
                for (long y = minY; y <= maxY; y += div) {
                    for (long z = minZ; z <= maxZ; z += div) {
                        long finalX = x;
                        long finalY = y;
                        long finalZ = z;

                        long count = nanobots.stream()
                                .filter(n -> n.inRange(finalX, finalY, finalZ))
                                .count();

                        if (count > 0) {
                            if (count > bestNumberInRange) {
                                bestNumberInRange = count;
                                bestLocations.clear();
                                bestLocations.add(new Triplet<>(x, y, z));
                            } else if (count == bestNumberInRange) {
                                bestLocations.add(new Triplet<>(x, y, z));
                            }
                        }
                    }
                }
            }

            if (div == 1) {
                Nanobot nanobot = new Nanobot("pos=<" + bestLocations.getFirst()
                        .getValue0() + "," + bestLocations.getFirst().getValue1() + "," + bestLocations.getFirst()
                        .getValue2() + ">, r=0");

                System.out.println("Part B: " + nanobot.manhattanDistance(0, 0, 0));
                return;
            }

            minX = bestLocations.getFirst().getValue0() - div;
            maxX = bestLocations.getFirst().getValue0() + div;
            minY = bestLocations.getFirst().getValue1() - div;
            maxY = bestLocations.getFirst().getValue1() + div;
            minZ = bestLocations.getFirst().getValue2() - div;
            maxZ = bestLocations.getFirst().getValue2() + div;
            bestNumberInRange = 0;
            bestLocations.clear();

        }
    }

    private static void partA(List<Nanobot> nanobots) {
        Nanobot strongest = nanobots.stream()
                .max(Comparator.comparingLong(n -> n.radius))
                .orElseThrow();

        System.out.println("Part A: " + nanobots.stream()
                .filter(n -> n.inRange(strongest))
                .count());
    }
}
