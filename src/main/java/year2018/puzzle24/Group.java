package year2018.puzzle24;

import lombok.Getter;

public class Group implements Comparable<Group> {

    @Getter
    private int amount;
    @Getter
    private final Unit unit;
    private final boolean isImmuneSystem;
    @Getter
    private Group targetedGroup;
    private long targetedDamage;

    public Group(int amount, Unit unit, boolean isImmuneSystem) {
        this.amount = amount;
        this.unit = unit;
        this.isImmuneSystem = isImmuneSystem;
    }

    public long getEffectivePower() {
        return (long) amount * unit.getAttack();
    }

    public long getInitiative() {
        return unit.getInitiative();
    }

    public long getDamageDealtBy(Group other) {
        if (this.unit.getImmunities().contains(other.unit.getDamageType())) {
            return 0;
        }
        long damage = other.getEffectivePower();
        if (this.unit.getWeaknesses().contains(other.unit.getDamageType())) {
            damage *= 2;
        }
        return damage;
    }

    public void considerTarget(Group other) {
        if (targetedGroup == null) {
            targetedGroup = other;
            targetedDamage = other.getDamageDealtBy(this);
        } else {
            long damage = other.getDamageDealtBy(this);
            if (damage > targetedDamage) {
                targetedGroup = other;
                targetedDamage = damage;
            } else if (damage == targetedDamage) {
                if (other.getEffectivePower() > targetedGroup.getEffectivePower()) {
                    targetedGroup = other;
                    targetedDamage = damage;
                } else if (other.getEffectivePower() == targetedGroup.getEffectivePower()) {
                    if (other.getInitiative() > targetedGroup.getInitiative()) {
                        targetedGroup = other;
                        targetedDamage = damage;
                    }
                }
            }
        }

        if (targetedDamage == 0) {
            targetedGroup = null;
        }
    }

    public void performAttack() {
        if (targetedGroup != null && amount > 0) {
            long damage = targetedGroup.getDamageDealtBy(this);
            long unitsKilled = Math.min(damage / targetedGroup.unit.getHitPoints(), targetedGroup.amount);
            targetedGroup.amount -= (int) unitsKilled;
//            System.out.println("Group " + this + " attacks " + targetedGroup + " for " + damage + " damage, killing " + unitsKilled + " units.");
        }
    }

    public void resetTarget() {
        targetedGroup = null;
        targetedDamage = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Group group = (Group) o;
        return unit.equals(group.unit);
    }

    @Override
    public int hashCode() {
        return unit.hashCode();
    }

    @Override
    public int compareTo(Group o) {
        int powerComparison = Long.compare(o.getEffectivePower(), this.getEffectivePower());
        if (powerComparison != 0) {
            return powerComparison;
        }
        return Long.compare(o.getInitiative(), this.getInitiative());
    }

    @Override
    public String toString() {
        return "Group{" +
                "amount=" + amount +
                ", unit=" + unit +
                ", isImmuneSystem=" + isImmuneSystem +
                '}';
    }
}
