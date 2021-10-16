package year2018.puzzle4;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SleepPeriod {

    private final LocalDateTime start;
    private final LocalDateTime end;

    public SleepPeriod(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public long getSleepTime() {
        return ChronoUnit.MINUTES.between(start, end);
    }

    @Override
    public String toString() {
        return "SleepPeriod{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    public boolean coversMinute(int minute) {
        return start.getMinute() <= minute && end.getMinute() > minute;
    }
}
