package year2024.puzzle19;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Towel {
    private final List<String> options;

    public Towel(List<String> options) {
        this.options = options;
    }

    public boolean isPossible(String target) {
        return buildPattern(target, "", new HashMap<>());
    }

    private boolean buildPattern(String target, String builder, Map<String, List<String>> seen) {
        if (target.equals(builder)) {
            return true;
        }

        if (!StringUtils.startsWith(target, builder) || builder.length() >= target.length()) {
            return false;
        }

        return options.stream()
                .filter(option -> !seen.containsKey(builder + option) || !seen.get(builder + option).contains(option))
                .anyMatch(option -> buildPattern(target,
                        builder + option,
                        addToSeen(seen, builder + option, option)));
    }

    private Map<String, List<String>> addToSeen(Map<String, List<String>> seen, String builder, String option) {
        seen.merge(builder, new ArrayList<>(List.of(option)), (existingList, newList) -> {
            existingList.addAll(newList);
            return existingList;
        });
        return seen;
    }
}
