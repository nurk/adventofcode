package year2018.puzzle4;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class Guard {
    private final int id;
    private final List<SleepPeriod> sleepPeriods = new ArrayList<>();

    public Guard(int id) {
        this.id = id;
    }

    public void addSleepPeriod(SleepPeriod sleepPeriod) {
        sleepPeriods.add(sleepPeriod);
    }

    public Long getTotalSleepTime() {
        return sleepPeriods.stream()
                .map(SleepPeriod::getSleepTime)
                .mapToLong(value -> value)
                .sum();
    }

    @Override
    public String toString() {
        return "Guard{" +
                "id=" + id +
                ", sleepPeriods=" + sleepPeriods +
                '}';
    }

    public Pair<Integer, Long> minuteMostASleep() {
        long currentMax = 0;
        int currentMinute = 0;
        for (int i = 0; i < 60; i++) {
            int finalI = i;
            long val = sleepPeriods.stream()
                    .filter(sleepPeriod -> sleepPeriod.coversMinute(finalI))
                    .count();
            if (val > currentMax) {
                currentMinute = i;
                currentMax = val;
            }

        }
        return Pair.of(currentMinute, currentMax);
    }

    public int getSolution() {
        return id * minuteMostASleep().getLeft();
    }
}
