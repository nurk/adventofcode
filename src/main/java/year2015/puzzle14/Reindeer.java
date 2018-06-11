package year2015.puzzle14;

public class Reindeer {
    private Integer speed = 0;
    private Integer flySeconds = 0;
    private Integer restSeconds = 0;
    private Integer distance = 0;
    private Integer inFirstPlaceSeconds = 0;

    public Reindeer(Integer speed, Integer flySeconds, Integer restSeconds) {
        this.speed = speed;
        this.flySeconds = flySeconds;
        this.restSeconds = restSeconds;
    }

    private int getDistance(int seconds) {
        Integer distance;
        Integer loopSize = flySeconds + restSeconds;
        Integer fullLoops = seconds / loopSize;
        Integer remainingSeconds = seconds % loopSize;

        distance = fullLoops * flySeconds * speed;

        if (remainingSeconds <= flySeconds) {
            distance = distance + remainingSeconds * speed;
        } else {
            distance = distance + flySeconds * speed;
        }

        return distance;
    }

    public void setCalculatedDistanceAtSecond(Integer seconds) {
        this.distance = getDistance(seconds);
    }

    public void addInFirstPlaceSecondIfDistanceMatches(Integer distance) {
        if (this.distance.equals(distance)) {
            inFirstPlaceSeconds++;
        }
    }

    public Integer getCurrentDistance() {
        return distance;
    }

    public Integer getInFirstPlaceSeconds() {
        return inFirstPlaceSeconds;
    }
}
