package year2024.puzzle19;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TowelB {
    private final List<String> options;

    public TowelB(List<String> options) {
        this.options = options;
    }

    public long countPossibilities(String target) {
        return buildPattern(target, new HashMap<>());
    }

    private long buildPattern(String target, Map<String, Long> seen) {
        if (seen.containsKey(target)) {
            return seen.get(target);
        }

        if (target.isEmpty()) {
            return 1L;
        }

        long count = 0L;

        for (String option : options) {
            if (target.startsWith(option)) {
                String remainder = target.substring(option.length());
                count += buildPattern(remainder, seen);
            }
        }

        seen.put(target, count);

        return count;
    }
}
