package year2016.puzzle20;

import util.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Part A: 22887907
 * Part B: 109
 */
public class Puzzle20 {

    private static final long MAX = 4294967295L;

    static void main() {
        List<Range> ranges = new ArrayList<>(Utils.getInput("2016/input20.txt")
                .stream()
                .map(Range::new)
                .toList());

        boolean merged = true;
        while (merged) {
            merged = false;
            for (int i = 0; i < ranges.size() && !merged; i++) {
                for (int j = i + 1; j < ranges.size() && !merged; j++) {
                    if (ranges.get(i).canBeMerged(ranges.get(j))) {
                        Range mergedRange = ranges.get(i).merge(ranges.get(j));
                        ranges.remove(j);
                        ranges.remove(i);
                        ranges.add(mergedRange);
                        merged = true;
                    }
                }
            }
        }

        List<Range> sortedRanges = ranges.stream()
                .sorted(Comparator.comparingLong(Range::getStart))
                .toList();

        sortedRanges.forEach(System.out::println);
        if (sortedRanges.getFirst().getStart() > 0) {
            System.out.println("Part A: " + 0);
        } else {
            System.out.println("Part A: " + (sortedRanges.getFirst().getEnd() + 1));
        }

        long allowed = 0;
        for (int i = 0; i < sortedRanges.size() - 1; i++) {
            Range currentRange = sortedRanges.get(i);
            Range nextRange = sortedRanges.get(i + 1);

            allowed += nextRange.getStart() - 1 - currentRange.getEnd();
        }

        allowed += MAX - sortedRanges.getLast().getEnd();

        System.out.println("Part B: " + allowed);
    }
}
