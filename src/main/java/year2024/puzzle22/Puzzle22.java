package year2024.puzzle22;

import org.javatuples.Pair;
import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 20441185092
 * Part B: 2268
 */
public class Puzzle22 {

    public static void main(String[] args) {
        List<Long> input = new ArrayList<>(Utils.getInput("2024/input22.txt", Long::valueOf));

        System.out.println("Part A: " + input.stream()
                .map(Puzzle22::get2000thSecretNumber)
                .mapToLong(Long::longValue)
                .sum());

        List<List<Pair<Long, Long>>> sequences = input.stream()
                .map(Puzzle22::generatePriceSequence)
                .toList();

        long maxBananas = 0;

        //lucky the highest sequence was in the first list.
        //looping over all lists would have been miserable
        List<Pair<Long, Long>> thisSequence = sequences.getFirst();
        for (int i = 3; i < 2000; i++) {
            List<Long> shortSequence = new ArrayList<>();

            shortSequence.add(thisSequence.get(i - 3).getValue1());
            shortSequence.add(thisSequence.get(i - 2).getValue1());
            shortSequence.add(thisSequence.get(i - 1).getValue1());
            shortSequence.add(thisSequence.get(i).getValue1());

            long bananas = 0;
            for (List<Pair<Long, Long>> seq : sequences) {
                bananas += findSequence(shortSequence, seq);

                if (bananas > maxBananas) {
                    maxBananas = bananas;
                }
            }
        }

        System.out.println("Part B: " + maxBananas);
    }

    private static long findSequence(List<Long> shortSequence, List<Pair<Long, Long>> sequence) {
        for (int i = 0; i < 1997; i++) {
            if (sequence.get(i).getValue1().equals(shortSequence.get(0)) &&
                    sequence.get(i + 1).getValue1().equals(shortSequence.get(1)) &&
                    sequence.get(i + 2).getValue1().equals(shortSequence.get(2)) &&
                    sequence.get(i + 3).getValue1().equals(shortSequence.get(3))
            ) {
                return sequence.get(i + 3).getValue0();
            }
        }
        return 0;
    }

    private static List<Pair<Long, Long>> generatePriceSequence(long secretNumber) {
        List<Pair<Long, Long>> sequence = new ArrayList<>();

        long currentPrice = secretNumber % 10;

        long nextSecretNumber = secretNumber;
        for (int i = 0; i < 2000; i++) {
            nextSecretNumber = nextSecretNumber(nextSecretNumber);

            long price = nextSecretNumber % 10;
            sequence.add(new Pair<>(price, price - currentPrice));
            currentPrice = price;
        }

        return sequence;
    }

    private static long get2000thSecretNumber(long secretNumber) {
        long secretNumber2000 = secretNumber;
        for (int i = 0; i < 2000; i++) {
            secretNumber2000 = nextSecretNumber(secretNumber2000);
        }

        return secretNumber2000;
    }

    private static long nextSecretNumber(long currentSecretNumber) {
        /*
        Calculate the result of multiplying the secret number by 64.
            Then, mix this result into the secret number.
            Finally, prune the secret number.
        Calculate the result of dividing the secret number by 32.
            Round the result down to the nearest integer.
            Then, mix this result into the secret number.
            Finally, prune the secret number.
        Calculate the result of multiplying the secret number by 2048.
            Then, mix this result into the secret number.
            Finally, prune the secret number.

        Each step of the above process involves mixing and pruning:

        To mix a value into the secret number,
            calculate the bitwise XOR of the given value and the secret number.
            Then, the secret number becomes the result of that operation.
            (If the secret number is 42 and you were to mix 15 into the secret number, the secret number would become 37.)
        To prune the secret number,
            calculate the value of the secret number modulo 16777216.
            Then, the secret number becomes the result of that operation.
            (If the secret number is 100000000 and you were to prune the secret number, the secret number would become 16113920.)
         */

        long a = currentSecretNumber * 64;
        long nextSecretNumber = mixSecretNumber(currentSecretNumber, a);
        nextSecretNumber = pruneSecretNumber(nextSecretNumber);

        long b = Math.floorDiv(nextSecretNumber, 32);
        nextSecretNumber = mixSecretNumber(nextSecretNumber, b);
        nextSecretNumber = pruneSecretNumber(nextSecretNumber);

        long c = nextSecretNumber * 2048;
        nextSecretNumber = mixSecretNumber(nextSecretNumber, c);
        nextSecretNumber = pruneSecretNumber(nextSecretNumber);

        return nextSecretNumber;
    }

    private static long mixSecretNumber(long currentSecretNumber, long value) {
        return currentSecretNumber ^ value;
    }

    private static long pruneSecretNumber(long value) {
        return value % 16777216;
    }
}
