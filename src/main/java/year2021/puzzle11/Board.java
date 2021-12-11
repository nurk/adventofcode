package year2021.puzzle11;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

class Board {
    private final Octopus[][] board;

    public Board(List<String> input) {
        board = new Octopus[input.size()][input.get(0).length()];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, columns.size())
                            .forEach(column -> board[row][column] = new Octopus(Integer.parseInt(columns.get(column)),
                                    row,
                                    column));
                });
    }

    private List<Octopus> getAdjacentOctopi(Octopus octopus) {
        return Stream.of(
                        getOctopusAt(octopus.getRow() + 1, octopus.getCol()),
                        getOctopusAt(octopus.getRow() - 1, octopus.getCol()),
                        getOctopusAt(octopus.getRow(), octopus.getCol() + 1),
                        getOctopusAt(octopus.getRow(), octopus.getCol() - 1),
                        getOctopusAt(octopus.getRow() + 1, octopus.getCol() - 1),
                        getOctopusAt(octopus.getRow() - 1, octopus.getCol() - 1),
                        getOctopusAt(octopus.getRow() + 1, octopus.getCol() + 1),
                        getOctopusAt(octopus.getRow() - 1, octopus.getCol() + 1))
                .flatMap(Optional::stream)
                .toList();
    }

    private Optional<Octopus> getOctopusAt(int row,
                                           int col) {
        try {
            return Optional.ofNullable(board[row][col]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    public boolean isSynchronized() {
        return getOctopusStream()
                .allMatch(Octopus::flashed);
    }

    public long countOctopiThatFlashed() {
        return getOctopusStream()
                .filter(Octopus::flashed)
                .count();
    }

    public void doStep() {
        getOctopusStream()
                .forEach(Octopus::incrementLevel);

        getOctopusStream()
                .forEach(this::doFlashes);
    }

    private Stream<Octopus> getOctopusStream() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream);
    }

    private void doFlashes(Octopus octopus) {
        if (octopus.flashing()) {
            octopus.resetLevel();
            getAdjacentOctopi(octopus).stream()
                    .filter(Predicate.not(Octopus::flashed))
                    .map(Octopus::incrementLevel)
                    .forEach(this::doFlashes);
        }
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> Arrays.stream(points)
                        .map(Octopus::toString)
                        .collect(joining("")))
                .collect(joining("\n"));
    }
}
