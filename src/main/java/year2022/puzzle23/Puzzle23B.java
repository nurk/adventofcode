package year2022.puzzle23;

import org.javatuples.Pair;
import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Puzzle23B {

    //Part B 1003
    static List<Elf> elves = new ArrayList<>();
    static List<Pair<Movement, List<Movement>>> checks = List.of(
            Pair.with(Movement.N, List.of(Movement.N, Movement.NE, Movement.NW)),
            Pair.with(Movement.S, List.of(Movement.S, Movement.SE, Movement.SW)),
            Pair.with(Movement.W, List.of(Movement.W, Movement.NW, Movement.SW)),
            Pair.with(Movement.E, List.of(Movement.E, Movement.NE, Movement.SE)));

    static int round = 0;

    public static void main(String[] args) {
        List<String> input = Utils.getInput("2022/input23.txt");
        for (int i = 0; i < input.size(); i++) {
            String[] line = input.get(i).split("");
            for (int j = 0; j < line.length; j++) {
                String s = line[j];
                if ("#".equals(s)) {
                    elves.add(new Elf(new Position(i, j)));
                }
            }
        }

        while (doRound()) {
            round++;
        }

        System.out.println(round + 1);
    }


    static boolean doRound() {
        boolean moved = false;
        elves.forEach(Elf::fillProposedPosition);
        for (Elf elf : elves) {
            if (elf.moveToProposedPosition()) {
                moved = true;
            }
        }

        return moved;
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
                    .noneMatch(m -> elves.contains(new Elf(new Position(m.rowFunction.apply(currentPosition.row),
                            m.colFunction.apply(currentPosition.col)))));

            if (noElvesInArea) {
                proposedPosition = null;
            } else {
                IntStream.range(0, checks.size())
                        .boxed()
                        .map(i -> (round + i) % checks.size())
                        .map(index -> checks.get(index))
                        .filter(check -> check.getValue1().stream()
                                .noneMatch(m -> elves.contains(new Elf(new Position(m.rowFunction.apply(currentPosition.row),
                                        m.colFunction.apply(currentPosition.col))))))
                        .findFirst()
                        .map(Pair::getValue0)
                        .ifPresent(proposedMovement -> proposedPosition = new Position(proposedMovement.rowFunction.apply(
                                currentPosition.row),
                                proposedMovement.colFunction.apply(currentPosition.col)));
            }
        }

        boolean moveToProposedPosition() {
            if (this.proposedPosition != null) {
                boolean onlyOneToMoveToPosition = elves.stream()
                        .filter(e -> !e.equals(this))
                        .filter(e -> e.proposedPosition != null)
                        .noneMatch(e -> e.proposedPosition.equals(this.proposedPosition));

                if (onlyOneToMoveToPosition) {
                    currentPosition = proposedPosition;
                    return true;
                }
            }
            return false;
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
