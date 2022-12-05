package year2022.puzzle5;

import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Puzzle5 {

    public static void main(String[] args) {
        //QNHWJVJZW
        //BPCZJLFJW

        Stacks stacksPartA = new Stacks();
        stacksPartA.initInput();
        Utils.getInput("2022/input5.txt").forEach(stacksPartA::doIndividualMove);
        System.out.println(stacksPartA.getSolution());

        Stacks stacksPartB = new Stacks();
        stacksPartB.initInput();
        Utils.getInput("2022/input5.txt").forEach(stacksPartB::doStackedMove);
        System.out.println(stacksPartB.getSolution());
    }

    static class Stacks {
        private final List<List<String>> stacks = new ArrayList<>();
        private final Pattern pattern = Pattern.compile("move (\\d*) from (\\d*) to (\\d*)");

        public void doIndividualMove(String move) {
            Matcher matcher = pattern.matcher(move);
            if (matcher.matches()) {
                int amount = Integer.parseInt(matcher.group(1));
                int from = Integer.parseInt(matcher.group(2));
                int to = Integer.parseInt(matcher.group(3));

                List<String> fromList = stacks.get(from - 1);
                List<String> toList = stacks.get(to - 1);

                Utils.reverseRange(fromList.size() - amount, fromList.size())
                        .forEach(i -> {
                            toList.add(fromList.get(i));
                            fromList.remove(i);
                        });
            }
        }

        public void doStackedMove(String move) {
            Matcher matcher = pattern.matcher(move);
            if (matcher.matches()) {
                int amount = Integer.parseInt(matcher.group(1));
                int from = Integer.parseInt(matcher.group(2));
                int to = Integer.parseInt(matcher.group(3));

                List<String> fromList = stacks.get(from - 1);
                stacks.get(to - 1).addAll(fromList.subList(fromList.size() - amount, fromList.size()));

                Utils.reverseRange(fromList.size() - amount, fromList.size())
                        .forEach(fromList::remove);
            }
        }

        public String getSolution() {
            return stacks.stream()
                    .map(stack -> stack.isEmpty() ? "" : stack.get(stack.size() - 1))
                    .collect(Collectors.joining());
        }

        @SuppressWarnings("unused")
        public void initTest() {
            stacks.add(Arrays.stream("ZN".split("")).collect(Collectors.toList()));
            stacks.add(Arrays.stream("MCD".split("")).collect(Collectors.toList()));
            stacks.add(Arrays.stream("P".split("")).collect(Collectors.toList()));
        }

        public void initInput() {
            stacks.add(Arrays.stream("CZNBMWQV".split("")).collect(Collectors.toList()));
            stacks.add(Arrays.stream("HZRWCB".split("")).collect(Collectors.toList()));
            stacks.add(Arrays.stream("FQRJ".split("")).collect(Collectors.toList()));
            stacks.add(Arrays.stream("ZSWHFNMT".split("")).collect(Collectors.toList()));
            stacks.add(Arrays.stream("GFWLNQP".split("")).collect(Collectors.toList()));
            stacks.add(Arrays.stream("WPL".split("")).collect(Collectors.toList()));
            stacks.add(Arrays.stream("VBDRGCQJ".split("")).collect(Collectors.toList()));
            stacks.add(Arrays.stream("ZQNBW".split("")).collect(Collectors.toList()));
            stacks.add(Arrays.stream("HLFCGTJ".split("")).collect(Collectors.toList()));
        }

        @Override
        public String toString() {
            return stacks.stream()
                    .map(stack -> String.join("", stack))
                    .collect(Collectors.joining("\n"));
        }
    }
}
