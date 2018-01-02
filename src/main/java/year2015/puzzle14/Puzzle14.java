package year2015.puzzle14;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Puzzle14 {
    public static void main(String[] args) {
        List<Reindeer> reindeers = new ArrayList<>();
        reindeers.add(new Reindeer(19, 7, 124));
        reindeers.add(new Reindeer(3, 15, 28));
        reindeers.add(new Reindeer(19, 9, 164));
        reindeers.add(new Reindeer(19, 9, 158));
        reindeers.add(new Reindeer(13, 7, 82));
        reindeers.add(new Reindeer(25, 6, 145));
        reindeers.add(new Reindeer(14, 3, 38));
        reindeers.add(new Reindeer(3, 16, 37));
        reindeers.add(new Reindeer(25, 6, 143));


        IntStream.rangeClosed(1, 2503).forEach(
                i -> {
                    reindeers.forEach(reindeer -> reindeer.setCalculatedDistanceAtSecond(i));
                    reindeers.sort((o1, o2) -> o2.getCurrentDistance().compareTo(o1.getCurrentDistance()));
                    int winningDistance = reindeers.get(0).getCurrentDistance();
                    reindeers.forEach(reindeer -> reindeer.addInFirstPlaceSecondIfDistanceMatches(winningDistance));
                }
        );

        reindeers.sort((o1, o2) -> o2.getCurrentDistance().compareTo(o1.getCurrentDistance()));
        System.out.println("wining distance: " + reindeers.get(0).getCurrentDistance());
        reindeers.sort((o1, o2) -> o2.getInFirstPlaceSeconds().compareTo(o1.getInFirstPlaceSeconds()));
        System.out.println("wining seconds: " + reindeers.get(0).getInFirstPlaceSeconds());
    }
}
