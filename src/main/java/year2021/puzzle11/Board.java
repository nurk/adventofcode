package year2021.puzzle11;

import java.util.*;
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

    public int doStep() {
        Set<Octopus> flashMap = new HashSet<>();
        /*getOctopusStream()
                .map(Octopus::incrementLevel)
                .toList().stream()
                .peek(o -> buildFlashMap(o, flashMap))
                .toList()
                .forEach(Octopus::resetLevel);*/

        getOctopusStream()
                .forEach(Octopus::incrementLevel);

        getOctopusStream()
                .forEach(o -> buildFlashMap(o, flashMap));

        getOctopusStream()
                .forEach(Octopus::resetLevel);

        return flashMap.size();
    }

    private Stream<Octopus> getOctopusStream() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream);
    }

    private void buildFlashMap(Octopus octopus, Set<Octopus> flashMap) {
        if (octopus.flashing() && !flashMap.contains(octopus)) {
            flashMap.add(octopus);
            getAdjacentOctopi(octopus).stream()
                    .map(Octopus::incrementLevel)
                    .forEach(o -> buildFlashMap(o, flashMap));
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
