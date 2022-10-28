package year2016.puzzle1;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import util.Utils;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Puzzle1 {

    public static void main(String[] args) throws URISyntaxException {
        List<String> input = Utils.getInput("2016/input1.txt", s -> StringUtils.split(s, ", "))
                .stream()
                .flatMap(Arrays::stream)
                .toList();

        AtomicInteger x = new AtomicInteger();
        AtomicInteger y = new AtomicInteger();
        AtomicReference<Direction> direction = new AtomicReference<>(Direction.NORTH);
        List<Pair<Integer, Integer>> visited = new ArrayList<>();
        verifyVisited(visited, x.get(), y.get());
        input.forEach(
                movement -> {
                    switch (movement.substring(0, 1)) {
                        case "L" -> direction.set(direction.get().left());
                        case "R" -> direction.set(direction.get().right());
                    }
                    int amount = Integer.parseInt(movement.substring(1));
                    int originalX = x.get();
                    int originalY = y.get();
                    x.set(x.get() + amount * direction.get().getXMultiplier());
                    y.set(y.get() + amount * direction.get().getYMultiplier());

                    // something a lot more fancy can be done here with intersections between
                    // vectors of two points but I don't feel like it
                    if (originalX == x.get()) {
                        for (int i = originalY + direction.get().getYMultiplier(); i != y.get(); i = i + direction.get()
                                .getYMultiplier()) {
                            verifyVisited(visited, originalX, i);
                        }
                    }
                    if (originalY == y.get()) {
                        for (int i = originalX + direction.get().getXMultiplier(); i != x.get(); i = i + direction.get()
                                .getXMultiplier()) {
                            verifyVisited(visited, i, originalY);
                        }
                    }
                }
        );

        System.out.println(Math.abs(x.get()) + Math.abs(y.get()));
    }

    private static void verifyVisited(List<Pair<Integer, Integer>> visited, int x, int y) {
        Pair<Integer, Integer> pair = Pair.of(x, y);
        if (visited.contains(pair)) {
            System.out.println("Visited " + pair);
            System.out.println("Visited distance " + (Math.abs(pair.getLeft()) + Math.abs(pair.getRight())));
        }
        visited.add(pair);
    }

    @Getter
    @RequiredArgsConstructor
    enum Direction {
        NORTH(0, 1) {
            @Override
            Direction left() {
                return WEST;
            }

            @Override
            Direction right() {
                return EAST;
            }
        },
        EAST(1, 0) {
            @Override
            Direction left() {
                return NORTH;
            }

            @Override
            Direction right() {
                return SOUTH;
            }
        },
        SOUTH(0, -1) {
            @Override
            Direction left() {
                return EAST;
            }

            @Override
            Direction right() {
                return WEST;
            }
        },
        WEST(-1, 0) {
            @Override
            Direction left() {
                return SOUTH;
            }

            @Override
            Direction right() {
                return NORTH;
            }
        };

        private final int xMultiplier, yMultiplier;

        abstract Direction left();

        abstract Direction right();
    }
}
