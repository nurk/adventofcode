package year2015.puzzle21;

import java.util.Comparator;
import java.util.List;

/**
 * Part A: 78
 * Part B: 148
 */
public class Puzzle21 {
    public static void main(String[] args) {
        Shop shop = new Shop();

        partA(shop);
        partB(shop);
    }

    private static void partB(Shop shop) {
        List<Loadout> sortedLoadouts = shop.getLoadouts().stream()
                .sorted(Comparator.comparingInt(Loadout::getTotalCost))
                .toList().reversed();

        Loadout dearestLoadoutThatLoses = null;
        for (Loadout loadout : sortedLoadouts) {
            Player player = new Player(100, loadout);
            Boss boss = new Boss(104, 8, 1);

            while (true) {
                int bossHpAfterPlayerTurn = boss.doTurn(loadout);
                if (bossHpAfterPlayerTurn <= 0) {
                    break;
                }

                int playerHpAfterBossTurn = player.doTurn(boss);
                if (playerHpAfterBossTurn <= 0) {
                    dearestLoadoutThatLoses = loadout;
                    break;
                }
            }
            if (dearestLoadoutThatLoses != null) {
                break;
            }

        }
        System.out.println("Part B: " + dearestLoadoutThatLoses.getTotalCost());
    }

    private static void partA(Shop shop) {
        List<Loadout> sortedLoadouts = shop.getLoadouts().stream()
                .sorted(Comparator.comparingInt(Loadout::getTotalCost))
                .toList();

        Loadout cheapestWinningLoadout = null;
        for (Loadout loadout : sortedLoadouts) {
            Player player = new Player(100, loadout);
            Boss boss = new Boss(104, 8, 1);

            while (true) {
                int bossHpAfterPlayerTurn = boss.doTurn(loadout);
                if (bossHpAfterPlayerTurn <= 0) {
                    cheapestWinningLoadout = loadout;
                    break;
                }

                int playerHpAfterBossTurn = player.doTurn(boss);
                if (playerHpAfterBossTurn <= 0) {
                    break;
                }
            }
            if (cheapestWinningLoadout != null) {
                break;
            }

        }
        System.out.println("Part A: " + cheapestWinningLoadout.getTotalCost());
    }
}
