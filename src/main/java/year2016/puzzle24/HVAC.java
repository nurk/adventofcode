package year2016.puzzle24;

import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class HVAC {
    private final String[][] board;

    public HVAC(List<String> input) {
        board = new String[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> board[row][column] = columns.get(column));
                });
    }

    public long shortestPathLength(String start, String end) {
        Pair<Integer, Integer> startCoordinates = findCoordinates(start);
        Pair<Integer, Integer> endCoordinates = findCoordinates(end);
        return shortestPathLength(startCoordinates, endCoordinates);
    }

    private long shortestPathLength(Pair<Integer, Integer> startCoordinates, Pair<Integer, Integer> endCoordinates) {
        Deque<Pair<Integer, Integer>> queue = new ArrayDeque<>();
        queue.addLast(startCoordinates);

        Map<Pair<Integer, Integer>, Pair<Long, List<Pair<Integer, Integer>>>> costSoFar = new HashMap<>();
        costSoFar.put(startCoordinates, Pair.with(0L, List.of(startCoordinates)));

        while (!queue.isEmpty()) {
            Pair<Integer, Integer> current = queue.pop();

            if (current.equals(endCoordinates)) {
                break;
            }

            long currentCost = costSoFar.get(current).getValue0();
            long newCost = currentCost + 1;

            for (Pair<Integer, Integer> next : getTargets(current)) {
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next).getValue0()) {
                    List<Pair<Integer, Integer>> newList = new ArrayList<>(costSoFar.get(current).getValue1());
                    newList.add(next);
                    costSoFar.put(next, Pair.with(newCost, newList));
                    queue.addLast(next);
                }
            }

        }

        return costSoFar.getOrDefault(endCoordinates, Pair.with(Long.MAX_VALUE, List.of())).getValue0();
    }

    private List<Pair<Integer, Integer>> getTargets(Pair<Integer, Integer> current) {
        List<Pair<Integer, Integer>> targets = new ArrayList<>();
        if (isFreeSpace(Pair.with(current.getValue0() + 1, current.getValue1()))) {
            targets.add(Pair.with(current.getValue0() + 1, current.getValue1()));
        }
        if (isFreeSpace(Pair.with(current.getValue0() - 1, current.getValue1()))) {
            targets.add(Pair.with(current.getValue0() - 1, current.getValue1()));
        }
        if (isFreeSpace(Pair.with(current.getValue0(), current.getValue1() + 1))) {
            targets.add(Pair.with(current.getValue0(), current.getValue1() + 1));
        }
        if (isFreeSpace(Pair.with(current.getValue0(), current.getValue1() - 1))) {
            targets.add(Pair.with(current.getValue0(), current.getValue1() - 1));
        }
        return targets;
    }

    private boolean isFreeSpace(Pair<Integer, Integer> space) {
        try {
            if (board[space.getValue0()][space.getValue1()].equals(".") || StringUtils.isNumeric(board[space.getValue0()][space.getValue1()])) {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }

    private Pair<Integer, Integer> findCoordinates(String start) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].equals(start)) {
                    return Pair.with(i, j);
                }
            }
        }
        throw new IllegalArgumentException();
    }

    public List<String> getAllNumbers() {
        List<String> numbers = new ArrayList<>();
        for (String[] strings : board) {
            for (String string : strings) {
                if (StringUtils.isNumeric(string)) {
                    numbers.add(string);
                }
            }
        }
        return numbers;
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", points))
                .collect(joining("\n"));
    }
}
