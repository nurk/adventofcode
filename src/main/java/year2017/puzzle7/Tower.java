package year2017.puzzle7;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tower {
    private final String input;
    @Getter
    private final String name;
    @Getter
    private final long weight;

    private Tower heldBy;
    private final List<Tower> holding = new ArrayList<>();

    public Tower(String input) {
        // fwft (72) -> ktlj, cntj, xhth
        this.input = input;
        String[] parts = input.split(" -> ");
        String[] nameAndWeight = parts[0].split(" ");
        this.name = nameAndWeight[0];
        this.weight = Long.parseLong(nameAndWeight[1].substring(1, nameAndWeight[1].length() - 1));
    }

    public void init(Map<String, Tower> towers) {
        String[] parts = input.split(" -> ");
        if (parts.length > 1) {
            String[] holdingNames = parts[1].split(", ");
            for (String holdingName : holdingNames) {
                Tower heldTower = towers.get(holdingName);
                holding.add(heldTower);
                heldTower.heldBy = this;
            }
        }
    }

    public boolean isBottom() {
        return heldBy == null;
    }

    public Long getTotalHoldingWeight() {
        long totalWeight = weight;
        for (Tower tower : holding) {
            totalWeight += tower.getTotalHoldingWeight();
        }
        return totalWeight;
    }

    public Pair<Tower, Long> getUnbalancedTower() {
        if (holding.isEmpty()) {
            return null;
        }

        Map<Long, List<Tower>> weightToTowers = new HashMap<>();
        for (Tower tower : holding) {
            long totalWeight = tower.getTotalHoldingWeight();
            weightToTowers.putIfAbsent(totalWeight, new ArrayList<>());
            weightToTowers.get(totalWeight).add(tower);
        }

        if (weightToTowers.size() > 1) {
            Tower unbalancedTower = null;
            long wrongWeight = 0;
            long correctWeight = 0;

            for (Map.Entry<Long, List<Tower>> entry : weightToTowers.entrySet()) {
                if (entry.getValue().size() == 1) {
                    unbalancedTower = entry.getValue().getFirst();
                    wrongWeight = entry.getKey();
                } else {
                    correctWeight = entry.getKey();
                }
            }
            return Pair.of(unbalancedTower, correctWeight - wrongWeight);
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tower tower = (Tower) o;
        return name.equals(tower.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
