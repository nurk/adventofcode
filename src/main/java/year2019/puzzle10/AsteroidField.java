package year2019.puzzle10;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.util.ArithmeticUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;

public class AsteroidField {

    private final List<Pair<Integer, Integer>> asteroids = new ArrayList<>();
    private final String[][] field;

    public AsteroidField(List<String> input) {
        field = new String[input.size()][input.getFirst().length()];
        for (int row = 0; row < input.size(); row++) {
            for (int column = 0; column < input.get(row).length(); column++) {
                field[row][column] = String.valueOf(input.get(row).charAt(column));
                if (field[row][column].equals("#")) {
                    asteroids.add(Pair.of(row, column));
                }
            }
        }
    }

    public long countVisibleAsteroids() {
        long maxCount = 0;
        for (Pair<Integer, Integer> source : asteroids) {
            long count = 0;
            for (Pair<Integer, Integer> canSee : asteroids) {
                if (!source.equals(canSee)) {
                    boolean blocked = isBlocked(source, canSee);
                    if (!blocked) {
                        count++;
                    }
                }
            }
            if (count > maxCount) {
                maxCount = count;
            }
        }
        return maxCount;
    }

    public long vaporizeAsteroids() {
        long totalVaporized = 0;
        List<Pair<Integer, Integer>> lastVaporized = new ArrayList<>();
        Pair<Integer, Integer> station = null;

        String[][] cloneField = new String[field.length][field[0].length];
        for (int row = 0; row < field.length; row++) {
            System.arraycopy(cloneField[row], 0, cloneField[row], 0, cloneField[row].length);
        }

        List<Pair<Integer, Integer>> cloneAsteroids = new ArrayList<>(asteroids);

        do {
            List<Pair<Integer, Integer>> toVaporize = new ArrayList<>();
            for (Pair<Integer, Integer> source : cloneAsteroids) {
                List<Pair<Integer, Integer>> currentToVaporize = new ArrayList<>();
                for (Pair<Integer, Integer> canSee : cloneAsteroids) {
                    if (!source.equals(canSee)) {
                        boolean blocked = isBlocked(source, canSee);
                        if (!blocked) {

                            currentToVaporize.add(canSee);
                        }
                    }
                }
                if (currentToVaporize.size() > toVaporize.size()) {
                    station = source;
                    toVaporize = currentToVaporize;
                }
            }

            totalVaporized += toVaporize.size();
            lastVaporized.clear();
            lastVaporized.addAll(toVaporize);
            for (Pair<Integer, Integer> vaporized : toVaporize) {
                cloneField[vaporized.getLeft()][vaporized.getRight()] = ".";
                cloneAsteroids.remove(vaporized);
            }
        } while (totalVaporized < 200);

        List<Pair<Integer, Integer>> sortedLastVaporized = sortByAngleClockWise(lastVaporized, station);
        return sortedLastVaporized.get(199).getRight() * 100 + sortedLastVaporized.get(199).getLeft();
    }

    private boolean isBlocked(Pair<Integer, Integer> source, Pair<Integer, Integer> canSee) {
        int columnDelta = canSee.getRight() - source.getRight();
        int rowDelta = canSee.getLeft() - source.getLeft();
        int gcd = ArithmeticUtils.gcd(Math.abs(columnDelta), Math.abs(rowDelta));
        int columnStep = columnDelta / gcd;
        int rowStep = rowDelta / gcd;

        int currentColumn = source.getRight() + columnStep;
        int currentRow = source.getLeft() + rowStep;

        boolean blocked = false;
        while (currentColumn != canSee.getRight() || currentRow != canSee.getLeft()) {
            if (field[currentRow][currentColumn].equals("#")) {
                blocked = true;
                break;
            }
            currentColumn += columnStep;
            currentRow += rowStep;
        }
        return blocked;
    }

    private List<Pair<Integer, Integer>> sortByAngleClockWise(List<Pair<Integer, Integer>> asteroids,
                                                              Pair<Integer, Integer> station) {
        return asteroids.stream()
                .sorted((a, b) -> {
                    double angleA = Math.atan2(a.getRight() - station.getRight(), station.getLeft() - a.getLeft());
                    double angleB = Math.atan2(b.getRight() - station.getRight(), station.getLeft() - b.getLeft());
                    angleA = (angleA + 2 * Math.PI) % (2 * Math.PI);
                    angleB = (angleB + 2 * Math.PI) % (2 * Math.PI);
                    return Double.compare(angleA, angleB);
                })
                .collect(toList());
    }

    @Override
    public String toString() {
        return Arrays.stream(field)
                .map(points -> String.join("", points))
                .collect(joining("\n"));
    }
}
