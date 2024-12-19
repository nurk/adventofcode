package year2024.puzzle15;

import lombok.Getter;
import org.javatuples.Pair;

import java.util.UUID;

@Getter
public class Obstacle {
    private final boolean isWall;
    private Pair<Integer, Integer> left;
    private Pair<Integer, Integer> right;
    private final UUID uuid = UUID.randomUUID();

    public Obstacle(boolean isWall, Pair<Integer, Integer> left, Pair<Integer, Integer> right) {
        this.isWall = isWall;
        this.left = left;
        this.right = right;
    }

    public boolean occupies(Pair<Integer, Integer> position) {
        return left.equals(position) || right.equals(position);
    }

    public String getLeftSymbol() {
        if (isWall) {
            return "#";
        } else {
            return "[";
        }
    }

    public String getRightSymbol() {
        if (isWall) {
            return "#";
        } else {
            return "]";
        }
    }

    public void move(Direction direction) {
        this.left = direction.getPosition(left);
        this.right = direction.getPosition(right);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Obstacle obstacle = (Obstacle) o;
        return uuid.equals(obstacle.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
