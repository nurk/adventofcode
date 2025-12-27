package year2017.puzzle7;

import org.apache.commons.lang3.tuple.Pair;
import util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Part A: mwzaxaj
 * Part B: 1219
 */
public class Puzzle7 {
    public static void main(String[] args) {
        Map<String, Tower> towers = new HashMap<>();

        Utils.getInput("2017/input7.txt").forEach(line -> {
            Tower tower = new Tower(line);
            towers.put(tower.getName(), tower);
        });

        towers.values().forEach(t -> t.init(towers));

        Tower bottomTower = towers.values().stream()
                .filter(Tower::isBottom)
                .findFirst()
                .orElseThrow();

        System.out.println("Part A: " + bottomTower.getName());

        Pair<Tower, Long> unbalancedTowerInfo = bottomTower.getUnbalancedTower();
        Pair<Tower, Long> currentUnbalanced = unbalancedTowerInfo;
        while (currentUnbalanced != null) {
            unbalancedTowerInfo = currentUnbalanced;
            currentUnbalanced = currentUnbalanced.getLeft().getUnbalancedTower();
        }

        System.out.println("Part B: " + (unbalancedTowerInfo.getLeft().getWeight() + unbalancedTowerInfo.getRight()));
    }
}
