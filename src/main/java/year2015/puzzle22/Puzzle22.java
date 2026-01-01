package year2015.puzzle22;

import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//https://aoc.just2good.co.uk/2015/22.html

/**
 * Part A: 1269
 * Part B: 1309
 */
public class Puzzle22 {
    public static void main(String[] args) {
        System.out.println("Part A: " + playGame(false));
        System.out.println("Part B: " + playGame(true));
    }

    private static @NonNull Integer playGame(boolean hardMode) {
        AttackComboGenerator generator = new AttackComboGenerator();

        int minManaSpent = Integer.MAX_VALUE;

        int previousComboSize = 0;
        int lastWinningComboSize = Integer.MAX_VALUE - 1;
        Map<Integer, List<Spell>> winningCombos = new HashMap<>();

        while (true) {
            List<Spell> combo = generator.getNextCombo();

            int currentComboSize = combo.size();
            if (currentComboSize != previousComboSize) {
                previousComboSize = currentComboSize;
                if (currentComboSize > lastWinningComboSize + 1) {
                    break;
                }
                System.out.println("Testing combos of size: " + currentComboSize);
            }
            List<Spell> comboCopy = List.copyOf(combo);

            int totalComboCost = combo.stream().mapToInt(Spell::getManaCost).sum();

            if (totalComboCost >= minManaSpent) {
                continue;
            }

            Wizard wizard = new Wizard(500, 50);
            Boss boss = new Boss(58, 9);

            boolean wizardWon = false;

            while (!combo.isEmpty()) {
                if (hardMode) {
                    wizard.takeHit(1);
                    if (wizard.getHitPoints() <= 0) {
                        break;
                    }
                }
                Spell spell = combo.removeFirst();
                boolean hasSpellBeenCast = wizard.wizardTurn(boss, spell);
                if (!hasSpellBeenCast) {
                    break;
                }
                if (boss.getHitPoints() <= 0) {
                    wizardWon = true;
                    break;
                }

                wizard.bossTurn(boss);
                if (boss.getHitPoints() <= 0) {
                    wizardWon = true;
                    break;
                }
                if (wizard.getHitPoints() <= 0) {
                    break;
                }
            }

            if (wizardWon) {
//                System.out.println("Wizard won with combo size: " + currentComboSize);
//                System.out.println("Total mana spent: " + wizard.getTotalManaSpent());
//                System.out.println("Mana cost of combo: " + totalComboCost);
//                System.out.println("Combo: ");
//                comboCopy.forEach(System.out::println);
                minManaSpent = Math.min(minManaSpent, wizard.getTotalManaSpent());
                lastWinningComboSize = currentComboSize;
                winningCombos.put(minManaSpent, comboCopy);
//                System.out.println("New minimum mana spent: " + minManaSpent);
            }
        }

        return winningCombos.keySet().stream().min(Integer::compare).orElseThrow();
    }

    private static void exampleTwo() {
        System.out.println("-------------------");
        Boss boss = new Boss(14, 8);
        Wizard wizard = new Wizard(250, 10);

        wizard.wizardTurn(boss, new Recharge());
        System.out.println("After wizard turn:");
        System.out.println(wizard);
        System.out.println(boss);
        System.out.println("After boss turn:");
        wizard.bossTurn(boss);
        System.out.println(wizard);
        System.out.println(boss);

        wizard.wizardTurn(boss, new Shield());
        System.out.println("After wizard turn:");
        System.out.println(wizard);
        System.out.println(boss);
        System.out.println("After boss turn:");
        wizard.bossTurn(boss);
        System.out.println(wizard);
        System.out.println(boss);

        wizard.wizardTurn(boss, new Drain());
        System.out.println("After wizard turn:");
        System.out.println(wizard);
        System.out.println(boss);
        System.out.println("After boss turn:");
        wizard.bossTurn(boss);
        System.out.println(wizard);
        System.out.println(boss);

        wizard.wizardTurn(boss, new Poison());
        System.out.println("After wizard turn:");
        System.out.println(wizard);
        System.out.println(boss);
        System.out.println("After boss turn:");
        wizard.bossTurn(boss);
        System.out.println(wizard);
        System.out.println(boss);

        wizard.wizardTurn(boss, new MagicMissile());
        System.out.println("After wizard turn:");
        System.out.println(wizard);
        System.out.println(boss);
        System.out.println("After boss turn:");
        wizard.bossTurn(boss);
        System.out.println(wizard);
        System.out.println(boss);
    }

    private static void exampleOne() {
        Boss boss = new Boss(13, 8);
        Wizard wizard = new Wizard(250, 10);

        wizard.wizardTurn(boss, new Poison());
        System.out.println("After wizard turn:");
        System.out.println(wizard);
        System.out.println(boss);
        System.out.println("After boss turn:");
        wizard.bossTurn(boss);
        System.out.println(wizard);
        System.out.println(boss);

        wizard.wizardTurn(boss, new MagicMissile());
        System.out.println("After wizard turn:");
        System.out.println(wizard);
        System.out.println(boss);
        System.out.println("After boss turn:");
        wizard.bossTurn(boss);
        System.out.println(wizard);
        System.out.println(boss);
    }
}
