package year2015.puzzle21;

import java.util.List;

public class Loadout {
    private final List<Item> items;

    public Loadout(List<Item> items) {
        this.items = items;
    }

    public int getTotalCost() {
        return items.stream().mapToInt(Item::getCost).sum();
    }

    public int getTotalDamage() {
        return items.stream().mapToInt(Item::getDamage).sum();
    }

    public int getTotalArmor() {
        return items.stream().mapToInt(Item::getArmor).sum();
    }
}
