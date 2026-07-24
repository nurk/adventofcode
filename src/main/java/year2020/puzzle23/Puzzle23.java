package year2020.puzzle23;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Puzzle23 {
    static void main() {
        /**
         * Before the crab starts, it will designate the first cup in your list as the current cup. The crab is then going to do 100 moves.
         *
         * Each move, the crab does the following actions:
         *
         * The crab picks up the three cups that are immediately clockwise of the current cup. They are removed from the circle; cup spacing is adjusted as necessary to maintain the circle.
         * The crab selects a destination cup: the cup with a label equal to the current cup's label minus one. If this would select one of the cups that was just picked up, the crab will keep subtracting one until it finds a cup that wasn't just picked up. If at any point in this process the value goes below the lowest value on any cup's label, it wraps around to the highest value on any cup's label instead.
         * The crab places the cups it just picked up so that they are immediately clockwise of the destination cup. They keep the same order as when they were picked up.
         * The crab selects a new current cup: the cup which is immediately clockwise of the current cup.
         */

        String input = "389125467";

        List<Integer> cups = Arrays.stream(input.split(""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int currentCup = 0;

        for (int i = 0; i < 10; i++) {
            System.out.println("Move " + (i + 1) + ": " + cups);
            int destinationCup = cups.get(currentCup) - 1;

            int removeCup1 = cups.remove((currentCup + 1) % cups.size());
            int removeCup2 = cups.remove((currentCup + 1) % cups.size());
            int removeCup3 = cups.remove((currentCup + 1) % cups.size());

            System.out.println("Removed cups: " + removeCup1 + ", " + removeCup2 + ", " + removeCup3);

            while (destinationCup < 1 || destinationCup == removeCup1 || destinationCup == removeCup2 || destinationCup == removeCup3) {
                if (destinationCup < 1) {
                    destinationCup = 9;
                } else {
                    destinationCup--;
                }
            }

            System.out.println("Destination cup: " + destinationCup);

            int indexOfDestinationCup = cups.indexOf(destinationCup) + 1;
            System.out.println("Index of destination cup: " + indexOfDestinationCup);

//            if(destinationCup > cups.size()) {
//                cups.add(removeCup1);
//                cups.add(removeCup2);
//                cups.add(removeCup3);
//            } else {
                cups.add(indexOfDestinationCup, removeCup3);
                cups.add(indexOfDestinationCup, removeCup2);
                cups.add(indexOfDestinationCup, removeCup1);
//            }


            System.out.println("After placing cups: " + cups);

            currentCup = (currentCup + 1) % cups.size();
            System.out.println("----");
        }
    }
}
