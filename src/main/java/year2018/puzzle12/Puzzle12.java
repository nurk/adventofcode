package year2018.puzzle12;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

/**
 * Part A: 3472
 * Part B: 2600000000919
 */
public class Puzzle12 {

    static int potCountStartsAt = 0;

    static void main() {
        List<String> input = Utils.getInput("2018/input12.txt");

        String initialState = StringUtils.substringAfter(input.removeFirst(), "initial state: ");
        input.removeFirst();

        List<Rule> rules = input.stream()
                .map(Rule::new)
                .toList();

        partA(initialState, rules);
        partB(initialState, rules);
    }

    private static void partB(String initialState, List<Rule> rules) {
        potCountStartsAt = 0;
        AtomicReference<String> state = new AtomicReference<>(initialState);
        long previousSum = 0;
        long previousDiff = 0;
        long stableDiffCount = 0;

        for (int i = 0; i < 1000; i++) {
            state.set(prePendAndPostPendPots(state.get()));
            state.set(doIteration(state.get(), rules));

            long sum = 0;
            for (int j = 0; j < state.get().length(); j++) {
                if (state.get().charAt(j) == '#') {
                    sum += j + potCountStartsAt;
                }
            }

            long diff = sum - previousSum;
            if (diff == previousDiff) {
                stableDiffCount++;
            } else {
                stableDiffCount = 0;
            }

            if (stableDiffCount >= 5) {
                long remainingGenerations = 50_000_000_000L - (i + 1);
                long finalSum = sum + remainingGenerations * diff;
                System.out.println("Part B: " + finalSum);
                return;
            }

            previousSum = sum;
            previousDiff = diff;
        }
    }

    private static void partA(String initialState, List<Rule> rules) {
        potCountStartsAt = 0;
        AtomicReference<String> state = new AtomicReference<>(initialState);
        IntStream.range(0, 20).forEach(_ -> {
            state.set(prePendAndPostPendPots(state.get()));
            state.set(doIteration(state.get(), rules));
        });

        int sum = 0;
        for (int i = 0; i < state.get().length(); i++) {
            if (state.get().charAt(i) == '#') {
                sum += i + potCountStartsAt;
            }
        }

        System.out.println("Part A: " + sum);
    }

    private static String prePendAndPostPendPots(String state) {
        int firstPlantIndex = state.indexOf('#');
        int lastPlantIndex = state.lastIndexOf('#');

        StringBuilder newState = new StringBuilder(state);
        if (firstPlantIndex < 3) {
            newState.insert(0, "...");
            potCountStartsAt -= 3;
        }
        if (lastPlantIndex > state.length() - 3) {
            newState.append("...");
        }

        return newState.toString();
    }

    private static String doIteration(String state, List<Rule> rules) {
        StringBuilder newState = new StringBuilder();
        for (int i = 0; i < state.length(); i++) {
            String pattern = extractPattern(state, i);

            String result = rules.stream()
                    .filter(rule -> rule.pattern.equals(pattern))
                    .map(rule -> rule.result)
                    .findFirst()
                    .orElse(".");
            newState.append(result);
        }

        return newState.toString();
    }

    private static String extractPattern(String state, int index) {
        StringBuilder pattern = new StringBuilder();
        if (index == 0) {
            pattern.append("..");
            pattern.append(state, 0, 3);
        } else if (index == 1) {
            pattern.append(".");
            pattern.append(state, 0, 4);
        } else if (index == state.length() - 2) {
            pattern.append(state, index - 2, state.length());
            pattern.append(".");
        } else if (index == state.length() - 1) {
            pattern.append(state, index - 2, state.length());
            pattern.append("..");
        } else {
            pattern.append(state, index - 2, index + 3);
        }

        return pattern.toString();
    }
}
