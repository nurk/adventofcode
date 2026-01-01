package year2015.puzzle21;

import lombok.Getter;

@Getter
public class Boss {
    private int hitPoints;
    private final int damage;
    private final int armor;

    public Boss(int hitPoints, int damage, int armor) {
        this.hitPoints = hitPoints;
        this.damage = damage;
        this.armor = armor;
    }

    public int doTurn(Loadout loadout){
        int totalDamage = loadout.getTotalDamage() - armor;
        if (totalDamage < 1) {
            totalDamage = 1;
        }
        hitPoints -= totalDamage;
        return hitPoints;
    }
}
