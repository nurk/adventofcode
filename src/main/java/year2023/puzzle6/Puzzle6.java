package year2023.puzzle6;

import java.util.List;

public class Puzzle6 {
    public static void main(String[] args) {
        System.out.println("Part A test: " + getTestRaces().stream()
                .map(Race::getNumberOfDifferentWaysToWin)
                .reduce((aLong, aLong2) -> aLong * aLong2)
                .orElseThrow());
        System.out.println("Part A: " + getRaces().stream()
                .map(Race::getNumberOfDifferentWaysToWin)
                .reduce((aLong, aLong2) -> aLong * aLong2)
                .orElseThrow());

        System.out.println("Part B test: " + getTestRacesB().stream()
                .map(Race::getNumberOfDifferentWaysToWin)
                .reduce((aLong, aLong2) -> aLong * aLong2)
                .orElseThrow());
        System.out.println("Part B: " + getRacesB().stream()
                .map(Race::getNumberOfDifferentWaysToWin)
                .reduce((aLong, aLong2) -> aLong * aLong2)
                .orElseThrow());
    }

    private static List<Race> getTestRaces() {
        /*
         * Time:      7  15   30
         * Distance:  9  40  200
         */
        return List.of(
                new Race(7, 9),
                new Race(15, 40),
                new Race(30, 200)
        );
    }

    private static List<Race> getTestRacesB() {
        /*
         * Time:      7  15   30
         * Distance:  9  40  200
         */
        return List.of(
                new Race(71530, 940200)
        );
    }

    private static List<Race> getRaces() {
        /*
         * Time:        41     77     70     96
         * Distance:   249   1362   1127   1011
         */
        return List.of(
                new Race(41, 249),
                new Race(77, 1362),
                new Race(70, 1127),
                new Race(96, 1011)
        );
    }

    private static List<Race> getRacesB() {
        /*
         * Time:        41     77     70     96
         * Distance:   249   1362   1127   1011
         */
        return List.of(
                new Race(41777096, 249136211271011L)
        );
    }
}
