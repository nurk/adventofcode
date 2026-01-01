package year2015.puzzle21;

import lombok.Getter;
import org.apache.commons.math3.util.CombinatoricsUtils;
import year2015.puzzle21.armor.*;
import year2015.puzzle21.ring.*;
import year2015.puzzle21.weapon.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Shop {
    private final List<Item> weapons = List.of(
            new Dagger(),
            new Greataxe(),
            new Longsword(),
            new Shortsword(),
            new Warhammer()
    );

    private final List<Item> armors = List.of(
            new Leather(),
            new Chainmail(),
            new Splintmail(),
            new Bandedmail(),
            new Platemail(),
            new NoArmor()
    );

    private final List<Item> rings = List.of(
            new Damage1(),
            new Damage2(),
            new Damage3(),
            new Defense1(),
            new Defense2(),
            new Defense3(),
            new NoRing(),
            new NoRing()
    );

    @Getter
    private final List<Loadout> loadouts = new ArrayList<>();

    public Shop() {
        Iterator<int[]> weaponIterator = CombinatoricsUtils.combinationsIterator(weapons.size(), 1);

        while (weaponIterator.hasNext()) {
            Item weapon = weapons.get(weaponIterator.next()[0]);

            Iterator<int[]> armorIterator = CombinatoricsUtils.combinationsIterator(armors.size(), 1);
            while (armorIterator.hasNext()) {
                Item armor = armors.get(armorIterator.next()[0]);

                Iterator<int[]> ringIterator = CombinatoricsUtils.combinationsIterator(rings.size(), 2);
                while (ringIterator.hasNext()) {
                    int[] ringCombination = ringIterator.next();
                    Item ring1 = rings.get(ringCombination[0]);
                    Item ring2 = rings.get(ringCombination[1]);

                    Loadout loadout = new Loadout(List.of(weapon, armor, ring1, ring2));
                    loadouts.add(loadout);
                }
            }
        }
    }
}
