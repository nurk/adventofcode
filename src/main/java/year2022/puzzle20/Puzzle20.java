package year2022.puzzle20;

import util.Utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Puzzle20 {
    static List<Coordinate> numbers = new ArrayList<>();
    static int numberOfCoordinates = 0;

    public static void main(String[] args) {
        //Part A: 3466
        //Part B: 9995532008348

        System.out.println("Part A: " + partA());
        System.out.println("Part B: " + partB());
    }

    private static long partA() {
        initData(1L);
        doMix();
        return getResult();
    }

    private static long partB() {
        initData(811589153L);

        for (int i = 0; i < 10; i++) {
            doMix();
        }

        return getResult();
    }

    private static void initData(long multiplier) {
        AtomicInteger index = new AtomicInteger(0);
        numberOfCoordinates = Utils.getInput("2022/input20.txt").size();
        numbers = Utils.getInput("2022/input20.txt", Integer::parseInt)
                .stream()
                .map(i -> new Coordinate(i * multiplier, index.getAndIncrement()))
                .toList();
    }


    private static void doMix() {
        numbers.stream()
                .sorted(Comparator.comparingLong(o -> o.originalIndex))
                .forEach(n -> {
                    if (n.moveAmount != 0) {
                        long fromIndex = n.index;
                        long toIndex = getModulo(n.index + n.moveAmount);
                        numbers.forEach(nn -> nn.move(fromIndex, toIndex, n.moveAmount));
                    }
                });
    }

    private static Long getResult() {
        long zeroIndex = numbers.stream()
                .filter(n -> n.number == 0)
                .findFirst()
                .orElseThrow()
                .index;

        return Stream.of(1000, 2000, 3000)
                .map(i -> numbers.stream()
                        .filter(n -> n.index == (zeroIndex + i) % numbers.size())
                        .findFirst()
                        .orElseThrow()
                        .number)
                .reduce(0L, Long::sum);
    }

    static class Coordinate {
        final long number;
        long index;
        final long originalIndex;
        final long moveAmount;

        Coordinate(long number, long index) {
            this.number = number;
            this.index = index;
            this.originalIndex = index;
            this.moveAmount = getMoveAmount(number);
        }

        void move(long fromIndex, long toIndex, long moveAmount) {
            if (moveAmount < 0 && fromIndex + moveAmount <= 0) {
                toIndex = getModulo(toIndex - 1);
            }

            if (moveAmount > 0 && toIndex < fromIndex) {
                toIndex = getModulo(toIndex + 1);
            }


            if (index == fromIndex) {
                index = toIndex;
                return;
            }
            if (fromIndex < toIndex) {
                if (index >= fromIndex && index <= toIndex) {
                    index = getModulo(index - 1);
                }
            } else {
                if (index <= fromIndex && index >= toIndex) {
                    index = getModulo(index + 1);
                }
            }
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "number=" + number +
                    ", index=" + index +
                    ", originalIndex=" + originalIndex +
                    ", moveAmount=" + moveAmount +
                    '}';
        }
    }

    static long getMoveAmount(long n) {
        return n % (numberOfCoordinates - 1);
    }

    static long getModulo(long n) {
        return BigInteger.valueOf(n).mod(BigInteger.valueOf(numbers.size())).longValue();
    }
}
