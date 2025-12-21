package year2023.puzzle12;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Spring {

    private final String pattern;
    private final List<Integer> groups;

    public Spring(String line) {
        String[] split = line.split(" ");

        pattern = split[0];
        groups = Arrays.stream(split[1].split(","))
                .map(Integer::parseInt)
                .toList();
    }

    public long solvePartA() {
        return solvePatternRecursive(pattern, groups);
    }

    public long solvePartB() {
        StringBuilder patternB = new StringBuilder();
        List<Integer> groupsB = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            patternB.append(pattern);
            if (i < 4) {
                patternB.append("?");
            }
            groupsB.addAll(groups);
        }
        System.out.println("Solving pattern " + patternB + " with groups " + groupsB);
        long l = solvePatternRecursive(patternB.toString(), groupsB);
        System.out.println("Total arrangements for pattern " + patternB + " with groups " + groupsB + " -> " + l);
        return l;
    }

    private long solvePatternRecursive(String currentPattern, List<Integer> groups) {
        if (!currentPattern.contains("?")) {
            if (isPatternValid(currentPattern, groups)) {
                //System.out.println(currentPattern + " with groups " + groups);
                return 1;
            }
            return 0;
        }

        if (!canPatternBeCompleted(currentPattern, groups)) {
            //System.out.println("Pattern cannot be completed: " + currentPattern + " with groups " + groups);
            return 0;
        }

        String patternWithHash = Strings.CS.replaceOnce(currentPattern, "?", "#");
        String patternWithDot = Strings.CS.replaceOnce(currentPattern, "?", ".");

        return solvePatternRecursive(patternWithHash, groups) + solvePatternRecursive(patternWithDot, groups);
    }

    private boolean canPatternBeCompleted(String pattern, List<Integer> groups) {
        String finishedPart = StringUtils.substring(pattern, 0, Strings.CS.indexOf(pattern, "?"));
        String unfinishedPart = StringUtils.substring(pattern, Strings.CS.indexOf(pattern, "?"));

        List<Integer> finishedGroups = Arrays.stream(finishedPart.split("\\."))
                .filter(s -> !s.isEmpty())
                .filter(s -> s.contains("#"))
                .map(String::length)
                .toList();

        if (finishedGroups.size() > groups.size()) {
            return false;
        }

        for (int i = 0; i < finishedGroups.size() - 1; i++) {
            if (!finishedGroups.get(i).equals(groups.get(i))) {
                return false;
            }
        }

        List<Integer> nonFinishedGroups = new ArrayList<>();
        for (int i = finishedGroups.size(); i < groups.size(); i++) {
            nonFinishedGroups.add(groups.get(i));
        }

        int placesNeeded = nonFinishedGroups.stream()
                .reduce(0, Integer::sum) + nonFinishedGroups.size() - 1;

        if (placesNeeded > unfinishedPart.length()) {
            //System.out.println("Not enough places: " + pattern + " needs " + placesNeeded + " but has " + unfinishedPart.length());
            return false;
        }
        return true;
    }

    private boolean isPatternValid(String pattern, List<Integer> groups) {
        return Arrays.stream(pattern.split("\\."))
                .filter(s -> !s.isEmpty())
                .filter(s -> s.contains("#"))
                .map(String::length)
                .toList().equals(groups);
    }

    @Override
    public String toString() {
        return "Spring{" +
                "pattern='" + pattern + '\'' +
                ", groups=" + groups +
                '}';
    }
}
