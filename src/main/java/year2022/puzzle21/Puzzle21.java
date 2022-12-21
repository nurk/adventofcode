package year2022.puzzle21;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Puzzle21 {

    static List<Monkey> monkeys;

    public static void main(String[] args) {
        //Part A: 169525884255464
        monkeys = Utils.getInput("2022/input21.txt", Monkey::new);
        monkeys.forEach(Monkey::replaceOperandsWithMonkeys);

        Monkey root = monkeys.stream()
                .filter(m -> m.name.equals("root"))
                .findFirst()
                .orElseThrow();

        System.out.println("Part A: " + root.calculateValue());

        String equation = root.rightMonkey.calculateValue() + " = " + root.leftMonkey.asString();
        System.out.println(equation);
        // https://www.mathpapa.com/equation-solver/
        System.out.println("Part B: " + "3247317268284");
    }

    static class Monkey {
        final String name;
        Long number;
        String leftOperand;
        String rightOperand;
        Monkey leftMonkey;
        Monkey rightMonkey;
        Operation operation;

        Monkey(String line) {
            name = StringUtils.substringBefore(line, ":");
            String data = StringUtils.substringAfter(line, ": ");
            if (StringUtils.isNumeric(data)) {
                number = Long.parseLong(data);
            } else {
                String[] split = data.split(" ");
                leftOperand = split[0];
                operation = Operation.fromSymbol(split[1]);
                rightOperand = split[2];
            }
        }

        void replaceOperandsWithMonkeys() {
            if (number == null) {
                leftMonkey = monkeys.stream()
                        .filter(m -> m.name.equals(leftOperand))
                        .findFirst()
                        .orElseThrow();
                rightMonkey = monkeys.stream()
                        .filter(m -> m.name.equals(rightOperand))
                        .findFirst()
                        .orElseThrow();
            }
        }

        String asString() {
            if (this.name.equals("humn")) {
                return "x";
            } else if (number != null) {
                return String.valueOf(number);
            } else {
                return "(" + leftMonkey.asString() + " " + operation.symbol + " " + rightMonkey.asString() + ")";
            }
        }

        long calculateValue() {
            if (number != null) {
                return number;
            }
            return operation.calculator.apply(leftMonkey.calculateValue(), rightMonkey.calculateValue());
        }
    }

    enum Operation {
        ADD("+", Long::sum),
        SUBTRACT("-", (a, b) -> a - b),
        MULTIPLY("*", (a, b) -> a * b),
        DIVIDE("/", (a, b) -> a / b);

        final String symbol;
        final BiFunction<Long, Long, Long> calculator;

        Operation(String symbol, BiFunction<Long, Long, Long> calculator) {
            this.symbol = symbol;
            this.calculator = calculator;
        }

        static Operation fromSymbol(String s) {
            return Arrays.stream(Operation.values())
                    .filter(o -> s.equals(o.symbol))
                    .findFirst()
                    .orElseThrow();
        }
    }
}
