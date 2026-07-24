package year2018.puzzle24;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Unit {

    private final String line;
    @Getter
    private final int hitPoints;
    @Getter
    private int attack;
    @Getter
    private final int initiative;
    @Getter
    private final String damageType;
    @Getter
    private final List<String> weaknesses = new ArrayList<>();
    @Getter
    private final List<String> immunities = new ArrayList<>();


    public Unit(String line) {
        this.line = line;
        hitPoints = Integer.parseInt(StringUtils.substringBetween(line, "with ", " hit points"));
        attack = Integer.parseInt(StringUtils.substringBetween(line, "with an attack that does ", " "));
        initiative = Integer.parseInt(StringUtils.substringAfter(line, "initiative "));
        damageType = StringUtils.substringAfterLast(StringUtils.substringBefore(line, " damage "), " ");

        String attributes = StringUtils.substringBetween(line, "(", ")");
        if (StringUtils.isNotBlank(attributes)) {
            String[] split = attributes.split("; ");

            if (split[0].contains("immune to")) {
                immunities.addAll(Arrays.asList(StringUtils.substringAfter(split[0], "immune to ").split(", ")));
            } else {
                weaknesses.addAll(Arrays.asList(StringUtils.substringAfter(split[0], "weak to ").split(", ")));
            }

            if (split.length > 1) {
                if (split[1].contains("immune to")) {
                    immunities.addAll(Arrays.asList(StringUtils.substringAfter(split[1], "immune to ").split(", ")));
                } else {
                    weaknesses.addAll(Arrays.asList(StringUtils.substringAfter(split[1], "weak to ").split(", ")));
                }
            }
        }
    }

    public void boost(int boost) {
        attack += boost;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Unit unit = (Unit) o;
        return line.equals(unit.line);
    }

    @Override
    public int hashCode() {
        return line.hashCode();
    }

    @Override
    public String toString() {
        return "Unit{" +
                "hitpoints=" + hitPoints +
                ", attack=" + attack +
                ", initiative=" + initiative +
                ", damageType='" + damageType + '\'' +
                ", weaknesses=" + weaknesses +
                ", immunities=" + immunities +
                '}';
    }
}
