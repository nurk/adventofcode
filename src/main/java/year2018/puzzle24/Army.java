package year2018.puzzle24;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Army {

    @Getter
    private final SortedSet<Group> groups = new TreeSet<>();

    public Army(List<String> lines, boolean isImmuneSystem) {
        lines.forEach(line -> {
            int amount = Integer.parseInt(StringUtils.substringBefore(line, " "));
            groups.add(new Group(amount, new Unit(line), isImmuneSystem));
        });
    }

    public void chooseTargetToAttack(Army otherArmy) {
        List<Group> otherArmyGroups = new ArrayList<>(otherArmy.getGroups());
        for (Group group : groups) {
            for (Group otherGroup : otherArmyGroups) {
                group.considerTarget(otherGroup);
            }

            otherArmyGroups = new ArrayList<>(otherArmy.getGroups());
            otherArmyGroups.removeAll(
                    groups.stream()
                            .map(Group::getTargetedGroup)
                            .filter(Objects::nonNull)
                            .toList());
        }
    }

    public boolean hasAtLeastOneTarget() {
        return groups.stream().anyMatch(g -> g.getTargetedGroup() != null);
    }

    public void boost(int boost) {
        groups.forEach(g -> {
            g.getUnit().boost(boost);
        });
        resetOrder();
    }

    public void resetRound() {
        groups.removeIf(g -> g.getAmount() <= 0);
        groups.forEach(Group::resetTarget);
        resetOrder();
    }

    private void resetOrder() {
        // Rebuild the TreeSet so groups are re-sorted by current effective power
        // (amount is mutable so the order becomes stale after attacks)
        List<Group> groupList = new ArrayList<>(groups);
        groups.clear();
        groups.addAll(groupList);
    }
}
