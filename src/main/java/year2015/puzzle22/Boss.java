package year2015.puzzle22;

import lombok.Getter;

@Getter
public class Boss {

    private int hitPoints;
    private final int damage;

    public Boss(int hitPoints, int damage) {
        this.hitPoints = hitPoints;
        this.damage = damage;
    }

    public void doSpell(Spell spell) {
        this.hitPoints -= spell.getDamage();
    }

    @Override
    public String toString() {
        return "Boss{" +
                "hitPoints=" + hitPoints +
                ", damage=" + damage +
                '}';
    }
}
