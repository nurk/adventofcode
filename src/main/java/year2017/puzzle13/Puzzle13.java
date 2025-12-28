package year2017.puzzle13;

import org.apache.commons.lang3.tuple.Pair;
import util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Part A: 2688
 * Part B: 3876272
 */
public class Puzzle13 {
    public static void main(String[] args) {
        List<Pair<Integer, Integer>> input = Utils.getInput("2017/input13.txt", line -> {
            String[] split = line.split(": ");
            return Pair.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        });

        int numberOfLayers = input.stream()
                .map(Pair::getLeft)
                .max(Integer::compareTo)
                .orElseThrow();

        Map<Integer, FirewallLayer> fireWallLayers = new HashMap<>();
        for (int i = 0; i <= numberOfLayers; i++) {
            int finalI = i;
            input.stream()
                    .filter(p -> p.getLeft() == finalI)
                    .findFirst()
                    .ifPresentOrElse(
                            p -> fireWallLayers.put(finalI, new FirewallLayer(finalI, p.getRight())),
                            () -> fireWallLayers.put(finalI, new FirewallLayer(finalI, 0))
                    );
        }

        int severity = 0;
        for (int picosecond = 0; picosecond <= numberOfLayers; picosecond++) {
            FirewallLayer currentLayer = fireWallLayers.get(picosecond);
            if (currentLayer.getScannerPosition() == 1) {
                severity += currentLayer.getDepth() * currentLayer.getRange();
            }
            fireWallLayers.values().forEach(FirewallLayer::tick);
        }
        System.out.println("Part A: " + severity);

        fireWallLayers.values().forEach(FirewallLayer::reset);

        boolean caught = true;
        int delay = -1;
        while (caught) {
            caught = false;
            delay++;
            fireWallLayers.values().forEach(FirewallLayer::reset);
            int finalDelay = delay;
            fireWallLayers.values().forEach(f -> f.initialTicks(finalDelay));

            for (int picosecond = 0; picosecond <= numberOfLayers; picosecond++) {
                FirewallLayer currentLayer = fireWallLayers.get(picosecond);
                if (currentLayer.getScannerPosition() == 1 && currentLayer.getRange() > 0) {
                    caught = true;
                    break;
                }
                fireWallLayers.values().forEach(FirewallLayer::tick);
            }
        }

        System.out.println("Part B: " + delay);
    }
}
