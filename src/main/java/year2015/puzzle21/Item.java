package year2015.puzzle21;

import lombok.Getter;

@Getter
public abstract class Item {
    private final int cost;
    private final int damage;
    private final int armor;

    public Item(int cost, int damage, int armor) {
        this.cost = cost;
        this.damage = damage;
        this.armor = armor;
    }
}
