package year2025.puzzle10;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MachineA {
    private String indicatorLights;
    private List<Boolean> indicatorBooleans;
    private final List<List<Integer>> buttonGroups = new ArrayList<>();
    private List<Integer> joltageRequirements;

    public MachineA(String line) {
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
        List<Boolean> currentState = new ArrayList<>(indicatorBooleans);
        currentState.replaceAll(ignored -> false);

        List<SolveState> solveStates = pushEveryButton(new SolveState(currentState, 0));

        while (solveStates.stream()
                .map(solveState -> solveState.indicators)
                .noneMatch(ind -> ind.equals(indicatorBooleans))) {
            List<SolveState> newSolveStates = new ArrayList<>();
            for (SolveState state : solveStates) {
                newSolveStates.addAll(pushEveryButton(state));
            }
            solveStates = newSolveStates;
        }

        SolveState result = solveStates.stream()
                .filter(ind -> ind.indicators.equals(indicatorBooleans))
                .min(Comparator.comparingInt(o -> o.presses))
                .orElseThrow();

        return result.presses;
    }

    private List<SolveState> pushEveryButton(SolveState solveState) {
        List<SolveState> results = new ArrayList<>();
        for (List<Integer> buttonGroup : buttonGroups) {
            List<Boolean> newIndicators = new ArrayList<>(solveState.indicators);
            for (Integer buttonIndex : buttonGroup) {
                newIndicators.set(buttonIndex, !newIndicators.get(buttonIndex));
            }
            if (solveState.presses < 10) {
                results.add(new SolveState(newIndicators, solveState.presses + 1));
            } else {
                System.out.println("Too many presses");
            }
        }
        return results;
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

    private record SolveState(List<Boolean> indicators, int presses) {
    }
}
