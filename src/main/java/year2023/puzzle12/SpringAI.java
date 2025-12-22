package year2023.puzzle12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpringAI {

    private final String pattern;
    private final List<Integer> groups;

    // Memoization cache: key is DP state (position, groupIndex, runLength), value is number of arrangements
    private final Map<String, Long> memo = new HashMap<>();

    public SpringAI(String line) {
        String[] split = line.split(" ");

        pattern = split[0];
        groups = Arrays.stream(split[1].split(","))
                .map(Integer::parseInt)
                .toList();
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
        memo.clear();
        long l = solvePatternRecursive(patternB.toString(), 0, 0, 0, groupsB);
        System.out.println("Total arrangements for pattern " + patternB + " with groups " + groupsB + " -> " + l);
        return l;
    }

    // Dynamic-programming style recursive solver
    // pattern: the full pattern string
    // i: current index in pattern (0..pattern.length())
    // groupIndex: current index in groups list (0..groups.size())
    // runLength: length of the current consecutive '#' run
    private long solvePatternRecursive(String pattern,
                                       int i,
                                       int groupIndex,
                                       int runLength,
                                       List<Integer> groups) {
        int n = pattern.length();

        // Build a compact cache key from the DP state; pattern and groups are fixed for a given solve
        String key = i + "|" + groupIndex + "|" + runLength;
        Long cached = memo.get(key);
        if (cached != null) {
            return cached;
        }

        long result;

        // Base case: reached end of pattern
        if (i == n) {
            result = isCompleteConfiguration(groupIndex, runLength, groups) ? 1L : 0L;
            memo.put(key, result);
            return result;
        }

        char c = pattern.charAt(i);
        result = 0L;

        // Option 1: place a '#', if allowed by the current character
        if (c == '#' || c == '?') {
            int newRun = runLength + 1;
            // Optional pruning: cannot exceed the size of the current group
            if (groupIndex < groups.size() && newRun <= groups.get(groupIndex)) {
                result += solvePatternRecursive(pattern, i + 1, groupIndex, newRun, groups);
            }
        }

        // Option 2: place a '.', if allowed by the current character
        if (c == '.' || c == '?') {
            if (runLength > 0) {
                // We're closing a group; runLength must match the current group exactly
                if (groupIndex < groups.size() && runLength == groups.get(groupIndex)) {
                    result += solvePatternRecursive(pattern, i + 1, groupIndex + 1, 0, groups);
                }
            } else {
                // No open run; just move on without changing group index
                result += solvePatternRecursive(pattern, i + 1, groupIndex, 0, groups);
            }
        }

        memo.put(key, result);
        return result;
    }

    // Check if, at the end of the pattern, the current group index and run length
    // describe a configuration that exactly matches all required groups.
    private boolean isCompleteConfiguration(int groupIndex, int runLength, List<Integer> groups) {
        if (runLength > 0) {
            // We are ending a run; it must match exactly the current group and be the last group
            return groupIndex < groups.size()
                    && runLength == groups.get(groupIndex)
                    && groupIndex + 1 == groups.size();
        } else {
            // No open run; must have used all groups
            return groupIndex == groups.size();
        }
    }

    @Override
    public String toString() {
        return "Spring{" +
                "pattern='" + pattern + '\'' +
                ", groups=" + groups +
                '}';
    }
}
