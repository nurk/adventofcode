package year2020.puzzle16;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ValidRanges {

    private final List<Range> ranges = new ArrayList<>();

    public ValidRanges() {

    }

    public ValidRanges(String line) {
        addLine(line);
    }

    public void addLine(String line) {
        String[] rangesSplit = StringUtils.splitByWholeSeparator(StringUtils.substringAfter(line, ": "), " or ");
        for (String s : rangesSplit) {
            String[] bounds = s.split("-");
            ranges.add(new Range(Integer.parseInt(bounds[0]), Integer.parseInt(bounds[1])));
        }
    }

    public boolean isValid(Integer number) {
        for (Range range : ranges) {
            if (range.inRange(number)) {
                return true;
            }
        }
        return false;
    }

    private static class Range {
        private final int start;
        private final int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public boolean inRange(int number) {
            return number >= start && number <= end;
        }
    }

}
