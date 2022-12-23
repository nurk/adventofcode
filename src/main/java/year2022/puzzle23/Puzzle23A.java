package year2022.puzzle23;

import org.javatuples.Pair;
import util.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Puzzle23A {

    //Part A 3812
    static Elf[][] board;
    static List<Pair<Movement, List<Movement>>> checks = List.of(
            Pair.with(Movement.N, List.of(Movement.N, Movement.NE, Movement.NW)),
            Pair.with(Movement.S, List.of(Movement.S, Movement.SE, Movement.SW)),
            Pair.with(Movement.W, List.of(Movement.W, Movement.NW, Movement.SW)),
            Pair.with(Movement.E, List.of(Movement.E, Movement.NE, Movement.SE)));

    static int round = 0;

    public static void main(String[] args) {
        List<String> input = Utils.getInput("2022/input23.txt");
        int rows = input.get(0).length();
        int cols = input.size();

        board = new Elf[rows * 3][cols * 3];

        for (int i = 0; i < input.size(); i++) {
            String[] line = input.get(i).split("");
            for (int j = 0; j < line.length; j++) {
                String s = line[j];
                if ("#".equals(s)) {
                    board[i + rows][j + cols] = new Elf(new Position(i + rows, j + cols));
                }
            }
        }

        do {
            doRound();
            round++;
        } while (round < 10);

        printPartA();
    }

    private static void printPartA() {
        Integer minRow = null;
        Integer maxRow = null;
        Integer minCol = null;
        Integer maxCol = null;
        for (int i = 0; i < board.length; i++) {
            if (Arrays.stream(board[i])
                    .anyMatch(Objects::nonNull)) {
                minRow = i;
                break;
            }
        }
        for (int i = board.length - 1; i >= 0; i--) {
            if (Arrays.stream(board[i])
                    .anyMatch(Objects::nonNull)) {
                maxRow = i;
                break;
            }
        }
        for (int j = 0; j < board[0].length; j++) {
            for (Elf[] elves : board) {
                if (elves[j] != null) {
                    minCol = j;
                    break;
                }
            }
            if (minCol != null) {
                break;
            }
        }
        for (int j = board[0].length - 1; j >= 0; j--) {
            for (Elf[] elves : board) {
                if (elves[j] != null) {
                    maxCol = j;
                    break;
                }
            }
            if (maxCol != null) {
                break;
            }
        }

        int counter = 0;
        for (int i = minRow; i <= maxRow; i++) {
            for (int j = minCol; j <= maxCol; j++) {
                if (board[i][j] == null) {
                    counter++;
                }
            }

        }

        System.out.println(counter);
    }

    static void doRound() {
        List<Elf> elves = Arrays.stream(board)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull).toList();

        elves.forEach(Elf::fillProposedPosition);
        elves.forEach(Elf::moveToProposedPosition);
    }

    static class Elf {
        Position currentPosition;
        Position proposedPosition;

        public Elf(Position currentPosition) {
            this.currentPosition = currentPosition;
        }

        void fillProposedPosition() {
            this.proposedPosition = null;
            boolean noElvesInArea = Arrays.stream(Movement.values())
                    .allMatch(m ->
                            board[m.rowFunction.apply(currentPosition.row)][m.colFunction.apply(currentPosition.col)] == null);

            if (noElvesInArea) {
                proposedPosition = null;
            } else {
                IntStream.range(0, checks.size())
                        .boxed()
                        .map(i -> (round + i) % checks.size())
                        .map(index -> checks.get(index))
                        .filter(check -> check.getValue1().stream()
                                .allMatch(m -> board[m.rowFunction.apply(currentPosition.row)][m.colFunction.apply(
                                        currentPosition.col)] == null))
                        .findFirst()
                        .map(Pair::getValue0)
                        .ifPresent(proposedMovement -> proposedPosition = new Position(proposedMovement.rowFunction.apply(
                                currentPosition.row),
                                proposedMovement.colFunction.apply(currentPosition.col)));
            }
        }

        void moveToProposedPosition() {
            if (this.proposedPosition != null) {
                boolean onlyOneToMoveToPosition = Arrays.stream(board)
                        .flatMap(Arrays::stream)
                        .filter(Objects::nonNull)
                        .filter(e -> !e.equals(this))
                        .filter(e -> e.proposedPosition != null)
                        .noneMatch(e -> e.proposedPosition.equals(this.proposedPosition));

                if (onlyOneToMoveToPosition) {
                    board[currentPosition.row][currentPosition.col] = null;
                    board[proposedPosition.row][proposedPosition.col] = this;
                    currentPosition = proposedPosition;
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Elf elf = (Elf) o;

            return currentPosition.equals(elf.currentPosition);
        }

        @Override
        public int hashCode() {
            return currentPosition.hashCode();
        }
    }

    record Position(int row, int col) {
    }

    enum Movement {
        N(i -> i - 1, j -> j),
        S(i -> i + 1, j -> j),
        E(i -> i, j -> j + 1),
        W(i -> i, j -> j - 1),
        NE(i -> i - 1, j -> j + 1),
        NW(i -> i - 1, j -> j - 1),
        SE(i -> i + 1, j -> j + 1),
        SW(i -> i + 1, j -> j - 1);


        final Function<Integer, Integer> rowFunction;
        final Function<Integer, Integer> colFunction;

        Movement(Function<Integer, Integer> rowFunction, Function<Integer, Integer> colFunction) {
            this.rowFunction = rowFunction;
            this.colFunction = colFunction;
        }
    }
}
