package year2021.puzzle17;

import org.javatuples.Pair;

import java.util.HashSet;
import java.util.Set;

/**
 * Part A: 10296
 * Part B: 2371
 */
public class Puzzle17 {

    static void main() {
        //TargetArea targetArea = new TargetArea(20, 30, -10, -5);
        TargetArea targetArea = new TargetArea(96, 125, -144, -98);
        int maxHeight = 0;
        Set<Pair<Integer, Integer>> validVelocities = new HashSet<>();
        for (int initialXVelocity = 1; initialXVelocity <= targetArea.getXMax(); initialXVelocity++) {
            for (int initialYVelocity = targetArea.getYMin(); initialYVelocity <= Math.abs(targetArea.getYMin()); initialYVelocity++) {
                int x = 0;
                int y = 0;
                int currentXVelocity = initialXVelocity;
                int currentYVelocity = initialYVelocity;
                int height = 0;
                while (!targetArea.hasOvershot(x, y)) {
                    x += currentXVelocity;
                    y += currentYVelocity;
                    if (y > height) {
                        height = y;
                    }
                    if (targetArea.isWithin(x, y)) {
                        if (height > maxHeight) {
                            maxHeight = height;
                        }
                        validVelocities.add(new Pair<>(initialXVelocity, initialYVelocity));
                    }
                    if (currentXVelocity > 0) {
                        currentXVelocity--;
                    }
                    currentYVelocity--;
                }
            }
        }
        System.out.println("Part A: " + maxHeight);
        System.out.println("Part B: " + validVelocities.size());
    }
}
