package year2016.puzzle13;


import org.javatuples.Pair;

import java.util.*;
import java.util.function.Function;

public class Puzzle13 {

    static long favoriteNumber = 1358;
    static Set<Position> visited = new HashSet<>();

    public static void main(String[] args) {
        System.out.println(shortestPath(new Position(1, 1), new Position(31, 39)));
        System.out.println(visited.size());

    }

    record Position(long x, long y) {
        boolean isValidNeighbour() {
            if (!(x >= 0 && y >= 0)) {
                return false;
            }

            //x*x + 3*x + 2*x*y + y + y*y
            long formula = x * x + 3 * x + 2 * x * y + y + y * y + favoriteNumber;
            return Long.bitCount(formula) % 2 == 0;
        }
    }

    static Integer shortestPath(Position start, Position end) {
        //This is FIFO
        Deque<Position> queue = new ArrayDeque<>();
        queue.addLast(start);

        Map<Position, Pair<Integer, List<Position>>> costSoFar = new HashMap<>();

        costSoFar.put(start, Pair.with(0, List.of(start)));

        while (!queue.isEmpty()) {
            Position current = queue.pop();

            if (current.equals(end)) {
                break;
            }
            int currentCost = costSoFar.get(current).getValue0();
            if (currentCost <= 50) {
                visited.addAll(costSoFar.get(current).getValue1());
            }

            int newCost = currentCost + 1;

            for (Movement movement : Movement.values()) {
                Position newPos = new Position(movement.xFunction.apply(current.x),
                        movement.yFunction.apply(current.y));
                if (newPos.isValidNeighbour()) {
                    if (!costSoFar.containsKey(newPos) || newCost < costSoFar.get(newPos).getValue0()) {
                        List<Position> newList = new ArrayList<>(costSoFar.get(current).getValue1());
                        newList.add(newPos);
                        costSoFar.put(newPos, Pair.with(newCost, newList));
                        queue.addLast(newPos);
                    }
                }
            }
        }

        return costSoFar.getOrDefault(end, Pair.with(Integer.MAX_VALUE, List.of())).getValue0();
    }

    enum Movement {
        N(i -> i - 1, j -> j, "^"),
        S(i -> i + 1, j -> j, "v"),
        E(i -> i, j -> j + 1, ">"),
        W(i -> i, j -> j - 1, "<");


        final Function<Long, Long> xFunction;
        final Function<Long, Long> yFunction;
        final String symbol;

        Movement(Function<Long, Long> xFunction, Function<Long, Long> yFunction, String symbol) {
            this.xFunction = xFunction;
            this.yFunction = yFunction;
            this.symbol = symbol;
        }
    }
}
