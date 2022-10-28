package year2016.puzzle2;

import lombok.RequiredArgsConstructor;
import util.Utils;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Puzzle2 {
    private static final String[][] keypad = {{"1", "2", "3"}, {"4", "5", "6"}, {"7", "8", "9"}};
    private static final String[][] keypad2 = {{"", "", "1", "", ""}, {"", "2", "3", "4", ""}, {"5", "6", "7", "8", "9"}, {"", "A", "B", "C", ""}, {"", "", "D", "", ""}};

    public static void main(String[] args) {
/*        AtomicInteger x = new AtomicInteger(1);
        AtomicInteger y = new AtomicInteger(1);
        String[][] usedKeypad = keypad;*/
        AtomicInteger x = new AtomicInteger(2);
        AtomicInteger y = new AtomicInteger(0);
        String[][] usedKeypad = keypad2;

        AtomicReference<String> numbers = new AtomicReference<>("");

        Utils.getInput("2016/input2.txt", s -> s).forEach(
                line -> {
                    Arrays.stream(line.split(""))
                            .map(Movement::valueOf)
                            .forEach(movement -> {
                                int newX = x.get() + movement.xIncrement;
                                int newY = y.get() + movement.yIncrement;
                                try {
                                    if (!"".equals(usedKeypad[newX][newY])) {
                                        x.set(newX);
                                        y.set(newY);
                                    }
                                } catch (Exception e) {
                                    //ignore
                                }
                            });
                    numbers.set(numbers.get() + usedKeypad[x.get()][y.get()]);
                }
        );
        System.out.println(numbers);
    }

    @RequiredArgsConstructor
    enum Movement {
        U(-1, 0), L(0, -1), R(0, 1), D(1, 0);

        private final int xIncrement, yIncrement;
    }
}
