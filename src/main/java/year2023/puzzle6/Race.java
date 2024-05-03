package year2023.puzzle6;

import java.util.stream.LongStream;

public class Race {
    private final long duration;
    private final long distance;

    public Race(long duration, long distance) {
        this.duration = duration;
        this.distance = distance;
    }

    public long getNumberOfDifferentWaysToWin() {
        return LongStream.range(1, duration)
                .map(this::calculateDistance)
                //.peek(System.out::println)
                .filter(currentDistance -> currentDistance > distance)
                .count();
    }

    private long calculateDistance(long secondsToHold) {
        long remainingDuration = duration - secondsToHold;
        return secondsToHold * remainingDuration;
    }
}
