package year2022.puzzle21;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Puzzle21 {

    static List<Monkey> monkeys;

    public static void main(String[] args) {
        monkeys = Utils.getInput("2022/input21.txt", Monkey::new);
        monkeys.forEach(Monkey::replaceWithMonkeys);

        Monkey root = monkeys.stream()
                .filter(m -> m.name.equals("root"))
                .findFirst()
                .orElseThrow();

        System.out.println("Part A: " + root.calculateValue());

        String equation = root.rightMonkey.calculateValue() + " = " +root.leftMonkey.asString();
        System.out.println(equation);
        // https://www.mathpapa.com/equation-solver/
        System.out.println("Part B: " + "3247317268284");
    }

    static class Monkey {
        String name;
        long number = 0;
        String left;
        String right;
        Monkey leftMonkey;
        Monkey rightMonkey;
        boolean hasNumber = false;
        Operation operation;

        Monkey(String line) {
            name = StringUtils.substringBefore(line, ":");
            String after = StringUtils.substringAfter(line, ": ");
            if (StringUtils.isNumeric(after)) {
                number = Integer.parseInt(after);
                hasNumber = true;
            } else {
                String[] s = after.split(" ");
                left = s[0];
                operation = Operation.fromName(s[1]);
                right = s[2];
            }
        }

        void replaceWithMonkeys() {
            if (!hasNumber) {
                leftMonkey = monkeys.stream()
                        .filter(m -> m.name.equals(left))
                        .findFirst()
                        .orElseThrow();
                rightMonkey = monkeys.stream()
                        .filter(m -> m.name.equals(right))
                        .findFirst()
                        .orElseThrow();
            }
        }

        String asString() {
            if(this.name.equals("humn")){
                return "x";
            } else if (this.hasNumber){
                return String.valueOf(number);
            } else {
                return "(" + leftMonkey.asString() + " " + operation.name + " " + rightMonkey.asString() + ")";
            }
        }

        public long calculateValue() {
            if (this.hasNumber) {
                return number;
            }
            return operation.oper.apply(leftMonkey.calculateValue(), rightMonkey.calculateValue());
        }
    }

    enum Operation {

        ADD("+", Long::sum),
        SUBTRACT("-", (a, b) -> a - b),
        MULTIPLY("*", (a, b) -> a * b),
        DIVIDE("/", (a, b) -> a / b);

        final String name;
        final BiFunction<Long, Long, Long> oper;

        Operation(String name, BiFunction<Long, Long, Long> oper) {
            this.name = name;
            this.oper = oper;
        }

        static Operation fromName(String n) {
            return Arrays.stream(Operation.values())
                    .filter(o -> n.equals(o.name))
                    .findFirst()
                    .orElseThrow();
        }
    }
}
