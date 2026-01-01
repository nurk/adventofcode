package year2015.puzzle24;

import org.apache.commons.math3.util.CombinatoricsUtils;
import util.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Part A: 11266889531
 * Part B: 77387711
 */
public class Puzzle24 {
    public static void main(String[] args) {
        List<Integer> packages = Utils.getInput("2015/input24.txt", Integer::parseInt);

        System.out.println("Part A: " + solve(packages, 3));
        System.out.println("Part B: " + solve(packages, 4));
    }

    private static Long solve(List<Integer> packages, int numberOfGroups) {
        int totalWeight = packages.stream().mapToInt(Integer::intValue).sum();
        int targetWeight = totalWeight / numberOfGroups;

        CombinatoricsUtils.combinationsIterator(packages.size(), 1);

        List<int[]> validCombinations = new ArrayList<>();

        for (int i = 1; i <= packages.size(); i++) {
            Iterator<int[]> combinationIterator = CombinatoricsUtils.combinationsIterator(packages.size(), i);
            while (combinationIterator.hasNext()) {
                int[] combination = combinationIterator.next();
                int sum = 0;
                for (int index : combination) {
                    sum += packages.get(index);
                }
                if (sum == targetWeight) {
                    validCombinations.add(combination);
                }
            }

            if (!validCombinations.isEmpty()){
                break;
            }
        }

        return validCombinations.stream()
                .map(combination -> calculateQuantumEntanglement(packages, combination))
                .min(Long::compareTo)
                .orElseThrow();
    }

    private static long calculateQuantumEntanglement(List<Integer> packages, int[] combination) {
        long quantumEntanglement = 1;
        for (int index : combination) {
            quantumEntanglement *= packages.get(index);
        }
        return quantumEntanglement;
    }
}