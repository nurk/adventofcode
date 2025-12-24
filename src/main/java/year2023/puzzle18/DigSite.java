package year2023.puzzle18;

import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.Arrays;

import static java.util.stream.Collectors.*;

public class DigSite {

    private final Pair<String, String>[][] board;

    private int row;
    private int col;

    private final Polygon polygon = new Polygon();

    public DigSite(int size) {
        //noinspection unchecked
        board = new Pair[size][size];
        row = size / 2;
        col = size / 2;

        Arrays.stream(board).forEach(r -> Arrays.fill(r, Pair.of(".", "")));
        board[row][col] = Pair.of("#", "");
        polygon.addPoint(col, row);
    }

    public void move(String move) {
        String[] parts = move.split(" ");
        String direction = parts[0];
        int distance = Integer.parseInt(parts[1]);
        String color = parts[2].substring(1, parts[2].length() - 1);

        for (int i = 0; i < distance; i++) {
            switch (direction) {
                case "U" -> row--;
                case "D" -> row++;
                case "L" -> col--;
                case "R" -> col++;
            }

            board[row][col] = Pair.of("#", color);
            polygon.addPoint(col, row);
        }
    }

    public void fillPolygon() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col].getLeft().equals(".") && polygon.contains(col, row)) {
                    board[row][col] = Pair.of("#", "filled");
                }
            }
        }
    }

    public int getArea() {
        int area = 0;
        for (Pair<String, String>[] pairs : board) {
            for (Pair<String, String> pair : pairs) {
                if (pair.getLeft().equals("#")) {
                    area++;
                }
            }
        }
        return area;
    }

    @Override
    public String toString() {
        return Arrays.stream(board)
                .map(points -> String.join("", Arrays.stream(points).map(Pair::getLeft).toList()))
                .collect(joining("\n"));
    }
}
