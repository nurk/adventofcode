package year2015.puzzle21;

public class Player {
    private int hitPoints;
    private final Loadout loadout;

    public Player(int hitPoints, Loadout loadout) {
        this.hitPoints = hitPoints;
        this.loadout = loadout;
    }

    public int doTurn(Boss boss) {
        int totalDamage = boss.getDamage() - loadout.getTotalArmor();
        if (totalDamage < 1) {
            totalDamage = 1;
        }
        hitPoints -= totalDamage;
        return hitPoints;
    }
}
