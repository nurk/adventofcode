package year2023.puzzle9;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OasisSensor {

    private final List<Long> numbers;

    public OasisSensor(String line) {
        numbers = Arrays.stream(line.split(" "))
                .map(Long::parseLong)
                .toList();
    }

    public Long solveForward() {
        List<List<Long>> solveList = solveToZero();

        solveList.getLast().add(0L);

        for (int i = solveList.size() - 2; i >= 0; i--) {
            List<Long> currentSolveList = solveList.get(i);
            List<Long> nextSolveList = solveList.get(i + 1);

            currentSolveList.add(currentSolveList.getLast() + nextSolveList.getLast());
        }

        return solveList.getFirst().getLast();
    }

    public Long solveBackward() {
        List<List<Long>> solveList = solveToZero();

        solveList.getLast().addFirst(0L);

        for (int i = solveList.size() - 2; i >= 0; i--) {
            List<Long> currentSolveList = solveList.get(i);
            List<Long> nextSolveList = solveList.get(i + 1);

            currentSolveList.addFirst(currentSolveList.getFirst() - nextSolveList.getFirst());
        }

        return solveList.getFirst().getFirst();
    }

    private @NonNull List<List<Long>> solveToZero() {
        List<List<Long>> solveList = new ArrayList<>();
        solveList.add(new ArrayList<>(numbers));

        while (!solveList.getLast().stream().allMatch(l -> l == 0L)) {
            List<Long> nextSolve = new ArrayList<>();

            for (int i = 1; i < solveList.getLast().size(); i++) {
                nextSolve.add(solveList.getLast().get(i) - solveList.getLast().get(i - 1));
            }

            solveList.add(nextSolve);
        }
        return solveList;
    }
}
