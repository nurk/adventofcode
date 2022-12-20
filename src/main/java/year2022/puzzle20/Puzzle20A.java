package year2022.puzzle20;

import util.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Puzzle20A {

    static List<Coordinate> numbers = new ArrayList<>();

    public static void main(String[] args) {
        //Part A: 3466
        AtomicInteger index = new AtomicInteger(0);
        Utils.getInput("2022/input20.txt", Integer::parseInt).forEach(i -> {
            numbers.add(new Coordinate(i, index.get()));
            index.getAndIncrement();
        });

        numbers.stream()
                .sorted(Comparator.comparingInt(o -> o.index))
                .forEach(System.out::println);

        numbers.stream()
                .sorted(Comparator.comparingInt(o -> o.index))
                .forEach(n -> {
                    System.out.println("Moving number " + n.number);
                    int fromIndex = n.index;
                    int toIndex = getToIndex(n.number, fromIndex);

                    numbers.forEach(nn -> nn.move(fromIndex, toIndex));
                });

        System.out.println();
        numbers.stream()
                .sorted(Comparator.comparingInt(o -> o.index))
                .forEach(System.out::println);

        int zeroIndex = numbers.stream()
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
                .reduce(0, Integer::sum));
    }

    static class Coordinate {
        int number;
        int index;

        public Coordinate(int number, int index) {
            this.number = number;
            this.index = index;
        }

        public void move(int fromIndex, int toIndex) {
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
                    '}';
        }
    }

    static int getToIndex(int number, int fromIndex) {
        int toIndex = fromIndex + number;
        if (toIndex > 0) {
            return toIndex / numbers.size() + toIndex % numbers.size();
        } else {
            int rounds = 0;
            while (toIndex <= 0) {
                rounds++;
                toIndex = toIndex + numbers.size();
            }
            return toIndex - rounds;
        }
    }

    static int getModulo(int n) {
        while (n < 0) {
            n = numbers.size() + n - 1;
        }
        return n % numbers.size();
    }
}
