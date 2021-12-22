package year2021.puzzle22;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.stream.IntStream;

class BoardA {
    private static final int[][][] board = new int[101][101][101];

    public void doStep(String step) {
        String[] split = StringUtils.split(step, " ");

        int newState = split[0].equals("on") ? 1 : 0;

        String[] coords = StringUtils.split(split[1], ",");

        doStep(newState, getFromTo(coords[0]), getFromTo(coords[1]), getFromTo(coords[2]));

    }

    private void doStep(int newState,
                        Pair<Integer, Integer> xPair,
                        Pair<Integer, Integer> yPair,
                        Pair<Integer, Integer> zPair) {
        IntStream.rangeClosed(xPair.getLeft(), xPair.getRight())
                .map(x -> x + 50)
                .forEach(x -> IntStream.rangeClosed(yPair.getLeft(), yPair.getRight())
                        .map(y -> y + 50)
                        .forEach(y -> IntStream.rangeClosed(zPair.getLeft(), zPair.getRight())
                                .map(z -> z + 50)
                                .forEach(z -> {
                                    try {
                                        board[x][y][z] = newState;
                                    } catch (ArrayIndexOutOfBoundsException e) {

                                    }
                                })));

    }

    public long countOn() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .flatMapToInt(Arrays::stream)
                .filter(value -> value == 1)
                .count();

    }

    private Pair<Integer, Integer> getFromTo(String s) {
        String[] split = StringUtils.split(StringUtils.substringAfter(s, "="), "..");
        int a = Math.max(Integer.parseInt(split[0]), -51);
        int b = Math.min(Integer.parseInt(split[1]), 51);
        return Pair.of(a, b);
    }
}
