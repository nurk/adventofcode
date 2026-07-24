package year2018.puzzle24;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 9878
 * Part B: 10954
 */
public class Puzzle24 {
    static void main() {
        List<String> immuneSystem = List.of(
                "522 units each with 9327 hit points with an attack that does 177 slashing damage at initiative 14",
                "2801 units each with 3302 hit points with an attack that does 10 bludgeoning damage at initiative 7",
                "112 units each with 11322 hit points with an attack that does 809 slashing damage at initiative 8",
                "2974 units each with 9012 hit points with an attack that does 23 slashing damage at initiative 11",
                "4805 units each with 8717 hit points (weak to radiation) with an attack that does 15 bludgeoning damage at initiative 5",
                "1466 units each with 2562 hit points (immune to radiation, fire) with an attack that does 17 radiation damage at initiative 10",
                "2513 units each with 1251 hit points (immune to cold; weak to fire) with an attack that does 4 slashing damage at initiative 3",
                "6333 units each with 9557 hit points (immune to slashing) with an attack that does 14 fire damage at initiative 9",
                "2582 units each with 1539 hit points (immune to bludgeoning) with an attack that does 5 slashing damage at initiative 2",
                "2508 units each with 8154 hit points (weak to bludgeoning, cold) with an attack that does 27 bludgeoning damage at initiative 4"
        );

        List<String> infectingSystem = List.of(
                "2766 units each with 20953 hit points (weak to fire) with an attack that does 14 radiation damage at initiative 1",
                "4633 units each with 18565 hit points (immune to cold, slashing) with an attack that does 6 fire damage at initiative 15",
                "239 units each with 47909 hit points (weak to slashing, cold) with an attack that does 320 slashing damage at initiative 16",
                "409 units each with 50778 hit points (immune to radiation) with an attack that does 226 fire damage at initiative 17",
                "1280 units each with 54232 hit points (immune to slashing, fire, bludgeoning) with an attack that does 60 bludgeoning damage at initiative 13",
                "451 units each with 38251 hit points (immune to bludgeoning) with an attack that does 163 bludgeoning damage at initiative 6",
                "1987 units each with 37058 hit points with an attack that does 31 slashing damage at initiative 20",
                "1183 units each with 19147 hit points (weak to slashing) with an attack that does 24 fire damage at initiative 12",
                "133 units each with 22945 hit points (weak to slashing; immune to cold, bludgeoning) with an attack that does 287 radiation damage at initiative 19",
                "908 units each with 47778 hit points with an attack that does 97 fire damage at initiative 18"
        );

//        immuneSystem = List.of(
//                "17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2",
//                "989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3"
//        );
//
//        infectingSystem = List.of(
//                "801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1",
//                "4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4"
//        );

        partA(immuneSystem, infectingSystem);
        partB(immuneSystem, infectingSystem);
    }

    private static void partA(List<String> immuneSystem, List<String> infectingSystem) {
        Army immune = new Army(immuneSystem, true);
        Army infecting = new Army(infectingSystem, false);


        do {
            //target
            immune.chooseTargetToAttack(infecting);
            infecting.chooseTargetToAttack(immune);

            //attack
            List<Group> groups = new ArrayList<>(immune.getGroups());
            groups.addAll(infecting.getGroups());

            groups.stream()
                    .sorted((g1, g2) -> g1.getInitiative() > g2.getInitiative() ? -1 : 1)
                    .forEach(Group::performAttack);

            // reset
            immune.resetRound();
            infecting.resetRound();

//            System.out.println("--------");
//            System.out.println("new round");
//            System.out.println("--------");
        } while (!immune.getGroups().isEmpty() && !infecting.getGroups().isEmpty());

        List<Group> winningGroups;
        if (!immune.getGroups().isEmpty()) {
            winningGroups = new ArrayList<>(immune.getGroups());
            System.out.println("Immune system wins");
        } else {
            winningGroups = new ArrayList<>(infecting.getGroups());
            System.out.println("Infecting system wins");
        }

        // Part A: 9649 too low
        System.out.println("Part A: " + winningGroups.stream().mapToInt(Group::getAmount).sum());
    }

    private static void partB(List<String> immuneSystem, List<String> infectingSystem) {

        int boost = 1;
        boolean didImmuneWin = false;
        List<Group> winningGroups = new ArrayList<>();

        do {
            Army immune = new Army(immuneSystem, true);
            immune.boost(boost);
            Army infecting = new Army(infectingSystem, false);

            boolean deadlocked = false;
            do {
                //target
                immune.chooseTargetToAttack(infecting);
                infecting.chooseTargetToAttack(immune);

                if (!immune.hasAtLeastOneTarget() && !infecting.hasAtLeastOneTarget()) {
                    System.out.println("No more targets to attack, ending battle.");
                    deadlocked = true;
                    break;
                }

                //attack
                List<Group> groups = new ArrayList<>(immune.getGroups());
                groups.addAll(infecting.getGroups());

                groups.stream()
                        .sorted((g1, g2) -> g1.getInitiative() > g2.getInitiative() ? -1 : 1)
                        .forEach(Group::performAttack);

                // reset
                immune.resetRound();
                infecting.resetRound();

//                System.out.println("--------");
//                System.out.println("new round");
//                System.out.println("--------");
            } while (!immune.getGroups().isEmpty() && !infecting.getGroups().isEmpty());

            System.out.println();

            if (deadlocked) {
                boost++;
            } else {
                if (!immune.getGroups().isEmpty()) {
                    winningGroups = new ArrayList<>(immune.getGroups());
                    System.out.println("Immune system wins");
                    didImmuneWin = true;
                } else {
                    winningGroups = new ArrayList<>(infecting.getGroups());
                    System.out.println("Infecting system wins");
                    boost++;
                }
            }

        } while (!didImmuneWin);

        System.out.println(boost);
        System.out.println("Part B: " + winningGroups.stream().mapToInt(Group::getAmount).sum());
    }
}
