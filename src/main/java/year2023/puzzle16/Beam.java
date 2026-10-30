package year2023.puzzle16;

import lombok.Getter;
import org.javatuples.Pair;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Beam {
    @Getter
    private final Pair<Integer, Integer> position;
    private final Direction direction;

    private static final Set<String> globalVisited = new HashSet<>();

    public Beam(Pair<Integer, Integer> position, Direction direction) {
        this.position = position;
        this.direction = direction;
    }

    public static void addToVisited(Pair<Integer, Integer> pair, Direction direction) {
        globalVisited.add(pair.getValue0() + "," + pair.getValue1() + "," + direction);
    }

    public static void clearVisited() {
        globalVisited.clear();
    }

    private boolean hasVisitedOrOutOfBounds(Pair<Integer, Integer> pair, Direction direction, Cave cave) {
        if (cave.getAtPosition(pair).isEmpty()) {
            return true;
        }
        return globalVisited.contains(pair.getValue0() + "," + pair.getValue1() + "," + direction);
    }

    public List<Beam> doAction(Cave cave) {
        String positionCharacter = cave.getAtPosition(position).orElseThrow();
        List<Direction> newDirections = new ArrayList<>();

        switch (positionCharacter) {
            case "/" -> {
                switch (direction) {
                    case UP -> newDirections.add(Direction.RIGHT);
                    case DOWN -> newDirections.add(Direction.LEFT);
                    case LEFT -> newDirections.add(Direction.DOWN);
                    case RIGHT -> newDirections.add(Direction.UP);
                }
            }
            case "\\" -> {
                switch (direction) {
                    case UP -> newDirections.add(Direction.LEFT);
                    case DOWN -> newDirections.add(Direction.RIGHT);
                    case LEFT -> newDirections.add(Direction.UP);
                    case RIGHT -> newDirections.add(Direction.DOWN);
                }
            }
            case "-" -> {
                if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                    newDirections.add(direction);
                } else {
                    newDirections.add(Direction.LEFT);
                    newDirections.add(Direction.RIGHT);
                }
            }
            case "|" -> {
                if (direction == Direction.UP || direction == Direction.DOWN) {
                    newDirections.add(direction);
                } else {
                    newDirections.add(Direction.UP);
                    newDirections.add(Direction.DOWN);
                }
            }
            case "." -> newDirections.add(direction);
        }

        List<Beam> newBeams = new ArrayList<>();
        for (Direction newDirection : newDirections) {
            Pair<Integer, Integer> newPosition = getNewPosition(newDirection);

            if (!hasVisitedOrOutOfBounds(newPosition, newDirection, cave)) {
                Beam newBeam = new Beam(newPosition, newDirection);
                Beam.addToVisited(newPosition, newDirection);
                newBeams.add(newBeam);
            }
        }

        return newBeams;
    }

    private @NonNull Pair<Integer, Integer> getNewPosition(Direction newDirection) {
        Pair<Integer, Integer> newPosition;
        switch (newDirection) {
            case UP -> newPosition = new Pair<>(position.getValue0() - 1, position.getValue1());
            case DOWN -> newPosition = new Pair<>(position.getValue0() + 1, position.getValue1());
            case LEFT -> newPosition = new Pair<>(position.getValue0(), position.getValue1() - 1);
            case RIGHT -> newPosition = new Pair<>(position.getValue0(), position.getValue1() + 1);
            default -> throw new IllegalArgumentException("Unknown direction");
        }
        return newPosition;
    }


    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
