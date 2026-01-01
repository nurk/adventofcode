package year2015.puzzle22;

import java.util.ArrayList;
import java.util.List;

public class AttackComboGenerator {
    private int index = 0;

    public AttackComboGenerator() {
    }

    public List<Spell> getNextCombo() {
        String combo = Integer.toString(index++, 5);

        return new ArrayList<>(combo.chars()
                .mapToObj(c -> switch (c) {
                    case '0' -> new MagicMissile();
                    case '1' -> new Drain();
                    case '2' -> new Shield();
                    case '3' -> new Poison();
                    case '4' -> new Recharge();
                    default -> throw new IllegalStateException("Unexpected value: " + c);
                })
                .toList());
    }
}
