package year2018.puzzle4;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Line {
    private int guard;
    private final LocalDateTime dateTime;
    private final Action action;

    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Line(String line) {
        dateTime = LocalDateTime.parse(StringUtils.substringBetween(line, "[", "]"), df);
        if (StringUtils.contains(line, "asleep")) {
            action = Action.SLEEP;
        } else if (StringUtils.contains(line, "wakes")) {
            action = Action.WAKEUP;
        } else {
            action = Action.START;
            guard = Integer.parseInt(StringUtils.substringBetween(line, "#", " "));
        }
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getGuard() {
        return guard;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "Line{" +
                "guard=" + guard +
                ", dateTime=" + dateTime +
                ", action=" + action +
                '}';
    }

    public enum Action {
        START, SLEEP, WAKEUP
    }
}
