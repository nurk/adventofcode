package year2023.puzzle19;

import org.apache.commons.lang3.tuple.Pair;
import util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Part A: 449531
 */
public class Puzzle19 {
    public static void main(String[] args) {
        List<List<String>> lists = Utils.splitOnBlankLine(Utils.getInput("2023/input19-test.txt"));

        Map<String, Workflow> workflows = new HashMap<>();

        lists.getFirst().forEach(line -> {
            Workflow workflow = new Workflow(line);
            workflows.put(workflow.getId(), workflow);
        });

        List<MachinePart> machineParts = lists.get(1).stream()
                .map(MachinePart::new)
                .toList();

        Workflow start = workflows.get("in");

        long rating = machineParts.stream()
                .filter(mp -> start.isAcceptable(mp, workflows))
                .map(MachinePart::getRating)
                .reduce(0L, Long::sum);

        System.out.println("Part A: " + rating);

        partBNotWorking(start, workflows);
    }

    private static void partBNotWorking(Workflow start, Map<String, Workflow> workflows) {
        List<List<Rule>> followedRules = new ArrayList<>();

        start.follow(workflows, followedRules, new ArrayList<>());

        //1.674.09.079.868.000
        //  86.400.000.000.000

        //496262999965815
        //167409079868000
        System.out.println("Part B: " + followedRules.size());

        List<Pair<Integer, Integer>> xRanges = new ArrayList<>();
        List<Pair<Integer, Integer>> mRanges = new ArrayList<>();
        List<Pair<Integer, Integer>> aRanges = new ArrayList<>();
        List<Pair<Integer, Integer>> sRanges = new ArrayList<>();
        for (List<Rule> rules : followedRules) {
            List<Rule> filteredRules = rules.stream()
                    .filter(Rule::isHasCondition)
                    .toList();

            Map<String, Pair<Integer, Integer>> ranges = new HashMap<>();
            ranges.put("x", Pair.of(1, 4000));
            ranges.put("m", Pair.of(1, 4000));
            ranges.put("a", Pair.of(1, 4000));
            ranges.put("s", Pair.of(1, 4000));

            for (Rule rule : filteredRules) {
                ranges.compute(rule.getConditionParameter(), (k, currentRange) -> rule.mergeRange(currentRange));
            }

            xRanges.add(ranges.get("x"));
            mRanges.add(ranges.get("m"));
            aRanges.add(ranges.get("a"));
            sRanges.add(ranges.get("s"));
        }

        List<Pair<Integer, Integer>> mergedXRanges = mergeRanges(xRanges);
        List<Pair<Integer, Integer>> mergedMRanges = mergeRanges(mRanges);
        List<Pair<Integer, Integer>> mergedARanges = mergeRanges(aRanges);
        List<Pair<Integer, Integer>> mergedSRanges = mergeRanges(sRanges);
        System.out.println("Part B total options: ");
    }

    private static List<Pair<Integer, Integer>> mergeRanges(List<Pair<Integer, Integer>> ranges) {
        List<Pair<Integer, Integer>> merged = new ArrayList<>(ranges);
        boolean mergedAny;
        do {
            mergedAny = false;
            Pair<Integer, Integer> toMerge = merged.removeLast();

            for (int i = 0; i < merged.size(); i++) {
                Pair<Integer, Integer> existing = merged.get(i);
                if (toMerge.getLeft() <= existing.getRight() && toMerge.getRight() >= existing.getLeft()) {
                    // Overlap detected, merge the ranges
                    Pair<Integer, Integer> newRange = Pair.of(
                            Math.min(existing.getLeft(), toMerge.getLeft()),
                            Math.max(existing.getRight(), toMerge.getRight())
                    );
                    merged.set(i, newRange);
                    mergedAny = true;
                }
            }
            if (!mergedAny) {
                merged.add(toMerge);
            }

        } while (mergedAny);
        return merged;
    }
}
