package year2022.puzzle14;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import util.Utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Puzzle14 {

    private static String[][] board;
    private static int sandStartingColumn;
    private static int bottom = 0;

    public static void main(String[] args) {
        partA();
        partB();
    }

    private static void partA() {
        initBoard();
        int droppedSand = 0;
        while (dropSand().getValue0()) {
            droppedSand++;
        }
        printBoard();
        System.out.println("PartA: " + droppedSand);
    }

    private static void partB() {
        initBoard();
        Arrays.fill(board[bottom + 2], "#");
        bottom = bottom + 2;

        int droppedSand = 0;
        Triplet<Boolean, Integer, Integer> dropResult;
        do {
            dropResult = dropSand();
            droppedSand++;
        } while (!(dropResult.getValue1() == 0 && dropResult.getValue2() == sandStartingColumn));
        printBoard();
        System.out.println("Part B: " + droppedSand);
    }

    private static Triplet<Boolean, Integer, Integer> dropSand() {
        boolean didAction = true;
        int row = 0;
        int col = sandStartingColumn;

        while (didAction && row <= bottom) {
            if (board[row + 1][col].equals(".")) {
                row++;
            } else if (board[row + 1][col - 1].equals(".")) {
                row++;
                col--;
            } else if (board[row + 1][col + 1].equals(".")) {
                row++;
                col++;
            } else {
                didAction = false;
            }
        }
        if (!didAction) {
            board[row][col] = "o";
            return Triplet.with(true, row, col);
        }
        return Triplet.with(false, row, col);
    }

    private static void initBoard() {
        //498,4 -> 498,6 -> 496,6
        //503,4 -> 502,4 -> 502,9 -> 494,9

        List<List<Pair<Integer, Integer>>> input = Utils.getInput("2022/input14.txt",
                s -> Arrays.stream(s.split(" -> "))
                        .map(p -> p.split(","))
                        .map(split -> Pair.with(Integer.parseInt(split[0]), Integer.parseInt(split[1])))
                        .toList());
        int columnsMin, columnsMax, rows;

        columnsMin = input.stream()
                .flatMap(Collection::stream)
                .map(Pair::getValue0)
                .min(Integer::compareTo)
                .orElse(0) - 160;

        columnsMax = input.stream()
                .flatMap(Collection::stream)
                .map(Pair::getValue0)
                .max(Integer::compareTo)
                .orElse(0) + 160;

        rows = input.stream()
                .flatMap(Collection::stream)
                .map(Pair::getValue1)
                .max(Integer::compareTo)
                .orElse(0) + 3;

        sandStartingColumn = 500 - columnsMin;
        board = new String[rows][columnsMax - columnsMin];
        Arrays.stream(board).forEach(arr -> Arrays.fill(arr, "."));

        input.forEach(lines -> {
            for (int i = 0; i < lines.size() - 1; i++) {
                Pair<Integer, Integer> start = lines.get(i);
                Pair<Integer, Integer> end = lines.get(i + 1);
                for (int r = Math.min(start.getValue1(), end.getValue1()); r <= Math.max(start.getValue1(),
                        end.getValue1()); r++) {
                    for (int c = Math.min(start.getValue0(),
                            end.getValue0()) - columnsMin; c <= Math.max(start.getValue0(),
                            end.getValue0()) - columnsMin; c++) {
                        if (r > bottom) {
                            bottom = r;
                        }
                        board[r][c] = "#";
                    }
                }
            }
        });
    }

    private static void printBoard() {
        System.out.println(Arrays.stream(board)
                .map(row -> Arrays.stream(row)
                        .map(String::valueOf)
                        .collect(Collectors.joining("")))
                .collect(Collectors.joining("\n")));
    }
}
