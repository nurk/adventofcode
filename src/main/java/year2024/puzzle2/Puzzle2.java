package year2024.puzzle2;

import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Part A: 442
 * Part B: 493
 */
public class Puzzle2 {

    public static void main(String[] args) {
        List<List<Integer>> reports = new ArrayList<>();

        Utils.getInput("2024/input2.txt").forEach(line -> reports.add(Arrays.stream(line.split(" "))
                .map(Integer::valueOf)
                .toList()));

        System.out.println("Part A: " +
                reports.stream()
                        .filter(r -> isAscending(r) || isDescending(r))
                        .count());

        System.out.println("Part B: " +
                reports.stream()
                        .filter(r -> isAscendingBadLevel(r) || isDescendingBadLevel(r))
                        .count());
    }

    @SuppressWarnings("SuspiciousListRemoveInLoop")
    public static boolean isAscendingBadLevel(List<Integer> report) {
        for (int i = 0; i < report.size(); i++) {
            List<Integer> subReport = new ArrayList<>(report);
            subReport.remove(i);
            if (isAscending(subReport)) {
                return true;
            }
        }

        return isAscending(report);
    }

    @SuppressWarnings("SuspiciousListRemoveInLoop")
    public static boolean isDescendingBadLevel(List<Integer> report) {
        for (int i = 0; i < report.size(); i++) {
            List<Integer> subReport = new ArrayList<>(report);
            subReport.remove(i);
            if (isDescending(subReport)) {
                return true;
            }
        }

        return isDescending(report);
    }

    public static boolean isAscending(List<Integer> report) {
        for (int i = 1; i < report.size(); i++) {
            if (Math.abs(report.get(i) - report.get(i - 1)) > 3) {
                return false;
            }
            if (report.get(i - 1) >= report.get(i)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isDescending(List<Integer> report) {
        for (int i = 1; i < report.size(); i++) {
            if (Math.abs(report.get(i) - report.get(i - 1)) > 3) {
                return false;
            }
            if (report.get(i - 1) <= report.get(i)) {
                return false;
            }
        }

        return true;
    }
}
