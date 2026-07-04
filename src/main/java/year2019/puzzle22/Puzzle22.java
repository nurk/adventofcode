package year2019.puzzle22;

import util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

/**
 * Part A: 6417
 */
public class Puzzle22 {
    static void main() {
        partA();
    }

    private static void partA() {
        int numberOfCards = 10007;
        List<String> input = Utils.getInput("2019/input22.txt");

        AtomicReference<List<String>> cards = new AtomicReference<>(IntStream.range(0, numberOfCards)
                .mapToObj(Integer::toString)
                .toList());

        input.forEach(shuffle -> {
            if (shuffle.equals("deal into new stack")) {
                cards.set(reverse(cards.get()));
            } else if (shuffle.startsWith("cut")) {
                int amount = Integer.parseInt(shuffle.split(" ")[1]);
                if (amount < 0) {
                    cards.set(cutLeft(cards.get(), amount));
                } else {
                    cards.set(cutRight(cards.get(), amount));
                }
            } else if (shuffle.startsWith("deal with increment")) {
                int amount = Integer.parseInt(shuffle.split(" ")[3]);
                cards.set(increment(cards.get(), amount));
            }
        });

        for (int i = 0; i < cards.get().size(); i++) {
            if (cards.get().get(i).equals("2019")) {
                System.out.println("Part A: " + i);
                break;
            }
        }
    }

    private static List<String> reverse(List<String> input) {
        return input.stream().toList().reversed();
    }

    private static List<String> cutRight(List<String> input, int amount) {
        List<String> blockOne = input.subList(0, amount);
        List<String> blockTwo = input.subList(amount, input.size());

        List<String> result = new ArrayList<>(blockTwo);
        result.addAll(blockOne);
        return result;
    }

    private static List<String> cutLeft(List<String> input, int amount) {
        int curRightAmount = input.size() - Math.abs(amount);
        return cutRight(input, curRightAmount);
    }

    private static List<String> increment(List<String> input, int amount) {
        List<String> result = new ArrayList<>(input);

        int currentIncrement = 0;
        for (int i = 0; i < input.size(); i++) {
            result.set(currentIncrement, input.get(i));
            currentIncrement = (currentIncrement + amount) % input.size();
        }
        return result;
    }
}
