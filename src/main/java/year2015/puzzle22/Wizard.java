package year2015.puzzle22;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Wizard {
    @Getter
    private int mana;
    @Getter
    private int hitPoints;
    private int armor;

    @Getter
    private int totalManaSpent;

    private List<Spell> activeSpells = new ArrayList<>();

    public Wizard(int mana, int hitPoints) {
        this.mana = mana;
        this.hitPoints = hitPoints;
        this.armor = 0;
    }

    public boolean wizardTurn(Boss boss, Spell spell) {
        if (spell.getManaCost() > mana) {
            return false;
        }

        if (activeSpells.stream().anyMatch(sp -> sp.getName().equals(spell.getName()) && !sp.willExpire())) {
            return false;
        }

        totalManaSpent += spell.getManaCost();

        mana -= spell.getManaCost();
        activeSpells.add(spell);
        doSpells(boss);

        return true;
    }

    private void doSpells(Boss boss) {
        armor = 0;

        List<Spell> newActiveSpells = new ArrayList<>();

        for (Spell activeSpell : activeSpells) {
            activeSpell.cast(this, boss);
            if (activeSpell.isActive()) {
                newActiveSpells.add(activeSpell);
            }
        }

        activeSpells = newActiveSpells;
    }

    public void bossTurn(Boss boss) {
        doSpells(boss);
        int damageTaken = Math.max(boss.getDamage() - armor, 1);
        hitPoints -= damageTaken;
    }

    public void doSpell(Spell spell) {
        this.hitPoints += spell.getHeal();
        this.mana += spell.getManaRecharge();
        this.armor += spell.getArmor();
    }

    public void takeHit(int i) {
        this.hitPoints -= i;
    }

    @Override
    public String toString() {
        return "Wizard{" +
                "mana=" + mana +
                ", hitPoints=" + hitPoints +
                ", armor=" + armor +
                ", activeSpells=" + activeSpells +
                '}';
    }
}
