package year2022.puzzle23;

import org.javatuples.Pair;
import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Puzzle23B {

    //Part A 3812
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
            if (round == 10) {
                printPartA();
            }
        }

        System.out.println("Part B: " + (round + 1));
    }

    static void printPartA() {
        int minRow = elves.stream()
                .map(e -> e.currentPosition.row)
                .min(Integer::compareTo)
                .orElseThrow();

        int maxRow = elves.stream()
                .map(e -> e.currentPosition.row)
                .max(Integer::compareTo)
                .orElseThrow();

        int minCol = elves.stream()
                .map(e -> e.currentPosition.col)
                .min(Integer::compareTo)
                .orElseThrow();

        int maxCol = elves.stream()
                .map(e -> e.currentPosition.col)
                .max(Integer::compareTo)
                .orElseThrow();

        System.out.println("Part A: " + ((maxRow - minRow + 1) * (maxCol - minCol + 1) - elves.size()));
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
            boolean elvesInArea = Arrays.stream(Movement.values())
                    .anyMatch(m -> elves.contains(new Elf(m.move(currentPosition))));

            if (elvesInArea) {
                IntStream.range(0, checks.size())
                        .boxed()
                        .map(i -> (round + i) % checks.size())
                        .map(index -> checks.get(index))
                        .filter(check -> check.getValue1().stream()
                                .noneMatch(m -> elves.contains(new Elf(m.move(currentPosition)))))
                        .findFirst()
                        .map(Pair::getValue0)
                        .ifPresent(proposedMovement -> proposedPosition = proposedMovement.move(currentPosition));
            }
        }

        boolean moveToProposedPosition() {
            if (this.proposedPosition != null) {
                boolean onlyOneToMoveToPosition = elves.stream()
                        .filter(e -> !e.equals(this))
                        .filter(e -> e.proposedPosition != null)
                        .map(e -> e.proposedPosition)
                        .noneMatch(p -> p.equals(this.proposedPosition));

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


        private final Function<Integer, Integer> rowFunction;
        private final Function<Integer, Integer> colFunction;

        Movement(Function<Integer, Integer> rowFunction, Function<Integer, Integer> colFunction) {
            this.rowFunction = rowFunction;
            this.colFunction = colFunction;
        }

        Position move(Position in) {
            return new Position(rowFunction.apply(in.row), colFunction.apply(in.col));
        }
    }
}
