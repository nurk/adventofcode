package year2025.puzzle10;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MachineB {
    private String indicatorLights;
    private List<Boolean> indicatorBooleans;
    private final List<List<Integer>> buttonGroups = new ArrayList<>();
    private List<Integer> joltageRequirements;

    public MachineB(String line) {
        String[] split = StringUtils.split(line, " ");
        for (String s : split) {
            if (s.startsWith("[")) {
                indicatorLights = StringUtils.substringBetween(s, "[", "]");
                indicatorBooleans = Arrays.stream(indicatorLights.split(""))
                        .map(symbol -> !symbol.equals("."))
                        .toList();
            }
            if (s.startsWith("(")) {
                String groupString = StringUtils.substringBetween(s, "(", ")");
                buttonGroups.add(Arrays.stream(groupString.split(","))
                        .map(Integer::parseInt)
                        .toList());
            }
            if (s.startsWith("{")) {
                String groupString = StringUtils.substringBetween(s, "{", "}");
                joltageRequirements = Arrays.stream(groupString.split(","))
                        .map(Integer::parseInt)
                        .toList();
            }
        }
    }

    public int solve() {
        List<Integer> currentState = new ArrayList<>(joltageRequirements);
        currentState.replaceAll(ignored -> 0);

        List<SolveState> solveStates = pushEveryButton(new SolveState(currentState, 0));

        while (solveStates.stream()
                .map(solveState -> solveState.joltageCounters)
                .noneMatch(ind -> ind.equals(joltageRequirements))) {
            List<SolveState> newSolveStates = new ArrayList<>();
            for (SolveState state : solveStates) {
                newSolveStates.addAll(pushEveryButton(state));
            }
            solveStates = newSolveStates;
            // System.out.println(solveStates.size());
        }

        SolveState result = solveStates.stream()
                .filter(ind -> ind.joltageCounters.equals(joltageRequirements))
                .min(Comparator.comparingInt(o -> o.presses))
                .orElseThrow();

        return result.presses;
    }

    private List<SolveState> pushEveryButton(SolveState solveState) {
        List<SolveState> results = new ArrayList<>();
        for (List<Integer> buttonGroup : buttonGroups) {
            List<Integer> newJoltageCounters = new ArrayList<>(solveState.joltageCounters);
            for (Integer buttonIndex : buttonGroup) {
                newJoltageCounters.set(buttonIndex, newJoltageCounters.get(buttonIndex) + 1);
            }
            if (areJoltageCountersBelowMax(newJoltageCounters)) {
                results.add(new SolveState(newJoltageCounters, solveState.presses + 1));
            } else {
                //System.out.println("Too many presses or joltage exceeded");
            }
        }
        return results;
    }

    private boolean areJoltageCountersBelowMax(List<Integer> counters) {
        for (int i = 0; i < counters.size(); i++) {
            if (counters.get(i) > joltageRequirements.get(i)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public String toString() {
        return "Machine{" +
                "indicatorLights='" + indicatorLights + '\'' +
                ", indicatorBooleans=" + indicatorBooleans +
                ", buttonGroups=" + buttonGroups +
                ", joltageRequirements=" + joltageRequirements +
                '}';
    }

    private record SolveState(List<Integer> joltageCounters, int presses) {
    }
}
