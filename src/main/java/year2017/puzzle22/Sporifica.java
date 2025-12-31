package year2017.puzzle22;

import lombok.Getter;
import org.javatuples.Pair;

import java.util.*;

public class Sporifica {

    private final Map<Pair<Integer, Integer>, State> infectedCells = new HashMap<>();

    private int currentX;
    private int currentY;

    private int directionX = 0;
    private int directionY = -1;

    @Getter
    private long numberOfTimesInfected = 0;

    public Sporifica(List<String> input) {
        for (int row = 0; row < input.size(); row++) {
            for (int column = 0; column < input.get(row).length(); column++) {
                if (input.get(row).charAt(column) == '#') {
                    infectedCells.put(Pair.with(column, row), State.INFECTED);
                }
            }
        }

        currentX = input.getFirst().length() / 2;
        currentY = input.size() / 2;
    }

    public void tickPartA() {
        boolean isInfected = infectedCells.containsKey(Pair.with(currentX, currentY));

        if (isInfected) {
            turnRight();
            infectedCells.remove(Pair.with(currentX, currentY));
        } else {
            numberOfTimesInfected++;
            turnLeft();
            infectedCells.put(Pair.with(currentX, currentY), State.INFECTED);
        }

        currentX += directionX;
        currentY += directionY;
    }

    public void tickPartB() {
        State currentState = infectedCells.getOrDefault(Pair.with(currentX, currentY), State.CLEAN);

        switch (currentState) {
            case CLEAN -> {
                turnLeft();
                infectedCells.put(Pair.with(currentX, currentY), State.WEAKENED);
            }
            case WEAKENED -> {
                numberOfTimesInfected++;
                infectedCells.put(Pair.with(currentX, currentY), State.INFECTED);
            }
            case INFECTED -> {
                turnRight();
                infectedCells.put(Pair.with(currentX, currentY), State.FLAGGED);
            }
            case FLAGGED -> {
                turnLeft();
                turnLeft();
                infectedCells.remove(Pair.with(currentX, currentY));
            }
        }

        currentX += directionX;
        currentY += directionY;
    }

    private void turnLeft() {
        if (directionY < 0) {
            directionX = -1;
            directionY = 0;
        } else if (directionY > 0) {
            directionX = 1;
            directionY = 0;
        } else if (directionX < 0) {
            directionX = 0;
            directionY = 1;
        } else if (directionX > 0) {
            directionX = 0;
            directionY = -1;
        }
    }

    private void turnRight() {
        for (int i = 0; i < 3; i++) {
            turnLeft();
        }
    }

    private static enum State {
        CLEAN,
        WEAKENED,
        INFECTED,
        FLAGGED
    }
}
