package year2022.puzzle20;

import org.apache.commons.lang3.time.StopWatch;
import util.Utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Puzzle20B {
    static List<Coordinate> numbers = new ArrayList<>();

    public static void main(String[] args) {
        AtomicInteger index = new AtomicInteger(0);
        Utils.getInput("2022/input20test.txt", Integer::parseInt).forEach(i -> {
            numbers.add(new Coordinate(i, index.get()));
            index.getAndIncrement();
        });

        numbers.stream()
                .sorted(Comparator.comparingLong(o -> o.index))
                .forEach(System.out::println);

        for (int i = 0; i < 10; i++) {
            System.out.println(i + 1);
            StopWatch s = StopWatch.createStarted();
            numbers.stream()
                    .sorted(Comparator.comparingLong(o -> o.originalIndex))
                    .forEach(n -> {
                        long moveAmount = getMoveAmount(n.number);
                        long fromIndex = n.index;
                        long toIndex = getModulo(n.index + moveAmount);

                        if (moveAmount != 0) {
                            numbers.forEach(nn -> nn.move(fromIndex, toIndex, n.number < 0));
                        }
                    });
            System.out.println();
            numbers.stream()
                    .sorted(Comparator.comparingLong(o -> o.index))
                    .forEach(System.out::println);
            s.split();
            System.out.println(s.formatSplitTime());
        }

        long zeroIndex = numbers.stream()
                .filter(n -> n.number == 0)
                .findFirst()
                .orElseThrow()
                .index;

        System.out.println(zeroIndex);
        System.out.println(Stream.of(1000, 2000, 3000)
                .map(i -> numbers.stream()
                        .filter(n -> n.index == (zeroIndex + i) % numbers.size())
                        .findFirst()
                        .orElseThrow()
                        .number)
                .reduce(0L, Long::sum));
    }

    static class Coordinate {
        long number;
        long index;
        long originalIndex;

        public Coordinate(long number, long index) {
            this.number = number * 811589153;
            this.index = index;
            this.originalIndex = index;
        }

        public void move(long fromIndex, long toIndex, boolean moveLeft) {
            //System.out.println("Moving from " + this);
            // System.out.println("Moving " + number + " fromIndex " + fromIndex + " to index " + toIndex + " moveLeft " + moveLeft);
            if (moveLeft) {
                toIndex = getModulo(toIndex - 1);
            }
            if (!moveLeft && toIndex < fromIndex) {
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
            //System.out.println("Moved to " + this);
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "number=" + number +
                    ", index=" + index +
                    '}';
        }
    }

    static long getToIndex(long number, long fromIndex) {
        long toIndex = fromIndex + number;
        return getModulo(toIndex);
    }

    static long getMoveAmount(long n) {
        return n % (numbers.size() - 1);
    }

    static long getModulo(long n) {
        return BigInteger.valueOf(n).mod(BigInteger.valueOf(numbers.size())).longValue();
    }
}
