package year2024.puzzle10;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class BoardB {
    private final Integer[][] board;
    private final List<Pair<Integer, Integer>> starts = new ArrayList<>();
    private final List<Pair<Integer, Integer>> ends = new ArrayList<>();

    public BoardB(List<String> input) {
        board = new Integer[input.size()][input.getFirst().length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> board[row][column] = Integer.parseInt(columns.get(column)));
                });

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] == 0) {
                    starts.add(new Pair<>(row, column));
                }
                if (board[row][column] == 9) {
                    ends.add(new Pair<>(row, column));
                }
            }
        }
    }

    public long getTotalRating() {
        int sum = 0;
        for (Pair<Integer, Integer> start : starts) {
            sum += getRatings(start);
        }

        return sum;
    }

    private int getRatings(Pair<Integer, Integer> start) {
        int trails = 0;
        for (Pair<Integer, Integer> end : ends) {
            trails += getRating(start, end);
        }

        return trails;
    }

    private int getRating(Pair<Integer, Integer> start, Pair<Integer, Integer> currentPosition) {
        if (currentPosition.equals(start)) {
            return 1;
        }
        if (board[currentPosition.getValue0()][currentPosition.getValue1()] == 0) {
            return 0;
        }

        return Arrays.stream(Direction.values())
                .map(direction -> direction.getPosition(currentPosition))
                .filter(this::isValidPosition)
                .filter(newPosition -> board[currentPosition.getValue0()][currentPosition.getValue1()] - 1 == board[newPosition.getValue0()][newPosition.getValue1()])
                .map(newPosition -> getRating(start, newPosition))
                .mapToInt(Integer::valueOf)
                .sum();
    }

    private boolean isValidPosition(Pair<Integer, Integer> position) {
        return position.getValue0() >= 0 && position.getValue0() < board.length && position.getValue1() >= 0 && position.getValue1() < board[position.getValue0()].length;
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", Arrays.stream(points).map(String::valueOf).toList()))
                .collect(joining("\n"));
    }
}
