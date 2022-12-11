package year2022.puzzle11;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Puzzle11 {

    static final List<Monkey> monkeys = new ArrayList<>();
    static BigInteger superModulo;

    public static void main(String[] args) {
        List<String> input = Utils.getInput("2022/input11.txt");

        int[] splitIndexes = Utils.getSplitIndexes(input);

        IntStream.range(0, splitIndexes.length - 1)
                .mapToObj(i -> input.subList(splitIndexes[i] + 1, splitIndexes[i + 1]))
                .forEach(monkeyStrings -> monkeys.add(new Monkey(monkeyStrings)));

        IntStream.range(0, 20)
                .forEach(i -> monkeys.forEach(Monkey::doRoundA));

        System.out.println("PartA: " + monkeys.stream()
                .map(Monkey::getInspectedItems)
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .peek(System.out::println)
                .reduce(1L, (integer, integer2) -> integer * integer2));

        // solution from internet (searched after I had a solution myself
        monkeys.clear();

        IntStream.range(0, splitIndexes.length - 1)
                .mapToObj(i -> input.subList(splitIndexes[i] + 1, splitIndexes[i + 1]))
                .forEach(monkeyStrings -> monkeys.add(new Monkey(monkeyStrings)));

        superModulo = monkeys.stream()
                .map(m -> m.testValue)
                .reduce(BigInteger.ONE, BigInteger::multiply);

        IntStream.range(0, 10000)
                .forEach(i -> monkeys.forEach(Monkey::doRoundInternetLogic));

        System.out.println("PartB Internet: " + monkeys.stream()
                .map(Monkey::getInspectedItems)
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .peek(System.out::println)
                .reduce(1L, (integer, integer2) -> integer * integer2));

        // my very inefficient solution but it worked
        monkeys.clear();

        IntStream.range(0, splitIndexes.length - 1)
                .mapToObj(i -> input.subList(splitIndexes[i] + 1, splitIndexes[i + 1]))
                .forEach(monkeyStrings -> monkeys.add(new Monkey(monkeyStrings)));

        IntStream.range(0, 10000)
                .forEach(i -> monkeys.forEach(Monkey::doRoundB));

        System.out.println("PartB: " + monkeys.stream()
                .map(Monkey::getInspectedItems)
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .peek(System.out::println)
                .reduce(1L, (integer, integer2) -> integer * integer2));
    }

    static class Monkey {
        private final List<BigInteger> items;
        private final List<List<String>> stack = new ArrayList<>();
        private final BiFunction<BigInteger, BigInteger, BigInteger> operation;
        private BigInteger operationValue = BigInteger.ZERO;
        private boolean isMultiplyOperation = false;
        private boolean useOldValueInOperation = false;
        private final BigInteger testValue;
        private final Integer trueMonkey;
        private final Integer falseMonkey;
        private Long inspectedItems = 0L;

        public Monkey(List<String> monkeyStrings) {
            items = Arrays.stream(StringUtils.substringAfter(monkeyStrings.get(1), ": ").split(", "))
                    .map(BigInteger::new)
                    .collect(Collectors.toList());

            Arrays.stream(StringUtils.substringAfter(monkeyStrings.get(1), ": ").split(", "))
                    .forEach(s -> {
                        List<String> ss = new ArrayList<>();
                        ss.add(s);
                        stack.add(ss);
                    });

            if (StringUtils.contains(monkeyStrings.get(2), "+")) {
                operation = BigInteger::add;
            } else {
                operation = BigInteger::multiply;
                isMultiplyOperation = true;
            }
            if (StringUtils.substringAfterLast(monkeyStrings.get(2), " ").equals("old")) {
                useOldValueInOperation = true;
            } else {
                operationValue = new BigInteger(StringUtils.substringAfterLast(monkeyStrings.get(2), " "));
            }
            testValue = new BigInteger(StringUtils.substringAfterLast(monkeyStrings.get(3), " "));
            trueMonkey = Integer.parseInt(StringUtils.substringAfterLast(monkeyStrings.get(4), " "));
            falseMonkey = Integer.parseInt(StringUtils.substringAfterLast(monkeyStrings.get(5), " "));
        }

        public void doRoundA() {
            for (BigInteger item : items) {
                inspectedItems++;
                BigInteger currentWorryLevel;
                if (useOldValueInOperation) {
                    currentWorryLevel = operation.apply(item, item);
                } else {
                    currentWorryLevel = operation.apply(item, operationValue);
                }

                currentWorryLevel = currentWorryLevel.divide(BigInteger.valueOf(3L));

                if (currentWorryLevel.remainder(testValue).equals(BigInteger.ZERO)) {
                    monkeys.get(trueMonkey).addItem(currentWorryLevel);
                } else {
                    monkeys.get(falseMonkey).addItem(currentWorryLevel);
                }
            }
            items.clear();
        }

        public void doRoundB() {
            for (List<String> operations : stack) {
                inspectedItems++;
                if (useOldValueInOperation) {
                    if (isMultiplyOperation) {
                        operations.add("squared");
                    } else {
                        operations.add("* 2");
                    }
                } else {
                    if (isMultiplyOperation) {
                        operations.add("* " + operationValue);
                    } else {
                        operations.add("+ " + operationValue);
                    }
                }

                BigInteger currentWorryLevel = calculateCurrentWorryLevel(operations);

                if (currentWorryLevel.remainder(testValue).equals(BigInteger.ZERO)) {
                    monkeys.get(trueMonkey).addItem(operations);
                } else {
                    monkeys.get(falseMonkey).addItem(operations);
                }
            }
            stack.clear();
        }

        public void doRoundInternetLogic() {
            for (BigInteger item : items) {
                inspectedItems++;
                BigInteger currentWorryLevel;
                if (useOldValueInOperation) {
                    currentWorryLevel = operation.apply(item, item);
                } else {
                    currentWorryLevel = operation.apply(item, operationValue);
                }

                currentWorryLevel = currentWorryLevel.remainder(superModulo);

                if (currentWorryLevel.remainder(testValue).equals(BigInteger.ZERO)) {
                    monkeys.get(trueMonkey).addItem(currentWorryLevel);
                } else {
                    monkeys.get(falseMonkey).addItem(currentWorryLevel);
                }
            }
            items.clear();
        }

        private BigInteger calculateCurrentWorryLevel(List<String> operations) {
            BigInteger currentWorryLevel = new BigInteger(operations.get(0));
            for (int i = 1; i < operations.size(); i++) {
                if (StringUtils.equals(operations.get(i), "squared")) {
                    currentWorryLevel = currentWorryLevel.pow(2).remainder(testValue);
                } else {
                    String[] split = operations.get(i).split(" ");
                    if (StringUtils.equals(split[0], "+")) {
                        currentWorryLevel = currentWorryLevel.add(new BigInteger(split[1]));
                    } else {
                        currentWorryLevel = currentWorryLevel.multiply(new BigInteger(split[1]).remainder(testValue));
                    }
                }
            }
            return currentWorryLevel;
        }

        private void addItem(List<String> operations) {
            stack.add(operations);
        }

        public void addItem(BigInteger item) {
            items.add(item);
        }

        public Long getInspectedItems() {
            return inspectedItems;
        }
    }
}
