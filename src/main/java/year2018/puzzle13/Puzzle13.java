package year2018.puzzle13;

import org.javatuples.Pair;
import util.Utils;

import java.util.*;

/**
 * Part A: 65,73
 * Part B: 54,66
 */
public class Puzzle13 {
    static void main() {
        partA();
        partB();
    }

    public static void partB() {
        List<String> input = new ArrayList<>(Utils.getInput("2018/input13.txt", (s) -> s));
        Track track = new Track(input, 151);

        List<Cart> carts = track.getCarts();

        while (carts.size() != 1) {
            carts.sort(Cart::compareTo);

            for (Cart cart : new ArrayList<>(carts)) {
                track.move(cart);
                removeCollisions(carts);
            }
        }

        System.out.println("Part B: " + carts.getFirst().getColumn() + "," + carts.getFirst().getRow());
    }

    private static void removeCollisions(List<Cart> carts) {
        Set<Pair<Integer, Integer>> positions = new HashSet<>();
        List<Cart> toRemove = new ArrayList<>();

        for (Cart cart : carts) {
            Pair<Integer, Integer> position = Pair.with(cart.getRow(), cart.getColumn());
            if (!positions.add(position)) {
                toRemove.add(cart);
                carts.stream()
                        .filter(c -> c != cart && c.getRow() == cart.getRow() && c.getColumn() == cart.getColumn())
                        .findFirst()
                        .ifPresent(toRemove::add);
            }
        }

        carts.removeAll(toRemove);
    }

    private static void partA() {
        List<String> input = new ArrayList<>(Utils.getInput("2018/input13.txt", (s) -> s));
        Track track = new Track(input, 151);

        List<Cart> carts = track.getCarts();

        while (hasCollision(carts).isEmpty()) {
            carts.sort(Cart::compareTo);

            for (Cart cart : carts) {
                track.move(cart);
                // FIRST COLLISION
                if (hasCollision(carts).isPresent()) {
                    break;
                }
            }
        }

        Pair<Integer, Integer> collision = hasCollision(carts).orElseThrow();
        System.out.println("Part A: " + collision.getValue1() + "," + collision.getValue0());
    }

    private static Optional<Pair<Integer, Integer>> hasCollision(List<Cart> carts) {
        Set<Pair<Integer, Integer>> set = new HashSet<>();
        for (Cart cart : carts) {
            Pair<Integer, Integer> position = Pair.with(cart.getRow(), cart.getColumn());
            if (!set.add(position)) {
                return Optional.of(position);
            }
        }
        return Optional.empty();
    }
}
