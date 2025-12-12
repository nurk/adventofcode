package year2025.puzzle12;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class Tree {
    private final long height;
    private final long width;
    private final List<Integer> numberOfPresentsForEachIndex;

    public Tree(String input) {
        String[] split = StringUtils.substringBefore(input, ": ").split("x");
        this.height = Long.parseLong(split[1]);
        this.width = Long.parseLong(split[0]);

        numberOfPresentsForEachIndex = Arrays.stream(StringUtils.substringAfter(input, ": ").split(" "))
                .map(Integer::parseInt)
                .toList();
    }

    public boolean canFitAllPresentsVerifyNaive(List<Present> presents) {
        return doOccupiedCellsFit(presents);
    }

    private boolean doOccupiedCellsFit(List<Present> presents) {
        long totalOccupiedCells = 0;
        for (int i = 0; i < numberOfPresentsForEachIndex.size(); i++) {
            Present present = findPresentByIndex(presents, i);
            totalOccupiedCells += present.getNumberOfOccupiedCells() * numberOfPresentsForEachIndex.get(i);
        }

        return totalOccupiedCells <= (height * width);
    }

    private Present findPresentByIndex(List<Present> presents, int index) {
        return presents.stream()
                .filter(present -> present.getIndex() == index)
                .findFirst()
                .orElseThrow();
    }

    @Override
    public String toString() {
        return "Tree{" +
                "height=" + height +
                ", width=" + width +
                ", numberOfPresents=" + numberOfPresentsForEachIndex +
                '}';
    }
}
