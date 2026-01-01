package year2015.puzzle22;

import lombok.Getter;

@Getter
public abstract class Spell {
    private final String name;
    private final boolean isInstant;
    private final int manaCost;
    private final int duration;
    private final int damage;
    private final int armor;
    private final int heal;
    private final int manaRecharge;

    private int timer;

    public Spell(String name,
                 boolean isInstant,
                 int manaCost,
                 int duration,
                 int damage,
                 int armor,
                 int heal,
                 int manaRecharge) {
        this.name = name;
        this.isInstant = isInstant;
        this.manaCost = manaCost;
        this.duration = duration;
        this.damage = damage;
        this.armor = armor;
        this.heal = heal;
        this.manaRecharge = manaRecharge;
        this.timer = duration;
    }

    public boolean isActive() {
        return timer >= 0;
    }

    public boolean willExpire() {
        return timer == 0;
    }

    public void cast(Wizard wizard, Boss boss) {
        if (isInstant) {
            wizard.doSpell(this);
            boss.doSpell(this);
        } else if (timer < duration) {
            wizard.doSpell(this);
            boss.doSpell(this);
        }
        timer--;
    }

    @Override
    public String toString() {
        return "Spell{" +
                "name='" + name + '\'' +
                ", isInstant=" + isInstant +
                ", manaCost=" + manaCost +
                ", duration=" + duration +
                ", damage=" + damage +
                ", armor=" + armor +
                ", heal=" + heal +
                ", manaRecharge=" + manaRecharge +
                ", timer=" + timer +
                '}';
    }
}
