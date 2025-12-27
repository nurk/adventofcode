package year2018.puzzle7;

import org.jspecify.annotations.NonNull;
import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: GJKLDFNPTMQXIYHUVREOZSAWCB
 * Part B: GJLNDKFPTXYIMHQUVREOZSAWCB in number of ticks 967
 */
public class Puzzle7 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2018/input7.txt");

        partA(input);
        partB(input);
    }

    private static void partB(List<String> input) {
        int duration = input.size() > 7 ? 60 : 0;
        int workers = input.size() > 7 ? 5 : 2;

        List<Step> steps = initSteps(input, duration);

        StringBuilder result = new StringBuilder();
        long tickCount = 0;

        while (!steps.stream().allMatch(Step::isFinished)) {
            if (workers > 0) {
                List<Step> canBeStartedSteps = new ArrayList<>(steps.stream()
                        .filter(Step::canBeStarted)
                        .sorted()
                        .toList());
                for (Step step : canBeStartedSteps) {
                    if (workers <= 0) {
                        break;
                    }
                    step.addWorker();
                    workers--;
                }
            }
            tickCount++;
            for (Step step : steps) {
                if (!step.isFinished()) {
                    boolean finished = step.tick();
                    if (finished) {
                        workers += step.getNumberOfWorkers();
                        step.setNumberOfWorkers(0);
                        result.append(step.getName());
                    }
                }
            }
        }

        System.out.println("Part B: " + result + " in number of ticks " + tickCount);
    }

    private static void partA(List<String> input) {
        List<Step> steps = initSteps(input, 0);

        StringBuilder result = new StringBuilder();
        List<Step> canBeStartedSteps = new ArrayList<>(steps.stream()
                .filter(Step::canBeStarted)
                .sorted()
                .toList());

        while (!canBeStartedSteps.isEmpty()) {
            Step step = canBeStartedSteps.getFirst();
            step.setFinished(true);
            result.append(step.getName());

            canBeStartedSteps = new ArrayList<>(steps.stream()
                    .filter(Step::canBeStarted)
                    .sorted()
                    .toList());
        }

        System.out.println("Part A: " + result);
    }

    private static @NonNull List<Step> initSteps(List<String> input, int duration) {
        List<Step> steps = new ArrayList<>();
        input.forEach(line -> {
            String stepName = line.substring(5, 6);
            String otherStepName = line.substring(36, 37);

            Step stepOne = new Step(stepName, duration);
            if (!steps.contains(stepOne)) {
                steps.add(stepOne);
            }

            Step stepTwo = new Step(otherStepName, duration);
            if (!steps.contains(stepTwo)) {
                steps.add(stepTwo);
            }
        });

        input.forEach(line -> {
            // Step C must be finished before step A can begin.
            String stepName = line.substring(5, 6);
            String otherStepName = line.substring(36, 37);

            Step previousStep = steps.stream().filter(s -> s.getName().equals(stepName)).findFirst().orElseThrow();
            Step nextStep = steps.stream().filter(s -> s.getName().equals(otherStepName)).findFirst().orElseThrow();

            nextStep.addPreviousStep(previousStep);
        });
        return steps;
    }
}
