package year2025.puzzle2;

import org.apache.commons.lang3.StringUtils;

public class Range {
    private final long start;
    private final long end;

    public Range(String rangeStr) {
        String[] parts = rangeStr.split("-");
        this.start = Long.parseLong(parts[0]);
        this.end = Long.parseLong(parts[1]);
    }

    public long sumInvalidIdsPartA() {
        long sum = 0;
        for (long i = start; i <= end; i++) {
            if (!isValidIdPartA(i)) {
                sum += i;
            }
        }
        return sum;
    }

    private boolean isValidIdPartA(long value) {
        String valueString = Long.toString(value);
        if (valueString.length() % 2 != 0) {
            return true;
        }
        return !StringUtils.substring(valueString, 0, valueString.length() / 2)
                .equals(StringUtils.substring(valueString, valueString.length() / 2));
    }

    public long sumInvalidIdsPartB() {
        long sum = 0;
        for (long i = start; i <= end; i++) {
            if (!isValidIdPartB(i)) {
                sum += i;
            }
        }
        return sum;
    }

    private boolean isValidIdPartB(long value) {
        String valueString = Long.toString(value);

        int halfSize = valueString.length() / 2;

        for (int i = 1; i <= halfSize; i++) {
            String pattern = StringUtils.substring(valueString, 0, i);
            StringBuilder compareString = new StringBuilder(pattern);
            while (compareString.length() < valueString.length()) {
                compareString.append(pattern);
            }
            if (compareString.toString().equals(valueString)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "Range{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
